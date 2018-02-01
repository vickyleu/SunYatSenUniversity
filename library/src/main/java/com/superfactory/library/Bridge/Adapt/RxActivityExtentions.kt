package com.superfactory.library.Bridge.Adapt

import android.app.Activity
import android.content.Intent
import com.superfactory.library.RxjavaExtensions.ActivityResult.RxActivityResult
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  19:28:46
 * @ClassName 这里输入你的类名(或用途)
 */
inline fun <reified T : Activity> Activity.startActivityForResult(requestCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, vararg params: Pair<String, Any?>) = {
    RxActivityResult.on(this).startIntent(AnkoInternals.createIntent(this, T::class.java, params))
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    // Do Something
                }
                if (resultCode == Activity.RESULT_OK) {
                    result.targetUI().apply {
                        fun0(data)
                    }
                } else {
                    result.targetUI().apply {
                        fun0(null)
                    }
                }
            })
}
