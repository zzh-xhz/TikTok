package com.lib.network.http.server;

import androidx.annotation.NonNull;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2019/05/19
 *    desc   : 测试环境
 */
public class TestServer extends ReleaseServer {

    @NonNull
    @Override
    public String getHost() {
        return "https://pixabay.com/";
    }
}