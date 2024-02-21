package com.bytedance.tiktok.utils

import com.bytedance.tiktok.bean.DataCreate
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.lib.base.ui.BaseActivity
import com.lib.network.http.api.TestNetworkApi

class NetworkRequestUtils {



    companion object {
        fun setSearchData(search : String,activity: BaseActivity) {
            EasyHttp.get(activity)
                .api(TestNetworkApi().apply {
                    setKey("42445463-2b7d14a51075cb646c2011843")
                    setQ(search)
                })
                .request(object : HttpCallbackProxy<TestNetworkApi.Bean>(activity) {
                    override fun onHttpSuccess(result: TestNetworkApi.Bean) {
                        DataCreate.addData(result.hits)
                    }
                })
        }
    }

}