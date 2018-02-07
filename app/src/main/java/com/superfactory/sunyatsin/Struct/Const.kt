package com.superfactory.sunyatsin.Struct

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  11:08:26
 * @ClassName 这里输入你的类名(或用途)
 */
open class Const {
    companion object {
        private const val ServerHostInternal: String = "http://192.168.31.237:8080"
        private const val ServerHost: String = "http://120.76.219.199:18080"

        const val RequestPrefix: String = ServerHostInternal + "/jeeplus/a/"
        const val AvatarPrefix: String = ServerHostInternal
        const val SignInInfo: String = "signInInfo"
        const val SignInSession: String = "SignInSession"
        const val SignInAccount: String = "signInAccount"
        const val SignInPassword: String = "signInPassword"
    }
}