package com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity

import android.graphics.Color
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.cleanUpEditText
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.toObservable
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  20:07:45
 * @ClassName 这里输入你的类名(或用途)
 */
class CompellationActivityComponent(viewModel: CompellationActivityViewModel) :
        BindingComponent<CompellationActivity, CompellationActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<CompellationActivity>) = with(ui) {
        refresh {
            backgroundColor = Color.parseColor("#f8f8f8")

            cleanUpEditText {
                backgroundResource = R.drawable.profile_recycle_shader
                bindSelf(this).toObservable { it.input }
                singleLine = true
                maxLength = 10
            }.lparams {
                width = matchParent
                height = wrapContent
            }

            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

}