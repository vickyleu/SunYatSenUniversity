package com.superfactory.sunyatsin.Anko.Sample.home

import com.superfactory.library.Bridge.Anko.BaseObservable

/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  14:46:40
 * @ClassName 这里输入你的类名(或用途)
 */
class HomeActivityViewModel : BaseObservable() {

    val homeItemsList = arrayListOf(HomeActivityItemViewModel("RecyclerView"),
            HomeActivityItemViewModel("Calendar"),
            HomeActivityItemViewModel("Input Mirroring"))

    var onItemClicked: ((Int, HomeActivityItemViewModel) -> Unit)? = null

}

data class HomeActivityItemViewModel(val name: String)