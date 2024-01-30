package com.bytedance.tiktok.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bytedance.tiktok.adapter.VideoAdapter.VideoViewHolder
import com.bytedance.tiktok.base.BaseAdapter
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.databinding.ItemVideoBinding
import com.bytedance.tiktok.utils.cache.PreloadManager
import com.bytedance.tiktok.view.LikeView.OnLikeListener



/**
 * create by libo
 * create on 2020-05-20
 * description
 */
class VideoAdapter(val context: Context, val recyclerView: RecyclerView): BaseAdapter<VideoViewHolder, VideoBean>(VideoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        mList[position]?.let {
            holder.binding.controller.setVideoData(it)
            Glide.with(context)
                .asBitmap()
                .load(it.videoRes)
                .apply(RequestOptions.frameOf(0))  // 从第一帧开始
                .into(holder.binding.ivCover)
            holder?.binding?.likeview?.setOnLikeListener(object : OnLikeListener {
                override fun onLikeListener() {
                    if (!it.isLiked) {  //未点赞，会有点赞效果，否则无
                        holder?.binding?.controller!!.like()
                    }
                }
            })
            holder.binding.ivPlay.alpha = 0.4f
        }

        //利用预加item，提前加载缓存资源
        mList[position].mediaSource = buildMediaSource(PreloadManager.getInstance(context).getPlayUrl(mList[position].videoRes))
    }

    /**
     * 构建一个共用缓存文件
     */
    val cache: SimpleCache by lazy {
        //构建缓存文件
        val cacheFile = context.cacheDir.resolve("tiktok_cache_file$this.hashCode()")
        //构建simpleCache缓存实例
        SimpleCache(cacheFile, LeastRecentlyUsedCacheEvictor(VideoPlayer.MAX_CACHE_BYTE), StandaloneDatabaseProvider(context))
    }

    /**
     * 构建当前url视频的缓存
     */
    private fun buildMediaSource(url: String): ProgressiveMediaSource {
        //开启缓存文件
        val mediaItem = MediaItem.fromUri(url)
        //构建 DataSourceFactory
        val dataSourceFactory = CacheDataSource.Factory().setCache(cache).setUpstreamDataSourceFactory(
            DefaultDataSource.Factory(context))
        //构建 MediaSource
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
    }

    /**
     * 通过position获取当前item.rootview
     */
    fun getRootViewAt(position: Int): View? {
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
        return if (viewHolder != null && viewHolder is VideoViewHolder) {
            viewHolder.itemView
        } else {
            null
        }
    }

    inner class VideoViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

class VideoDiff: DiffUtil.ItemCallback<VideoBean>() {
    override fun areItemsTheSame(oldItem: VideoBean, newItem: VideoBean): Boolean {
        return (oldItem.userBean!!.uid == newItem.userBean!!.uid)
    }

    override fun areContentsTheSame(oldItem: VideoBean, newItem: VideoBean): Boolean {
        return (oldItem.videoRes == newItem.videoRes && oldItem.userBean!!.uid == newItem.userBean!!.uid)
    }

}