package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.R
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:57:26
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragmentComponent(viewModel: NoteFragmentViewModel) : BindingComponent<NoteFragment, NoteFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<NoteFragment>): View = with(ui) {
        refresh {
            backgroundColor = Color.WHITE
            setEnableRefresh(true)
            setEnableLoadmore(false)
            Debuger.printMsg(this, "生成view没有")
            verticalLayout {
                backgroundColor = Color.RED
                lparams {
                    width = matchParent
                    height = matchParent
                }
                onClick {
                    doAsync {
                        ui.owner.setTitle("1122111")
//                    ui.owner.setBackIcon(R.mipmap.ic_launcher)
                        ui.owner.setBackTextSize(16)
                        ui.owner.setBackTextColor(Color.WHITE)
                         ui.owner.setRightTextSize(16)
                        ui.owner.setRightTextColor(Color.WHITE)


                        ui.owner.setBackgroundColor(Color.parseColor("#222222"))

//                        val left=TextView(ctx)
//                        left.text="jbok返回"
//                        left.textSize=14f
//                        ui.owner.setBackView(left)
                        ui.owner.setBackIcon("123")
                        ui.owner.setRightIcon("返回ojbk")
                    }
//                    viewModelSafe.notifyChange()

                }
            }
            lparams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

}