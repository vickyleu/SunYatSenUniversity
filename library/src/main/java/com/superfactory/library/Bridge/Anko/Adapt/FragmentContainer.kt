package com.superfactory.library.Bridge.Anko.Adapt

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

class FragmentContainer {
    var manager: FragmentManager? = null
    var fragment: Fragment? = null

    constructor() {}

    constructor(manager: FragmentManager, fragment: Fragment) {
        this.manager = manager
        this.fragment = fragment
    }
}
