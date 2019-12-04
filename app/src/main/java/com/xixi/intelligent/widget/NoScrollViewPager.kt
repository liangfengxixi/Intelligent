package com.xixi.intelligent.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * Created by xixi on 2019/12/2.
 */
class NoScrollViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private val isScroll: Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev)
        } else {
            return false
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (isScroll) {
            return super.onTouchEvent(ev)
        } else {
            return true// 可行,消费,拦截事件
        }
    }

}