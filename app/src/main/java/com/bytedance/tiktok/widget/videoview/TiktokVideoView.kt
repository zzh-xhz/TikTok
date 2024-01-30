 package com.bytedance.tiktok.widget.videoview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource
import androidx.media3.exoplayer.source.ConcatenatingMediaSource2
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.bytedance.tiktok.bean.VideoBean

/**
 * 可播放在线和本地url
 * Created by Doikki on 2022/7/18.
 */
class TiktokVideoView : ExoVideoView {
    // 创建 DefaultHttpDataSource.Factory
    val dataSourceFactory = DefaultHttpDataSource.Factory()

    // 创建 ConcatenatingMediaSource2
    val concatenatingMediaSource = ConcatenatingMediaSource2.Builder()
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    public override fun initPlayer() {
        super.initPlayer()
    }
     fun setAddData(mVideoList: MutableList<VideoBean>){
        for (videoUrl in mVideoList) {
            mHelper.getMediaSource(videoUrl.videoRes,true)
        }

    }
    fun playFirst(){
        super.startInPlaybackState()

    }

    public override fun prepare() {
        super.prepare()
    }
}