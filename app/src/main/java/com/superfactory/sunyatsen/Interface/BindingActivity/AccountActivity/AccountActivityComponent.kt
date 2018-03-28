package com.superfactory.sunyatsen.Interface.BindingActivity.AccountActivity

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Debuger
import com.superfactory.sunyatsen.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by vicky on 2018.01.31.
 *
 * @Author vicky
 * @Date 2018年01月31日  18:57:18
 * @ClassName 这里输入你的类名(或用途)
 */
class AccountActivityComponent(viewModel: AccountActivityViewModel) :
        BindingComponent<AccountActivity, AccountActivityViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<AccountActivity>) = with(ui) {
        refresh {
            backgroundColor = Color.parseColor("#f8f8f8")

            recyclerView {
                backgroundResource = R.drawable.profile_recycle_shader
                leftPadding = dip(10)

                val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                    AnkoViewHolder(viewGroup, AccountActivityItemComponent())
                }.apply {
                    onItemClickListener = { i, viewModel, _ ->
                        this@AccountActivityComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                    }
                }
                bindSelf(AccountActivityViewModel::accountItemsList) { it.accountItemsList.value }
                        .toView(this) { _, value ->
                            bindAdapter.setItemsList(value)
                        }
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                adapter = bindAdapter
            }.lparams {
                topMargin = dip(10)
                width = matchParent
                height = wrapContent
            }


            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }


}

class AccountActivityItemComponent : BindingComponent<ViewGroup, AccountActivityItemViewModel>() {
    override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
        relativeLayout {
            rightPadding = dip(10)
            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(AccountActivityItemViewModel::name) {
                    it.name
                }.toView(this) { view, value ->
                    if (!TextUtils.isEmpty(value)) {
                        view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentLeft()
                centerVertically()
            }


            val iv = imageView {
                id = R.id.left_icon
                backgroundColor = Color.TRANSPARENT
                bindSelf(AccountActivityItemViewModel::right) {
                    it.right
                }.toView(this) { view, value ->
                    if (value != null && value != 0) {
                        view.imageResource = value
                    }
                }
                scaleType = ImageView.ScaleType.FIT_XY
            }.lparams {
                width = wrapContent
                height = wrapContent
                alignParentRight()
                centerVertically()
            }


            verticalLayout {
                val that = this
                backgroundResource = R.drawable.circle_stoke_selector
                padding = dip(0.5f)
                that.visibility = View.GONE
                verticalLayout {
                    backgroundResource = R.drawable.circle_stoke_inner_selector
                    padding = dip(15)
                    imageView {
                        backgroundColor = Color.TRANSPARENT
                        scaleType = ImageView.ScaleType.FIT_XY
                        imageResource = R.drawable.camera_icon
                        bindSelf(AccountActivityItemViewModel::description) {
                            it.description
                        }.toView(this) { view, value ->
                            val index = viewModelSafe.index
                            if (index == 0) {
                                that.visibility = View.VISIBLE
                                var val2 = value
                                if (TextUtils.isEmpty(value)) {
                                    val2 = ""
                                }
                                Glide.with(view)
                                        .asBitmap()
                                        .load(val2)
                                        .apply(RequestOptions.centerCropTransform()
                                                .placeholder(R.drawable.camera_icon)
//                                        .error(R.drawable.camera_icon)
                                        )
                                        .listener(object : RequestListener<Bitmap> {
                                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                                                Debuger.printMsg("onLoadFailed = %s", e?.message
                                                        ?: "null")
                                                return false
                                            }

                                            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?,
                                                                         isFirstResource: Boolean): Boolean {
                                                return false
                                            }

                                        })
                                        .into(object : BitmapImageViewTarget(view) {
                                            override fun setResource(resource: Bitmap?) {
                                                if (resource != null) {
                                                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                                                    circularBitmapDrawable.isCircular = true
                                                    view.setImageDrawable(circularBitmapDrawable)
                                                }
                                            }
                                        })
                            } else {
                                that.visibility = View.GONE
                            }
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                    }
                    gravity = Gravity.CENTER
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                }

            }.lparams {
                width = dip(50)
                height = dip(50)
                rightMargin = dip(10)
                addRule(RelativeLayout.LEFT_OF, iv.id)
                centerVertically()
            }


            textView {
                textSize = 14f
                textColor = Color.parseColor("#222222")
                bindSelf(AccountActivityItemViewModel::description) {
                    it.description
                }.toView(this) { view, value ->
                    val index = viewModelSafe.index
                    if (!TextUtils.isEmpty(value)) {
                        if (index != 0)
                            view.text = value
                    }
                }
            }.lparams {
                width = wrapContent
                height = wrapContent
                addRule(RelativeLayout.LEFT_OF, iv.id)
                rightMargin = dip(10)
                centerVertically()
            }

            lparams {
                width = matchParent
                height = wrapContent
                bottomPadding = dip(10)
                topPadding = dip(10)
            }
        }
    }
}