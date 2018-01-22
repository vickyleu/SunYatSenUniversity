package com.superfactory.library.Bridge.Anko.ViewExtensions

import android.widget.CompoundButton


fun CompoundButton.setCheckedIfNecessary(checked: Boolean?) {
    if (checked != null && isChecked != checked) {
        isChecked = checked
    }
}