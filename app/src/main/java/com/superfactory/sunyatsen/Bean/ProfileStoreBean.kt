package com.superfactory.sunyatsen.Bean

import com.superfactory.sunyatsen.Bean.BaseBean.MultipartBean

/**
 * Created by vicky on 2018/2/8.
 */

data class ProfileStoreBean(val name: String? = null,
                            val email: String? = null,
                            val phone: String? = null,
                            val mobile: String? = null,
                            val gender: Int? = null,
                            val remarks: String? = null,
                            val regId: String? = null
) : MultipartBean()