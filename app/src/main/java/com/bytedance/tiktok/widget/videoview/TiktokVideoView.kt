package com.bytedance.tiktok.widget.videoview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.bytedance.tiktok.bean.VideoBean
import xyz.doikki.videoplayer.util.PlayerUtils

/**
 * 可播放在线和本地url
 * Created by Doikki on 2022/7/18.
 */
class TiktokVideoView : ExoVideoView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private var defaultScaleFactor = 1.0f
    private lateinit var scaleFactorChangeListener : OnScaleFactorChangeListener

    init {
        // 初始化ScaleGestureDetector
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

        // 设置TouchListener来处理触摸事件
        setOnTouchListener { _, motionEvent ->
            // 将触摸事件传递给ScaleGestureDetector
            scaleGestureDetector.onTouchEvent(motionEvent)
            true
        }
        defaultScaleFactor = this.scaleX
    }

    public override fun initPlayer() {
        super.initPlayer()
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        // 处理双指离开时的回弹效果
        if ((motionEvent.pointerCount == 1 || motionEvent.pointerCount == 2 )  && motionEvent.action == MotionEvent.ACTION_UP) {
            defaultScaleFactorAnimate()
        }
        super.onTouchEvent(motionEvent)
        if (motionEvent.actionMasked == MotionEvent.ACTION_POINTER_UP ||  motionEvent.actionMasked ==MotionEvent.ACTION_POINTER_DOWN){
            scaleFactorChangeListener.onScale(scaleFactor)
        }
        return true
    }
      fun defaultScaleFactorAnimate() {
        this.animate().scaleX(defaultScaleFactor).scaleY(defaultScaleFactor).setDuration(200).start()
        scaleFactor = defaultScaleFactor
    }

    fun setAddData(mVideoList: MutableList<VideoBean>) {
        for (videoUrl in mVideoList) {
            mHelper.getMediaSource(videoUrl.videoRes, true)
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            // 缩放因子
            scaleFactor *= detector.scaleFactor
            // 设置最小和最大缩放值
            scaleFactor = scaleFactor.coerceIn(0.5f, 6.0f)
            // 应用缩放到视图
            scaleX = scaleFactor
            scaleY = scaleFactor
            return true
        }
    }
    fun setScaleFactorChange(scaleFactorChangeListener: OnScaleFactorChangeListener) {
        this.scaleFactorChangeListener = scaleFactorChangeListener
    }

    public interface OnScaleFactorChangeListener {

        fun onScale(detector: Float)
    }



}