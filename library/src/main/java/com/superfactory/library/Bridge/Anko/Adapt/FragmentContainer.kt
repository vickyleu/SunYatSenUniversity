package com.superfactory.library.Bridge.Anko.Adapt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.superfactory.library.Debuger

class FragmentContainer {
    companion object {
        val TAG = javaClass.name
    }
    var manager: FragmentManager? = null
    var fragment: Fragment? = null
        get() = field
        set(value) {
            field = value
            if (fragment?.arguments == null && extras != null) {
                val arg = Bundle()
                arg.putBundle(TAG, extras)
                fragment?.arguments = arg
                Debuger.printMsg(this, "arg assignment")
            }
        }

    var extras: Bundle? = null

    constructor() {}

    constructor(manager: FragmentManager, fragment: Fragment) {
        this.manager = manager
        this.fragment = fragment
    }
}
