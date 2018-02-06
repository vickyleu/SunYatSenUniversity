package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireDetailActivity

import android.view.View
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Context.BaseToolBarActivity

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  16:27:29
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireDetailActivity : BaseToolBarActivity<QuestionnaireDetailActivityViewModel, QuestionnaireDetailActivity>() {
    override fun newViewModel() = QuestionnaireDetailActivityViewModel(intent)
    override fun newComponent(v: QuestionnaireDetailActivityViewModel) = QuestionnaireDetailActivityComponent(v).apply {
        viewModel?.ownerNotifier = { i, any ->
            //            CircleDialog.Builder(this@QuestionnaireDetailActivity)

            finish()
        }
    }

    override fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        super.performToolbarClickEvent(view, event)
        when (event) {
//    todo 接口定义有问题,应该为      [


// todo String params ：[{"questionId":"ss","optionId":"aa","score":"1","remark":""}] ； String parentId 问卷编号
//
//
//
//
// ]
//            [String params ：[{"questionId":"ss","optionId":"aa","score":"1","remark":""}] ； String parentId 问卷编号]
            BaseToolBar.Companion.ToolbarEvent.RIGHT -> {

            }
        }
    }
}