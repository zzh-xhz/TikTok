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
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.utils.AnimUtils
import com.bytedance.tiktok.utils.AutoLinkHerfManager
import com.bytedance.tiktok.utils.NumUtils
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.bytedance.tiktok.view.CircleImageView
import com.bytedance.tiktok.view.IconFontTextView
import com.bytedance.tiktok.view.autolinktextview.AutoLinkTextView
import xyz.doikki.videoplayer.controller.ControlWrapper
import xyz.doikki.videoplayer.controller.IControlComponent
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.util.L
import xyz.doikki.videoplayer.util.PlayerUtils
import java.util.Random

class TikTokView : FrameLayout, IControlComponent {
    private var onLikeListener: OnLikeListener? = null
    private var llBootom: LinearLayout? = null
    private var tvNickname: TextView? = null
    private var autoLinkTextView: AutoLinkTextView? = null
    private var llRight: LinearLayout? = null
    private var ivHead: CircleImageView? = null
    private var ivFocus: ImageView? = null
    private var rlLike: RelativeLayout? = null
    private var ivLike: IconFontTextView? = null
    private var animationView: LottieAnimationView? = null
    private var tvLikecount: TextView? = null
    private var ivComment: IconFontTextView? = null
    private var tvCommentcount: TextView? = null
    private var ivShare: IconFontTextView? = null
    private var tvSharecount: TextView? = null
    private var rlRecord: RelativeLayout? = null
    private var ivRecord: ImageView? = null
    private var ivHeadAnim: CircleImageView? = null
    private var thumb: ImageView? = null
    private var mPlayBtn: ImageView? = null
    private var mControlWrapper: ControlWrapper? = null
    private var mScaledTouchSlop = 0
    private var mStartX = 0
    private var mStartY = 0
//    private var gestureDetector: GestureDetector? = null
    private var listener: OnVideoControllerListener? = null
    private val angles = intArrayOf(-30, 0, 30)
    private var videoData: VideoBean? = null
    private var tvFullScreenView :TextView ? = null
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
        tvFullScreenView = findViewById(R.id.tv_full_screen_view);
        thumb = findViewById(R.id.iv_thumb)
        mPlayBtn = findViewById(R.id.play_btn)
        llBootom = findViewById(R.id.ll_bootom);
        tvNickname = findViewById(R.id.tvNickname);
        autoLinkTextView = findViewById(R.id.autoLinkTextView);
        llRight = findViewById(R.id.ll_right);
        ivHead = findViewById(R.id.ivHead);
        ivFocus = findViewById(R.id.ivFocus);
        rlLike = findViewById(R.id.rlLike);
        ivLike = findViewById(R.id.ivLike);
        animationView = findViewById(R.id.animationView);
        tvLikecount = findViewById(R.id.tvLikecount);
        ivComment = findViewById(R.id.ivComment);
        tvCommentcount = findViewById(R.id.tvCommentcount);
        ivShare = findViewById(R.id.ivShare);
        tvSharecount = findViewById(R.id.tvSharecount);
        rlRecord = findViewById(R.id.rlRecord);
        ivRecord = findViewById(R.id.ivRecord);
        ivHeadAnim = findViewById(R.id.ivHeadAnim);
        init()
        mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    fun setListener(listener: OnVideoControllerListener?) {
        this.listener = listener
    }

