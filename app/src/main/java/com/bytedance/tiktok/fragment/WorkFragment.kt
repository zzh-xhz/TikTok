package com.bytedance.tiktok.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.tiktok.adapter.WorkAdapter
import com.bytedance.tiktok.base.BaseBindingFragment
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.FragmentWorkBinding

/**
 * create by libo
 * create on 2020-05-19
 * description 个人作品fragment
 */
class WorkFragment : BaseBindingFragment<FragmentWorkBinding>({FragmentWorkBinding.inflate(it)}) {
    private lateinit var workAdapter: WorkAdapter

    companion object {
        val recyclerViewPool = RecyclerView.RecycledViewPool()  //创建一份静态RecycledViewPool实例，让多个workfragment页面共用
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun init() {
        workAdapter = WorkAdapter(requireContext())
        binding.recyclerview.layoutManager = GridLayoutManager(activity, 3)
        binding.recyclerview.adapter = workAdapter
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.setRecycledViewPool(recyclerViewPool)
        workAdapter.appendList(DataCreate.datas)
    }
}