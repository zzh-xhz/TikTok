package com.bytedance.tiktok.application

import android.os.StrictMode
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.airbnb.lottie.animation.content.Content
import com.bytedance.tiktok.utils.ProgressManagerImpl
import com.bytedance.tiktok.widget.render.SurfaceRenderViewFactory
import com.danikula.videocache.Logger
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import com.hjq.gson.factory.GsonFactory
import com.hjq.gson.factory.ParseExceptionCallback
import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestInterceptor
import com.hjq.http.config.IRequestServer
import com.hjq.http.model.HttpHeaders
import com.hjq.http.model.HttpParams
import com.hjq.http.request.HttpRequest
import com.hjq.toast.Toaster
import com.lib.network.http.model.RequestHandler
import com.lib.network.http.server.ReleaseServer
import com.lib.network.http.server.TestServer
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import okhttp3.OkHttpClient

import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager

/**
 * create by libo
 * create on 2020-05-19
 * description 全局Application
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        //播放器配置，注意：此为全局配置，按需开启
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG) //调试的时候请打开日志，方便排错
                /** 软解，支持格式较多，可通过自编译so扩展格式，结合 [xyz.doikki.dkplayer.widget.videoview.IjkVideoView] 使用更佳  */ //                .setPlayerFactory(IjkPlayerFactory.create())
                //.setPlayerFactory(AndroidMediaPlayerFactory.create()) //不推荐使用，兼容性较差
                /** 硬解，支持格式看手机，请使用CpuInfoActivity检查手机支持的格式，结合 [xyz.doikki.dkplayer.widget.videoview.ExoVideoView] 使用更佳  */
                .setPlayerFactory(ExoMediaPlayerFactory.create()) // 设置自己的渲染view，内部默认TextureView实现
                .setRenderViewFactory(SurfaceRenderViewFactory.create())
                // 根据手机重力感应自动切换横竖屏，默认false
                 .setEnableOrientation(true)
                // 监听系统中其他播放器是否获取音频焦点，实现不与其他播放器同时播放的效果，默认true
                 .setEnableAudioFocus(true)
                // 视频画面缩放模式，默认按视频宽高比居中显示在VideoView中
                 .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                // 适配刘海屏，默认true
                 .setAdaptCutout(true)
                // 移动网络下提示用户会产生流量费用，默认不提示，
                // 如果要提示则设置成false并在控制器中监听STATE_START_ABORT状态，实现相关界面，具体可以参考PrepareView的实现
                 .setPlayOnMobileNetwork(false)
                // 进度管理器，继承ProgressManager，实现自己的管理逻辑
                .setProgressManager(ProgressManagerImpl())
                .build()
        )

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy( StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy( StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
        // VideoCache 日志
        Logger.setDebug(BuildConfig.DEBUG)

        Toaster.init(this)
        MMKV.initialize(this)

        // Bugly 异常捕捉
        CrashReport.initCrashReport(this, "8ca94a2408", BuildConfig.DEBUG)

        // 设置 Json 解析容错监听
        GsonFactory.setParseExceptionCallback(object : ParseExceptionCallback {
            override fun onParseObjectException(
                typeToken: TypeToken<*>,
                fieldName: String,
                jsonToken: JsonToken
            ) {
                handlerGsonParseException("解析对象析异常：$typeToken#$fieldName，后台返回的类型为：$jsonToken")
            }

            override fun onParseListItemException(
                typeToken: TypeToken<*>,
                fieldName: String,
                listItemJsonToken: JsonToken
            ) {
                handlerGsonParseException("解析 List 异常：$typeToken#$fieldName，后台返回的条目类型为：$listItemJsonToken")
            }

            override fun onParseMapItemException(
                typeToken: TypeToken<*>,
                fieldName: String,
                mapItemKey: String,
                mapItemJsonToken: JsonToken
            ) {
                handlerGsonParseException("解析 Map 异常：$typeToken#$fieldName，mapItemKey = $mapItemKey，后台返回的条目类型为：$mapItemJsonToken")
            }

            private fun handlerGsonParseException(message: String) {
                require(!BuildConfig.DEBUG) { message }
                CrashReport.postCatchedException(IllegalArgumentException(message))
            }
        })

        // 网络请求框架初始化
        val server: IRequestServer
        server = if (BuildConfig.DEBUG) {
            TestServer()
        } else {
            ReleaseServer()
        }
        val okHttpClient = OkHttpClient.Builder()
            .build()
        EasyConfig.with(okHttpClient) // 是否打印日志
            .setLogEnabled(BuildConfig.DEBUG) // 设置服务器配置（必须设置）
            .setServer(server) // 设置请求处理策略（必须设置）
            .setHandler(RequestHandler(this)) // 设置请求参数拦截器
            .setInterceptor(object : IRequestInterceptor {
                override fun interceptArguments(
                    httpRequest: HttpRequest<*>,
                    params: HttpParams,
                    headers: HttpHeaders
                ) {
                    headers.put("timestamp", System.currentTimeMillis().toString())
                }
            })
            // 设置请求重试次数
            .setRetryCount(1)
            // 设置请求重试时间
            .setRetryTime(2000)
            // 添加全局请求参数
            .addParam("token", "46c49847a9efc74aef2bb089b57acccd")
            // 添加全局请求头
            //.addHeader("date", "20191030")
            .addHeader("Authorization", "P3pPyRUDwHGShlTWnV4hg2dUKelRi6V7cErdkKIb")
            .into()
    }
    companion object {
        @JvmStatic
        var instance: App? = null
            private set
    }
}