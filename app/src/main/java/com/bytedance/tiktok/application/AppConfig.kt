package com.bytedance.tiktok.application

//import com.bytedance.tiktok.BuildConfig
/**
 *    author : Android 张泽昊
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/09/02
 *    desc   : App 配置管理类
 */
object AppConfig {
    // logined appid
    const val IM_SDK_APPID = 1600046101


    const val DEMO_UI_STYLE_CLASSIC = 0
    const val DEMO_UI_STYLE_MINIMALIST = 1
    const val FLAVOR_LOCAL = "local"
    // app flavor
    const  val DEMO_FLAVOR_VERSION: String = FLAVOR_LOCAL

    const val DEMO_UI_STYLE = DEMO_UI_STYLE_CLASSIC

    // long connection addr: china、india ...
    const val DEMO_TEST_ENVIRONMENT = 0

    /**
     * 当前是否为调试模式
     */
    fun isDebug(): Boolean {
        return true
    }

//    /**
//     * 获取当前构建的模式
//     */
//    fun getBuildType(): String {
//        return BuildConfig.BUILD_TYPE
//    }
//
//    /**
//     * 当前是否要开启日志打印功能
//     */
//    fun isLogEnable(): Boolean {
//        return BuildConfig.LOG_ENABLE
//    }
//
//    /**
//     * 获取当前应用的包名
//     */
//    fun getPackageName(): String {
//        return BuildConfig.APPLICATION_ID
//    }
//
//    /**
//     * 获取当前应用的版本名
//     */
//    fun getVersionName(): String {
//        return BuildConfig.VERSION_NAME
//    }
//
//    /**
//     * 获取当前应用的版本码
//     */
//    fun getVersionCode(): Int {
//        return BuildConfig.VERSION_CODE
//    }
//
//    /**
//     * 获取 Bugly Id
//     */
//    fun getBuglyId(): String {
//        return BuildConfig.BUGLY_ID
//    }
//
//    /**
//     * 获取服务器主机地址
//     */
//    fun getHostUrl(): String {
//        return BuildConfig.HOST_URL
//    }
}