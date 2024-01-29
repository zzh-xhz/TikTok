package com.bytedance.tiktok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.utils.cache.PreloadManager
import com.bytedance.tiktok.widget.component.TikTokView

class Tiktok3Adapter(
    /**
     * 数据源
     */
    private val mVideoBeans: List<VideoBean>?
) : RecyclerView.Adapter<Tiktok3Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tik_tok, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = mVideoBeans!![position]
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(item.videoRes, position)
        Glide.with(context)
            .asBitmap()
            .load(item.videoRes)
            .apply(RequestOptions.frameOf(0)) // 从第一帧开始
            .into(holder.mThumb)
        holder.mTitle.text = item.content
        holder.mPosition = position
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val item = mVideoBeans!![holder.mPosition]
        //取消预加载
        PreloadManager.getInstance(holder.itemView.context).removePreloadTask(item.videoRes)
    }

    override fun getItemCount(): Int {
        return mVideoBeans?.size ?: 0
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mPosition = 0
        var mTitle //标题
                : TextView
        var mThumb //封面图
                : ImageView
        var mTikTokView: TikTokView
        var mPlayerContainer: FrameLayout

        init {
            mTikTokView = itemView.findViewById(R.id.tiktok_View)
            mTitle = mTikTokView.findViewById(R.id.tv_title)
            mThumb = mTikTokView.findViewById(R.id.iv_thumb)
            mPlayerContainer = itemView.findViewById(R.id.container)
            itemView.tag = this
        }
    }
}