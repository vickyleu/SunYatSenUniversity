package com.superfactory.sunyatsin.Interface.BindingFragment.Profile

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.DslView.circleImage
import com.superfactory.library.Bridge.Anko.DslView.refresh
import com.superfactory.library.Bridge.Anko.ViewExtensions.getLineDividerItemDecoration
import com.superfactory.library.Bridge.Anko.bindings.toText
import com.superfactory.library.Bridge.Anko.widget.AnkoViewHolder
import com.superfactory.library.Bridge.Anko.widget.AutoBindAdapter
import com.superfactory.library.Communication.Sender.senderAsync
import com.superfactory.library.Context.Extensions.takeApi
import com.superfactory.library.Debuger
import com.superfactory.library.Graphics.Badge.Badge.Companion.STATE_CANCELED
import com.superfactory.library.Graphics.Badge.Badge.Companion.STATE_DRAGGING
import com.superfactory.library.Graphics.Badge.Badge.Companion.STATE_DRAGGING_OUT_OF_RANGE
import com.superfactory.library.Graphics.Badge.Badge.Companion.STATE_START
import com.superfactory.library.Graphics.Badge.Badge.Companion.STATE_SUCCEED
import com.superfactory.library.Graphics.Badge.BadgeView
import com.superfactory.sunyatsin.Communication.RetrofitImpl
import com.superfactory.sunyatsin.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import android.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.Nullable
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception


/**
 * Created by vicky on 2018.01.19.
 *
 * @Author vicky
 * @Date 2018年01月19日  13:41:48
 * @ClassName 这里输入你的类名(或用途)
 */
