package com.lib.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import xyz.doikki.videoplayer.player.BaseVideoView
import xyz.doikki.videoplayer.player.VideoViewManager

abstract class BaseBindingPlayerFragment< T: BaseVideoView<*>?,VB : ViewBinding>(val block: (LayoutInflater) -> VB) : Fragment() {
    private var _binding: VB? = null
    @JvmField
    protected var mVideoView: T? = null
    protected val binding: VB get() = requireNotNull(_binding) { "The property of binding has been destroyed." }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = block(layoutInflater)
        return binding.root
    }
    protected open fun getVideoViewManager(): VideoViewManager? {
        return VideoViewManager.instance()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        if (mVideoView != null) {
            mVideoView!!.resume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mVideoView != null) {
            mVideoView!!.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mVideoView != null) {
            mVideoView!!.release()
        }
    }
}
