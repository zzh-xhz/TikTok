package com.bytedance.tiktok.dialog


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.KeyboardUtils
import com.bytedance.tiktok.R
import com.bytedance.tiktok.databinding.KeyWordDialogBinding
import com.bytedance.tiktok.emojikeyboard.keyboard.input.OnChatInputViewListener


/**
 * create by libo
 * create on 2020-05-24
 * description 评论弹框
 */
class KeyWordDialog : DialogFragment () , OnChatInputViewListener {
    companion object {
        private val TAG = KeyWordDialog::class.java.simpleName
    }

    private lateinit var binding: KeyWordDialogBinding

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //去掉dialog的标题，需要在setContentView()之前
        this.dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 设置点击外部区域关闭弹窗
        dialog?.setCanceledOnTouchOutside(true)
        val window: Window? = this.dialog!!.window
        //去掉dialog默认的padding
        window?.decorView?.setPadding(0, 0, 0, 0)
        val lp: WindowManager.LayoutParams? = window?.attributes
        lp?.width = WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        //设置dialog的位置在底部
        lp?.gravity = Gravity.BOTTOM
        //设置dialog的动画
        lp?.windowAnimations = R.style.MyDialog
        window?.attributes = lp
        window?.setBackgroundDrawable(ColorDrawable())
        binding = KeyWordDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
        binding.chatInputView.setOnChatInputViewListener(this)
        KeyboardUtils.showSoftInput(binding.chatInputView)
        binding.chatInputView.getInputView()?.requestFocus()
    }

    override fun onRecordVoice(v: View, event: MotionEvent): Boolean {
        return true
    }

    override fun onClickVoice(v: View) {
    }

    override fun onSendMsg(msg: String) {
    }


}