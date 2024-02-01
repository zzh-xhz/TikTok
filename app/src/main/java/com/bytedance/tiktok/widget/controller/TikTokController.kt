package com.bytedance.tiktok.widget.controller

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.utils.AnimUtils
import com.bytedance.tiktok.widget.component.DebugInfoView
import com.bytedance.tiktok.widget.component.TiktokControlView
import xyz.doikki.videocontroller.component.CompleteView
import xyz.doikki.videocontroller.component.ErrorView
import xyz.doikki.videoplayer.controller.GestureVideoController
import java.util.Random

/**
 * 抖音
 * Created by Doikki on 2018/1/6.
 */
class TikTokController : GestureVideoController {
    private var tiktokControlView: TiktokControlView? = null
    private val angles = intArrayOf(-30, 0, 30)
    /** 图片大小  */
    private val likeViewSize = 330
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setCanChangePosition(false)
        setEnableInNormal(false)
        setGestureEnabled(false)
        setDoubleTapTogglePlayEnabled(false)
        //不监听设备方向
        mOrientationHelper.setOnOrientationChangeListener(null)
        tiktokControlView = TiktokControlView(context)
        addControlComponent(tiktokControlView)
        //显示调试信息
        addControlComponent(DebugInfoView(context))
    }

    override fun getLayoutId(): Int {
        return 0
    }

    override fun initView() {
        super.initView()
        val completeView = CompleteView(context)
        val errorView = ErrorView(context)
        addControlComponent(completeView)
        addControlComponent(errorView)

    }

    override fun showNetWarning(): Boolean {
        //显示移动网络播放警告
        return false
    }

    /**
     * 单击
     */
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        if (!isLocked && isInPlaybackState) {
            togglePlay()
        }
        return true
    }

    /**
     * 双击
     */
    override fun onDoubleTap(e: MotionEvent): Boolean {
        addLikeView(e)
        return true
    }

    override fun onBackPressed(): Boolean {
        return if (mControlWrapper.isFullScreen) {
            stopFullScreen()
        } else super.onBackPressed()
    }
    private fun addLikeView(e: MotionEvent) {
        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.ic_like)
        addView(imageView)
        val layoutParams = FrameLayout.LayoutParams(likeViewSize, likeViewSize)
        layoutParams.leftMargin = e.x.toInt() - likeViewSize / 2
        layoutParams.topMargin = e.y.toInt() - likeViewSize
        imageView.layoutParams = layoutParams
        playAnim(imageView)
    }

    private fun playAnim(view: View) {
        val animationSet = AnimationSet(true)
        val degrees = angles[Random().nextInt(3)]
        animationSet.addAnimation(AnimUtils.rotateAnim(0, 0, degrees.toFloat()))
        animationSet.addAnimation(AnimUtils.scaleAnim(100, 2f, 1f, 0))
        animationSet.addAnimation(AnimUtils.alphaAnim(0f, 1f, 100, 0))
        animationSet.addAnimation(AnimUtils.scaleAnim(500, 1f, 1.8f, 300))
        animationSet.addAnimation(AnimUtils.alphaAnim(1f, 0f, 500, 300))
        animationSet.addAnimation(AnimUtils.translationAnim(500, 0f, 0f, 0f, -400f, 300))
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Handler().post { removeView(view) }
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        view.startAnimation(animationSet)
    }
}