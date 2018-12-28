package com.common.cook.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.common.cook.core.util.IImageLoader
import com.common.cook.core.util.PickToast

/**
 * Desc:定义一些常用的扩展方法
 * Author: QiuRonaC
 * Date: 2018/9/27/027
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */

fun Context.gColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun View.gColor(@ColorRes color: Int) = ContextCompat.getColor(context, color)

fun Context.gDrawable(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

fun View.setBgDrawable(@DrawableRes drawable: Drawable) = ViewCompat.setBackground(this, drawable)

fun Float.dp() = SizeUtils.dp2px(this)

fun ImageView.loadAvatar(url: String?) {
    IImageLoader.loadAvatar(this, url)
}

fun ImageView.load(url: String) {
    IImageLoader.loadImage(this, url)
}

fun ImageView.load(@DrawableRes res: Int) {
    setImageResource(res)
}

fun TextView.setDrawableSide(start: Int = 0, top: Int = 0, end: Int = 0, bottom: Int = 0) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}

fun View.setPaddingSide(start: Int = paddingStart, top: Int = paddingTop
                        , end: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(start, top, end, bottom)
}

fun View.getActivity(): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun View.marginStart(start: Int = -1): Int {
    layoutParams?.let {
        it as ViewGroup.MarginLayoutParams
        if (start != -1) {
            it.marginStart = start
            requestLayout()
        }
        return it.marginStart
    }
    return 0
}

fun View.marginTop(top: Int = -1): Int {
    layoutParams?.let {
        it as ViewGroup.MarginLayoutParams
        if (top != -1) {
            it.topMargin = top
            requestLayout()
        }
        return it.topMargin
    }
    return 0
}

fun View.marginEnd(end: Int = -1): Int {
    layoutParams?.let {
        it as ViewGroup.MarginLayoutParams
        if (end != -1) {
            it.marginEnd = end
            requestLayout()
        }
        return it.marginEnd
    }
    return 0
}

fun View.marginBottom(bottom: Int = -1): Int {
    layoutParams?.let {
        it as ViewGroup.MarginLayoutParams
        if (bottom != -1) {
            it.bottomMargin = bottom
            requestLayout()
        }
        return it.bottomMargin
    }
    return 0
}

fun Any.showToast(text: String) {
    PickToast.show(text)
}