package com.superfactory.library.Graphics.KDialog.NSAlert

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.superfactory.library.Graphics.KDialog.params.CircleParams
import com.superfactory.library.Graphics.KDialog.view.BuildViewImpl


/**
 * Created by hupei on 2017/3/29.
 */

class Controller(private val mContext: Context, private val mParams: CircleParams) {
    private val mCreateView: BuildView

    private val view: View?
        get() = mCreateView.getView()

    init {
        mCreateView = BuildViewImpl(mContext, mParams)
    }

    fun getInputText(): String? {
        return mCreateView.getInputText()
    }

    fun getInputView(): View? {
        return mCreateView.getInputView()
    }

    fun createView(): View? {
        applyRoot()
        applyHeader()
        applyBody()
        return view
    }

    fun refreshView() {
        mCreateView.refreshText()
        mCreateView.refreshItems()
        mCreateView.refreshProgress()
        //刷新时带动画
        if (mParams.dialogParams?.refreshAnimation !== 0 && view != null)
            view!!.post {
                val animation = AnimationUtils.loadAnimation(mContext, mParams
                        .dialogParams
                        ?.refreshAnimation ?: 0)
                if (animation != null) view!!.startAnimation(animation)
            }
    }

    private fun applyRoot() {
        mCreateView.buildRoot()
    }

    private fun applyHeader() {
        if (mParams.titleParams != null)
            mCreateView.buildTitle()
    }

    private fun applyBody() {
        //文本
        if (mParams.textParams != null) {
            mCreateView.buildText()
            applyButton()
        } else if (mParams.itemsParams != null) {
            mCreateView.buildItems()
            //有确定或者有取消按钮
            if (mParams.positiveParams != null || mParams.negativeParams != null)
                mCreateView.buildItemsButton()
        } else if (mParams.progressParams != null) {
            mCreateView.buildProgress()
            applyButton()
        } else if (mParams.inputParams != null) {
            mCreateView.buildInput()
            applyButton()
            mCreateView.regInputListener()
        }//输入框
        //进度条
        //列表
    }

    private fun applyButton() {
        //有确定并且有取消按钮
        if (mParams.positiveParams != null && mParams.negativeParams != null)
            mCreateView.buildMultipleButton()
        else if (mParams.positiveParams != null || mParams.negativeParams != null)
            mCreateView.buildSingleButton()//有确定或者有取消按钮
    }
}
