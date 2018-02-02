package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import com.superfactory.library.Context.BaseFragment
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.Interface.BindingActivity.QuestionnaireActivity.QuestionnaireActivity
import com.superfactory.sunyatsin.Struct.Note.NoteStruct
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
                if (option==101){
                    viewModelSafe.updateItemList(any as NoteStruct)
                }else
                startActivityForResult<QuestionnaireActivity>(1001, Pair("data", any))
            }
        }
    }

}