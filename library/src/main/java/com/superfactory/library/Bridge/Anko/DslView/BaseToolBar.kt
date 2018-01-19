package com.superfactory.library.Bridge.Anko.DslView

import android.os.Build
import android.support.v7.widget.Toolbar
import android.view.View
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarColor
import com.superfactory.library.Bridge.Anko.BindingExtensions.getActionBarSize
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Context.BaseActivity
import com.superfactory.library.Context.BaseFragment
import com.superfactory.library.R
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.*


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  15:57:43
 * @ClassName 这里输入你的类名(或用途)
 */
open class BaseToolBar<V, A>(model: V) : BindingComponent<A, V>(model) {
    override fun createViewWithBindings(ui: AnkoContext<A>): View = with(ui) {
        themedToolbar(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
            id = R.id.toolbar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupTheme = R.style.ThemeOverlay_AppCompat_Light
            }
            lparams {
                width = matchParent
                height = wrapContent
                topPadding = StatusBarUtil.getStatusBarHeight(ctx)
                backgroundColor = getActionBarColor(ctx)
                minimumHeight = getActionBarSize(ctx)
            }

            if (viewModel!=null&&viewModel is ToolbarBindingModel){
//                bindSelf()
            }
        }
    }

    fun <V, A : BaseActivity<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        owner.setSupportActionBar(toolbar);
    }

    fun <V, A : BaseFragment<V, A>> initToolbar(owner: A, toolbar: Toolbar) {
        (owner.activity as BaseActivity<*, *>).setSupportActionBar(toolbar);
    }

    fun setToolbarTitle(title: String, toolbar: Toolbar): Unit {
        toolbar.title = title
//
//
//        // App Logo
//        toolbar.setLogo(R.drawable.ic_launcher);
//// Title
//        toolbar.setTitle("My Title");
//// Sub Title
//        toolbar.setSubtitle("Sub title");

//        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
//        //否則會出現 back button
//        toolbar.setNavigationIcon(R.drawable.ab_android)

    }


}
