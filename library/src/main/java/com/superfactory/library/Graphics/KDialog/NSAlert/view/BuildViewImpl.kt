package com.superfactory.library.Graphics.KDialog.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.superfactory.library.Graphics.KDialog.NSAlert.BuildView
import com.superfactory.library.Graphics.KDialog.params.CircleParams


/**
 * Created by hupei on 2017/3/29.
 */

class BuildViewImpl(private val mContext: Context, private val mParams: CircleParams) : BuildView {
    override fun getInputText(): String? {
        return mBodyInputView?.input?.text?.toString()
    }
    override  fun getInputView(): View? {
        return mBodyInputView
    }

    private var mRoot: LinearLayout? = null
    private var mTitleView: TitleView? = null
    private var mBodyTextView: BodyTextView? = null
    private var mBodyItemsView: BodyItemsView<*>? = null
    private var mBodyProgressView: BodyProgressView? = null
    private var mBodyInputView: BodyInputView? = null
    private var mItemsButton: ItemsButton? = null
    private var mMultipleButton: MultipleButton? = null
    private var mSingleButton: SingleButton? = null

    override fun buildRoot() {
        if (mRoot == null) {
            mRoot = ScaleLinearLayout(mContext)
            mRoot!!.orientation = LinearLayout.VERTICAL
        }
    }

    override fun buildTitle() {
        if (mTitleView == null) {
            mTitleView = TitleView(mContext, mParams)
            mRoot!!.addView(mTitleView)
        }
    }

    override fun buildText() {
        if (mBodyTextView == null) {
            mBodyTextView = BodyTextView(mContext, mParams)
            mRoot!!.addView(mBodyTextView)
        }
    }

    override fun refreshText() {
        if (mBodyTextView != null) mBodyTextView!!.refreshText()
    }

    override fun buildItems() {
        if (mBodyItemsView == null) {
            mBodyItemsView = BodyItemsView<Any>(mContext, mParams)
            mRoot!!.addView(mBodyItemsView)
        }
    }

    override fun buildItemsButton() {
        if (mItemsButton == null) {
            mItemsButton = ItemsButton(mContext, mParams)
            mRoot!!.addView(mItemsButton)
        }
    }

    override fun refreshItems() {
        if (mBodyItemsView != null) mBodyItemsView!!.refreshItems()
    }

    override fun buildProgress() {
        if (mBodyProgressView == null) {
            mBodyProgressView = BodyProgressView(mContext, mParams)
            mRoot!!.addView(mBodyProgressView)
        }
    }


    override fun refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView!!.refreshProgress()
    }

    override fun buildInput() {
        if (mBodyInputView == null) {
            mBodyInputView = BodyInputView(mContext, mParams)
            mRoot!!.addView(mBodyInputView)
        }
    }

    override fun buildMultipleButton() {
        if (mMultipleButton == null) {
            mMultipleButton = MultipleButton(mContext, mParams)
            val dividerView = DividerView(mContext)
            dividerView.setVertical()
            mRoot!!.addView(dividerView)
            mRoot!!.addView(mMultipleButton)
        }
    }

    override fun buildSingleButton() {
        if (mSingleButton == null) {
            mSingleButton = SingleButton(mContext, mParams)
            val dividerViewV = DividerView(mContext)
            dividerViewV.setVertical()
            mRoot!!.addView(dividerViewV)
            mRoot!!.addView(mSingleButton)
        }
    }

    override fun regInputListener() {
        if (mSingleButton != null && mBodyInputView != null)
            mSingleButton!!.regOnInputClickListener(mBodyInputView!!.input!!)

        if (mMultipleButton != null && mBodyInputView != null)
            mMultipleButton!!.regOnInputClickListener(mBodyInputView!!.input!!)
    }

    override fun getView(): View? {
        return mRoot
    }

}
