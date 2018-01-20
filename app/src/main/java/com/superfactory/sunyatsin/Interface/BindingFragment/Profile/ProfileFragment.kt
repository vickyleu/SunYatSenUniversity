package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import com.superfactory.library.Context.BaseToolbarFragment

/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:40:24
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragment : BaseToolbarFragment<ProfileFragmentViewModel, ProfileFragment>() {

    override fun newViewModel() = ProfileFragmentViewModel()

    override fun newComponent(v: ProfileFragmentViewModel) = ProfileFragmentComponent(v)

}