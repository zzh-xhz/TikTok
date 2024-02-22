package com.bytedance.tiktok.emojikeyboard.keyboard.input

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.blankj.utilcode.util.KeyboardUtils
import com.bytedance.tiktok.R


/**
 *  Created by sai
 *  on 2020/4/14
 *
 *  desc:  聊天软键盘输入框
 */
class ChatInputView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr), TextView.OnEditorActionListener {

    companion object {
        private val TAG = ChatInputView::class.java.simpleName
    }

    private var mOnChatInputViewListener: OnChatInputViewListener? = null
    private var commentKeyboardContainer : ConstraintLayout? = null
    private var  ivVoice :ImageView? = null
    private var  etMsg: EditText? = null
    private var  tvRecordVoice :TextView ? = null
    private var  ivFace: ImageView ? = null
    private var  rightSendContainer :FrameLayout ? = null
    private var  ivMore :ImageView ? = null
    private var  btnSendMsg: AppCompatButton? = null
    init {
         var rootView =  View.inflate(context, R.layout.lay_chat_input_view, this)
         ivVoice = rootView.findViewById<ImageView>(R.id.iv_voice)
         etMsg = rootView.findViewById<EditText>(R.id.et_msg)
         btnSendMsg = rootView.findViewById<AppCompatButton>(R.id.btn_send_msg)
         ivMore = rootView.findViewById<ImageView>(R.id.iv_more)
         tvRecordVoice = rootView.findViewById<TextView>(R.id.tv_record_voice)
         ivFace = rootView.findViewById<ImageView>(R.id.iv_face)
         ivVoice?.setOnClickListener {
            if (it.tag == null) {
                it.tag = true
                showAudioButton()
                mOnChatInputViewListener?.onClickVoice(it)
                getInputView()?.let { it1 -> KeyboardUtils.hideSoftInput(it1) }
            } else {
                it.tag = null
                hideAudioButton()
                getInputView()?.let { it1 -> KeyboardUtils.showSoftInput(it1) }
            }
        }

        etMsg?.imeOptions = EditorInfo.IME_ACTION_SEND
        etMsg?.setOnEditorActionListener(this)
        etMsg?.doAfterTextChanged {
            if (etMsg?.text.toString().trim().isBlank()) {
                btnSendMsg?.visibility = View.GONE
                ivMore?.visibility = View.VISIBLE
            } else {
                btnSendMsg?.visibility = View.VISIBLE
                ivMore?.visibility = View.GONE
            }
        }

        tvRecordVoice?.setOnTouchListener { v, event ->
            return@setOnTouchListener mOnChatInputViewListener?.onRecordVoice(v, event) ?: false
        }
//        ivFace.setOnClickListener {
//            mOnChatInputViewListener?.onFacePanelClick()
//        }
//        ivMore.setOnClickListener {
//            mOnChatInputViewListener?.onMorePanelClick()
//        }
        btnSendMsg?.setOnClickListener {
            val inputMsg = etMsg?.text.toString().trim()
            etMsg?.text = null
            mOnChatInputViewListener?.onSendMsg(inputMsg)
        }
    }

    fun getInputView(): EditText? {
        return etMsg
    }

    fun getIvEmoji(): ImageView? {
        return ivFace
    }

    fun getIvMore(): ImageView? {
        return ivMore
    }

    fun getIvVoice(): ImageView? {
        return ivVoice
    }

    fun setOnChatInputViewListener(onChatInputViewListener: OnChatInputViewListener) {
        this.mOnChatInputViewListener = onChatInputViewListener
    }

    private fun showAudioButton() {
        tvRecordVoice?.visibility = View.VISIBLE
        ivVoice?.setImageResource(R.drawable.ic_chat_text_keyboard)
    }

    fun hideAudioButton() {
        tvRecordVoice?.visibility = View.GONE
        ivVoice?.setImageResource(R.drawable.ic_chat_voice)
        ivVoice?.tag = null
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        when (actionId) {
            EditorInfo.IME_ACTION_SEND -> {
                mOnChatInputViewListener?.onSendMsg(v?.text.toString().trim())
                etMsg?.setText("")
            }
        }
        return true
    }
}

interface OnChatInputViewListener {
    fun onRecordVoice(v: View, event: MotionEvent): Boolean
    fun onClickVoice(v: View)
    fun onSendMsg(msg: String)
}