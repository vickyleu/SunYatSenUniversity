package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.os.Bundle
import com.superfactory.library.Context.BaseToolbarFragment
import com.superfactory.sunyatsin.Anko.Sample.calendar.CalendarActivity
import com.superfactory.sunyatsin.Anko.Sample.input.InputActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity.MainActivity
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.onItemClicked = { _, (idx,_,_,_) ->
            when (idx) {
                0/*"警号"*/->{
                    startActivity<MainActivity>()
                }
                1/*"部门"*/->{
                    startActivity<MainActivity>()
                }
                2/*"岗位"*/->{
                    startActivity<MainActivity>()
                }
                3/*"职务"*/->{
                    startActivity<MainActivity>()
                }
                4/*"问卷"*/->{
                    startActivity<MainActivity>()
                }
                5/*"设置"*/->{
                    startActivity<MainActivity>()
                }
            }
        }
    }

}