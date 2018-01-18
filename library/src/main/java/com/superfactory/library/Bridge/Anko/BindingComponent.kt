package com.superfactory.library.Bridge.Anko

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.*
import com.superfactory.library.Bridge.Anko.bindings.bind
import com.superfactory.library.Bridge.Anko.bindings.onSelf
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseApp
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.*
import java.util.*

fun <T, Data> BindingComponent<T, Data>.bind(v: FrameLayout) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: View) = register.bind(v)


fun <T, Data> BindingComponent<T, Data>.bind(v: TextView) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: CompoundButton) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = register.bind(v, initialValue)
fun <T, Data> BindingComponent<T, Data>.bind(v: TimePicker) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: RatingBar) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: SeekBar) = register.bind(v)

fun <T, Data> BindingComponent<T, Data>.bindSelf(v: FrameLayout) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: View) = bind(v).onSelf()


fun <T, Data> BindingComponent<T, Data>.bindSelf(v: TextView) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: CompoundButton) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = bind(v, initialValue).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: TimePicker) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: RatingBar) = bind(v).onSelf()
fun <T, Data> BindingComponent<T, Data>.bindSelf(v: SeekBar) = bind(v).onSelf()

abstract class BindingComponent<in T, V>
(viewModel: V? = null, val register: BindingRegister<V> = BindingHolder(viewModel)
) : AnkoComponent<T>, BindingRegister<V> by register {


    override var viewModel: V?
        set(value) {
            register.viewModel = value
        }
        get() {
            return register.viewModel
        }

    override var isBound: Boolean
        get() = register.isBound
        set(value) {
            register.isBound = value
        }

    override final fun createView(ui: AnkoContext<T>) = createViewWithBindings(ui).apply { register.bindAll() }

    abstract fun createViewWithBindings(ui: AnkoContext<T>): View

    fun destroyView() = register.unbindAll()

    open fun getAppNoStatusBarSize(ctx: Context): ScreenSizeExtension {
        val noStatusSize = ScreenSizeExtension()
        val height = StatusBarUtil.getStatusBarHeight(ctx)
        val screenSizeExtension = getAppOverSize(ctx)
        noStatusSize.width = screenSizeExtension.width
        noStatusSize.height = screenSizeExtension.height - height
        noStatusSize.density = screenSizeExtension.density
        noStatusSize.densityDpi = screenSizeExtension.densityDpi
        return noStatusSize;
    }

    open fun getAppOverSize(ctx: Context?): ScreenSizeExtension {
        if (ctx == null) {
            return ScreenSizeExtension()
        }
        var appCtx: BaseApp? = null
        if (ctx is Application) {
            appCtx = ctx as BaseApp?
        } else {
            appCtx = ctx.applicationContext as BaseApp?
        }
        if (appCtx == null) {
            return ScreenSizeExtension()
        }
        return appCtx.mScreenSizeExtension;
    }

    inline fun dipValue(value: Int, ctx: Context): Int = ctx.dip(value)
    inline fun getModel(): V = viewModel!!
    inline fun supportFragmentManager(ui: AnkoContext<T>): FragmentManager = (ui.owner as BaseActivity<*, *>).getSupportFragmentManager()
    inline fun fragmentManager(ui: AnkoContext<T>): android.app.FragmentManager = (ui.owner as BaseActivity<*, *>).getFragmentManager()

}