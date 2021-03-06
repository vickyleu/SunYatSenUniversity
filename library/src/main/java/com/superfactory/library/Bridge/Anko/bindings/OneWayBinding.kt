package com.superfactory.library.Bridge.Anko.bindings

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.superfactory.library.Bridge.Anko.Adapt.FragmentContainer
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.ViewExtensions.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.doAsyncResult
import java.util.*

typealias BindingExpression<Input, Output> = (Input) -> Output

interface Binding {

    fun notifyValueChange()

    fun bind()

    fun unbind()
}

internal val mainHandler = Handler(Looper.getMainLooper())

/**
 * Allows both the [Input] and [Output] of the function to be null.
 */
infix fun <Data, Input, Output, TBinding : BindingConverter<Data, Input>>
        TBinding.onNullable(expression: BindingExpression<Input?, Output?>) = OneWayExpression(this, expression)

/**
 * Runs the [expression] only if the [Input] is not null, otherwise returns null.
 */
inline fun <Data, Input, Output, TBinding : BindingConverter<Data, Input>>
        TBinding.on(crossinline expression: BindingExpression<Input, Output>,
                    crossinline nullExpression: () -> Output?) = OneWayExpression(this) {
    if (it != null) expression(it) else nullExpression()
}

inline fun <Data, Input, Output, TBinding : BindingConverter<Data, Input>>
        TBinding.on(crossinline expression: BindingExpression<Input, Output>) = on(expression) { null }

/**
 * Builds an expression that flips itself as the Output of a Boolean. If value is null, we do not
 * reverse it.
 */
fun <Data, TBinding : BindingConverter<Data, Boolean>> TBinding.reverse() = OneWayExpression(this, { if (it != null) !it else null })

/**
 * Builds an expression that passes the [Input] directly into the [OneWayExpression].
 */
fun <Data, Input, TBinding : BindingConverter<Data, Input>> TBinding.onSelf() = OneWayExpression(this, { it })

/**
 * Builds an expression that returns true if the object is null.
 */
fun <Data, Input, TBinding : BindingConverter<Data, Input>> TBinding.onIsNull() = OneWayExpression(this, { it == null })

/**
 * Builds an expression that returns true if the object is not null.
 */
fun <Data, Input, TBinding : BindingConverter<Data, Input>> TBinding.onIsNotNull() = OneWayExpression(this, { it != null })

/**
 * Builds an expression on a [CharSequence] that returns true if [isNullOrEmpty].
 */
fun <Data, TChar : CharSequence?, TBinding : BindingConverter<Data, TChar>> TBinding.onIsNullOrEmpty() = OneWayExpression(this, { it.isNullOrEmpty() })

/**
 * Builds an expression on a [CharSequence] that returns true if not [isNullOrEmpty].
 */
fun <Data, TChar : CharSequence?, TBinding : BindingConverter<Data, TChar>> TBinding.onIsNotNullOrEmpty() = OneWayExpression(this, { !it.isNullOrEmpty() })

class OneWayExpression<Data, Input, Output, Converter : BindingConverter<Data, Input>>(
        val converter: Converter, val expression: BindingExpression<Input?, Output?>) {


    fun <V : View> toView(view: V, viewExpression: (V, Output?) -> Unit) = OneWayBinding<Data, Input, Output, Converter, V>(this).toView(view, viewExpression)

    //todo total
}


class OneWayBinding<Data, Input, Output, Converter : BindingConverter<Data, Input>, V : View>
internal constructor(val oneWayExpression: OneWayExpression<Data, Input, Output, Converter>,
                     val converter: Converter = oneWayExpression.converter) : Binding {

    var viewExpression: ((V, Output?) -> Unit)? = null
    var view: V? = null

    private var deferredUI: Deferred<Unit>? = null

    fun evaluateBinding() = oneWayExpression.expression(converter.convertValue(converter.component.viewModel))

    @Suppress("UNCHECKED_CAST")
    fun toView(view: V, viewExpression: ((V, Output?) -> Unit)) = apply {
        this.viewExpression = viewExpression
        this.view = view
        converter.component.registerBinding(this)
    }

    fun toAny(viewExpression: ((V, Output?) -> Unit)) = apply {
        this.viewExpression = viewExpression
        converter.component.registerBinding(this)
    }

    override fun bind() {
        notifyValueChange()
        converter.bind(this)
    }

    override fun unbind() {
        unbindInternal()
        converter.component.unregisterBinding(this)
    }

    internal fun unbindInternal() {
        converter.unbind(this)
    }

    /**
     * Reruns binding expressions to views.
     */
    override fun notifyValueChange() {
        viewExpression?.let { viewExpression ->
            this@OneWayBinding.view?.let { view ->
                deferredUI?.cancel()
                deferredUI = async(UI) {
                    val value = doAsyncResult { evaluateBinding() }.get()
                    viewExpression(view, value)
                }
            }
        }
    }
}

