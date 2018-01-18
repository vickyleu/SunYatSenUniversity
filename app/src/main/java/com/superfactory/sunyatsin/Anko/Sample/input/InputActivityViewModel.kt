package com.superfactory.sunyatsin.Anko.Sample.input

import com.superfactory.library.Bridge.Anko.BaseObservable
import com.superfactory.library.Bridge.Anko.observable


/**
 * Description:
 */
class InputActivityViewModel : BaseObservable() {

    val firstName = "Andrew"

    val lastName: String by observable("Grosner")

    val formInput = observable("")

    val oneWaySourceInput = observable("")

    val selected = observable(true)

    var normalField = ""
        set(value) {
            field = value
            notifyChange(this::normalField)
        }

    fun onFirstNameClick() {

    }

    fun onLastNameClick() {

    }
}