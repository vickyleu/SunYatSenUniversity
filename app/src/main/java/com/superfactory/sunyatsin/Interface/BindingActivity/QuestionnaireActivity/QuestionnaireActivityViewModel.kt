package com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.Struct.QuestionaireStruct.QuestionnaireDetailStruct
import com.superfactory.sunyatsin.Struct.QuestionaireStruct.QuestionnaireStruct
import com.superfactory.sunyatsin.Struct.QuestionaireStruct.Row
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * Created by vicky on 2018.02.01.
 *
 * @Author vicky
 * @Date 2018年02月01日  13:51:01
 * @ClassName 这里输入你的类名(或用途)
 */
class QuestionnaireActivityViewModel(intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "问卷"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

    override fun requestSuccess(ld: LoadingDialog, model: Any?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is QuestionnaireDetailStruct) {
            if (model.success) {
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    val tips = observable("")


    val list = observable(arrayListOf<QuestionnaireItem>())

    var onItemClicked: ((Int, QuestionnaireActivityViewModel) -> Unit)? = null


    init {
        val struct = intent.extras.getParcelable<QuestionnaireStruct>("data")
        if (struct != null) {
            val rows = struct.body.rows
            for (i in 0 until rows.size) {
                val row = rows.get(i)
                list.value.add(QuestionnaireItem(row.title, TimeUtil.compareNowTime("yyyy-MM-dd HH:mm:ss", row.createDate),
                        row,
                        haveToSee = if (row.status == 0) true else false))
            }
            list.notifyChange()
        }
    }
}

data class QuestionnaireItem(val msg: String, val date: String, val row: Row, val leftIcon: Int = R.drawable.questionnaire_small_icon, var haveToSee: Boolean = false)