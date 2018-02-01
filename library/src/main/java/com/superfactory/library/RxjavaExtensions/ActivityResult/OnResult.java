package com.superfactory.library.RxjavaExtensions.ActivityResult;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.Serializable;

interface OnResult extends Serializable {
    void response(int requestCode, int resultCode, @Nullable Intent data);
    void error(Throwable throwable);
}
