package com.superfactory.library.Bridge.Anko.viewextensions

import android.widget.ProgressBar

fun ProgressBar.setProgressIfNecessary(progress: Int?) {
    if (this.progress != progress) {
        this.progress = progress ?: 0
    }
}