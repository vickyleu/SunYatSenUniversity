package com.superfactory.library.Bridge.Anko

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


typealias PropertyChangedCallback = (Observable, KProperty<*>?) -> Unit

/**
 * Observable classes provide a way in which data bound UI can be notified of changes. [ObservableField]
 * provides most of the observability for our use cases.
 *
 *
 * An Observable object should notify the [PropertyChangedCallback] whenever
 * an observed property of the class changes.
 *
 * Convenience class BaseObservable implements this interface and PropertyChangeRegistry
 * can help classes that don't extend BaseObservable to implement the listener registry.
 */
interface Observable {

    /**
     * Adds a callback to listen for changes to the Observable.
     * @param callback The callback to start listening.
     */
    fun addOnPropertyChangedCallback(callback: PropertyChangedCallback)

    /**
     * Removes a callback from those listening for changes.
     * @param callback The callback that should stop listening.
     */
    fun removeOnPropertyChangedCallback(callback: PropertyChangedCallback)
}

open class BaseObservable() : Observable, Parcelable {
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null
    open var ownerNotifier: ((Int, Any?) -> Unit)? = null

    constructor(parcel: Parcel) : this() {
    }

    @Synchronized
    override fun addOnPropertyChangedCallback(callback: PropertyChangedCallback) {
        if (mCallbacks == null) {
            mCallbacks = PropertyChangeRegistry()
        }
        mCallbacks?.add(callback)
    }

    @Synchronized
    override fun removeOnPropertyChangedCallback(callback: PropertyChangedCallback) {
        mCallbacks?.remove(callback)
    }

    /**
     * Notifies listeners that a specific property has changed. Call this when an [ObservableField] has
     * changed.
     */
    @Synchronized
    fun notifyChange(property: KProperty<*>? = null) = mCallbacks?.notifyChange(this, property)

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseObservable> {
        override fun createFromParcel(parcel: Parcel): BaseObservable {
            return BaseObservable(parcel)
        }

        override fun newArray(size: Int): Array<BaseObservable?> {
            return arrayOfNulls(size)
        }
    }

    open fun startRequest(ld: LoadingDialog) {
        ld.setLoadingText("加载中")
                .setSuccessText("加载成功")//显示加载成功时的文字
                //.setFailedText("加载失败")
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_ONE)
                .setRepeatCount(0)
                .setDrawColor(Color.parseColor("#f8f8f8"))
                .show()
    }

    open fun appendingRequest(ld: LoadingDialog, model: Any?): Boolean {
        return true
    }

    open fun requestSuccess(ld: LoadingDialog, model: Any?, witch: Int?) {
        ld.loadSuccess()
    }

    open fun requestFailed(ld: LoadingDialog, error: Throwable?, witch: Int?) {
        ld.setFailedText(error?.message ?: "异常退出")
        ld.loadFailed()
    }


}

interface ObservableField<T> : Observable {

    var value: T

    val defaultValue: T

    /**
     * Called when unbinding from its own reference. Useful for cleanup.
     */
    fun unregisterFromBinding() {

    }
}

class ObservableFieldImpl<T : Any?>(private var _value: T, private val configureClosure: PropertyChangedCallback? = null)
    : BaseObservable(), ReadWriteProperty<Any?, T>, ObservableField<T>, Parcelable {

    private var configured: Boolean = false
    private var name = convertValue(_value)


    private fun convertValue(value: T) = if (value == null) "" else {
        if ((value as Any).javaClass is Parcelable) {
            Parcelable::class.java.name
        } else {
            (value as Any).javaClass.superclass.name
        }
    }

    override val defaultValue = _value

    private var internalFlags = false
    override var value = _value
        set(value) {
            if (!internalFlags) {
                checkConfigured()
                field = value
                name = convertValue(value)
                notifyChange()
            } else {
                field = value
            }
        }

    fun setStableValue(stableVar: T) {
        internalFlags = true
        value = stableVar
        internalFlags = false
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        checkConfigured()
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        checkConfigured()
        if (value != this.value) {
            this.value = value
            (thisRef as? BaseObservable)?.notifyChange(property)
        }
    }

    private fun checkConfigured() {
        if (!configured && configureClosure != null) {
            configured = true
            addOnPropertyChangedCallback(configureClosure)
        }
    }

//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        super.writeToParcel(parcel, flags)
//        parcel.writeByte(if (configured) 1 else 0)
//        parcel.writeStringNotNull(name)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ObservableFieldImpl<*>> {
//        override fun createFromParcel(parcel: Parcel): ObservableFieldImpl<*> {
//            val name = parcel.readString()
//            if (name==null||name.equals("")){
//                return observable<Any?>(null)
//            }
//            var inst:ObservableFieldImpl<*> ?= null
//            when(name){
//                String.javaClass.name->{
//                    inst=observable(parcel.readString())
//                }
//                Int.javaClass.name->{
//                    inst=observable(parcel.readInt())
//                }
//                Float.javaClass.name->{
//                    inst=observable(parcel.readFloat())
//                }
//                Double.javaClass.name->{
//                    inst=observable(parcel.readDouble())
//                }
//                Byte.javaClass.name->{
//                    inst=observable(parcel.readByte())
//                }
//                Long.javaClass.name->{
//                    inst=observable(parcel.readLong())
//                }
//                Boolean::class.java.name->{
//                    inst=observable(parcel.readByte() != 0.toByte())
//                }
//                Parcelable::class.java.name->{
//                    inst=observable(parcel.readParcelable<Parcelable>(ClassLoader.getSystemClassLoader()))
//                }
//                Array<Parcelable>::class.java.name->{
//
//                }
//                List::class.java.name->{
//
//                }
//                else->{
//                    inst=observable(Any())
//                }
//            }
//            inst.configured = parcel.readByte() != 0.toByte()
//            return inst
//        }
//
//        override fun newArray(size: Int): Array<ObservableFieldImpl<*>?> {
//            return arrayOfNulls(size)
//        }
//    }


}


/**
 * Creates new instance of the [Observable] field.
 */
fun <T> observable(initialValue: T, change: PropertyChangedCallback? = null) = ObservableFieldImpl(initialValue, change)

/**
 * Creates new instance of the [Observable] field.
 */
fun <T> BaseObservable.observable(initialValue: T) = ObservableFieldImpl(initialValue) { _, kProperty -> notifyChange(kProperty) }


//fun  BaseObservable.observableInt(initialValue: Int) = ObservableFieldImpl(SignedInt(initialValue)) { _, kProperty -> notifyChange(kProperty) }

class SignedInt(var initialValue: Int) : Parcelable {

    constructor(parcel: Parcel, any: Any?) : this(parcel.readInt()) {
    }

    fun toInt(): Int {
        return initialValue
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(initialValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignedInt> {
        override fun createFromParcel(parcel: Parcel): SignedInt {
            return SignedInt(parcel, null)
        }

        override fun newArray(size: Int): Array<SignedInt?> {
            return arrayOfNulls(size)
        }
    }
}

fun <T> BaseObservable.observableNullable(initialValue: T?) = ObservableFieldImpl<T?>(initialValue) { _, kProperty -> notifyChange(kProperty) }
