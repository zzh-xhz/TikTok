package com.bytedance.tiktok.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.tiktok.adapter.FansAdapter
import com.bytedance.tiktok.adapter.MessageAdapter
import com.bytedance.tiktok.adapter.MessageTopAdapter
import com.bytedance.tiktok.base.BaseBindingFragment
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.FragmentFansBinding
import com.bytedance.tiktok.databinding.FragmentMessageBinding

class MessageFragment : BaseBindingFragment<FragmentMessageBinding>({FragmentMessageBinding.inflate(it)}) {
    private var messageAdapter: MessageAdapter? = null
    private var messageTopAdapter: MessageTopAdapter? = null
    companion object {
        val recyclerViewPool = RecyclerView.RecycledViewPool()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    fun init() {
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.setRecycledViewPool(recyclerViewPool)
        messageAdapter = MessageAdapter()
        messageAdapter?.appendList(DataCreate.userList)
        binding.recyclerview.adapter = messageAdapter


        binding.recyclerviewTop.setHasFixedSize(true)
        binding.recyclerviewTop.setRecycledViewPool(recyclerViewPool)
        messageTopAdapter = MessageTopAdapter()
        messageTopAdapter?.appendList(DataCreate.userList)
        binding.recyclerviewTop.adapter = messageTopAdapter

    }
}