package com.bytedance.tiktok.activity

import android.view.View
import com.bumptech.glide.Glide
import com.bytedance.tiktok.databinding.ActivityShowImageBinding
import com.lib.base.ui.BaseBindingActivity

class ShowImageActivity : BaseBindingActivity<ActivityShowImageBinding>({ActivityShowImageBinding.inflate(it)}) {

    override fun init() {
        binding.ivHead.setOnClickListener { v: View? -> finish() }
        val headRes = intent.getStringExtra("res")
//        binding.ivHead.setImageResource(headRes)
        Glide.with( binding.ivHead.context)
            .load(headRes)
            .into(binding.ivHead)
    }
}