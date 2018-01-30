package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.WindowManager
import com.superfactory.library.Context.BaseActivity
import com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity.MainActivity
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<LoginActivityViewModel, LoginActivity>() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun newViewModel() = LoginActivityViewModel()

    override fun newComponent(v: LoginActivityViewModel) = LoginActivityComponent(v).apply {
        viewModelSafe.ownerNotifier = { _, any ->
            val bundle = Bundle()
            bundle.putParcelable("data", any as? Parcelable)
            startActivity<MainActivity>()
            finish()
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
