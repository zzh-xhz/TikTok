package com.bytedance.tiktok.activity

import android.content.Intent
import android.os.CountDownTimer
import com.bytedance.tiktok.base.BaseBindingActivity
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.databinding.ActivitySplashBinding
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.hjq.http.listener.OnHttpListener
import com.hjq.toast.Toaster
import com.lib.network.http.api.TestLoginNetworkApi
import com.lib.network.http.api.TestNetworkApi

/**
 * create by libo
 * create on 2020/5/19
 * description App启动页面
 */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>({ActivitySplashBinding.inflate(it)}) {

    override fun init() {
        setFullScreen()
        DataCreate()
        var countDownTimer    = object : CountDownTimer(300, 300) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }

//        EasyHttp.post(this)
//            .api(TestLoginNetworkApi()
//                .setGrantType("a")
//            )
//            .request(object : HttpCallbackProxy<TestLoginNetworkApi.Bean>(this) {
//                override fun onHttpSuccess(result: TestLoginNetworkApi.Bean) {
//
//                }
//            })
//        EasyHttp.get(this)
//            .api(TestNetworkApi().setUserId("215607337"))
//            .request(object : HttpCallbackProxy<TestNetworkApi.Bean>(this) {
//                override fun onHttpSuccess(result: TestNetworkApi.Bean) {
//                    result.videos[0].video_files.forEach {
//                        DataCreate.mutableNumbers.add(it.link)
//                    }
//
//
//                }
//            })
        countDownTimer.start()

    }
}