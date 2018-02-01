package com.superfactory.library.Graphics.KDialog.params

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.DialogFragment

/**
 * Created by hupei on 2017/3/30.
 */

open class CircleParams : Parcelable {
    var dialogFragment: DialogFragment? = null
    var dialogParams: DialogParams? = null
    var titleParams: TitleParams? = null
    var textParams: TextParams? = null
    var negativeParams: ButtonParams? = null
    var positiveParams: ButtonParams? = null

    var positiveInterrupt:Any?=null
    var negativeInterrupt:Any?=null

    var itemsParams: ItemsParams? = null
    var progressParams: ProgressParams? = null
    var inputParams: InputParams? = null

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(this.dialogParams, flags)
        dest.writeParcelable(this.titleParams, flags)
        dest.writeParcelable(this.textParams, flags)
        dest.writeParcelable(this.negativeParams, flags)
        dest.writeParcelable(this.positiveParams, flags)
        dest.writeParcelable(this.itemsParams, flags)
        dest.writeParcelable(this.progressParams, flags)
        dest.writeParcelable(this.inputParams, flags)
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        this.dialogParams = `in`.readParcelable(DialogParams::class.java.classLoader)
        this.titleParams = `in`.readParcelable(TitleParams::class.java.classLoader)
        this.textParams = `in`.readParcelable(TextParams::class.java.classLoader)
        this.negativeParams = `in`.readParcelable(ButtonParams::class.java.classLoader)
        this.positiveParams = `in`.readParcelable(ButtonParams::class.java.classLoader)
        this.itemsParams = `in`.readParcelable(ItemsParams::class.java.classLoader)
        this.progressParams = `in`.readParcelable(ProgressParams::class.java.classLoader)
        this.inputParams = `in`.readParcelable(InputParams::class.java.classLoader)
    }

    companion object {

        val CREATOR: Parcelable.Creator<CircleParams> = object : Parcelable.Creator<CircleParams> {
            override fun createFromParcel(source: Parcel): CircleParams {
                return CircleParams(source)
            }

            override fun newArray(size: Int): Array<CircleParams?> {
                return arrayOfNulls(size)
            }
        }
    }
}
