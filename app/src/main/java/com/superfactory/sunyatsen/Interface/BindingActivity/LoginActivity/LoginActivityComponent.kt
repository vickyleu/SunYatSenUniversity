package com.superfactory.sunyatsen.Interface.BindingActivity.LoginActivity

import android.Manifest
import android.app.Activity
import android.app.LoaderManager
import android.content.Context
import android.content.CursorLoader
import android.content.Loader
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.text.InputType
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.OnTextCleanListener
import com.superfactory.library.Bridge.Anko.DslView.autoCompleteCleanUpTextView
import com.superfactory.library.Bridge.Anko.DslView.cleanUpEditText
import com.superfactory.library.Bridge.Anko.ViewExtensions.hideInput
import com.superfactory.sunyatsen.Struct.Const
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Graphics.Adapt.SimpleWatcher
import com.superfactory.library.Graphics.AutoCompleteCleanUpTextView
import com.superfactory.library.Graphics.CleanUpEditText
import com.superfactory.library.Utils.ConfigXmlAccessor
import com.superfactory.sunyatsen.Communication.RetrofitImpl
import com.superfactory.sunyatsen.Communication.senderAsyncMultiple
import com.superfactory.sunyatsen.R
import com.superfactory.sunyatsen.Struct.Login.LoginAfterStruct
import com.superfactory.sunyatsen.Struct.Login.LoginStruct
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onTouch
import java.util.*

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:33:39
 * @ClassName 这里输入你的类名(或用途)
 */
class LoginActivityComponent(viewModel: LoginActivityViewModel) : BindingComponent<LoginActivity, LoginActivityViewModel>(viewModel) {
    companion object {
        /**
         * Id to identity READ_CONTACTS permission request.
         */
        val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }

    object ProfileQuery {
        val PROJECTION = arrayOf(ContactsContract.Contacts._ID,
                ContactsContract.Contacts.LOOKUP_KEY,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                else
                    ContactsContract.Contacts.DISPLAY_NAME)
        val ADDRESS = 0
    }

