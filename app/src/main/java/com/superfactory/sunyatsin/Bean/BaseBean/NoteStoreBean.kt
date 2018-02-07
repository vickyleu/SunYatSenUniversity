package com.superfactory.sunyatsin.Bean.BaseBean

/**
 * Created by vicky on 2018/2/7.
 */
//private val jobType: String? = null    // 职责类型
// private String itemType;	// 事项类型
// private Date startTime;	// 开始时间
// private Date endTime;	// 结束时间
data class NoteStoreBean(val jobType: String, val itemType: String, val startTime: Long, val endTime: Long) : MultipartBean()