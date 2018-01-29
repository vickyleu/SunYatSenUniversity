package com.superfactory.sunyatsin.Interface.BindingActivity.LoginActivity

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
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Graphics.AutoCompleteCleanUpTextView
import com.superfactory.library.Graphics.CleanUpEditText
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

/**
 * Created by vicky on 2018.01.29.
 *
 * @Author vicky
 * @Date 2018年01月29日  10:33:39
 * @ClassName 这里输入你的类名(或用途)
 */
class LoginActivityComponent(viewModel: LoginActivityViewModel) : BindingComponent<LoginActivity, LoginActivityViewModel>(viewModel) {


    object ProfileQuery {
        val PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }

    private fun addPhonesToAutoComplete(textView: AutoCompleteCleanUpTextView, contactCollection: ArrayList<String>) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        val adapter = ArrayAdapter(textView.context,
                android.R.layout.simple_dropdown_item_1line, contactCollection)
        textView.setAdapter(adapter)
    }


    private fun populateAutoComplete(context: Activity, account: AutoCompleteCleanUpTextView) {
        if (!mayRequestContacts(context, account)) {
            return
        }
        context.loaderManager.initLoader(0, null, object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                return CursorLoader(context,
                        // Retrieve data rows for the device user's 'profile' contact.
                        Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                                ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                        // Select only email addresses.
                        ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                        .CONTENT_ITEM_TYPE),

                        // Show primary email addresses first. Note that there won't be
                        // a primary email address if the user hasn't specified one.
                        ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
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
            }
        })
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
                    .setAction(android.R.string.ok, { requestPermissions(context, arrayOf(Manifest.permission.READ_CONTACTS), 0) })
        } else {
            requestPermissions(context, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        }
        return false
    }

    override fun createViewWithBindings(ui: AnkoContext<LoginActivity>) = with(ui) {
        scrollView {
            verticalLayout {

                imageView {
                    backgroundColor = Color.TRANSPARENT
                    imageResource = R.drawable.note_icon
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams {
                    width = dip(60)
                    height = dip(60)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }

                var account: AutoCompleteCleanUpTextView? = null
                var passwd: CleanUpEditText? = null
                textInputLayout {
                    account = autoCompleteCleanUpTextView {
                        id = R.id.account
                        hint = "请输入您的账号"
                        textSize = 14f
                        maxEms = 11
                        singleLine = true
                        maxLength = 11
//                        isFocusable=false
//                        isFocusableInTouchMode=false
                        imeOptions = EditorInfo.IME_ACTION_NEXT
                        setRightClick(object : OnTextCleanListener {
                            override fun onClean() {
                                requestFocus()
                            }
                        })
                        onClick {
                            requestFocus()
                        }
                        inputType = EditorInfo.TYPE_CLASS_PHONE
                        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.note_icon), null, null, null)
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                            if (id == EditorInfo.IME_NULL || id == EditorInfo.IME_ACTION_NEXT) {
                                if (isValidPhoneNumber()) {
                                    passwd!!.requestFocus()
                                    return@OnEditorActionListener true
                                } else {
                                    if (isValidSize(11)) {
                                        error = "手机号码格式不正确"
                                    }
                                    Thread({
                                        this.post {
                                            this.requestFocus()
                                        }
                                    }).start()
                                }
                            }
                            return@OnEditorActionListener false
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
                        maxEms = 20
                        singleLine = true
//                        isFocusable=false
//                        isFocusableInTouchMode=false
                        imeOptions = EditorInfo.IME_ACTION_DONE
                        setRightClick(object : OnTextCleanListener {
                            override fun onClean() {
                                if (account!!.isValidPhoneNumber()) {
                                    requestFocus()
                                } else {
                                    account!!.requestFocus()
                                }
                            }
                        })
                        onClick {
                            if (account!!.isValidPhoneNumber()) {
                                account!!.clearFocus()
                                isFocusable = true
                                isFocusableInTouchMode = true
                                requestFocus()
                                val imm = this@cleanUpEditText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.toggleSoftInputFromWindow(windowToken, 0, 0);
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
//                        inputType = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                        setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.note_icon), null, null, null)
                        layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
                        setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
                            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                                attemptLogin(account!!, passwd!!)
                                return@OnEditorActionListener true
                            }
                            return@OnEditorActionListener false
                        })
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }


                button {
                    text = "登录"
                    textSize = 14f
                    setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_state))
                    backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.button_submit_selector)
                    onClick {
                        attemptLogin(account!!, passwd!!)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    minimumWidth = 0
                    minimumHeight = 0
                }
//            btn.isEnabled = false

                lparams {
                    width = matchParent
                    height = matchParent
                    leftPadding = dip(10)
                    rightPadding = dip(10)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                populateAutoComplete(ui.owner, account!!)
            }
            lparams {
                width = matchParent
                height = matchParent
                leftPadding = dip(10)
                rightPadding = dip(10)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }

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
        } else if (!TextUtils.isEmpty(passwordStr) && !passwd.isValidSize(6)) {
            passwd.error = passwd.context.getString(R.string.error_invalid_password)
            focusView = passwd
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phoneStr)) {
            account.error = account.context.getString(R.string.error_field_required)
            focusView = account
            cancel = true
        } else if (!account.isValidPhoneNumber()) {
            account.error = account.context.getString(R.string.error_invalid_phone)
            focusView = account
            cancel = true
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            takeApi(account.context, RetrofitImpl::class)?.login(account.text.toString(), passwd.text.toString(),
                    true)?.senderAsync(Unit::class, this@LoginActivityComponent, account.context)
            // Show a progress spinner, and kick off a background task to
        }
    }
}