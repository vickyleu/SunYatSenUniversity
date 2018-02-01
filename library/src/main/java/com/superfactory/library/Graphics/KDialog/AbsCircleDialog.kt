package com.superfactory.library.Graphics.KDialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.superfactory.library.Graphics.KDialog.params.CircleParams


/**
 * Created by hupei on 2017/3/29.
 */

class AbsCircleDialog : BaseCircleDialog() {
    override fun getInputText(): String? {
        return mController?.getInputText()
    }

     override fun getInputView(): View? {
        return mController?.getInputView()
    }

    private var mParams: CircleParams? = null
    private var mController: Controller? = null

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        mParams = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mParams = savedInstanceState.getParcelable(SAVED_PARAMS)
        }
        val dialogParams = mParams!!.dialogParams
        setGravity(dialogParams!!.gravity)
        setCanceledOnTouchOutside(dialogParams.canceledOnTouchOutside)
        setCanceledBack(dialogParams.cancelable)
        setWidth(dialogParams.width)
        val mPadding = dialogParams.mPadding
        if (mPadding != null)
            setPadding(mPadding[0], mPadding[1], mPadding[2], mPadding[3])
        setAnimations(dialogParams.animStyle)
        setDimEnabled(dialogParams.isDimEnabled)
        setBackgroundColor(dialogParams.backgroundColor)
        setRadius(dialogParams.radius)
        setAlpha(dialogParams.alpha)
        setX(dialogParams.xOff)
        setY(dialogParams.yOff)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putParcelable(SAVED_PARAMS, mParams)
    }

    override fun createView(context: Context, inflater: LayoutInflater?, container: ViewGroup?): View? {
        mController = Controller(getContext(), mParams!!)
        mParams!!.dialogFragment = this
        return mController!!.createView()
    }

    fun refreshView() {
        mController!!.refreshView()
    }

    companion object {
        private val SAVED_PARAMS = "circle:params"

        fun newAbsCircleDialog(params: CircleParams): AbsCircleDialog {
            val circleDialog = AbsCircleDialog()
            circleDialog.mParams = params
            val bundle = Bundle()
            bundle.putParcelable(SAVED_PARAMS, params)
            circleDialog.arguments = bundle
            return circleDialog
        }
    }
}
