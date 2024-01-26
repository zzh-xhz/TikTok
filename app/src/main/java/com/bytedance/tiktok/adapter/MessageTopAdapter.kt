package com.bytedance.tiktok.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.tiktok.base.BaseAdapter
import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.bytedance.tiktok.databinding.ItemMessageTopBinding

/**
 * create by libo
 * create on 2020-05-24
 * description
 */
class MessageTopAdapter : BaseAdapter<MessageTopAdapter.MessageTopViewHolder, UserBean>(MessageTopDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageTopViewHolder {
        return MessageTopViewHolder(ItemMessageTopBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MessageTopViewHolder, position: Int) {
        holder?.binding?.let {
            var userBean = mList[position]
            it.ivHead!!.setImageResource(userBean!!.head)
        }
    }

    inner class MessageTopViewHolder(val binding: ItemMessageTopBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}

class MessageTopDiff: DiffUtil.ItemCallback<UserBean>() {
    override fun areItemsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid)
    }

    override fun areContentsTheSame(oldItem: UserBean, newItem: UserBean): Boolean {
        return (oldItem.uid == newItem.uid && oldItem.nickName.equals(newItem.nickName))
    }

}