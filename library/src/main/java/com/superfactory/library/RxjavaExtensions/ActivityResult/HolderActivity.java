/*
 * Copyright 2016 Copyright 2016 Víctor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.superfactory.library.RxjavaExtensions.ActivityResult;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import io.reactivex.functions.Action;

public class HolderActivity extends Activity {
    private static Request request;
    private OnPreResult onPreResult;
    private OnResult onResult;
    private int resultCode;
    private int requestCode;
    private Intent data;
    private static int FAILED_REQUEST_CODE = -909;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (request == null) {
            finish();
            return;
        }

        onPreResult = request.onPreResult();
        onResult = request.onResult();

        if (savedInstanceState != null) return;

        if (request instanceof RequestIntentSender) {
            RequestIntentSender requestIntentSender = (RequestIntentSender) request;

            if (requestIntentSender.getOptions() == null) startIntentSender(requestIntentSender);
            else startIntentSenderWithOptions(requestIntentSender);
        } else {
            try {
                startActivityForResult(request.intent(), 0);
            } catch (ActivityNotFoundException e) {
                if (onResult != null) {
                    onResult.error(e);
                }
            }
        }
    }

    private void startIntentSender(RequestIntentSender requestIntentSender) {
        try {
            startIntentSenderForResult(requestIntentSender.getIntentSender(), 0,
                    requestIntentSender.getFillInIntent(), requestIntentSender.getFlagsMask(),
                    requestIntentSender.getFlagsValues(), requestIntentSender.getExtraFlags());
        } catch (IntentSender.SendIntentException exception) {
            exception.printStackTrace();
            onResult.response(FAILED_REQUEST_CODE, RESULT_CANCELED, null);
        }
    }

    private void startIntentSenderWithOptions(RequestIntentSender requestIntentSender) {
        try {
            startIntentSenderForResult(requestIntentSender.getIntentSender(), 0,
                    requestIntentSender.getFillInIntent(), requestIntentSender.getFlagsMask(),
                    requestIntentSender.getFlagsValues(), requestIntentSender.getExtraFlags(), requestIntentSender.getOptions());
        } catch (IntentSender.SendIntentException exception) {
            exception.printStackTrace();
            onResult.response(FAILED_REQUEST_CODE, RESULT_CANCELED, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.resultCode = resultCode;
        this.requestCode = requestCode;
        this.data = data;

        if (this.onPreResult != null) {
            this.onPreResult.response(requestCode, resultCode, data)
                    .doOnComplete(new Action() {
                        @Override
                        public void run() throws Exception {
                            finish();
                        }
                    })
                    .subscribe();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onResult != null)
            onResult.response(requestCode, resultCode, data);
    }

    static void setRequest(Request aRequest) {
        request = aRequest;
    }
}