    private fun addPhonesToAutoComplete(textView: AutoCompleteCleanUpTextView, contactCollection: ArrayList<String>?) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        if (contactCollection == null) return
        if (textView.adapter == null) {
            val adapter = ArrayAdapter(textView.context,
                    android.R.layout.simple_dropdown_item_1line, contactCollection)
            textView.setAdapter(adapter)
        } else {
            val adapter = textView.adapter as? ArrayAdapter<String>
            adapter?.clear()
            adapter?.addAll(contactCollection)
        }

    }


    override fun onRequestPermissionsResult(ctx: Activity?, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(ctx, requestCode, permissions, grantResults)
        if (requestCode == LoginActivityComponent.REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ctx != null) {
                    populateAutoComplete(ctx, ctx.findViewById(R.id.account))
                }
            }
        }
    }

    protected fun populateAutoComplete(context: Activity, account: AutoCompleteCleanUpTextView) {
        if (!mayRequestContacts(context, account)) {
            return
        }
        val loader = context.loaderManager.getLoader<Cursor>(0)
        val callBack = object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(context,
                        // Retrieve data rows for the device user's 'profile' contact.
                        Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(account.text.toString())),

                        ProfileQuery.PROJECTION,

                        "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                                + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))",

                        null,

                        ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"

                )
            }

            override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
                val contact = ArrayList<String>()
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    contact.add(cursor.getString(ProfileQuery.ADDRESS))
                    cursor.moveToNext()
                }
                addPhonesToAutoComplete(account, contact)
            }

            override fun onLoaderReset(loader: Loader<Cursor>?) {
                addPhonesToAutoComplete(account, null)
            }
        }
        if (loader == null) {
            context.loaderManager.initLoader(0, null, callBack)
        } else {
            context.loaderManager.restartLoader(0, null, callBack)
        }

    }

    private fun mayRequestContacts(context: Activity, account: AutoCompleteCleanUpTextView): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
            Snackbar.make(account, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, { requestPermissions(context, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS) })
        } else {
            requestPermissions(context, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    override fun createViewWithBindings(ui: AnkoContext<LoginActivity>) = with(ui) {
        var account: AutoCompleteCleanUpTextView? = null
        var passwd: CleanUpEditText? = null
        coordinatorLayout {
            //            scrollView {
            //            isFocusable = true
//            isFocusableInTouchMode = true
            verticalLayout {
                isFocusable = true
                isFocusableInTouchMode = true
                imageView {
                    backgroundColor = Color.TRANSPARENT
                    imageResource = R.drawable.launch_icon
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    topMargin = dip(55)
                    bottomMargin = dip(45)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }

                textInputLayout {
                    account = autoCompleteCleanUpTextView {
                        id = R.id.account
                        hint = "请输入您的账号"
                        textSize = 14f
                        setText("admin")
                        singleLine = true
                        bottomPadding = dip(20)
                        compoundDrawablePadding = dip(30)
                        backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.text_underline)
                        maxLength = 20
                        imeOptions = EditorInfo.IME_ACTION_NEXT
                        setRightClick(object : OnTextCleanListener {
                            override fun onClean() {
                                requestFocus()
                            }
                        })
                        setOnClickListener {
                            requestFocus()
                        }
//                        inputType = EditorInfo.TYPE_CLASS_PHONE
                        inputType = EditorInfo.TYPE_CLASS_TEXT
                        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.avatar_icon), null, null, null)
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                            if (id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_NEXT) {
                                if (isValidSize(5)) {
                                    passwd!!.requestFocus()
                                    return@OnEditorActionListener true
                                } else {
//                                    if (isValidSize(11)) {
//                                        error = "手机号码格式不正确"
//                                    }else{
//                                        error="手机号码长度过短"
//                                    }
                                    error = "账号太短"
                                    Thread({
                                        this.post {
                                            this.requestFocus()
                                        }
                                    }).start()
                                }
                            }
                            return@OnEditorActionListener false
                        })
                        addTextChangedListener(object : SimpleWatcher() {
                            /**
                             * This method is called to notify you that, within `s`,
                             * the `count` characters beginning at `start`
                             * have just replaced old text that had length `before`.
                             * It is an error to attempt to make changes to `s` from
                             * this callback.
                             */
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                viewModel?.tips?.value = ""
                                if (s?.length ?: 0 > 0)
                                    populateAutoComplete(ui.owner, this@autoCompleteCleanUpTextView)
                            }
                        })
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                textInputLayout {
                    passwd = cleanUpEditText {
                        hint = "请输入您的密码"
                        textSize = 14f
                        maxLength = 20

                        setText("admin")
                        singleLine = true
                        bottomPadding = dip(20)
                        compoundDrawablePadding = dip(30)
                        backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.text_underline)
                        imeOptions = EditorInfo.IME_ACTION_DONE
                        setRightClick(object : OnTextCleanListener {
                            override fun onClean() {
                                if (account!!.isValidSize(5)) {
//                                if (account!!.isValidPhoneNumber()) {
                                    requestFocus()
                                } else {
                                    account!!.requestFocus()
                                }
                            }
                        })
                        setOnClickListener {
                            if (account!!.isValidSize(5)) {
//                            if (account!!.isValidPhoneNumber()) {
                                account!!.clearFocus()
                                isFocusable = true
                                isFocusableInTouchMode = true
//
                                requestFocus()
                                val imm = this@cleanUpEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.toggleSoftInputFromWindow(windowToken, 0, 0)
                            } else {
                                clearFocus()
                                isFocusable = false
                                isFocusableInTouchMode = false
                                Thread({
                                    post {
                                        account!!.requestFocus()
                                        isFocusable = true
                                    }
                                }).start()
                            }
                        }
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.passwd_icon), null, null, null)
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                                attemptLogin(account!!, passwd!!)
                                return@OnEditorActionListener true
                            }
                            return@OnEditorActionListener false
                        })
                        addTextChangedListener(object : SimpleWatcher() {
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                viewModel?.tips?.value = ""
                            }
                        })
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }


                textView {
                    textColor = Color.RED
                    textSize = 12f
                    visibility = View.INVISIBLE
                    bindSelf(LoginActivityViewModel::tips) { it.tips.value }.toView(this) { view, value ->
                        view.text = value
                        view.visibility = if (TextUtils.isEmpty(value)) View.INVISIBLE else View.VISIBLE
                        if (!TextUtils.isEmpty(value)) {
                            Snackbar.make(view, value!!, LENGTH_SHORT).show()
                        }
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                button {
                    text = "登录"
                    textSize = 17f
                    padding = 0
                    setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_state))
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_submit_selector)
                    onClick {
                        attemptLogin(account!!, passwd!!)
                    }
                    isFocusable = true
                    requestFocus()
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    minimumWidth = 0
                    minimumHeight = 0
                }
                lparams {
                    width = matchParent
                    height = matchParent
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                populateAutoComplete(ui.owner, account!!)

                onTouch { v, event ->
                    account!!.clearFocus()
                    passwd!!.clearFocus()
                    isFocusable = true
                    isFocusableInTouchMode = true
                    requestFocus()
                    this@verticalLayout.hideInput(owner)
                }
            }
            lparams {
                width = matchParent
                height = matchParent
                leftPadding = dip(10)
                rightPadding = dip(10)
                gravity = Gravity.CENTER_HORIZONTAL
            }

            onTouch { v, event ->
                account!!.clearFocus()
                passwd!!.clearFocus()
                isFocusable = true
                isFocusableInTouchMode = true
                requestFocus()
                this@coordinatorLayout.hideInput(owner)
            }
        }
