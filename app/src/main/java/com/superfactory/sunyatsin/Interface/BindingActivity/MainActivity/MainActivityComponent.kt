package com.superfactory.sunyatsin.Interface.BindingActivity.MainActivity

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.*
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.superfactory.library.Bridge.Anko.BindingComponent
import com.superfactory.library.Bridge.Anko.BindingExtensions.dipValue
import com.superfactory.library.Bridge.Anko.BindingExtensions.getAppNoStatusBarSize
import com.superfactory.library.Bridge.Anko.DslView.horizontalLayout
import com.superfactory.library.Bridge.Anko.bindSelf
import com.superfactory.library.Bridge.Anko.bindings.notifyOnClickObservable
import com.superfactory.library.Bridge.Anko.bindings.toFragment
import com.superfactory.sunyatsin.R
import com.superfactory.sunyatsin.R.color.ctrl_text_selector
import org.jetbrains.anko.*


/**
 * Created by vicky on 2018.01.17.
 *
 * @Author vicky
 * @Date 2018年01月17日  13:38:08
 * @ClassName 这里输入你的类名(或用途)
 */
class MainActivityComponent(viewModel: MainActivityViewModel) : BindingComponent<MainActivity, MainActivityViewModel>(viewModel) {


    override fun createViewWithBindings(ui: AnkoContext<MainActivity>): View = with(ui) {
        val screenSizeHeight = getAppNoStatusBarSize(ctx).height.toFloat()
        val screenSizeWidth = getAppNoStatusBarSize(ctx).width.toFloat()

        verticalLayout {
            backgroundColor = Color.TRANSPARENT
            weightSum = 1f
            frameLayout {
                id = R.id.alt_display
                backgroundColor = Color.BLUE
                bindSelf {
                    it.fragments
                }.toFragment(this)
            }.lparams {
                width = matchParent
                weight = 0.92f
                height = 0
            }

            relativeLayout {
                backgroundColor = Color.TRANSPARENT


                val buttonHeight = ctx.resources.getDimension(R.dimen.size_button_height)
                val btw = ctx.resources.getDimension(R.dimen.size_button_btw).toInt()
                val dividerPercent = (0.4).toFloat()
                val screenGap = (screenSizeWidth * 0.08).toInt()

                frameLayout {
                    id = R.id.ctrl_shadow
                    backgroundDrawable = getDrawable(ctx, R.drawable.gradient_shape)//getGradientDrawable(ctx)
                    visibility = View.INVISIBLE
                }.lparams {
                    topMargin = -(buttonHeight * dividerPercent).toInt()
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    alignParentTop()
                    width = matchParent
                    height = matchParent
                }

                horizontalLayout {
                    backgroundResource = R.drawable.top_stoke_selector
                }.lparams {
                    width = matchParent
                    height = matchParent
                    alignParentBottom()
                }


                relativeLayout {
                    id = R.id.ctrl_button
                    isClickable = true
                    bindSelf(this, viewModelSafe.clickListener.value)
                            .notifyOnClickObservable { it.clickListener }
                    imageView() {
                        isDuplicateParentStateEnabled = true
                        backgroundDrawable = getLayerDrawable(buttonHeight.toInt(), dividerPercent, btw, ctx)
                    }.lparams {
                        width = buttonHeight.toInt() + btw * 2
                        height = buttonHeight.toInt()
                        centerHorizontally()
                        alignParentTop()
                    }

                    textView {
                        id = R.id.ctrl_text
                        isDuplicateParentStateEnabled = true
                        backgroundColor = Color.TRANSPARENT
                        setTextColor(ContextCompat.getColorStateList(ctx, ctrl_text_selector))
                        text = ctx.getString(R.string.note_create)
                        textSize = 12f
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentTop()
                        val textMarginHeight = (((Math.abs(buttonHeight.toInt()) - ((Math.abs(buttonHeight.toInt()) * 0.13)))).toInt())
                        topMargin = textMarginHeight
                        centerHorizontally()
                        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    }
                }.lparams {
                    //                    topMargin = -(buttonHeight * dividerPercent).toInt()
//                    alignParentTop()
                    addRule(RelativeLayout.ALIGN_TOP, R.id.ctrl_shadow)
                    setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    centerHorizontally()
                }
                relativeLayout {
                    id = R.id.ctrl_text_left
                    isClickable = true
                    backgroundColor = Color.TRANSPARENT
                    bindSelf(this, viewModelSafe.clickListener.value)
                            .notifyOnClickObservable { it.clickListener }
                    val tv = textView {
                        id = R.id.ctrl_left_id
                        backgroundColor = Color.TRANSPARENT
                        text = ctx.getString(R.string.note_controller)
                        textSize = 12f
                        isDuplicateParentStateEnabled = true
                        setTextColor(ContextCompat.getColorStateList(ctx, ctrl_text_selector))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentBottom()
                        centerHorizontally()
                        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    }
                    imageView {
                        isDuplicateParentStateEnabled = true
                        bindSelf(MainActivityViewModel::selected){it.selected.value}.toView(this){
                            view,value->
                            if (value!=null){
                                if (value==0){
                                    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.left_note_highlight_icon))
                                }else{
                                    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.note_selector))
                                }
                            }
                        }
                        backgroundColor = Color.TRANSPARENT
                        scaleType = ImageView.ScaleType.FIT_XY
                        adjustViewBounds = true
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                        alignParentTop()
                        centerHorizontally()
                        above(tv)
                    }

