import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.Tiktok3Adapter
import com.bytedance.tiktok.bean.CurUserBean
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.MainPageChangeEvent
import com.bytedance.tiktok.bean.MainTabChangeEvent
import com.bytedance.tiktok.bean.PauseVideoEvent
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.databinding.FragmentFriendBinding
import com.bytedance.tiktok.dialog.CommentDialog
import com.bytedance.tiktok.dialog.ShareDialog
import com.bytedance.tiktok.utils.DataUtil
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.bytedance.tiktok.utils.RxBus
import com.bytedance.tiktok.utils.Utils
import com.bytedance.tiktok.utils.cache.PreloadManager
import com.bytedance.tiktok.view.HorImageView
import com.bytedance.tiktok.widget.VerticalViewPager
import com.bytedance.tiktok.widget.component.TikTokView
import com.bytedance.tiktok.widget.controller.TikTokController
import com.bytedance.tiktok.widget.render.TikTokRenderViewFactory
import com.bytedance.tiktok.widget.render.gl2.GLSurfaceRenderView2
import com.bytedance.tiktok.widget.videoview.TiktokVideoView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lib.base.dialog.BaseVideoBottomSheetDialog
import com.lib.base.ui.BaseBindingPlayerFragment
import kotlinx.coroutines.NonDisposableHandle.parent
import xyz.doikki.videoplayer.player.BaseVideoView.OnStateChangeListener
import xyz.doikki.videoplayer.player.BaseVideoView.SimpleOnStateChangeListener
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.util.L


/**
 * create by libo
 * create on 2020-05-19
 * description 朋友播放页
 */
