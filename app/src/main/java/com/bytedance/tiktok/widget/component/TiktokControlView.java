package com.bytedance.tiktok.widget.component;

import static xyz.doikki.videoplayer.util.PlayerUtils.stringForTime;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bytedance.tiktok.R;
import com.bytedance.tiktok.bean.VideoBean;

import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videoplayer.controller.ControlWrapper;
import xyz.doikki.videoplayer.controller.IControlComponent;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.util.PlayerUtils;

/**
 * 点播底部控制栏
 */
public class TiktokControlView extends FrameLayout implements IControlComponent, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    protected ControlWrapper mControlWrapper;

    private final TextView mTotalTime;
    private final TextView mCurrTime;
    private final ImageView mFullScreen;
    private final LinearLayout mBottomContainer;
    private final SeekBar mVideoProgress;
    private final ImageView mPlayButton;
    private final TextView time;
    private final LinearLayout mBottomVerticalScreenContainer;
    private final SeekBar mVideoVerticalScreenProgress;

    private boolean mIsDragging;
    private final LinearLayout mTitleContainer;
    private  TextView mTitle;
    private  TextView mSysTime;//系统当前时间

    private  BatteryReceiver mBatteryReceiver;
    private ImageView back;
    private boolean mIsRegister;//是否注册BatteryReceiver
    public TiktokControlView(@NonNull Context context) {
        super(context);
    }

    public TiktokControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TiktokControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int getLayoutId() {
        return R.layout.tiktok_layout_vod_control_view;
    }
    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(getLayoutId(), this, true);
        //竖屏
        mBottomVerticalScreenContainer = findViewById(R.id.bottom_vertical_screen_container);
        mVideoVerticalScreenProgress = findViewById(R.id.seekBar_vertical_screen);
        time = findViewById(R.id.time_vertical_screen);
        // 横屏
        mTitleContainer = findViewById(R.id.title_container);
        back = findViewById(R.id.back);
        mTitle = findViewById(R.id.title);
        mSysTime = findViewById(R.id.sys_time);
        mFullScreen = findViewById(R.id.fullscreen);
        mFullScreen.setOnClickListener(this);
        mBottomContainer = findViewById(R.id.bottom_container);
        mVideoProgress = findViewById(R.id.seekBar);
        mVideoProgress.setOnSeekBarChangeListener(this);
        mTotalTime = findViewById(R.id.total_time);
        mCurrTime = findViewById(R.id.curr_time);
        mPlayButton = findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(this);
        //电量
        ImageView batteryLevel = findViewById(R.id.iv_battery);
        mBatteryReceiver = new BatteryReceiver(batteryLevel);
        back.setOnClickListener(this);
        mVideoVerticalScreenProgress.setOnSeekBarChangeListener(this);

        //5.1以下系统SeekBar高度需要设置成WRAP_CONTENT
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mVideoVerticalScreenProgress.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }
    public void setData(VideoBean videoBean) {
        mTitle.setText(videoBean.getContent());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIsRegister) {
            getContext().unregisterReceiver(mBatteryReceiver);
            mIsRegister = false;
        }
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mIsRegister) {
            getContext().registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            mIsRegister = true;
        }
    }

    @Override
    public void attach(@NonNull ControlWrapper controlWrapper) {
        mControlWrapper = controlWrapper;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onVisibilityChanged(boolean isVisible, Animation anim) {
        //只在全屏时才有效
        if (!mControlWrapper.isFullScreen()) {
            return;
        }
        if (isVisible) {
            if (mTitleContainer.getVisibility() == GONE) {
                mSysTime.setText(PlayerUtils.getCurrentSystemTime());
                mTitleContainer.setVisibility(VISIBLE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        } else {
            if (mTitleContainer.getVisibility() == VISIBLE) {
                mTitleContainer.setVisibility(GONE);
                if (anim != null) {
                    startAnimation(anim);
                }
            }
        }
        if (isVisible) {
            mBottomContainer.setVisibility(VISIBLE);
            if (anim != null) {
                mBottomContainer.startAnimation(anim);
            }
        } else {
            mBottomContainer.setVisibility(GONE);
            if (anim != null) {
                mBottomContainer.startAnimation(anim);
            }
        }
    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_IDLE:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                setVisibility(GONE);
                mVideoProgress.setProgress(0);
                mVideoProgress.setSecondaryProgress(0);
                mVideoVerticalScreenProgress.setProgress(0);
                mVideoVerticalScreenProgress.setSecondaryProgress(0);
                break;
            case VideoView.STATE_START_ABORT:
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_PREPARED:
            case VideoView.STATE_ERROR:
                setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                mPlayButton.setSelected(true);
                mBottomContainer.setVisibility(GONE);
                setVisibility(VISIBLE);
                //开始刷新进度
                mControlWrapper.startProgress();
                break;
            case VideoView.STATE_PAUSED:
                mPlayButton.setSelected(false);
                break;
            case VideoView.STATE_BUFFERING:
                mPlayButton.setSelected(mControlWrapper.isPlaying());
                // 停止刷新进度
                mControlWrapper.stopProgress();
                break;
            case VideoView.STATE_BUFFERED:
                mPlayButton.setSelected(mControlWrapper.isPlaying());
                //开始刷新进度
                mControlWrapper.startProgress();
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                mFullScreen.setSelected(false);
                mTitleContainer.setVisibility(GONE);
                mTitle.setSelected(false);
                mBottomContainer.setVisibility(GONE);
                mBottomVerticalScreenContainer.setVisibility(VISIBLE);
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                mFullScreen.setSelected(true);
                if (mControlWrapper.isShowing() && !mControlWrapper.isLocked()) {
                    mTitleContainer.setVisibility(VISIBLE);
                    mSysTime.setText(PlayerUtils.getCurrentSystemTime());
                    mBottomContainer.setVisibility(VISIBLE);
                }
                mTitle.setSelected(true);
                mBottomVerticalScreenContainer.setVisibility(GONE);
                break;
        }

        Activity activity = PlayerUtils.scanForActivity(getContext());
        if (activity != null && mControlWrapper.hasCutout()) {
            int orientation = activity.getRequestedOrientation();
            int cutoutHeight = mControlWrapper.getCutoutHeight();
            if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                mBottomVerticalScreenContainer.setPadding(0, 0, 0, 0);
                mTitleContainer.setPadding(0, 0, 0, 0);
                mBottomContainer.setPadding(0, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mBottomVerticalScreenContainer.setPadding(cutoutHeight, 0, 0, 0);
                mTitleContainer.setPadding(cutoutHeight, 0, 0, 0);
                mBottomContainer.setPadding(cutoutHeight, 0, 0, 0);
            } else if (orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                mBottomVerticalScreenContainer.setPadding(0, 0, cutoutHeight, 0);
                mTitleContainer.setPadding(0, 0, cutoutHeight, 0);
                mBottomContainer.setPadding(0, 0, cutoutHeight, 0);
            }
        }
    }

    @Override
    public void setProgress(int duration, int position) {
        if (mIsDragging) {
            return;
        }
        if (mVideoVerticalScreenProgress != null) {
            if (duration > 0) {
                mVideoVerticalScreenProgress.setEnabled(true);
                mVideoProgress.setEnabled(true);
                int pos = (int) (position * 1.0 / duration * mVideoVerticalScreenProgress.getMax());
                mVideoVerticalScreenProgress.setProgress(pos);
                mVideoProgress.setProgress(pos);
            } else {
                mVideoVerticalScreenProgress.setEnabled(false);
                mVideoProgress.setEnabled(false);
            }
            int percent = mControlWrapper.getBufferedPercentage();
            if (percent >= 95) { //解决缓冲进度不能100%问题
                mVideoVerticalScreenProgress.setSecondaryProgress(mVideoVerticalScreenProgress.getMax());
                mVideoProgress.setSecondaryProgress(mVideoProgress.getMax());
            } else {
                mVideoVerticalScreenProgress.setSecondaryProgress(percent * 10);
                mVideoProgress.setSecondaryProgress(percent * 10);
            }
        }
        if (mTotalTime != null) {
            mTotalTime.setText(stringForTime(duration));
        }
        if (mCurrTime != null) {
            mCurrTime.setText(stringForTime(position));
        }
        if (time != null) {
            time.setText(stringForTime(position) + "/" + stringForTime(duration));
        }
    }

    @Override
    public void onLockStateChanged(boolean isLocked) {
        onVisibilityChanged(!isLocked, null);
        if (isLocked) {
            mTitleContainer.setVisibility(GONE);
        } else {
            mTitleContainer.setVisibility(VISIBLE);
            mSysTime.setText(PlayerUtils.getCurrentSystemTime());
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fullscreen) {
            toggleFullScreen();
        }
        else if (id == R.id.iv_play) {
            mControlWrapper.togglePlay();
        } else  if (id == R.id.back) {
            Activity activity = PlayerUtils.scanForActivity(getContext());
            if (activity != null && mControlWrapper.isFullScreen()) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mControlWrapper.stopFullScreen();
            }
        }
    }
    /**
     * 横竖屏切换
     */
    private void toggleFullScreen() {
        Activity activity = PlayerUtils.scanForActivity(getContext());
        mControlWrapper.toggleFullScreen(activity);
        // 下面方法会根据适配宽高决定是否旋转屏幕
//        mControlWrapper.toggleFullScreenByVideoSize(activity);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mIsDragging = true;
        mControlWrapper.stopProgress();
        mControlWrapper.stopFadeOut();
        time.setVisibility(GONE);
    }

    @Override
    public void onStopTrackingTouch(@NonNull SeekBar seekBar) {
        long duration = mControlWrapper.getDuration();
        long newPosition = (duration * seekBar.getProgress()) / mVideoVerticalScreenProgress.getMax();
        mControlWrapper.seekTo((int) newPosition);
        mIsDragging = false;
        mControlWrapper.startProgress();
        mControlWrapper.startFadeOut();
        time.setVisibility(GONE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }
        time.setVisibility(VISIBLE);
        long duration = mControlWrapper.getDuration();
        long newPosition = (duration * progress) / mVideoVerticalScreenProgress.getMax();
        if (time != null) {
            time.setText(stringForTime((int) newPosition) + "/" + stringForTime((int) duration));
        }
        if (mCurrTime != null) {
            mCurrTime.setText(stringForTime((int) newPosition));
        }
    }
    private static class BatteryReceiver extends BroadcastReceiver {
        private final ImageView pow;

        public BatteryReceiver(ImageView pow) {
            this.pow = pow;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            int current = extras.getInt("level");// 获得当前电量
            int total = extras.getInt("scale");// 获得总电量
            int percent = current * 100 / total;
            pow.getDrawable().setLevel(percent);
        }
    }
}
