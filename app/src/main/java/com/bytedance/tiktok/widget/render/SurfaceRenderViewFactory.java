package com.bytedance.tiktok.widget.render;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.bytedance.tiktok.R;
import com.bytedance.tiktok.widget.render.gl2.filter.GlWatermarkFilter;

import xyz.doikki.videoplayer.render.IRenderView;
import xyz.doikki.videoplayer.render.RenderViewFactory;

public class SurfaceRenderViewFactory extends RenderViewFactory {

    public static SurfaceRenderViewFactory create() {
        return new SurfaceRenderViewFactory();
    }

    @Override
    public IRenderView createRenderView(Context context) {
        return new SurfaceRenderView(context);
    }
}
