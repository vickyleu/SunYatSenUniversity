package com.superfactory.sunyatsin.Interface.BindingFragment.Note

import com.superfactory.library.Context.BaseFragment
import com.superfactory.library.Context.BaseToolbarFragment

/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:54:32
 * @ClassName 这里输入你的类名(或用途)
 */
class NoteFragment : BaseToolbarFragment<NoteFragmentViewModel, NoteFragment>() {

    override fun newViewModel() = NoteFragmentViewModel()

    override fun newComponent(v: NoteFragmentViewModel) = NoteFragmentComponent(v)

}