package com.superfactory.sunyatsen.Interface.BindingActivity.LoginActivity

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Debuger
import com.superfactory.sunyatsen.Interface.BindingActivity.MainActivity.MainActivity
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<LoginActivityViewModel, LoginActivity>() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun newViewModel() = LoginActivityViewModel()

    override fun newComponent(v: LoginActivityViewModel) = LoginActivityComponent(v).apply {
        viewModelSafe.ownerNotifier = { _, any ->
            if (any == null) {
                Debuger.printMsg(this, "数据不能为空啊")
            } else {
                Debuger.printMsg("simpleName","987654311212121"+any.javaClass.simpleName)
                startActivity<MainActivity>(Pair("data",any))
                finish()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LoginActivityComponent.REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binder?.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
            }
        }
    }

}
