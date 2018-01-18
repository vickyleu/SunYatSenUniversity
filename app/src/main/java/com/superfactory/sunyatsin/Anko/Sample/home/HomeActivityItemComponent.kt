package com.superfactory.sunyatsin.Anko.Sample.home

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.recyclerview.v7.recyclerView

class HomeActivityItemComponent : BindingComponent<ViewGroup, HomeActivityItemViewModel>() {

    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            textView {
                bindSelf(HomeActivityItemViewModel::name) { it.name }.toText(this)
                padding = dip(12)
                textSize = 16.0f
                textColor = Color.BLACK
            }.lparams {
                width = MATCH_PARENT
                height = WRAP_CONTENT
            }
        }
    }
}

class HomeActivityLayout(viewModel: HomeActivityViewModel)
    : BindingComponent<HomeActivity, HomeActivityViewModel>(viewModel) {


    override fun createViewWithBindings(ui: AnkoContext<HomeActivity>) = with(ui) {


        verticalLayout {
            toolbar {
                title = "Sun YatSin University"
                backgroundColor = Color.parseColor("#1688ff")
                setTitleTextColor(Color.parseColor("#222222"))
            }
            refresh {
                setEnableRefresh(true)
                setEnableLoadmore(false)
                recyclerView {
                    val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                        AnkoViewHolder(viewGroup, HomeActivityItemComponent())
                    }.apply {
                        onItemClickListener = { i, viewModel, _ ->
                            this@HomeActivityLayout.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                        }
                    }

                    bindSelf(HomeActivityViewModel::homeItemsList) { it.homeItemsList }
                            .toView(this) { _, value ->
                                bindAdapter.setItemsList(value)
                            }

                    layoutManager = LinearLayoutManager(context)
                    adapter = bindAdapter

                    lparams {
                        width = MATCH_PARENT
                        height = WRAP_CONTENT
                    }
                }

                lparams {
                    width = MATCH_PARENT
                    height = MATCH_PARENT
                }
            }
        }
    }
}


