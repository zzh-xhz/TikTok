package com.bytedance.tiktok.activity

import android.content.Intent
import android.os.CountDownTimer
import androidx.core.content.ContextCompat.startActivity
import com.bytedance.tiktok.application.AppConfig
import com.bytedance.tiktok.bean.DataCreate
import com.bytedance.tiktok.bean.DataCreate.Companion.type
import com.bytedance.tiktok.databinding.ActivitySplashBinding
import com.bytedance.tiktok.utils.NetworkRequestUtils
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.lib.base.ui.BaseBindingActivity
import com.lib.network.http.api.TestNetworkApi


/**
 * create by libo
 * create on 2020/5/19
 * description App启动页面
 */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>({ActivitySplashBinding.inflate(it)}) {

    override fun init() {
        setFullScreen()
        var countDownTimer    = object : CountDownTimer(300, 300) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
        if (AppConfig.isDebug()){

//            NetworkRequestUtils.setSearchDataOne(this)
            NetworkRequestUtils.setSearchData(this)
            EasyHttp.get(this)
                .api(TestNetworkApi().apply {
                    setKey("42445463-2b7d14a51075cb646c2011843")
                    setQ("beauty")
                })
                .request(object : HttpCallbackProxy<TestNetworkApi.Bean>(this) {
                    override fun onHttpSuccess(result: TestNetworkApi.Bean) {
                        DataCreate.mutableNumbers.addAll(result.hits)
                        DataCreate()
                        countDownTimer.start()
                    }
                })
        }else{

        }



    }


}
