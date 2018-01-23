package com.superfactory.library.Graphics.Badge

import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.view.View

/**
 * @author chqiu
 * Email:qstumn@163.com
 */

interface Badge {

    var badgeNumber: Int

    var badgeText: String?

    var isExactMode: Boolean

    var isShowShadow: Boolean

    var badgeBackgroundColor: Int

    var badgeBackground: Drawable?

    var badgeTextColor: Int

    var isDraggable: Boolean

    var badgeGravity: Int

    var dragCenter: PointF?

    var targetView: View?

    fun setBadgeNumber(badgeNum: Int): Badge

    fun setBadgeText(badgeText: String): Badge

    fun setExactMode(isExact: Boolean): Badge

    fun setShowShadow(showShadow: Boolean): Badge

    fun setBadgeBackgroundColor(color: Int): Badge

    fun stroke(color: Int, width: Float, isDpValue: Boolean): Badge

    fun setBadgeBackground(drawable: Drawable): Badge

    fun setBadgeBackground(drawable: Drawable, clip: Boolean): Badge

    fun setBadgeTextColor(color: Int): Badge

    fun setBadgeTextSize(size: Float, isSpValue: Boolean): Badge

    fun getBadgeTextSize(isSpValue: Boolean): Float

    fun setBadgePadding(padding: Float, isDpValue: Boolean): Badge

    fun getBadgePadding(isDpValue: Boolean): Float

    fun setBadgeGravity(gravity: Int): Badge

    fun setGravityOffset(offset: Float, isDpValue: Boolean): Badge

    fun setGravityOffset(offsetX: Float, offsetY: Float, isDpValue: Boolean): Badge

    fun getGravityOffsetX(isDpValue: Boolean): Float

    fun getGravityOffsetY(isDpValue: Boolean): Float

    fun setOnDragStateChangedListener(l: ((dragState: Int, badge: Badge, targetView: View?) -> Unit)?): Badge

    fun bindTarget(view: View?): Badge

    fun hide(animate: Boolean)

    companion object {
        val STATE_START = 1
        val STATE_DRAGGING = 2
        val STATE_DRAGGING_OUT_OF_RANGE = 3
        val STATE_CANCELED = 4
        val STATE_SUCCEED = 5
    }
}
