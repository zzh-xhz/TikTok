package com.bytedance.tiktok.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bytedance.tiktok.adapter.PrivateLetterAdapter.PrivateLetterViewHolder
import com.lib.base.adapter.BaseAdapter
import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.bytedance.tiktok.databinding.ItemPrivateLetterBinding

/**
 * create by libo
 * create on 2020-05-25
 * description
 */
class PrivateLetterAdapter : BaseAdapter<PrivateLetterViewHolder, UserBean>(FansDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrivateLetterViewHolder {
        return PrivateLetterViewHolder(ItemPrivateLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PrivateLetterViewHolder, position: Int) {
        var userBean = mList[position]
        holder?.binding?.ivHead?.context?.let {
            Glide.with(it)
                .load(userBean!!.head)
                .into(holder?.binding?.ivHead)
        }
        holder?.binding?.tvNickname!!.text = userBean?.nickName
    }

    inner class PrivateLetterViewHolder(val binding: ItemPrivateLetterBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}