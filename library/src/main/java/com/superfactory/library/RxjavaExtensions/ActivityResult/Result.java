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

import android.content.Intent;

public class Result<T> {
    private final T targetUI;
    private final int resultCode;
    private final int requestCode;
    private final Intent data;

    Result(T targetUI, int requestCode, int resultCode, Intent data) {
        this.targetUI = targetUI;
        this.resultCode = resultCode;
        this.requestCode = requestCode;
        this.data = data;
    }

    public int requestCode() {
        return requestCode;
    }

    public int resultCode() {
        return resultCode;
    }

    public Intent data() {
        return data;
    }

    public T targetUI() {
        return targetUI;
    }
}
