package com.bytedance.tiktok.fragment

import VideoPlayer
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout.LayoutParams
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlaybackException
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.bytedance.tiktok.R
import com.bytedance.tiktok.activity.PlayListActivity
import com.bytedance.tiktok.adapter.VideoAdapter
import com.bytedance.tiktok.bean.CurUserBean
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.MainPageChangeEvent
import com.bytedance.tiktok.bean.PauseVideoEvent
import com.bytedance.tiktok.databinding.FragmentRecommendBinding
import com.bytedance.tiktok.dialog.CommentDialog
import com.bytedance.tiktok.dialog.ShareDialog
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.bytedance.tiktok.utils.RxBus
import com.bytedance.tiktok.utils.cache.PreloadManager
import com.bytedance.tiktok.view.ControllerView
import com.bytedance.tiktok.view.LikeView
import com.danikula.videocache.Logger
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lib.base.dialog.BaseVideoBottomSheetDialog
import com.lib.base.ui.BaseBindingFragment
import rx.Subscription
import rx.functions.Action1


/**
 * create by libo
 * create on 2020-05-19
 * description 推荐播放页
 */
class RecommendFragment : BaseBindingFragment<FragmentRecommendBinding>({FragmentRecommendBinding.inflate(it)}) {
    private var adapter: VideoAdapter?= null
    private var commentDialog : CommentDialog?= null
    private var shareDialog : ShareDialog?= null
    private var mPreloadManager: PreloadManager? = null
    /** 当前播放视频位置  */
    private var curPlayPos = -1
    private lateinit var videoView: VideoPlayer

    private var ivCurCover: ImageView? = null
    private var subscribe: Subscription?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mPreloadManager = PreloadManager.getInstance(requireActivity())
        DataCreate.datas.forEachIndexed { index, videoBean ->
            //开始预加载
            PreloadManager.getInstance(context).addPreloadTask(videoBean.videoRes, index)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initVideoPlayer()
        setViewPagerLayoutManager()
        setRefreshEvent()
        observeEvent()
    }

    private fun initRecyclerView() {
        adapter  = VideoAdapter(requireContext(), binding.recyclerView.getChildAt(0) as RecyclerView)
        binding.recyclerView.adapter = adapter
        adapter?.appendList(DataCreate.datas)
    }

    private fun initVideoPlayer() {
        var params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        videoView = VideoPlayer(requireActivity())
        videoView.layoutParams = params
        lifecycle.addObserver(videoView)
    }

    private fun observeEvent() {
        //监听播放或暂停事件
        subscribe = RxBus.getDefault().toObservable(PauseVideoEvent::class.java)
            .subscribe(Action1 { event: PauseVideoEvent ->
                if (videoView ==null){
                    return@Action1
                }
                if (event.isPlayOrPause) {
                    videoView!!.play()
                } else {
                    videoView!!.pause()
                }
            } as Action1<PauseVideoEvent>)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.unsubscribe()
    }

    private fun setViewPagerLayoutManager() {
        with(binding.recyclerView) {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            offscreenPageLimit = 1
            registerOnPageChangeCallback(pageChangeCallback)
            (binding.recyclerView.getChildAt(0) as RecyclerView).scrollToPosition(PlayListActivity.initPos)
        }
    }

    private val pageChangeCallback = object: OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            playCurVideo(position)
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

    private fun playCurVideo(position: Int) {
        if (position == curPlayPos) {
            return
        }
        val itemView = adapter!!.getRootViewAt(position)
        val rootView = itemView!!.findViewById<ViewGroup>(R.id.rl_container)
        val likeView: LikeView = rootView.findViewById(R.id.likeview)
        val controllerView: ControllerView = rootView.findViewById(R.id.controller)
        val ivPlay = rootView.findViewById<ImageView>(R.id.iv_play)
        val ivCover = rootView.findViewById<ImageView>(R.id.iv_cover)

        //播放暂停事件
        likeView.setOnPlayPauseListener(object: LikeView.OnPlayPauseListener {
            override fun onPlayOrPause() {
                if (videoView!!.isPlaying()) {
                    videoView?.pause()
                    ivPlay.visibility = View.VISIBLE
                } else {
                    videoView?.play()
                    ivPlay.visibility = View.GONE
                }
            }

        })
        closeDialog()
        //评论点赞事件
        likeShareEvent(controllerView)

        //切换播放视频的作者主页数据
        RxBus.getDefault().post(CurUserBean(DataCreate.datas[position]?.userBean!!))
        curPlayPos = position

        //切换播放器位置
        dettachParentView(rootView)
        autoPlayVideo(curPlayPos, ivCover,ivPlay)
        playVideoViewInitialization(ivPlay)
    }

