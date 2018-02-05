package com.superfactory.library.Bridge.Adapt

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.superfactory.library.RxjavaExtensions.ActivityResult.RxActivityResult
import org.jetbrains.anko.internals.AnkoInternals
import org.jetbrains.anko.startActivity

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  19:28:46
 * @ClassName 这里输入你的类名(或用途)
 */
inline fun <reified T : Activity> Fragment.startActivityForResult(requestCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, vararg params: Pair<String, Any?>) {
    if (this.activity == null) return
    RxActivityResult.on(this).startIntent(AnkoInternals.createIntent(this.activity!!, T::class.java, params))
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == Activity.RESULT_OK) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }
            })
}

inline fun <reified T : Activity> Fragment.startActivity(params: Pair<String, Any?>) {
    if (this.activity == null) return
    activity?.startActivity<T>(params)
}


inline fun Fragment.startActivityForResult(requestCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, intent: Intent? = null) {
    RxActivityResult.on(this).startIntent(intent)
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == Activity.RESULT_OK) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                    return@subscribe
                }
            })
}


inline fun <reified T : Activity> Activity.startActivityForResult(requestCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, vararg params: Pair<String, Any?>) {
    RxActivityResult.on(this).startIntent(AnkoInternals.createIntent(this, T::class.java, params))
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == Activity.RESULT_OK) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }

            })
}

inline fun Activity.startActivityForResult(requestCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, intent: Intent? = null) {
    RxActivityResult.on(this).startIntent(intent)
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == Activity.RESULT_OK) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }

            })
}


inline fun <reified T : Activity> Fragment.startActivityForResult(requestCodeOrigin: Int, resultCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, vararg params: Pair<String, Any?>) {
    if (this.activity == null) return
    RxActivityResult.on(this).startIntent(AnkoInternals.createIntent(this.activity!!, T::class.java, params))
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == resultCodeOrigin) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }
            })
}

inline fun Fragment.startActivityForResult(requestCodeOrigin: Int, resultCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, intent: Intent? = null) {
    RxActivityResult.on(this).startIntent(intent)
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == resultCodeOrigin) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                    return@subscribe
                }
            })
}


inline fun <reified T : Activity> Activity.startActivityForResult(requestCodeOrigin: Int, resultCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, vararg params: Pair<String, Any?>) {
    RxActivityResult.on(this).startIntent(AnkoInternals.createIntent(this, T::class.java, params))
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == resultCodeOrigin) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }

            })
}

inline fun Activity.startActivityForResult(requestCodeOrigin: Int, resultCodeOrigin: Int, crossinline fun0: (Intent?) -> Unit, intent: Intent? = null) {
    RxActivityResult.on(this).startIntent(intent)
            .subscribe({ result ->
                val data = result.data()
                val resultCode = result.resultCode()
                // the requestCode using which the activity is started can be received here.
                val requestCode = result.requestCode()
                if (requestCode == requestCodeOrigin) {
                    if (resultCode == resultCodeOrigin) {
                        result.targetUI().apply {
                            fun0(data)
                        }
                    } else {
                        result.targetUI().apply {
                            fun0(null)
                        }
                    }
                }

            })
}

















