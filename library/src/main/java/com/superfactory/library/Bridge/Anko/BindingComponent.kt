package com.superfactory.library.Bridge.Anko

import android.content.Context
import android.view.View
import android.widget.*
import com.superfactory.library.Bridge.Anko.bindings.bind
import com.superfactory.library.Bridge.Anko.bindings.onSelf
import org.jetbrains.anko.*
import java.util.*

fun <T, Data> BindingComponent<T, Data>.bind(v: View, any: View.OnClickListener?) = register.bind(v, any)


fun <T, Data> BindingComponent<T, Data>.bind(v: TextView) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: CompoundButton) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: DatePicker, initialValue: Calendar = Calendar.getInstance()) = register.bind(v, initialValue)
fun <T, Data> BindingComponent<T, Data>.bind(v: TimePicker) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: RatingBar) = register.bind(v)
fun <T, Data> BindingComponent<T, Data>.bind(v: SeekBar) = register.bind(v)


fun <T, Data> BindingComponent<T, Data>.bindSelf(v: View, any: View.OnClickListener?) = bind(v, any).onSelf()

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

    final fun createView(ui: AnkoContext<T>, toolbar: View?, ctx: Context, owner: T): View {
        return if (toolbar == null) {
            createView(ui)
        } else {
            with(ui) {
                verticalLayout {
                    toolbar.lparams {

                    }
                    val attach = createViewWithBindings(AnkoContextImpl(ctx, owner, false)).lparams {

                    }
                    addView(toolbar)
                    addView(attach)
                    lparams {
                        width = matchParent
                        height = matchParent
                    }
                }

//                constraintLayout {
//                    // app:layout_constraintBottom_toBottomOf="parent"
//                    // app:layout_constraintLeft_toLeftOf="parent"
//                    // app:layout_constraintRight_toRightOf="parent"
//                    constraintSet {
//
//                    }
//
//                }
            }.apply { register.bindAll() }
        }
    }


    abstract fun createViewWithBindings(ui: AnkoContext<T>): View

    fun destroyView() = register.unbindAll()


}