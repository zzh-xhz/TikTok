package com.bytedance.tiktok.player

import androidx.media3.exoplayer.ExoPlayer


interface Iplayer {

    fun playVideo(url: String)

    fun getplayer(): ExoPlayer

    fun play()

    fun pause()

    fun stop()

    fun release()

    fun isPlaying(): Boolean
}