    /**
     * Returns the debugging information string to be shown by the target [TextView].
     */
    protected fun setAdjustingViews() {
        if (isLandscapeVideo() && tvFullScreenView?.visibility != visibility ){
            tvFullScreenView?.visibility = visibility
            return
        }
        if (!isLandscapeVideo() && tvFullScreenView?.visibility != GONE ){
            tvFullScreenView?.visibility = GONE
            return
        }
    }
     fun isLandscapeVideo() :Boolean{
       return mControlWrapper!!.videoSize[0] > mControlWrapper!!.videoSize[1]
    }
    fun setVideoData(videoData: VideoBean) {
        this.videoData = videoData
        ivHead!!.setImageResource(videoData.userBean!!.head)
        tvNickname!!.text = "@" + videoData.userBean!!.nickName
        autoLinkTextView?.let { AutoLinkHerfManager.setContent(videoData.content, it) }
        ivHeadAnim!!.setImageResource(videoData.userBean!!.head)
        tvLikecount!!.text = NumUtils.numberFilter(videoData.likeCount)
        tvCommentcount!!.text = NumUtils.numberFilter(videoData.commentCount)
        tvSharecount!!.text = NumUtils.numberFilter(videoData.shareCount)
        animationView!!.setAnimation("like.json")
        //点赞状态
        if (videoData.isLiked) {
            ivLike!!.setTextColor(resources.getColor(R.color.color_FF0041))
        } else {
            ivLike!!.setTextColor(resources.getColor(R.color.white))
        }

        //关注状态
        if (videoData.isFocused) {
            ivFocus!!.visibility = GONE
        } else {
            ivFocus!!.visibility = VISIBLE
        }
        // 默认都是隐藏 根据进度条监听  得出结果是否是 横屏竖屏视频正常应该是 后台接口给出
        if (tvFullScreenView?.visibility == VISIBLE){
            tvFullScreenView?.visibility = GONE
        }
    }

    private fun init() {
        ivHead!!.setOnClickListener {
            if (listener == null) {
                return@setOnClickListener
            }
            listener!!.onHeadClick()
        }
        ivComment!!.setOnClickListener {
            if (listener == null) {
                return@setOnClickListener
            }
            listener!!.onCommentClick()
        }
        ivShare!!.setOnClickListener {
            if (listener == null) {
                return@setOnClickListener
            }
            listener!!.onShareClick()
        }
        rlLike!!.setOnClickListener {
            if (listener == null) {
                return@setOnClickListener
            }
            listener!!.onLikeClick()
            like()
        }
        ivFocus!!.setOnClickListener {
            if (!videoData!!.isFocused) {
                videoData!!.isLiked = true
                ivFocus!!.visibility = GONE
            }
        }

        tvFullScreenView!!.setOnClickListener {
            if (listener == null) {
                return@setOnClickListener
            }
            toggleFullScreen()
            listener!!.onFullScreenClick()
        }
        setRotateAnim()
    }

    /**
     * 循环旋转动画
     */
    private fun setRotateAnim() {
        val rotateAnimation = RotateAnimation(
            0f, 359f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.duration = 8000
        rotateAnimation.interpolator = LinearInterpolator()
        rlRecord!!.startAnimation(rotateAnimation)
    }

    public fun setOnTouchEvent(event: MotionEvent) {
//        gestureDetector!!.onTouchEvent(event)
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

    override fun onVisibilityChanged(isVisible: Boolean, anim: Animation) {


    }
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

    override fun onPlayerStateChanged(playerState: Int) {
        when (playerState) {
            VideoView.PLAYER_NORMAL -> {
                tvFullScreenView?.isSelected = false;
            }
            VideoView.PLAYER_FULL_SCREEN -> {
                tvFullScreenView?.isSelected = true;
            }
        }
    }
    override fun setProgress(duration: Int, position: Int) {
        if (llBootom?.visibility == View.VISIBLE){
            setAdjustingViews()
        }
    }
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

    /**
     * 点赞动作
     */
    fun like() {
        if (!videoData!!.isLiked) {
            //点赞
            animationView!!.visibility = VISIBLE
            animationView!!.playAnimation()
            ivLike!!.setTextColor(resources.getColor(R.color.color_FF0041))
        } else {
            //取消点赞
            animationView!!.visibility = INVISIBLE
            ivLike!!.setTextColor(resources.getColor(R.color.white))
        }
        videoData!!.isLiked = !videoData!!.isLiked
    }
    /**
     * 横竖屏切换
     */
    private fun toggleFullScreen() {
        val activity = PlayerUtils.scanForActivity(context)
        mControlWrapper?.toggleFullScreen(activity)
        // 下面方法会根据适配宽高决定是否旋转屏幕
//        mControlWrapper?.toggleFullScreenByVideoSize(activity)
    }
}