/**
 * Immediately binds the [View] to the value of this binding. Toggles visibility based on [Int] returned
 * in previous expressions. The [Int] aligns with [View] visibility ints.
 */
infix fun <Input, TBinding : BindingConverter<*, Input>>
        OneWayExpression<*, Input, Int, TBinding>.toViewVisibility(textView: View) = toView(textView, View::setVisibilityIfNeeded)


//todo

/**
 * Immediately binds the [FrameLayout] to the value of this binding. have no idea to handle what......
 */
infix fun <Data, Input, TBinding : BindingConverter<Data, Input>, Output : FragmentContainer?>
        OneWayExpression<Data, Input, Output, TBinding>.toFragment(view: FrameLayout) = toView(view, FrameLayout::setFragmentPosition)

/**
 * Immediately binds the [TextView] to the value of this binding. Subsequent changes are handled by
 * the kind of object it is.
 */
infix fun <Data, Input, TBinding : BindingConverter<Data, Input>, TChar : CharSequence?>
        OneWayExpression<Data, Input, TChar, TBinding>.toText(textView: TextView) = toView(textView, TextView::setTextIfNecessary)

/**
 * Immediately binds the [View] to the value of this binding. Toggles visibility based on [Boolean] returned
 * in previous expressions. If true, [View.VISIBLE] is used, otherwise it's set to [View.GONE]
 */
infix fun <Input, TBinding : BindingConverter<*, Input>>
        OneWayExpression<*, Input, Boolean, TBinding>.toShowHideView(textView: View) = toView(textView, { view, value -> view.setVisibilityIfNeeded(if (value != null && value) View.VISIBLE else View.GONE) })


/**
 * Binds the output of the initial expression to [CompoundButton.setChecked] method.
 */
infix fun <Data, Input, TBinding : BindingConverter<Data, Input>>
        OneWayExpression<Data, Input, Boolean, TBinding>.toOnCheckedChange(compoundButton: CompoundButton) = toView(compoundButton, CompoundButton::setCheckedIfNecessary)


///**
// * Binds the output of the initial expression to [View.isClickable] method.
// */
//infix fun <Data, Input, TBinding : BindingConverter<Data, Input>>
//        OneWayExpression<Data, Input, View.OnClickListener, TBinding>.toOnClickChange(view: View) = toView(view, View::setClickedIfNecessary)

/**
 * Binds the output of the initial expression to [DatePicker.updateDate] method.
 */
infix fun <Data, Input, TBinding : ObservableBindingConverter<Data, Input>>
        OneWayExpression<Data, Input, Calendar, TBinding>.toDatePicker(datePicker: DatePicker) = toView(datePicker, DatePicker::setTimeIfNecessary)

/**
 * Binds the output of the initial expression to [RatingBar.setRating] method.
 */
infix fun <Data, Input, TBinding : BindingConverter<Data, Input>>
        OneWayExpression<Data, Input, Float, TBinding>.toRating(ratingBar: RatingBar) = toView(ratingBar, RatingBar::setRatingIfNecessary)

/**
 * Binds the output of the initial expression to [ProgressBar.setProgress] method.
 */
infix fun <Data, Input, TBinding : BindingConverter<Data, Input>>
        OneWayExpression<Data, Input, Int, TBinding>.toProgressBar(progressBar: ProgressBar) = toView(progressBar, ProgressBar::setProgressIfNecessary)