package com.bytedance.tiktok.widget.player;


import android.content.Context;


import androidx.media3.exoplayer.source.MediaSource;

import xyz.doikki.videoplayer.exo.ExoMediaPlayer;

/**
 * 自定义ExoMediaPlayer，目前扩展了诸如边播边存，以及可以直接设置Exo自己的MediaSource。
 */
public class CustomExoMediaPlayer extends ExoMediaPlayer {

    public CustomExoMediaPlayer(Context context) {
        super(context);
    }

    public void setDataSource(MediaSource dataSource) {
        mMediaSource = dataSource;
    }
}
