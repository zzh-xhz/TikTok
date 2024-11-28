package com.bytedance.tiktok.utils

import androidx.lifecycle.LifecycleOwner
import com.bytedance.tiktok.bean.DataCreate
import com.hjq.gson.factory.GsonFactory
import com.hjq.http.EasyHttp
import com.hjq.http.body.JsonRequestBody
import com.hjq.http.listener.HttpCallbackProxy
import com.hjq.http.listener.OnHttpListener
import com.hjq.toast.Toaster
import com.lib.base.ui.BaseActivity
import com.lib.network.http.api.ReleaseFourApi
import com.lib.network.http.api.ReleaseOneApi
import com.lib.network.http.api.TestNetworkApi
import com.lib.network.http.model.HttpData

object NetworkRequestUtils {



    fun setSearchData(search : String,activity: LifecycleOwner) {

        EasyHttp.get(activity)
            .api(TestNetworkApi().apply {
                setKey("42445463-2b7d14a51075cb646c2011843")
                setQ(search)
            })
            .request(object : OnHttpListener<TestNetworkApi.Bean> {
                override fun onHttpSuccess(result: TestNetworkApi.Bean) {
                    DataCreate.addData(result.hits)
                }

                override fun onHttpFail(p0: Throwable) {
                }
            })
    }
    fun setSearchData(activity: LifecycleOwner) {
        var type = mutableSetOf<String>()
        type.add("animal")
        type.add("people")
        type.add("funny")
        type.add("humorous")
        type.add("sound")
        type.add("ShortVideo")
        type.add("plant")
        type.add("big")
        type.add("sound")
        type.add("flowers")




        EasyHttp.get(activity)
            .api(TestNetworkApi().apply {
                setKey("42445463-2b7d14a51075cb646c2011843")
                setQ(type.random())
            })
            .request(object : OnHttpListener<TestNetworkApi.Bean> {
                override fun onHttpSuccess(result: TestNetworkApi.Bean) {
                    DataCreate.addData(result.hits)
                }

                override fun onHttpFail(p0: Throwable) {
                }
            })
    }
    fun setSearchDataOne(activity: LifecycleOwner) {
//        repeat(10) {
//            EasyHttp.get(activity)
//                .api(ReleaseOneApi().apply {
//                    setType("json")
//                })
//                .request(object : OnHttpListener<ReleaseOneApi.Bean> {
//                    override fun onHttpSuccess(result: ReleaseOneApi.Bean) {
//                        DataCreate.type.add(result.mp4_video)
//                    }
//
//                    override fun onHttpFail(p0: Throwable) {
//                    }
//                })
//        }

    }
    fun setSearchDataFour(search : String,activity: BaseActivity) {
        EasyHttp.get(activity)
            .api(ReleaseFourApi().apply {
                setPage("1").setDate(System.currentTimeMillis().toString())
            })
            .request(object : HttpCallbackProxy<ReleaseFourApi.Bean>(activity) {
                override fun onHttpSuccess(result: ReleaseFourApi.Bean) {
//                    DataCreate.addData(result.itemList)
                }
            })
    }

}