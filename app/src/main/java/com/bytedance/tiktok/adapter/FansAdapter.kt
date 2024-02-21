package com.bytedance.tiktok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.FansAdapter.FansViewHolder

import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.bytedance.tiktok.databinding.ItemFansBinding
import com.lib.base.adapter.BaseAdapter

/**
 * create by libo
 * create on 2020-05-24
 * description
 */
class FansAdapter : BaseAdapter<FansViewHolder, UserBean>(FansDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FansViewHolder {
        return FansViewHolder(ItemFansBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FansViewHolder, position: Int) {
        holder?.binding?.let {
            var userBean = mList[position]
            Glide.with(it.ivHead.context)
                .load(userBean!!.head)
                .into( it.ivHead)
            it.tvNickname!!.text = userBean.nickName
            it.tvFocus!!.text = if (userBean.isFocused) "已关注" else "关注"
            it.tvFocus!!.setOnClickListener { v: View? ->
                if (!userBean.isFocused) {
                    it.tvFocus!!.text = "已关注"
                    it.tvFocus!!.setBackgroundResource(R.drawable.shape_round_halfwhite)
                } else {
                    it.tvFocus!!.text = "关注"
                    it.tvFocus!!.setBackgroundResource(R.drawable.shape_round_red)
                }
                userBean.isFocused = !userBean.isFocused
            }
        }
    }

    inner class FansViewHolder(val binding: ItemFansBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

class FansDiff: DiffUtil.ItemCallback<UserBean>() {
    override fun areItemsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid)
    }

    override fun areContentsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid && oldItem.nickName.equals(newItem.nickName))
    }

}