    /**
     * 移除videoview父view
     */
    private fun dettachParentView(rootView: ViewGroup) {
        //1.添加videoView到当前需要播放的item中,添加进item之前，保证videoView没有父view
        videoView.parent?.let {
            (it as ViewGroup).removeView(videoView)
        }

        rootView.addView(videoView, 0)
    }

    /**
     * 自动播放视频
     */
    private fun autoPlayVideo(position: Int, ivCover: ImageView,ivPlay: ImageView) {
        videoView.playVideo(adapter!!.getDatas()[position].mediaSource!!)
        videoView.getplayer()?.addListener(object: Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                // 播放状态发生变化时的回调
                // 播放状态包括：Player.STATE_IDLE、Player.STATE_BUFFERING、Player.STATE_READY、Player.STATE_ENDED
                if (state == Player.STATE_READY) {

                }
                Logger.info("测试 onPlaybackStateChanged $state")
            }

            fun onPlayerError(error: ExoPlaybackException?) {
                // 播放发生错误时的回调
                Logger.info("测试 onPlayerError ${error.toString()}")
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                // 播放状态变为播放或暂停时的回调
                Logger.info("测试 onIsPlayingChanged $isPlaying")
            }

            override fun onRenderedFirstFrame() {
                //第一帧已渲染，隐藏封面
                ivCover.visibility = View.GONE
                ivCurCover = ivCover
                playVideoViewInitialization(ivPlay)
                // 播放状态变为播放或暂停时的回调
                Logger.info("测试 onRenderedFirstFrame")
            }
        })
    }
    /**
     * 恢复最初的视图
     */
    private fun playVideoViewInitialization(ivPlay: ImageView) {
        if (ivPlay?.visibility !=  View.GONE){
            ivPlay?.visibility = View.GONE
        }

    }
    /**
     * 关闭弹窗
     */
    private fun closeDialog() {

        if (commentDialog != null && commentDialog?.isBottomSheetVisible() == true){
            commentDialog?.dismiss()
        }
        if (shareDialog == null) {
            return
        }
        if (shareDialog?.isBottomSheetVisible() == true){
            shareDialog?.dismiss()
        }
    }

    /**
     * 用户操作事件
     */
    private fun likeShareEvent(controllerView: ControllerView) {
        controllerView.setListener(object : OnVideoControllerListener {
            override fun onHeadClick() {
                RxBus.getDefault().post(MainPageChangeEvent(1))
            }

            override fun onLikeClick() {}
            override fun onCommentClick() {
                val views = arrayOfNulls<View>(1)
                commentDialog  = CommentDialog()
                commentDialog?.setViewListener(object : CommentDialog.ViewListener{
                    override fun bindView(v: View?) {
                        views[0] = v
                    }
                })
                commentDialog?.show(childFragmentManager, "RecommendFragment")
                commentDialog?.behaviorChanged = object :BaseVideoBottomSheetDialog.IBehaviorChanged{
                    override fun changedState(bottomSheet: View?, state: Int) {
                        val width: Float = ScreenUtils.getScreenWidth().toFloat()
                        val height: Float = ScreenUtils.getScreenHeight().toFloat()
                        if (state == BottomSheetBehavior.STATE_EXPANDED) {
                            val x = width / 2f
                            views[0]?.post(Runnable {
                                val scale: Float = height - views[0]?.height!!
                                videoView.scaleX = scale / height
                                videoView.scaleY = scale / height
                                videoView.pivotX = x
                                videoView.pivotY = 0f
                            })
                        } else if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                            videoView.scaleX = 1.0f
                            videoView.scaleY = 1.0f
                            videoView.pivotX = 0f
                            videoView.pivotY = 0f
                        }
                    }

                    override fun changedOffset(bottomSheet: View?, slideOffset: Float) {
                        startAnimator(bottomSheet!!)
                    }
                }
            }

            override fun onShareClick() {
                shareDialog  = ShareDialog()
                shareDialog?.show(childFragmentManager, "RecommendFragment")
            }

            override fun onFullScreenClick() {

            }
        })
    }

    /**
     * @param parent
     */
    private fun startAnimator(parent: View) {
        val width = ScreenUtils.getScreenWidth().toFloat()
        val height = ScreenUtils.getScreenHeight().toFloat()
        val x = width / 2f
        val py = parent.y / height
        LogUtils.e(parent.y)
        LogUtils.e(parent.y + BarUtils.getStatusBarHeight())
        videoView.scaleX = py
        videoView.scaleY = py
        videoView.pivotX = x
        videoView.pivotY = 0f
    }
}