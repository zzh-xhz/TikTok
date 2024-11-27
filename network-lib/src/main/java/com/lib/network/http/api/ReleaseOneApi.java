package com.lib.network.http.api;

import androidx.annotation.NonNull;

import com.google.gson.internal.LinkedTreeMap;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.config.IRequestHost;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2019/11/18
 *    desc   : 高质量小姐姐
 */
public final class ReleaseOneApi implements IRequestApi , IRequestHost {
    /** userid*/
    private String type;

    //https://api.kuleu.com/api/MP4_xiaojiejie
    @NonNull
    @Override
    public String getApi() {
        return "api/MP4_xiaojiejie";
    }
    @NonNull
    @Override
    public String getHost() {
        return "http://baobab.kaiyanapp.com";
    }
    public ReleaseOneApi setType(String type) {
        this.type = type;
        return this;
    }


    public final static class Bean {
        private String mp4_video;
        private int code;
        private String msg;

        public String getMp4_video() {
            return mp4_video;
        }

        public void setMp4_video(String mp4_video) {
            this.mp4_video = mp4_video;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}