package com.bytedance.tiktok.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.danikula.videocache.Logger

class HackyViewPager : ViewPager {
    private var startX = 0f
    private var startY = 0f
    private var isHorizontalScroll = false
    private var scrollListener:onScrollListener? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            false
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 记录触摸起始点
                startX = x
                startY = y
                isHorizontalScroll = false
            }

            MotionEvent.ACTION_MOVE -> {
                // 根据触摸点的移动距离判断是水平滑动还是垂直滑动
                val deltaX = Math.abs(x - startX)
                val deltaY = Math.abs(y - startY)
                if (!isHorizontalScroll && deltaX > deltaY) {
                    // 如果水平滑动距离大于垂直滑动距离，则认为是水平滑动
                    isHorizontalScroll = true
                }
            }
        }
        Logger.error("测试 isHorizontalScroll" +isHorizontalScroll)
        if (scrollListener != null){
            scrollListener?.onScroll(isHorizontalScroll)
        }
//        return if (isHorizontalScroll) {
//            // 如果是水平滑动，则交给ViewPager处理
//            super.onTouchEvent(event)
//        } else {
//            // 如果是垂直滑动，则不处理，交给父View处理
//            false
//        }
        return  super.onTouchEvent(event)
    }
    interface onScrollListener {
        fun onScroll(isHorizontal: Boolean)
    }
    /**
     * 设置滑动方向
     * @param onScrollListener
     */
    fun setOnScrollListener(scrollListener: onScrollListener?) {
        this.scrollListener = scrollListener
    }

}