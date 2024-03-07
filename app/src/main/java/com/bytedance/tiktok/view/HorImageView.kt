package com.bytedance.tiktok.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import com.androidkun.xtablayout.XTabLayout
import com.bytedance.tiktok.R
import com.github.chrisbanes.photoview.PhotoView

/**
 * 图片
 */
class HorImageView : FrameLayout {
    private var viewPager: HackyViewPager? = null
    private var tablayout: XTabLayout? = null

    private var viewPagerDirectionListener: onViewPagerDirectionListener? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_view_pager, this, true)
        viewPager = findViewById(R.id.view_pager)
        tablayout = findViewById(R.id.xTablayout)
        viewPager?.adapter = SamplePagerAdapter()
        viewPager?.setOnScrollListener(object :HackyViewPager.onScrollListener{
            override fun onScroll(isHorizontal: Boolean) {
                if (viewPagerDirectionListener != null){
                    viewPagerDirectionListener?.onDirectionListener(isHorizontal)
                }
            }

        })
        // 将ViewPager与TabLayout关联起来
        tablayout?.setupWithViewPager(viewPager);
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        viewPager?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }
    interface onViewPagerDirectionListener {
        fun onDirectionListener(isHorizontal: Boolean)
    }

    /**
     * 设置滑动方向
     * @param onViewPagerDirectionListener
     */
    fun setOnViewPagerDirectionListener(viewPagerDirectionListener: onViewPagerDirectionListener ?) {
        this.viewPagerDirectionListener = viewPagerDirectionListener
    }
    internal class SamplePagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return sDrawables.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {
            val photoView = PhotoView(container.context)
            photoView.setImageResource(sDrawables[position])
            // 现在只需将PhotoView添加到ViewPager并返回即可
            container.addView(
                photoView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            return photoView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        companion object {
            private val sDrawables = intArrayOf(
                R.mipmap.head1, R.mipmap.head2, R.mipmap.head3,
                R.mipmap.head4, R.mipmap.head5, R.mipmap.head6
            )
        }
    }
}