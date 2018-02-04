package com.superfactory.library.Context

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.superfactory.library.Bridge.Anko.Adapt.BaseAnko
import com.superfactory.library.Bridge.Anko.Adapt.BaseToolBar
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.observable
import com.superfactory.library.Bridge.Model.ToolbarBindingModel
import com.superfactory.library.Debuger
import com.superfactory.library.R
import com.superfactory.library.Utils.StatusBarUtil
import org.jetbrains.anko.AnkoContextImpl
import org.jetbrains.anko.topPadding


/**
 * Created by vicky on 2018.01.18.
 *
 * @Author vicky
 * @Date 2018年01月18日  11:11:39
 * @ClassName 这里输入你的类名(或用途)
 */
abstract class BaseFragment<V : Parcelable, A : BaseFragment<V, A>> : Fragment(), BaseAnko<V, A> {
    private val TAG = javaClass.simpleName
    protected var toolbar: BaseToolBar<A, V>? = null
    protected var toolbarAnko: View? = null

    private var layout: BindingComponent<A, V>? = null
    var viewModel: V? = null

    protected open var binder: BindingComponent<*, *>? = null
        get() = layout
    open var bar: View? = null
        get() = toolbarAnko

    protected var showToolBar: Boolean = false

    open var extra: Bundle? = null
        get() = arguments?.getBundle(FragmentContainer.TAG)

    private val toolbarClickEvent = observable(View.OnClickListener {
        if (it != null) {
            var event: BaseToolBar.Companion.ToolbarEvent? = null
            when (it.id) {
                R.id.toolbar_left -> {
                    event = BaseToolBar.Companion.ToolbarEvent.LEFT
                }
                R.id.toolbar_right -> {
                    event = BaseToolBar.Companion.ToolbarEvent.RIGHT
                }

                else -> {
                    event = BaseToolBar.Companion.ToolbarEvent.NONE
                }
            }
            performToolbarClickEvent(it, event)
        }
    })

    protected open fun performToolbarClickEvent(view: View, event: BaseToolBar.Companion.ToolbarEvent) {
        when (view.id) {
            R.id.toolbar_left -> {

            }
            R.id.toolbar_right -> {

            }
        }

    }


