package com.bytedance.tiktok.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytedance.tiktok.R
import com.bytedance.tiktok.adapter.PrivateLetterAdapter
import com.bytedance.tiktok.adapter.ShareAdapter
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.ShareBean
import com.bytedance.tiktok.databinding.DialogShareBinding
import com.lib.base.dialog.BaseBottomSheetDialog
import java.util.*

/**
 * create by libo
 * create on 2020-05-25
 * description 分享弹框
 */
class ShareDialog : BaseBottomSheetDialog() {

    private var privateLetterAdapter: PrivateLetterAdapter = PrivateLetterAdapter()
    private var shareAdapter: ShareAdapter = ShareAdapter()
    private val shareBeans = ArrayList<ShareBean>()
    private lateinit var binding: DialogShareBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogShareBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.rvPrivateLetter!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        privateLetterAdapter.appendList(DataCreate.userList)
        binding.rvPrivateLetter!!.adapter = privateLetterAdapter
        binding.rvShare!!.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvShare!!.adapter = shareAdapter
        setShareDatas()
        shareAdapter.appendList(shareBeans)
        binding.tvCancel.setOnClickListener { v: View? ->
            dismiss()
        }

    }

    private fun setShareDatas() {
        shareBeans.add(ShareBean(R.string.icon_friends, "朋友圈", R.color.color_wechat_iconbg))
        shareBeans.add(ShareBean(R.string.icon_wechat, "微信", R.color.color_wechat_iconbg))
        shareBeans.add(ShareBean(R.string.icon_qq, "QQ", R.color.color_qq_iconbg))
        shareBeans.add(ShareBean(R.string.icon_qq_space, "QQ空间", R.color.color_qqzone_iconbg))
        shareBeans.add(ShareBean(R.string.icon_weibo, "微博", R.color.color_weibo_iconbg))
        shareBeans.add(ShareBean(R.string.icon_comment, "私信好友", R.color.color_FF0041))
    }

    protected override val height: Int
        protected get() = dp2px(requireContext(), 355f)
}