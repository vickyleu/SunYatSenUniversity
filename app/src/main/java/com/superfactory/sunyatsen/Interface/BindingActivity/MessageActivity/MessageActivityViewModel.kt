package com.superfactory.sunyatsen.Interface.BindingActivity.MessageActivity

import android.content.Intent
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.Base.BaseBody
import com.superfactory.sunyatsen.Struct.BaseStructImpl
import com.superfactory.sunyatsen.Struct.Message.MessageStruct
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * Created by vicky on 2018.02.03.
 *
 * @Author vicky
 * @Date 2018年02月03日  21:09:46
 * @ClassName 这里输入你的类名(或用途)
 */
open class MessageActivityViewModel(val intent: Intent) : ToolbarBindingModel() {
    override fun setToolbar(toolbarBindingModel: ToolbarBindingModel) {
        toolbarBindingModel.title.value = "消息"
        toolbarBindingModel.rightTextColor.value = Color.WHITE
        toolbarBindingModel.rightText.value = "清空"
        toolbarBindingModel.backgroundColor.value = Color.parseColor("#1688ff")
        val ctx = getStaticsContextRef()
        toolbarBindingModel.leftIcon.value = ContextCompat.getDrawable(ctx, R.drawable.back_stack_icon)
    }

    val itemList = observable<ArrayList<MessageItemView>>(arrayListOf<MessageItemView>())

    init {
        val struct = intent.extras.getParcelable<MessageStruct>("data")
        itemList.value.clear()
        struct.body.rows.forEachWithIndex { i, row ->
            itemList.value.add(i, MessageItemView(row.title, row.content, TimeUtil.compareNowTime("yyyy-MM-dd HH:mm:ss", row.createDate)))
        }
    }

    val tips = observable("")
    override fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        if (model == null) {
            ld.close()
            tips.value = "无法解析数据"
            return
        }
        if (model is BaseStructImpl) {
            if (model.success) {
                itemList.value.clear()
                ld.close()
                ownerNotifier?.invoke(0, model)
            } else {
                ld.close()
                tips.value = model.msg ?: "未知错误"
            }
        }
    }

    override fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.close()
        if (!TextUtils.isEmpty(error?.message)) {
            tips.value = error?.message!!
        }
    }

}

data class MessageItemView(val title: String, val content: String, val date: String,
                           val image: Int? = R.drawable.message_notification_icon) : BaseBody() {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(date)
        parcel.writeValue(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageItemView> {
        override fun createFromParcel(parcel: Parcel): MessageItemView {
            return MessageItemView(parcel)
        }

        override fun newArray(size: Int): Array<MessageItemView?> {
            return arrayOfNulls(size)
        }
    }
}