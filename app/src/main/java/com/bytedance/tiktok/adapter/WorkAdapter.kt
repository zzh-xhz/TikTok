package com.bytedance.tiktok.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bytedance.tiktok.activity.PlayListActivity
import com.bytedance.tiktok.adapter.WorkAdapter.WorkViewHolder
import com.bytedance.tiktok.base.BaseAdapter
import com.bytedance.tiktok.bean.VideoBean
import com.bytedance.tiktok.databinding.ItemWorkBinding
import com.bytedance.tiktok.utils.NumUtils.numberFilter

/**
 * create by libo
 * create on 2020-05-21
 * description
 */
class WorkAdapter(val context: Context) : BaseAdapter<WorkViewHolder, VideoBean>(WorkDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(ItemWorkBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: WorkViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var videoBean = mList[position]
        Glide.with(context)
            .asBitmap()
            .load(videoBean.videoRes)
            .apply(RequestOptions.frameOf(0))  // 从第一帧开始
            .into(holder.binding.ivCover)
        holder?.binding?.tvLikecount!!.text = numberFilter(videoBean.likeCount)
        holder?.binding.root.setOnClickListener { v: View? ->
            PlayListActivity.initPos = position
            context.startActivity(Intent(context, PlayListActivity::class.java))
        }
    }

    inner class WorkViewHolder(val binding: ItemWorkBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

class WorkDiff: DiffUtil.ItemCallback<VideoBean>() {
    override fun areItemsTheSame(oldItem: VideoBean, newItem: VideoBean): Boolean {
        return (oldItem.userBean!!.uid == newItem.userBean!!.uid)
    }

    override fun areContentsTheSame(oldItem: VideoBean, newItem: VideoBean): Boolean {
        return (oldItem.videoRes == newItem.videoRes && oldItem.userBean!!.uid == newItem.userBean!!.uid)
    }

}