package com.superfactory.sunyatsen.Interface.BindingActivity.MainActivity


import com.superfactory.library.Bridge.Adapt.startActivityForResult
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.library.Utils.TimeUtil
import com.superfactory.sunyatsen.Bean.NoteListBean
import com.superfactory.sunyatsen.Communication.RetrofitImpl
import com.superfactory.sunyatsen.Interface.BindingActivity.NoteDetailOrAddActivity.NoteDetailOrAddActivity
import com.superfactory.sunyatsen.Interface.BindingFragment.Note.NoteFragment
import com.superfactory.sunyatsen.Struct.Const
import com.superfactory.sunyatsen.Struct.Note.NoteStruct

class MainActivity : BaseActivity<MainActivityViewModel, MainActivity>() {

    override fun ifNeedTopPadding(): Boolean {
        return false
    }

    override fun newViewModel() = MainActivityViewModel(intent, supportFragmentManager)


    override fun newComponent(v: MainActivityViewModel) = MainActivityComponent(v).apply {
        viewModel?.ownerNotifier = { i, any ->
            startActivityForResult<NoteDetailOrAddActivity>(1001, {
                if (it==null)return@startActivityForResult
                try {
                    val fragment = viewModelSafe.fragments?.value?.fragment as? NoteFragment
                    fragment?.takeApi(RetrofitImpl::class)?.queryNoteList(ConfigXmlAccessor.restoreValue(
                            this@MainActivity, Const.SignInInfo, Const.SignInSession, "")
                            ?: "", true, NoteListBean(TimeUtil.takeNowTime("yyyy-MM-dd")
                            ?: ""))?.senderAsync(NoteStruct::class, fragment!!.binder!!, this@MainActivity,witch = 2)
                }catch (e:Exception){}
            }, Pair("data", any))

        }
    }


    override fun onBackPressed() {
//        super.onBackPressed()
    }

}
