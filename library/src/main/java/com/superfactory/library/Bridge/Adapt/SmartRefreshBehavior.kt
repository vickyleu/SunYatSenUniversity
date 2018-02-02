//package com.superfactory.library.Bridge.Adapt
//
//import android.support.design.widget.AppBarLayout
//import android.support.design.widget.CoordinatorLayout
//import android.support.v4.view.ViewCompat
//import android.view.View
//import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.child
//import android.support.v4.view.ViewCompat.setTranslationY
//import android.support.v4.view.ViewCompat.getTranslationY
//
//
//
//
///**
// * Created by vicky on 2018.02.02.
// *
// * @Author vicky
// * @Date 2018年02月02日  14:54:45
// * @ClassName 这里输入你的类名(或用途)
// */
//class SmartRefreshBehavior : AppBarLayout.ScrollingViewBehavior() {
//
//    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
//        return dependency is AppBarLayout
//    }
//
//    //1.判断滑动的方向 我们需要垂直滑动
//    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View,
//                                     nestedScrollAxes: Int, type: Int): Boolean {
//        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
//    }
//
//    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
//
//
//        if (dependency!=null&&dependency is AppBarLayout) {
//            val translationY = Math.abs(dependency.translationY)
//            child?.translationY = translationY
//
////            super.onDependentViewChanged(parent, child, dependency)
//
//
////            val translationY = Math.min(0f, (dependency.getTranslationY() - dependency.getHeight()))
////            child?.translationY = translationY
//        }
//        return true
//    }
//
//}
