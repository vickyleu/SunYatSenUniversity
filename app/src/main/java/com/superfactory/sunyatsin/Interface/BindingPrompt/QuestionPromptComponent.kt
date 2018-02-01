package com.superfactory.sunyatsin.Interface.BindingPrompt

import android.graphics.Color
import android.widget.ImageView
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by vicky on 2018/2/1.
 */
class QuestionPromptComponent(viewModel: QuestionPromptViewModel) : BindingComponent<QuestionPrompt, QuestionPromptViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<QuestionPrompt>) = with(ui) {
        relativeLayout {

            val iv = imageView {
                id = R.id.image
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
                imageResource = viewModelSafe.headerImage
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentTop()
                topMargin = dip(10)
                centerHorizontally()
            }

            textView {
                text = "提交成功"
            }.lparams {
                addRule(RelativeLayout.RIGHT_OF, iv.id)
                alignParentTop()
                centerHorizontally()
            }

            imageView {
                backgroundColor = Color.TRANSPARENT
                scaleType = ImageView.ScaleType.FIT_XY
                imageResource = viewModelSafe.centreImage
            }.lparams {
                addRule(RelativeLayout.BELOW, iv.id)
                topMargin = dip(15)
                centerHorizontally()
            }

            button {
                text="知道了"
                onClick {
                    owner.dismiss()
                }
            }

            lparams {
                width = matchParent
                height = wrapContent
            }
        }
    }
}