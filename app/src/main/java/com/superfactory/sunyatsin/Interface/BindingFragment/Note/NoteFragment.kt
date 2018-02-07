package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import android.os.Bundle
import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Debuger
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsin.Bean.NoteListBean
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.Interface.BindingActivity.MessageActivity.MessageActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.NoteByDayActivity.NoteByDayActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.NoteDetailOrAddActivity.NoteDetailOrAddActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.Struct.Const
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:54:32
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragment : BaseToolbarFragment<NoteFragmentViewModel, NoteFragment>() {

    override fun newViewModel() = NoteFragmentViewModel()

    override fun newComponent(v: NoteFragmentViewModel) = NoteFragmentComponent(v).apply {
        viewModelSafe.ownerNotifier = { option, any ->
            if (any == null) {
                Debuger.printMsg(this, "数据不能为空啊")
            } else {
                if (option == 101) {
                    viewModelSafe.updateItemList(any as NoteStruct)
                } else if (option == 103) {
                    startActivityForResult<NoteByDayActivity>(1001, Pair("data", any))
                } else if (option == 102) {
                    startActivity<MessageActivity>(Pair("data", any))
                } else if (option == 104) {
                    startActivityForResult<NoteByDayActivity>(1001, Pair("data", any))
                } else
                    startActivityForResult<QuestionnaireActivity>(1001, Pair("data", any))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.currDate?.value = TimeUtil.takeNowTime("yyyy年MM月dd日")
    }

    override fun onLoadedModel(viewModel: NoteFragmentViewModel) {
        viewModel.onItemClicked = { i, model ->
            when (i) {
                -1 -> {
                    startActivityForResult<NoteDetailOrAddActivity>(1001, {
                        takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                                context!!, Const.SignInInfo, Const.SignInSession, "")
                                ?: "", true, NoteListBean(TimeUtil.takeNowTime("yyyy-MM-dd")
                                ?: ""))?.senderAsync(NoteStruct::class, binder!!, context!!,witch = 2)
                    })
                }
                else -> {

                }
            }
        }
    }

}