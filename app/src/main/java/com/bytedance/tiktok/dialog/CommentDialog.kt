package com.bytedance.tiktok.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bytedance.tiktok.adapter.CommentAdapter
import com.bytedance.tiktok.bean.CommentBean
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.DialogCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lib.base.dialog.BaseVideoBottomSheetDialog

/**
 * create by libo
 * create on 2020-05-24
 * description 评论弹框
 */
class CommentDialog : BaseVideoBottomSheetDialog() {

    private var commentAdapter: CommentAdapter = CommentAdapter()
    private val datas = ArrayList<CommentBean>()
    private val likeArray = intArrayOf(4919, 334, 121, 423, 221, 23)
    private val commentArray = arrayOf("我就说左脚踩右脚可以上天你们还不信！", "全是评论点赞，没人关注吗", "哈哈哈哈", "像谁，没看出来", "你这西安话真好听")
    private lateinit var binding: DialogCommentBinding
    private var mViewListener: ViewListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCommentBinding.inflate(inflater, container, false)
        mViewListener?.bindView( binding.root)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun setViewListener(listener: ViewListener): CommentDialog? {
        mViewListener = listener
        return this
    }
    private fun init() {
        binding.recyclerView!!.layoutManager = LinearLayoutManager(context)
        binding.recyclerView!!.adapter = commentAdapter
        loadData()
        commentAdapter.appendList(datas)
        binding.commentKeyboardContainer.setOnClickListener {
           var  keyWordDialog = KeyWordDialog()
            keyWordDialog?.show(childFragmentManager, "")

        }
    }
    private fun loadData() {
        for (i in DataCreate.userList.indices) {
            val commentBean = CommentBean()
            commentBean.userBean = DataCreate.userList[i]
            commentBean.content = commentArray[(Math.random() * commentArray.size).toInt()]
            commentBean.likeCount = likeArray[(Math.random() * likeArray.size).toInt()]
            datas.add(commentBean)
        }
    }
//    protected override val height: Int
//        protected get() = resources.displayMetrics.heightPixels - 700
    protected override val height: Int
        protected get() = dp2px(requireContext(), 500f)
    interface ViewListener {
        fun bindView(v: View?)
    }

}