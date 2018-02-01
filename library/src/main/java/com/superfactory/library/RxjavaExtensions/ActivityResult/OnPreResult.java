package com.superfactory.library.RxjavaExtensions.ActivityResult;

import android.content.Intent;
import android.support.annotation.Nullable;
import io.reactivex.Observable;

public interface OnPreResult<T> {
    Observable<T> response(int requestCode, int resultCode, @Nullable Intent data);
}