                    lparams {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                }.lparams {
                    addRule(RelativeLayout.ALIGN_BOTTOM, R.id.ctrl_button)
                    leftMargin = screenGap
                    alignParentLeft()
                    width = wrapContent
                    height = wrapContent
                }
                relativeLayout {
                    id = R.id.ctrl_text_right
                    isClickable = true
                    bindSelf(this, viewModelSafe.clickListener.value)
                            .notifyOnClickObservable { it.clickListener }
                    backgroundColor = Color.TRANSPARENT
                    val tv = textView {
                        id = R.id.ctrl_right_id
                        text = ctx.getString(R.string.profile_controller)
                        backgroundColor = Color.TRANSPARENT
                        textSize = 12f
                        isDuplicateParentStateEnabled = true
                        setTextColor(ContextCompat.getColorStateList(ctx, ctrl_text_selector))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentBottom()
                        centerHorizontally()
                        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                    }

                    imageView {
                        isDuplicateParentStateEnabled = true
                        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile_selector))

                        bindSelf(MainActivityViewModel::selected){it.selected.value}.toView(this){
                            view,value->
                            if (value!=null){
                                if (value==1){
                                    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.right_profile_highlight_icon))
                                }else{
                                    setImageDrawable(ContextCompat.getDrawable(context, R.drawable.profile_selector))
                                }
                            }
                        }
                        backgroundColor = Color.TRANSPARENT
                        scaleType = ImageView.ScaleType.FIT_XY
                        adjustViewBounds = true
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        setHorizontalGravity(Gravity.CENTER_HORIZONTAL)
                        alignParentTop()
                        centerHorizontally()
                        above(tv)
                    }



                    lparams {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                }.lparams {
                    addRule(RelativeLayout.ALIGN_BOTTOM, R.id.ctrl_button)
                    rightMargin = screenGap
                    alignParentRight()
                    width = wrapContent
                    height = wrapContent
                }
            }.lparams {
                width = matchParent
                clipChildren = false
                weight = 0.1f
                height = 0
            }

            lparams {
                width = matchParent
                height = matchParent
            }
        }
    }

    private fun getGradientDrawable(ctx: Context): Drawable? {
        // 创建渐变的shape drawable
        val colors = intArrayOf(
                Color.parseColor("#828b8787"),
                Color.parseColor("#46cccbcc")
        )//分别为开始颜色，中间夜色，结束颜色
        val gd = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)//创建drawable
        gd.gradientType = GradientDrawable.LINEAR_GRADIENT
        return gd
    }

    private fun getLayerDrawable(height: Int, percent: Float, btw: Int, ctx: Context): LayerDrawable {
        val ElementTopCircle = 0
        val ElementOutCircle = 1
        val ElementBottomCover = 2
        val ElementCentral = 3

        val elementTopCircle = ShapeDrawable(OvalShape())
        elementTopCircle.paint.color = Color.parseColor("#b4b3b3")
        elementTopCircle.paint.strokeWidth = 1f// dipValue(1, ctx).toFloat()
        elementTopCircle.paint.style = Paint.Style.STROKE
        elementTopCircle.paint.isAntiAlias = true

        val elementOutCircle = ShapeDrawable(OvalShape())
        elementOutCircle.paint.color = Color.WHITE
        elementOutCircle.paint.style = Paint.Style.FILL
        elementOutCircle.paint.isAntiAlias = true

        val elementBottomCover = ShapeDrawable(RectShape())
        elementBottomCover.paint.color = Color.WHITE
        elementBottomCover.paint.style = Paint.Style.FILL
        elementBottomCover.paint.isAntiAlias = true

        val elementsCentral = StateListDrawable()


        val press = ContextCompat.getDrawable(ctx, R.drawable.new_note_highlight_icon) as BitmapDrawable
        val normal = ContextCompat.getDrawable(ctx, R.drawable.new_note_icon) as BitmapDrawable
        press.paint.isAntiAlias = true
        normal.paint.isAntiAlias = true

        elementsCentral.addState(intArrayOf(android.R.attr.state_pressed), press)
        elementsCentral.addState(intArrayOf(), normal)

        val layerDrawable = LayerDrawable(arrayOf<Drawable>(elementTopCircle, elementOutCircle, elementBottomCover, elementsCentral))
        val h = (height * percent + 1).toInt()

        layerDrawable.setLayerInset(ElementTopCircle, btw, 0, btw, 0)
        layerDrawable.setLayerInset(ElementOutCircle, 1 + btw, 1, 1 + btw, 1)
        layerDrawable.setLayerInset(ElementBottomCover, 0, h, 0, 0)
        layerDrawable.setLayerInset(ElementCentral, dipValue(8, ctx) + btw, dipValue(8, ctx), dipValue(8, ctx) + btw, dipValue(8, ctx))
        return layerDrawable
    }


}

