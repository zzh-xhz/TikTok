package com.bytedance.tiktok.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bytedance.tiktok.R
import com.bytedance.tiktok.base.BaseAdapter
import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.bytedance.tiktok.databinding.ItemMessageBinding

/**
 * create by libo
 * create on 2020-05-24
 * description
 */
class MessageAdapter : BaseAdapter<MessageAdapter.MessageViewHolder, UserBean>(MessageDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder?.binding?.let {
            var userBean = mList[position]
            Glide.with(it.ivHead.context)
                .load(userBean!!.head)
                .into(it.ivHead)
            it.tvNickname!!.text = userBean.nickName
            it.tvMessage!!.text = "测试消息数据$position"

        }
    }

    inner class MessageViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

class MessageDiff: DiffUtil.ItemCallback<UserBean>() {
    override fun areItemsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid)
    }

    override fun areContentsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid && oldItem.nickName.equals(newItem.nickName))
    }

}