//        }
    }


    private fun attemptLogin(account: AutoCompleteCleanUpTextView, passwd: CleanUpEditText) {
        // Reset errors.
        account.error = null
        passwd.error = null

        // Store values at the time of the login attempt.
        val phoneStr = account.text.toString()
        val passwordStr = passwd.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordStr)) {
            passwd.error = account.context.getString(R.string.error_field_required)
            focusView = passwd
            cancel = true
        } else if (!TextUtils.isEmpty(passwordStr) && !passwd.isValidSize(5)) {
            passwd.error = passwd.context.getString(R.string.error_invalid_password)
            focusView = passwd
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phoneStr)) {
            account.error = account.context.getString(R.string.error_field_required)
            focusView = account
            cancel = true
        } else if (!account.isValidSize(5)) {
//        } else if (!account.isValidPhoneNumber()) {
            account.error = "账号太短"
//            account.error = account.context.getString(R.string.error_invalid_phone)
            focusView = account
            cancel = true
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            takeApi(account.context, RetrofitImpl::class)?.senderAsyncMultiple(
                    { retrofitImpl -> retrofitImpl.login(account.text.toString(), passwd.text.toString(), true) },
                    LoginStruct::class,
                    this, account.context,
                    { retrofitImpl, loginStruct ->
                        if (loginStruct.isValidLogin()) {
                            ConfigXmlAccessor.storeValue(account.context, Const.SignInInfo, Const.SignInSession, loginStruct.body!!.JSESSIONID)
                            ConfigXmlAccessor.storeValue(account.context, Const.SignInInfo, Const.SignInAccount, loginStruct.body.username)

                            retrofitImpl.loginAfter(loginStruct.body.JSESSIONID, true)
                        } else {
                            null
                        }
                    },
                    LoginAfterStruct::class
            )

//            takeApi(account.context, RetrofitImpl::class)?.login(account.text.toString(), passwd.text.toString(),true)?.
//                    senderAsyncMultiple(LoginStruct::class,
//                    this@LoginActivityComponent,
//                    account.context)
            // Show a progress spinner, and kick off a background task to
        }
    }
}