package com.superfactory.sunyatsin.Interface.BindingActivity.AccountActivity

import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Context.BaseToolBarActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.CompellationActivity.CompellationActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.GenderActivity.GenderActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:55:06
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivity : BaseToolBarActivity<AccountActivityViewModel, AccountActivity>() {
    override fun newViewModel() =AccountActivityViewModel()

    override fun newComponent(v: AccountActivityViewModel)=AccountActivityComponent(v)

    override fun onLoadedModel(viewModel: AccountActivityViewModel) {
        viewModel.onItemClicked = { idx, model ->
            when (idx) {
                0/*头像*/ -> {
                    startActivityForResult<AccountActivity>(101,{
                        intent ->

                    })
                }
                1/*姓名*/ -> {
                    startActivityForResult<CompellationActivity>(101,{
                        intent ->

                    })
                }
                2/*性别*/ -> {
                    startActivityForResult<GenderActivity>(101,{
                        intent ->

                    })
                }
            }
        }
    }

}