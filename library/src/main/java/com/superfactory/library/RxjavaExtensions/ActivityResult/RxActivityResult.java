/*
 * Copyright 2016 Víctor Albertos
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
import android.app.Application;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


public final class RxActivityResult {
    static ActivitiesLifecycleCallbacks activitiesLifecycle;

    private RxActivityResult() {
    }

    public static void register(final Application application) {
        activitiesLifecycle = new ActivitiesLifecycleCallbacks(application);
    }

    public static <T extends Activity> Builder<T> on(T activity) {
        return new Builder<T>(activity);
    }

    public static <T extends Fragment> Builder<T> on(T fragment) {
        return new Builder<T>(fragment);
    }

    public static class Builder<T> {
        final Class clazz;
        final PublishSubject<Result<T>> subject = PublishSubject.create();
        private final boolean uiTargetActivity;

        public Builder(T t) {
            if (activitiesLifecycle == null) {
                throw new IllegalStateException(Locale.RX_ACTIVITY_RESULT_NOT_REGISTER);
            }

            this.clazz = t.getClass();
            this.uiTargetActivity = t instanceof Activity;
        }

        public Observable<Result<T>> startIntentSender(IntentSender intentSender, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) {
            return startIntentSender(intentSender, fillInIntent, flagsMask, flagsValues, extraFlags, null);
        }

        public Observable<Result<T>> startIntentSender(IntentSender intentSender, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) {
            RequestIntentSender requestIntentSender = new RequestIntentSender(intentSender, fillInIntent, flagsMask, flagsValues, extraFlags, options);
            return startHolderActivity(requestIntentSender, null);
        }

        public Observable<Result<T>> startIntent(final Intent intent) {
            return startIntent(intent, null);
        }

        public Observable<Result<T>> startIntent(final Intent intent, @Nullable OnPreResult onPreResult) {
            return startHolderActivity(new Request(intent), onPreResult);
        }

        private Observable<Result<T>> startHolderActivity(Request request, @Nullable OnPreResult onPreResult) {

            OnResult onResult = uiTargetActivity ? onResultActivity() : onResultFragment();
            request.setOnResult(onResult);
            request.setOnPreResult(onPreResult);

            HolderActivity.setRequest(request);

            activitiesLifecycle.getOLiveActivity().subscribe(activity -> activity.startActivity(new Intent(activity, HolderActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)));

            return subject;
        }

        private OnResult onResultActivity() {
            return new OnResult() {
                @Override
                public void response(int requestCode, int resultCode, Intent data) {
                    if (activitiesLifecycle.getLiveActivity() == null) return;

                    //If true it means some other activity has been stacked as a secondary process.
                    //Wait until the current activity be the target activity
                    if (activitiesLifecycle.getLiveActivity().getClass() != clazz) {
                        return;
                    }

                    T activity = (T) activitiesLifecycle.getLiveActivity();
                    subject.onNext(new Result<>(activity, requestCode, resultCode, data));
                    subject.onComplete();
                }

                @Override
                public void error(Throwable throwable) {
                    subject.onError(throwable);
                }
            };
        }

        private OnResult onResultFragment() {
            return new OnResult() {
                @Override
                public void response(int requestCode, int resultCode, Intent data) {
                    if (activitiesLifecycle.getLiveActivity() == null) return;

                    Activity activity = activitiesLifecycle.getLiveActivity();

                    FragmentActivity fragmentActivity = (FragmentActivity) activity;
                    FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();

                    Fragment targetFragment = getTargetFragment(fragmentManager.getFragments());

                    if (targetFragment != null) {
                        subject.onNext(new Result<>((T) targetFragment, requestCode, resultCode, data));
                        subject.onComplete();
                    }

                    //If code reaches this point it means some other activity has been stacked as a secondary process.
                    //Do nothing until the current activity be the target activity to get the associated fragment
                }

                @Override
                public void error(Throwable throwable) {
                    subject.onError(throwable);
                }
            };
        }

        @Nullable
        Fragment getTargetFragment(List<Fragment> fragments) {
            if (fragments == null) return null;

            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible() && fragment.getClass() == clazz) {
                    return fragment;
                } else if (fragment != null && fragment.isAdded() && fragment.getChildFragmentManager() != null) {
                    List<Fragment> childFragments = fragment.getChildFragmentManager().getFragments();
                    Fragment candidate = getTargetFragment(childFragments);
                    if (candidate != null) return candidate;
                }
            }

            return null;
        }
    }
}