    override fun onLoadedModel(viewModel: V) {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
//        if (savedInstanceState!=null){
//            viewModel=savedInstanceState.getParcelable(TAG)
//        }else {
        if (viewModel == null) {
            viewModel = newViewModel()
        } else {
            Debuger.printMsg(this, TAG)
        }
        if (viewModel == null) return null
        viewModel!!.apply {
            if (showToolBar) {
                val abc = this as ToolbarBindingModel
//                Debuger.printMsg(this, abc.rightIcon.value?.toString() ?: "空么")
                val tc = newToolBarComponent(this)
                if (tc != null) {
                    toolbar = tc.apply {
                        toolbarAnko = createView(AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false))
                        notifyChanges()
                    } as BaseToolBar<A, V>
                    toolbar!!.eventDelegate = toolbarClickEvent
                }
            }
            layout = newComponent(this).apply {
                if (toolbarAnko != null) {
//                    Debuger.printMsg(this, "妈卖批")
                    view = createView(
                            AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false),
                            toolbarAnko,
                            this@BaseFragment.context,
                            this@BaseFragment
                    )
                    if (toolbarAnko is Toolbar && toolbar != null) {
                        toolbar!!.initToolbar(this@BaseFragment, toolbarAnko!! as Toolbar)
                        setToolbarAttribution(
                                toolbar!!,
                                toolbar!!.getActionBar(this@BaseFragment),
                                toolbarAnko!! as Toolbar)
                    }
                } else {
                    view = createView(AnkoContextImpl(this@BaseFragment.context, this@BaseFragment as A, false))
                    if (ifNeedTopPadding()) {
                        openTopPadding()
                    }
                }
                try {
                    notifyChanges()
                } catch (e: Exception) {
                }

            }
        }

        if (viewModel != null) {
            onLoadedModel(viewModel!!)
        }
        if (view == null) {
            throw RuntimeException(javaClass.simpleName + "创建view为空")
        }
        return view;
    }

    protected open fun ifNeedTopPadding(): Boolean {
        return true
    }

    private fun openTopPadding() {
        view?.topPadding = StatusBarUtil.getStatusBarHeight(view?.context!!)
    }

    protected open fun setToolbarAttribution(toolbarBinder: BaseToolBar<A, V>, actionBar: ActionBar?, toolbarView: Toolbar) {

    }

    open fun newToolBarComponent(v: V): BindingComponent<A, V>? {
        return null
    }

    override fun ankoToolBar(viewModel: V): BaseToolBar<V, A>? {
        return null
    }

    abstract fun newViewModel(): V

    abstract fun newComponent(v: V): BindingComponent<A, V>

    override fun onDestroy() {
        super.onDestroy()
        layout?.destroyView()
        layout = null
    }

    var behavior: CoordinatorLayout.Behavior<*>? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (behavior != null)
            return
        val layout = this.view
        if (layout == null || !(layout is ViewGroup)) return
        if (layout.layoutParams != null && layout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = layout.layoutParams as CoordinatorLayout.LayoutParams
            behavior = params.behavior
            params.behavior = null
        }
    }
    /* override fun onAttach(activity: Activity?) {
         super.onAttach(activity)
         if (behavior != null)
             return
         val layout = this.view
         if (layout==null)return
         val params = layout.layoutParams as CoordinatorLayout.LayoutParams
         behavior = params.behavior
         params.behavior = null
     }*/

    override fun onDetach() {
        super.onDetach()
//        if (showToolBar){
//            (activity as AppCompatActivity).setSupportActionBar(null)
//        }
        if (behavior == null)
            return
        val layout = this.view
        if (layout == null || !(layout is ViewGroup)) return
        if (layout.layoutParams != null && layout.layoutParams is CoordinatorLayout.LayoutParams) {
            val params = layout.layoutParams as CoordinatorLayout.LayoutParams
            params.behavior = behavior
            layout.layoutParams = params
            behavior = null
//            layout.removeView()
        }
    }

    override fun onStop() {
        try {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onStop()
    }


    private var savedState: Bundle? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState);
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time running, Initialize something here
            onFirstTimeLaunched()
        }
    }

    private fun onFirstTimeLaunched() {

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState);
        // Save State Here
        saveStateToArguments();
    }


    override fun onDestroyView() {
        super.onDestroyView()
        destroyModel(viewModel)
        // Save State Here
        saveStateToArguments()
    }

    protected open fun destroyModel(viewModel: V?) {

    }

    private fun saveStateToArguments() {
        if (view != null)
            savedState = saveState()
        if (savedState != null) {
            val b = arguments ?: return
            b.putBundle("internalSavedViewState" + javaClass.hashCode(), savedState)
        }
    }

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments ?: return false
        savedState = b.getBundle("internalSavedViewState" + javaClass.hashCode())
        if (savedState != null) {
            restoreState()
            return true;
        }
        return false;
    }


    /////////////////////////////////
// 取出状态数据
/////////////////////////////////
    private fun restoreState() {
        if (savedState != null) {
            viewModel = savedState?.getParcelable(TAG)
            if (viewModel != null && viewModel is ToolbarBindingModel) {
                //todo
            }
            Debuger.printMsg(this, "savedState:" + viewModel?.toString() ?: "null")
            onRestoreState(savedState!!)
        }
    }

    private fun onRestoreState(savedState: Bundle) {

    }

    //////////////////////////////
// 保存状态数据
//////////////////////////////
    private fun saveState(): Bundle {
        val state = Bundle()
        if (viewModel != null)
            state.putParcelable(TAG, viewModel)
        return state
    }

}