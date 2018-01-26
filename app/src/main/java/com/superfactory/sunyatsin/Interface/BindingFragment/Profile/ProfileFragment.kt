package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.os.Bundle
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.library.Debuger
import com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity.MainActivity
import org.jetbrains.anko.support.v4.startActivity


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:40:24
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragment : BaseToolbarFragment<ProfileFragmentViewModel, ProfileFragment>() {

    override fun newViewModel(): ProfileFragmentViewModel {
        val model = ProfileFragmentViewModel()
        return model
    }

    override fun newComponent(v: ProfileFragmentViewModel) = ProfileFragmentComponent(v)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onLoadedModel(viewModel: ProfileFragmentViewModel) {
        viewModel.onItemClicked = { idx, model ->

            when (idx) {
                -1/*"头像"*/ -> {
                    startActivity<MainActivity>()
                }
                0/*"警号"*/ -> {
                    startActivity<MainActivity>()
                }
                1/*"部门"*/ -> {
                    startActivity<MainActivity>()
                }
                2/*"岗位"*/ -> {
                    startActivity<MainActivity>()
                }
                3/*"职务"*/ -> {
                    startActivity<MainActivity>()
                }
                4/*"问卷"*/ -> {
                    startActivity<MainActivity>()
                }
                5/*"设置"*/ -> {
                    startActivity<MainActivity>()
                }
            }
        }
        Debuger.printMsg(this, "onCreate  1  " + viewModel)
        Debuger.printMsg(this, "onCreate  2  " + viewModel.onItemClicked)
    }

}


