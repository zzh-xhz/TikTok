package com.lib.network.http.api;

import androidx.annotation.NonNull;

import com.hjq.http.config.IRequestApi;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2019/11/18
 *    desc   : 短剧查询
 */
public final class ReleaseThereApi implements IRequestApi {
    /** userid*/
    private String type;

    //https://api.kuleu.com/api/action
    @NonNull
    @Override
    public String getApi() {
        return "api/action";
    }

    public ReleaseThereApi setType(String type) {
        this.type = type;
        return this;
    }



    public final static class Bean {
        private String video;

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }
    }
}