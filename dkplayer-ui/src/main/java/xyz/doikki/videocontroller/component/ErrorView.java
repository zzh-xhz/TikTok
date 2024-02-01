package xyz.doikki.videocontroller.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import xyz.doikki.videocontroller.R;
import xyz.doikki.videoplayer.controller.IControlComponent;
import xyz.doikki.videoplayer.controller.ControlWrapper;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * 播放出错提示界面
 * Created by Doikki on 2017/4/13.
 */
public class ErrorView extends LinearLayout implements IControlComponent {

    private float mDownX;
    private float mDownY;
    private TextView statusBtn;
    private ControlWrapper mControlWrapper;

    public ErrorView(Context context) {
        this(context, null);
    }




    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setVisibility(GONE);
        LayoutInflater.from(getContext()).inflate(R.layout.dkplayer_layout_error_view, this, true);
        statusBtn    = (TextView) findViewById(R.id.status_btn);
        statusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                mControlWrapper.replay(false);
            }
        });
        setClickable(true);
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

    }

    @Override
    public void onPlayStateChanged(int playState) {
        if (playState == VideoView.STATE_ERROR) {
            bringToFront();
            setVisibility(VISIBLE);
        } else if (playState == VideoView.STATE_IDLE) {
            setVisibility(GONE);
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void setProgress(int duration, int position) {

    }

    @Override
    public void onLockStateChanged(boolean isLock) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                // True if the child does not want the parent to intercept touch events.
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float absDeltaX = Math.abs(ev.getX() - mDownX);
                float absDeltaY = Math.abs(ev.getY() - mDownY);
                if (absDeltaX > ViewConfiguration.get(getContext()).getScaledTouchSlop() ||
                        absDeltaY > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 单击
     */
    public boolean onSingleTapConfirmed(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int[] location = new int[2];
        statusBtn.getLocationOnScreen(location);
        int viewX = location[0] -10;
        int viewY = location[1] -100;

        int viewWidth = statusBtn.getWidth()+10;
        int viewHeight = statusBtn.getHeight()+10;

        // 判断点击是否在 View 的范围内
        if (x >= viewX && x <= viewX + viewWidth && y >= viewY && y <= viewY + viewHeight) {
            // 在 View 的范围内
            // 在这里处理点击事件
            setVisibility(GONE);
            mControlWrapper.replay(false);
            return true;  // 返回 true 表示已经处理了触摸事件
        } else {
            // 不在 View 的范围内
            return false;  // 返回 false 表示未处理触摸事件，事件会继续传递
        }
    }

}
