package com.superfactory.sunyatsen.Bean

import com.superfactory.sunyatsen.Bean.BaseBean.MultipartBean

/**
 * Created by vicky on 2018.02.04.
 *
 * @Author vicky
 * @Date 2018年02月04日  14:44:45
 * @ClassName 这里输入你的类名(或用途)
 */
data class LoginBean(val oldPassword: String, val newPassword: String) : MultipartBean() {
    /** Returns the Content-Type header for this body.  */


}