package com.superfactory.sunyatsin.Struct

import com.superfactory.sunyatsin.Struct.Base.BaseBody
import com.superfactory.sunyatsin.Struct.Base.BaseStruct

/**
 * Created by vicky on 2018.01.30.
 *
 * @Author vicky
 * @Date 2018年01月30日  14:05:16
 * @ClassName 这里输入你的类名(或用途)
 */
data class LoginStruct(val body: LoginBody?) : BaseStruct()

data class LoginBody(val username: String?, val name: String, val mobileLogin: Boolean?, val JSESSIONID: String) : BaseBody()