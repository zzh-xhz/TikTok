package com.bytedance.tiktok.widget.component

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.bytedance.tiktok.R
import com.bytedance.tiktok.utils.AnimUtils
import com.bytedance.tiktok.view.LikeView
import xyz.doikki.videoplayer.controller.ControlWrapper
import xyz.doikki.videoplayer.controller.IControlComponent
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.util.L
import java.util.Random

class TikTokView : FrameLayout, IControlComponent {
    private var onLikeListener: OnLikeListener? = null
    private var thumb: ImageView? = null
    private var mPlayBtn: ImageView? = null
    private var mControlWrapper: ControlWrapper? = null
    private var mScaledTouchSlop = 0
    private var mStartX = 0
    private var mStartY = 0
    private var gestureDetector: GestureDetector? = null
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
        LayoutInflater.from(context).inflate(R.layout.layout_tiktok_controller, this, true)
        thumb = findViewById(R.id.iv_thumb)
        mPlayBtn = findViewById(R.id.play_btn)
//        setOnClickListener { togglePlay() }
        mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

        gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                addLikeView(e)
                onLikeListener!!.onLikeListener()
                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                togglePlay()
                return true
            }
        })
        setOnTouchListener { v: View?, event: MotionEvent ->
            gestureDetector!!.onTouchEvent(event)
            true
        }
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
    fun togglePlay() {
        if (mControlWrapper == null) {
            return
        }
        mControlWrapper!!.togglePlay()
    }

    /**
     * 解决点击和VerticalViewPager滑动冲突问题
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = event.x.toInt()
                mStartY = event.y.toInt()
                return true
            }

            MotionEvent.ACTION_UP -> {
                val endX = event.x.toInt()
                val endY = event.y.toInt()
                if (Math.abs(endX - mStartX) < mScaledTouchSlop
                    && Math.abs(endY - mStartY) < mScaledTouchSlop
                ) {
                    performClick()
                }
            }
        }
        return false
    }

    override fun attach(controlWrapper: ControlWrapper) {
        mControlWrapper = controlWrapper
    }

    override fun getView(): View? {
        return this
    }

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation) {}
    override fun onPlayStateChanged(playState: Int) {
        when (playState) {
            VideoView.STATE_IDLE -> {
                L.e("STATE_IDLE " + hashCode())
                thumb!!.visibility = VISIBLE
            }

            VideoView.STATE_PLAYING -> {
                L.e("STATE_PLAYING " + hashCode())
                thumb!!.visibility = GONE
                mPlayBtn!!.visibility = GONE
            }

            VideoView.STATE_PAUSED -> {
                L.e("STATE_PAUSED " + hashCode())
                thumb!!.visibility = GONE
                mPlayBtn!!.visibility = VISIBLE
            }

            VideoView.STATE_PREPARED -> L.e("STATE_PREPARED " + hashCode())
            VideoView.STATE_ERROR -> {
                L.e("STATE_ERROR " + hashCode())
                Toast.makeText(context, R.string.dkplayer_error_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPlayerStateChanged(playerState: Int) {}
    override fun setProgress(duration: Int, position: Int) {}
    override fun onLockStateChanged(isLocked: Boolean) {}
    interface OnLikeListener {
        fun onLikeListener()
    }

    /**
     * 设置双击点赞事件
     * @param onLikeListener
     */
    fun setOnLikeListener(onLikeListener: OnLikeListener?) {
        this.onLikeListener = onLikeListener
    }
}