class FriendFragment : BaseBindingPlayerFragment<TiktokVideoView, FragmentFriendBinding>({
    FragmentFriendBinding.inflate(it)
}) {
    private var commentDialog: CommentDialog? = null
    private var shareDialog: ShareDialog? = null
    private var isLandscapeVideo: Boolean? = false;

    private val renderView by lazy {
        GLSurfaceRenderView2(context)
    }
    private  var photoView :  HorImageView? = null
    /**
     * 当前播放位置
     */
    private var mCurPos = 0
    private val mVideoList: MutableList<VideoBean> = ArrayList()
    private var mTiktok3Adapter: Tiktok3Adapter? = null
    private var mPreloadManager: PreloadManager? = null
    private var mController: TikTokController? = null
    private var mViewPagerImpl: RecyclerView? = null
    var loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(32 * 1024, 1024 * 1024, 1024, 1024)
        .build()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPreloadManager = PreloadManager.getInstance(requireActivity())
        initVideoView()
        initImageView()
        DataUtil.getTiktokDataFromAssets(requireActivity()).forEachIndexed { index, videoBean ->
            //开始预加载
            PreloadManager.getInstance(context).addPreloadTask(videoBean.videoRes, index)
        }
    }
    override fun onResume() {
        super.onResume()
        mVideoView?.start()
    }

    override fun onPause() {
        super.onPause()
        mVideoView?.pause()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        addData(null)
        val index = 0
        binding.viewPager2.post {
            if (index == 0) {
                startPlay(0)
            } else {
                binding.viewPager2.setCurrentItem(index, false)
            }
        }
        setRefreshEvent()
        RxBus.getDefault().post(MainTabChangeEvent(false))
    }
    private fun initImageView(){
        photoView = HorImageView(requireActivity())
        photoView?.setOnViewPagerDirectionListener(object : HorImageView.onViewPagerDirectionListener{
            override fun onDirectionListener(isHorizontal: Boolean) {
                if (mCurPos == 3){
                    doubleFingerStatus(!isHorizontal);
                }
            }

        })
    }

    private fun initVideoView() {
        mVideoView = TiktokVideoView(requireActivity())
        mVideoView!!.setAddData(mVideoList);
        mVideoView!!.setLoadControl(loadControl)
        mVideoView!!.setCacheEnabled(true)
        mVideoView!!.initPlayer()
        mVideoView!!.setLooping(true)
        //以下只能二选一，看你的需求
        mVideoView!!.setRenderViewFactory(TikTokRenderViewFactory.create())
        //        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        mController = TikTokController(requireActivity())
        mVideoView!!.setVideoController(mController)
        mVideoView!!.addOnStateChangeListener(mOnStateChangeListener)
//        mVideoView!!.setRenderViewFactory(object : RenderViewFactory() {
//            override fun createRenderView(context: Context?): IRenderView {
//                return renderView
//            }
//        })
//        // 设置滤镜
//        renderView.setGlFilter(
//            GlFilterGroup(
//            // 水印
//            GlWatermarkFilter(BitmapFactory.decodeResource(resources, R.mipmap.add_focus)),
//            GlSepiaFilter(),
//            GlSharpenFilter()
//        )
//        )
    }

    private fun initViewPager() {
        binding.viewPager2.offscreenPageLimit = 4
        mTiktok3Adapter = Tiktok3Adapter(mVideoList)
        binding.viewPager2.adapter = mTiktok3Adapter
        binding.viewPager2.overScrollMode = View.OVER_SCROLL_NEVER
        binding.viewPager2.registerOnPageChangeCallback(pageChangeCallback)
        //ViewPage2内部是通过RecyclerView去实现的，它位于ViewPager2的第0个位置
        mViewPagerImpl = binding.viewPager2.getChildAt(0) as RecyclerView
    }

    private fun startPlay(position: Int) {
        closeDialog()
        val count = mViewPagerImpl?.childCount
        for (i in 0 until count!!) {
            val itemView = mViewPagerImpl?.getChildAt(i)
            val viewHolder = itemView?.tag as Tiktok3Adapter.ViewHolder
            //评论点赞事件
            likeShareEvent(viewHolder.mTikTokView)
            //切换播放视频的作者主页数据
            RxBus.getDefault().post(CurUserBean(DataCreate.datas[position].userBean!!))
            //手指双指监听
            tiktokVideoEvent(viewHolder.mTikTokView)
            //双指滑动监听
            videoViewLister(
                viewHolder.mTikTokView,
                viewHolder.llBootom
            )
            mController?.setData(DataCreate.datas[position])
            if (viewHolder.mPosition == position) {
                mVideoView?.release()
                Utils.removeViewFormParent(mVideoView)
                if (position == 3){
                    Utils.removeViewFormParent(photoView)
                    mController!!.addControlComponent(viewHolder.mTikTokView, true)
                    viewHolder.mPlayerContainer.addView(photoView, 0)
                    if (viewHolder.tvFullScreenView.visibility != View.INVISIBLE){
                        viewHolder.tvFullScreenView.visibility = View.INVISIBLE
                    }
                    if (viewHolder.mThumb.visibility != View.INVISIBLE){
                        viewHolder.mThumb.visibility = View.INVISIBLE
                    }
                }else{
                    val tiktokBean = mVideoList[position]
                    val playUrl = mPreloadManager!!.getPlayUrl(tiktokBean.videoRes)
                    L.i("startPlay: position: $position  url: $playUrl")
                    mVideoView!!.setUrl(playUrl)
                    //请点进去看isDissociate的解释
                    mController!!.addControlComponent(viewHolder.mTikTokView, true)
                    viewHolder.mPlayerContainer.addView(mVideoView, 0)
                    mVideoView!!.start()
                }
                mCurPos = position
                break
            }
        }
    }

    fun addData(view: View?) {
        val size = mVideoList.size
        mVideoList.addAll(DataUtil.getTiktokDataFromAssets(requireActivity()))
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        mTiktok3Adapter!!.notifyItemRangeChanged(size, mVideoList.size)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPreloadManager!!.removeAllPreloadTask()
//        //清除缓存，实际使用可以不需要清除，这里为了方便测试
//        ProxyVideoCacheManager.clearAllCache(requireActivity())
    }


    private val pageChangeCallback = object : OnPageChangeCallback() {
        private var mCurItem = 0

        /**
         * VerticalViewPager是否反向滑动
         */
        private var mIsReverseScroll = false
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            if (position == mCurItem) {
                return
            }
            mIsReverseScroll = position < mCurItem
        }

        override fun onPageSelected(position: Int) {
            if (position == mCurPos) return
            binding.viewPager2.post(Runnable { startPlay(position) })
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                mCurItem = binding.viewPager2.getCurrentItem()
            }
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                mPreloadManager!!.resumePreload(mCurPos, mIsReverseScroll)
            } else {
                mPreloadManager!!.pausePreload(mCurPos, mIsReverseScroll)
            }
        }
    }

    private fun setRefreshEvent() {
        binding.refreshLayout.setColorSchemeResources(R.color.color_link)
        binding.refreshLayout.setOnRefreshListener {
            object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    binding.refreshLayout!!.isRefreshing = false
                }
            }.start()
        }
    }

    /**
     * 关闭弹窗
     */
    private fun closeDialog() {

        if (commentDialog != null && commentDialog?.isBottomSheetVisible() == true) {
            commentDialog?.dismiss()
        }
        if (shareDialog == null) {
            return
        }
        if (shareDialog?.isBottomSheetVisible() == true) {
            shareDialog?.dismiss()
        }
    }

    /**
     * 用户操作事件
     */
    private fun likeShareEvent(mTikTokView: TikTokView) {
        mTikTokView.setListener(object : OnVideoControllerListener {
            override fun onHeadClick() {
                RxBus.getDefault().post(MainPageChangeEvent(1))
            }

            override fun onLikeClick() {}
            override fun onCommentClick() {
                refreshIsLandscapeVideo()
                val views = arrayOfNulls<View>(1)
                commentDialog = CommentDialog().apply {
                    this.setViewListener(object : CommentDialog.ViewListener {
                    override fun bindView(v: View?) {
                        views[0] = v
                    }
                })
                }
                commentDialog?.show(childFragmentManager, "FriendFragment")
                commentDialog?.behaviorChanged =
                    object : BaseVideoBottomSheetDialog.IBehaviorChanged {
                        override fun changedState(bottomSheet: View?, state: Int) {
                            val width: Float = ScreenUtils.getScreenWidth().toFloat()
                            val height: Float = ScreenUtils.getScreenHeight().toFloat()
                            if (state == BottomSheetBehavior.STATE_EXPANDED) {
                                var x = width / 2f
                                views[0]?.post(Runnable {
                                    val scale: Float = height - views[0]?.height!!
                                    val py = if (isLandscapeVideo == true) {
                                        Math.max(2f - (scale / height), 1f)
                                    } else {
                                        scale / height
                                    }
                                    mVideoView?.scaleX = py
                                    mVideoView?.scaleY = py
                                    mVideoView?.pivotX = x
                                    mVideoView?.pivotY = if (isLandscapeVideo == true) {
                                        height -BarUtils.getStatusBarHeight() -BarUtils.getNavBarHeight()- SizeUtils.dp2px(50f)
                                    } else {
                                        0f
                                    }
                                })
                            } else if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                                mVideoView?.scaleX = 1.0f
                                mVideoView?.scaleY = 1.0f
                                mVideoView?.pivotX = 0f
                                mVideoView?.pivotY = 0f
                            }
                        }

                        override fun changedOffset(bottomSheet: View?, slideOffset: Float) {
                            startAnimator(bottomSheet!!)
                        }
                    }
            }

            override fun onShareClick() {
                shareDialog = ShareDialog()
                shareDialog?.show(childFragmentManager, "FriendFragment")
            }

            override fun onFullScreenClick() {
//                mVideoView.toggleFullScreen()
            }
        })
    }

    private fun tiktokVideoEvent(tikTokView: TikTokView) {
        tikTokView.setOnTouchListener { v: View?, motionEvent: MotionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    L.i("tikTokView: ACTION_POINTER_DOWN")
                    // 多指按下，判断是否是双指
                    if (motionEvent.pointerCount == 2) {
                        // 双指按下
                        // 处理双指按下逻辑
                        L.i("tikTokView: ACTION_POINTER_DOWN 双指按下")
                        doubleFingerStatus(false)
                    }
                }
            }
            mVideoView?.onTouchEvent(motionEvent)
            tikTokView.onTouchEvent(motionEvent)
            mController?.onTouchEvent(motionEvent)
            photoView?.onTouchEvent(motionEvent)
            true
        }
    }

    /**
     *  status true 代表 当前双指需要隐藏
     * */
    private fun doubleFingerStatus(status: Boolean) {
        if (status) {
            binding.refreshLayout.isEnabled = true
            binding.viewPager2.isUserInputEnabled = true
            return
        }
        if (!status) {
            binding.refreshLayout.isEnabled = false
            binding.viewPager2.isUserInputEnabled = false// 设置为 false 禁止上下滑动
            return
        }
    }

    /**
     * 视图扩大比例监听
     */
    private fun videoViewLister(tikTokView: TikTokView, llBottom: LinearLayout) {
        mVideoView?.setScaleFactorChange(object : TiktokVideoView.OnScaleFactorChangeListener {
            override fun onScale(detector: Float) {
                if (detector < 1.0f) {
                    if (llBottom.visibility == View.GONE) {
                        doubleFingerStatus(true)
                        mVideoView?.defaultScaleFactorAnimate()
                    }
                }
                tikTokView.setAdjustingViews(detector)
            }
        })
    }

    private val mOnStateChangeListener: OnStateChangeListener =
        object : SimpleOnStateChangeListener() {
            override fun onPlayerStateChanged(playerState: Int) {
                when (playerState) {
                    VideoView.PLAYER_NORMAL -> {

                    }

                    VideoView.PLAYER_FULL_SCREEN -> {

                    }
                }
            }

            override fun onPlayStateChanged(playState: Int) {
                when (playState) {
                    VideoView.STATE_IDLE -> {}
                    VideoView.STATE_PREPARING -> {}
                    VideoView.STATE_PREPARED -> {}
                    VideoView.STATE_PLAYING -> {
                        refreshIsLandscapeVideo()
                    }

                    VideoView.STATE_PAUSED -> {}
                    VideoView.STATE_BUFFERING -> {}
                    VideoView.STATE_BUFFERED -> {}
                    VideoView.STATE_PLAYBACK_COMPLETED -> {}
                    VideoView.STATE_ERROR -> {}
                }
            }
        }

    fun onBackPressed() {
        mController?.onBackPressed()
    }

    fun refreshIsLandscapeVideo() {
        //需在此时获取视频宽高
        val videoSize = mVideoView!!.videoSize
        L.d("视频宽：" + videoSize[0])
        L.d("视频高：" + videoSize[1])
        isLandscapeVideo = videoSize[0] > videoSize[1]
    }

    /**
     * @param parent
     */
    private fun startAnimator(parent: View) {
        val width = ScreenUtils.getScreenWidth().toFloat()
        val height = ScreenUtils.getScreenHeight().toFloat()
        var x = width / 2f
        var py = if (isLandscapeVideo == true) {
            Math.max(2f - (parent.y / height), 1f)
        } else {
             parent.y / height
        }

        LogUtils.e(parent.y)
        LogUtils.e(parent.y + BarUtils.getStatusBarHeight())
        mVideoView?.scaleX = py
        mVideoView?.scaleY = py
        mVideoView?.pivotX = x
        mVideoView?.pivotY = if (isLandscapeVideo == true) {
            height -BarUtils.getStatusBarHeight() -BarUtils.getNavBarHeight()- SizeUtils.dp2px(50f)
        } else {
            0f
        }
    }
}