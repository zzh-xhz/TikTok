package com.bytedance.tiktok.widget.controller;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bytedance.tiktok.bean.VideoBean;
import com.bytedance.tiktok.view.ControllerView;
import com.bytedance.tiktok.widget.component.DebugInfoView;
import com.bytedance.tiktok.widget.component.TiktokControlView;


import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videoplayer.controller.BaseVideoController;

/**
 * 抖音
 * Created by Doikki on 2018/1/6.
 */

public class TikTokController extends BaseVideoController {


    public TikTokController(@NonNull Context context) {
        super(context);
    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TikTokController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        addControlComponent(new TiktokControlView(getContext()));
        //显示调试信息
        addControlComponent(new DebugInfoView(getContext()));
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }
    @Override
    protected void initView() {
        super.initView();

        CompleteView completeView = new CompleteView(getContext());
        ErrorView errorView = new ErrorView(getContext());
        addControlComponent(completeView);
        addControlComponent(errorView);
    }

    @Override
    public boolean showNetWarning() {
        //显示移动网络播放警告
        return false;
    }
}
