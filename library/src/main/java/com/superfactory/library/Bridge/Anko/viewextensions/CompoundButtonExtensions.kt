package com.superfactory.library.Bridge.Anko.viewextensions

import android.widget.CompoundButton


fun CompoundButton.setCheckedIfNecessary(checked: Boolean?) {
    if (checked != null && isChecked != checked) {
        isChecked = checked
    }
}