class ProfileFragmentComponent(viewModel: ProfileFragmentViewModel) : BindingComponent<ProfileFragment, ProfileFragmentViewModel>(viewModel) {
    override fun createViewWithBindings(ui: AnkoContext<ProfileFragment>): View = with(ui) {

        Debuger.printMsg(this, "createViewWithBindings")
        val screenHeight = getAppNoStatusBarSize(ctx).height
        verticalLayout {
            verticalLayout {
                backgroundColor = Color.parseColor("#1688ff")
                circleImage {
                    id = R.id.avatar
                    circleBackgroundColor = Color.BLUE
                    borderColor = Color.WHITE
                    borderWidth = dip(2)
                    imageResource = R.drawable.note_icon
                    bindSelf(ProfileFragmentViewModel::avatar) { it.avatar.value }.toView(this) { view, value ->
                        Glide.with(context).load(value)
                                .asBitmap()
                                .centerCrop()
                                .placeholder(R.drawable.note_icon)
                                .listener(object : RequestListener<String, Bitmap> {
                                    override fun onException(e: Exception?, model: String?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                                        Debuger.printMsg("onLoadFailed = %s", e?.message?.toString()
                                                ?: "null")
                                        return false;
                                    }

                                    override fun onResourceReady(resource: Bitmap?, model: String?, target: Target<Bitmap>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                                        return false
                                    }

                                })
                                .into(object : BitmapImageViewTarget(view) {
                                    override fun setResource(resource: Bitmap) {
                                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                                        circularBitmapDrawable.isCircular = true
                                        view.setImageDrawable(circularBitmapDrawable)
                                    }
                                })
                    }

                    onClick {
                        this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(-1,
                                ProfileFragmentItemViewModel(-1, 0, "", "", -1))
                    }
                }.lparams {
                    height = (screenHeight * 0.15).toInt()
                    width = (screenHeight * 0.15).toInt()
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }
                textView {
                    text = context.getString(R.string.profile_name)
                    textSize = 14f
                    textColor = Color.parseColor("#ffffff")
                    bindSelf(ProfileFragmentViewModel::profileName) { it.profileName.value }.toText(this)
                }.lparams {
                    setVerticalGravity(Gravity.CENTER_VERTICAL)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                }

            }.lparams {
                width = matchParent
                height = (screenHeight * 0.2f).toInt()
            }
            refresh {
                backgroundColor = Color.parseColor("#f8f8f8")
                isEnableRefresh = false
                isEnableLoadmore = false

                nestedScrollView {
                    fitsSystemWindows = true
                    verticalLayout {
                        backgroundColor = Color.TRANSPARENT
//                        fitsSystemWindows=true
                        recyclerView {
                            backgroundResource = R.drawable.profile_recycle_shader
                            leftPadding = dip(10)
                            val bindAdapter = AutoBindAdapter { viewGroup, _ ->
                                AnkoViewHolder(viewGroup, ProfileFragmentItemComponent())
                            }.apply {
                                onItemClickListener = { i, viewModel, _ ->
                                    Debuger.printMsg(this, "invoke  1  " + viewModelSafe.onItemClicked)
                                    this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(i, viewModel)
                                }
                            }.assignment { holder, _, position ->
                                        when (position) {
                                            0 -> {
                                                Debuger.printMsg(this, "0" + viewModel?.profileNo?.value)
                                                holder.component.viewModel?.description = viewModel?.profileNo?.value ?: ""
                                            }
                                            1 -> {
                                                Debuger.printMsg(this, "1")
                                                holder.component.viewModel?.description = viewModel?.employ?.value ?: ""
                                            }
                                            2 -> {
                                                Debuger.printMsg(this, "2")
                                                holder.component.viewModel?.description = viewModel?.station?.value ?: ""
                                            }
                                            3 -> {
                                                Debuger.printMsg(this, "3")
                                                holder.component.viewModel?.description = viewModel?.position?.value ?: ""
                                            }
                                            else -> {
                                                return@assignment
                                            }
                                        }
                                        holder.component.notifyChanges()
                                    }
                            bindSelf(ProfileFragmentViewModel::profileItemsList) { it.profileItemsList }
                                    .toView(this) { _, value ->
                                        bindAdapter.setItemsList(value)
                                    }
                            layoutManager = LinearLayoutManager(context)!!
                            addItemDecoration(getLineDividerItemDecoration(1, ContextCompat.getColor(context, R.color.gray)))
                            adapter = bindAdapter
                        }.lparams {
                            width = matchParent
                            height = wrapContent

                            topMargin = dip(10)
//                    bottomMargin = dip(10)
                        }
                        verticalLayout {
                            backgroundColor = Color.TRANSPARENT
                            id = R.id.settings_layout
                            val that = this
                            doAsync {
                                for (i in 0 until viewModel?.profileSettingsList?.size!!) {
                                    val component = ProfileFragmentItemComponent()
                                    bindSelf(ProfileFragmentViewModel::profileSettingsList) { it.profileSettingsList }
                                            .toView(that) { arg, value ->
                                                if (value == null) return@toView
                                                component.isBound = true //数据是否绑定,当手动设置databinding数据时需要修改为true,否则数据无法自动刷新
                                                component.viewModel = value[i]
                                            }
                                    component.bindSelf {
                                        viewModelSafe.notificationTotalObserva
                                    }.toView(that) { _, va ->
                                                Debuger.printMsg(this, "notificationTotalObserva:"
                                                + va?.toString() ?: "null")
                                                @Suppress("LABEL_NAME_CLASH")
                                                if (va == null) return@toView
                                                component.viewModel?.notificationTotal?.value = va
                                            }

                                    component.bindSelf { it.notificationTotal }.toView(that) { _, value ->
                                        if (value != null) {
                                            viewModelSafe.notificationTotalObserva.setStableValue(value)
                                        }
                                    }
                                    val v = component.createViewWithBindings(AnkoContextImpl(that.context,
                                            that, false))
                                    v.backgroundResource = R.drawable.profile_recycle_shader
                                    val lp = LinearLayout.LayoutParams(matchParent, wrapContent)
                                    lp.topMargin = dip(10)
                                    v.leftPadding = dip(10)
                                    uiThread {
                                        addView(v, lp)
                                    }
                                    v.apply {
                                        register.bindAll()
                                    }
                                    v.apply {
                                        onClick {
//                                            viewModel?.notificationTotalObserva?.value = 5
//                                            viewModel?.profileNo?.value = "12530"
                                            Debuger.printMsg(this, "invoke  2  ")
                                            this@ProfileFragmentComponent.viewModelSafe.onItemClicked?.invoke(component.viewModelSafe.index, component.viewModelSafe)
                                        }
                                    }
                                }
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }.lparams {
                        height = matchParent
                        width = matchParent
                        bottomMargin = dip(30)
                    }
                }.lparams {
                    height = matchParent
                    width = matchParent
                }


            }.lparams {
                width = matchParent
                height = matchParent
            }
            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

    class ProfileFragmentItemComponent : BindingComponent<ViewGroup, ProfileFragmentItemViewModel>() {

        override fun createViewWithBindings(ui: AnkoContext<ViewGroup>) = with(ui) {
            val screenWidth = getAppNoStatusBarSize(ctx).width
            relativeLayout {
                val that = this
                val icon = imageView {
                    id = R.id.left_icon
                    backgroundColor = Color.TRANSPARENT
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams {
                    width = dip(20)
                    height = dip(20)
                    centerVertically()
                    alignParentLeft()
                }
                bindSelf(ProfileFragmentItemViewModel::icon) { it.icon }
                        .toView(icon) { view, value ->
                            if (value != null && value != 0) {
                                view.imageResource = value
                            }
                        }
                textView {
                    bindSelf(ProfileFragmentItemViewModel::name) { it.name }.toText(this)
                    padding = dip(12)
                    leftPadding = 0
                    textSize = 16.0f
                    textColor = Color.BLACK
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    leftMargin = dip(10)
                    centerVertically()
                    addRule(RelativeLayout.RIGHT_OF, R.id.left_icon)
                }

                textView {
                    id = R.id.right_text
                    bindSelf(ProfileFragmentItemViewModel::description) { it.description }.toView(this) { view, value ->
                        view.text = value
                    }
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    centerVertically()
                    alignParentRight()
                }
                bindSelf(ProfileFragmentItemViewModel::type) { it.type }.toView(this) { view, value ->
                    if (value == 1) {
                        if (view.findViewById<View>(R.id.right_arrow) != null) return@toView
                        val rightChild = ImageView(view.context)
                        rightChild.id = R.id.right_arrow
                        val lp = RelativeLayout.LayoutParams(wrapContent, wrapContent)
                        rightChild.backgroundColor = Color.TRANSPARENT
                        rightChild.imageResource = R.drawable.note_icon
                        rightChild.scaleType = ImageView.ScaleType.FIT_XY
                        lp.width = dip(20)
                        lp.height = dip(20)
                        lp.alignParentRight()
                        lp.centerVertically()
                        addView(rightChild, lp)
                    }
                }

                bindSelf(ProfileFragmentItemViewModel::dragging) { it }.toView(this) { view, item ->
                    @Suppress("LABEL_NAME_CLASH")
                    if (item == null || !viewModelSafe.dragging.value || viewModelSafe.badge.value == null) return@toView
                    viewModelSafe.dragging.value = false
                    viewModelSafe.badge.value?.setBadgeNumber(0)
                    takeApi(RetrofitImpl::class)?.eraseBadge(
                            "eraseBadge/index.html")?.senderAsync(
                            String::class, this@ProfileFragmentItemComponent,view.context)
                }

                bindSelf(ProfileFragmentItemViewModel::notificationTotal) {
                    it/*.notificationTotal*/
                }.toView(this) { view, model ->
                    var arrow: View? = null
                    if (model == null) return@toView
                    val value = model.notificationTotal.value
                    if (model.index > 4) return@toView
                    arrow = view.findViewById(R.id.right_arrow)
                    if (arrow == null) return@toView
                    val attachValue = "  " + value.toString() + "  "
                    if (viewModelSafe.badge.value != null) {
                        if (value > 0) {
                            if (viewModelSafe.badge.value!!.targetView?.hashCode() != view.hashCode()) {
                                viewModelSafe.badge.value!!.bindTarget(view)
                                viewModelSafe.badge.value?.setBadgeText(attachValue)
                                return@toView
                            } else {
                                viewModelSafe.badge.value?.setBadgeText(attachValue)
                                return@toView
                            }
                        } else {
                            viewModelSafe.badge.value?.setBadgeNumber(0)
                            return@toView
                        }
                    }
                    val mBadge = BadgeView(context)
                    viewModelSafe.badge.value = mBadge
                            .bindTarget(view)
                            .setBadgeGravity(Gravity.CENTER or Gravity.END)
                            .setGravityOffset((screenWidth * 0.03).toFloat(), true)
                            .setBadgeBackgroundColor(Color.parseColor("#ff03a9f4"))
                            .setBadgeTextColor(Color.parseColor("#ffffff"))
                            .setBadgeTextSize(10f, true)
                            .setBadgeBackground(ContextCompat.getDrawable(context, R.drawable.badge_shape))
                            .setBadgeText(attachValue)
                            .setOnDragStateChangedListener({ dragState, badge, _ ->
                                when (dragState) {
                                    STATE_START/*拖拽开始*/ -> {
                                    }
                                    STATE_DRAGGING /*拖拽中*/ -> {
                                    }
                                    STATE_DRAGGING_OUT_OF_RANGE /*拖拽区域超出所属ViewParent*/ -> {
                                    }
                                    STATE_SUCCEED /*拖拽完成,气泡消失*/ -> {
                                        viewModelSafe.notificationTotal.value = 0
                                        viewModelSafe.dragging.value = true
                                    }
                                    STATE_CANCELED/*拖拽取消*/ -> {
                                    }
                                }
                            })
                }
                register.assignmentHolder { holder, item, position ->
                    //                    when (position) {
//                        0 -> {
//                            register.bindSelf(ProfileFragmentViewModel::profileNo)
//                            {it.description}.toText(tv)
//
//                        }
//                        1 -> {
//                            register.bindSelf(ProfileFragmentViewModel::employ)
//                            {it.description}.toText(tv)
//                        }
//                        2 -> {'
//                            register.bindSelf(ProfileFragmentViewModel::station)
//                            {it.description}.toText(tv)
//                        }
//                        3 -> {
//                            register.bindSelf(ProfileFragmentViewModel::position)
//                            {it.description}.toText(tv)
//                        }
//
//                    }


                }

                lparams {
                    width = matchParent
                    height = wrapContent
                    gravity = Gravity.CENTER_VERTICAL and Gravity.START
                    rightPadding = dip(12)
                }
            }
        }


    }
}
