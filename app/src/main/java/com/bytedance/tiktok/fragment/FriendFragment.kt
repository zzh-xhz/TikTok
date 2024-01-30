import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.Tiktok3Adapter
import com.bytedance.tiktok.base.BaseBindingPlayerFragment
import com.bytedance.tiktok.bean.CurUserBean
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.MainPageChangeEvent
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.databinding.FragmentFriendBinding
import com.bytedance.tiktok.utils.DataUtil
import com.bytedance.tiktok.utils.OnVideoControllerListener
import com.bytedance.tiktok.utils.RxBus
import com.bytedance.tiktok.utils.Utils
import com.bytedance.tiktok.utils.cache.PreloadManager
import com.bytedance.tiktok.view.CommentDialog
import com.bytedance.tiktok.view.ControllerView
import com.bytedance.tiktok.view.ShareDialog
import com.bytedance.tiktok.widget.VerticalViewPager
import com.bytedance.tiktok.widget.controller.TikTokController
import com.bytedance.tiktok.widget.render.TikTokRenderViewFactory
import com.bytedance.tiktok.widget.videoview.TiktokVideoView
import xyz.doikki.videoplayer.util.L


/**
 * create by libo
 * create on 2020-05-19
 * description 朋友播放页
 */
class FriendFragment : BaseBindingPlayerFragment<TiktokVideoView, FragmentFriendBinding>({ FragmentFriendBinding.inflate(it) }) {
    private var commentDialog: CommentDialog? = null
    private var shareDialog: ShareDialog? = null

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
        DataUtil.getTiktokDataFromAssets(requireActivity()).forEachIndexed { index, videoBean ->
            //开始预加载
            PreloadManager.getInstance(context).addPreloadTask(videoBean.videoRes, index)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        addData(null)
        val index = 0
        binding.viewPager2!!.post {
            if (index == 0) {
                startPlay(0)
            } else {
                binding.viewPager2.setCurrentItem(index, false)
            }
        }
        setRefreshEvent()
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
            likeShareEvent(viewHolder.controllerView)
            //切换播放视频的作者主页数据
            RxBus.getDefault().post(CurUserBean(DataCreate.datas[position]?.userBean!!))
            if (viewHolder.mPosition == position) {
                mVideoView?.release()
                Utils.removeViewFormParent(mVideoView)
                val tiktokBean = mVideoList[position]
                val playUrl = mPreloadManager!!.getPlayUrl(tiktokBean.videoRes)
                L.i("startPlay: position: $position  url: $playUrl")
                mVideoView!!.setUrl(playUrl)
                //请点进去看isDissociate的解释
                mController!!.addControlComponent(viewHolder.mTikTokView, true)
                viewHolder.mPlayerContainer.addView(mVideoView, 0)
                mVideoView!!.start()
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
    private fun likeShareEvent(controllerView: ControllerView) {
        controllerView.setListener(object : OnVideoControllerListener {
            override fun onHeadClick() {
                RxBus.getDefault().post(MainPageChangeEvent(1))
            }

            override fun onLikeClick() {}
            override fun onCommentClick() {
                commentDialog = CommentDialog()
                commentDialog?.show(childFragmentManager, "")
            }

            override fun onShareClick() {
                shareDialog = ShareDialog()
                shareDialog?.show(childFragmentManager, "")
            }
        })
    }
}