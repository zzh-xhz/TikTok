package com.bytedance.tiktok.bean

import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean.UserBean
import java.util.*

/**
 * create by libo
 * create on 2020-06-03
 * description 本地数据创建，代替接口获取数据
 */
class DataCreate {
    var contents = mutableListOf("#街坊 #颜值打分 给自己颜值打100分的女生集合",
        "400 户摊主开进济南环联夜市，你们要的烟火气终于来了！",
        "科比生涯霸气庆祝动作，最后动作诠释了一生荣耀 #科比 @路人王篮球 ",
        "美好的一天，从发现美开始 #莉莉柯林斯 ",
        "有梦就去追吧，我说到做到。 #网球  #网球小威 ",
        "能力越大，责任越大，英雄可能会迟到，但永远不会缺席  #蜘蛛侠 ",
        "真的拍不出来你的神颜！现场看大屏帅疯！#陈情令南京演唱会 #王一博 😭",
        "逆序只是想告诉大家，学了舞蹈的她气质开了挂！")
    var distances = mutableListOf( 7.9f,)
    var isFocuseds= mutableListOf( false,true)
    var isLikeds = mutableListOf(false,true)
    var likeCounts = mutableListOf(226823,2262822,3480,348051,152)
    var commentCounts = mutableListOf(3480,2822,3480,348051,34804)
    var shareCounts = mutableListOf(4252,226,82822,3480,3480)
    var uids = mutableListOf(1,2,3,4,5,6,7,8,9,10,11)
    var heads = mutableListOf(R.mipmap.head1,R.mipmap.head2,R.mipmap.head3,R.mipmap.head4,R.mipmap.head5,R.mipmap.head6,
        R.mipmap.head7,R.mipmap.head8)
    var nickNames = mutableListOf("南京街坊", "民生直通车","七叶篮球","一只爱修图的剪辑师","国际网球联合会", "罗鑫颖","Sean", "曹小宝")
    var signs = mutableListOf("你们喜欢的话题，就是我们采访的内容",
        "直通现场新闻，直击社会热点，深入事件背后，探寻事实真相",
        "老科的视频会一直保留，想他了就回来看看",
        "接剪辑，活动拍摄，修图单\n 合作私信",
        "ITF国际网球联合会负责制定统一的网球规则，在世界范围内普及网球运动",
        "一个行走在Tr与剪辑之间的人\n 有什么不懂的可以来直播间问我",
        "云深不知处",
        "一个晒娃狂魔麻麻，平日里没啥爱好！喜欢拿着手机记录孩子成长片段，风格不喜勿喷！" )
    var subCounts = mutableListOf(119323,11933,11923,1323,)
    var focusCounts = mutableListOf(482,45,25,254,)
    var fansCounts = mutableListOf(32823,323,3223,3282,)
    var workCounts = mutableListOf(1123,821,42,423,)
    var dynamicCounts = mutableListOf(12,57,58,66,)
    var likeCountS = mutableListOf(821,8421,8251,7821,)

    fun initData(){
        contents.forEachIndexed { index, it ->
            val videoBean = VideoBean()
            videoBean.content = it
            videoBean.videoRes = mutableNumbers.random()
            videoBean.distance = distances.random()
            videoBean.isFocused = isFocuseds.random()
            videoBean.isLiked = isLikeds.random()
            videoBean.likeCount = likeCounts.random()
            videoBean.commentCount = commentCounts.random()
            videoBean.shareCount = shareCounts.random()
            val userBean = UserBean()
            userBean.uid = uids.random()
            userBean.head = heads[index]
            userBean.nickName = nickNames[index]
            userBean.sign = signs[index]
            userBean.subCount = subCounts.random()
            userBean.focusCount = focusCounts.random()
            userBean.fansCount = fansCounts.random()
            userBean.workCount = workCounts.random()
            userBean.dynamicCount = dynamicCounts.random()
            userBean.likeCount = likeCounts.random()
            userList.add(userBean)
            videoBean.userBean = userBean
            datas.add(videoBean)
        }
        mutableNumbers.forEachIndexed { index, i ->
            val videoBean = VideoBean()
            videoBean.content = "#街坊 #颜值打分 风景视频$i"
            videoBean.videoRes = mutableNumbers.get(index)
            videoBean.distance = 7.9f
            videoBean.isFocused = false
            videoBean.isLiked = true
            videoBean.likeCount = 226823
            videoBean.commentCount = 3480
            videoBean.shareCount = 4252
            val userBean = UserBean()
            userBean.uid = 9+index
            userBean.head = R.mipmap.head1
            userBean.nickName = "风景视频$index"
            userBean.sign = "风景视频风景视频风景视频风景视频风景视频$index"
            userBean.subCount = 119323
            userBean.focusCount = 482
            userBean.fansCount = 32823
            userBean.workCount = 42
            userBean.dynamicCount = 42
            userBean.likeCount = 821
            userList.add(userBean)
            videoBean.userBean = userBean
            datas.add(videoBean)
        }
    }
    init {
        initData()
    }

    companion object {
        @JvmField
        var datas = ArrayList<VideoBean>()
        @JvmField
        var userList = ArrayList<UserBean>()

        var mutableNumbers = mutableListOf("https://wave.video/embed/6581e9eedd95d26a606f6c87/6581e9eedd95d26a606f6c85.mp4",
            "https://vod-progressive.akamaized.net/exp=1708257088~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4242%2F11%2F296210754%2F1127677361.mp4~hmac=d2f3828d6370b94d520e8d7566f83060eb1a5d227bd0d0cf701b76b2a4b93880/vimeo-prod-skyfire-std-us/01/4242/11/296210754/1127677361.mp4",
            "https://images-assets.nasa.gov/video/Mars%202020%20Perserverance%20-%20Initial%20Surface%20Checkout%20Briefing/Mars%202020%20Perserverance%20-%20Initial%20Surface%20Checkout%20Briefing~orig.mp4",
            "https://images-assets.nasa.gov/video/Recientemente_0428/Recientemente_0428~orig.mp4",
            "https://images-assets.nasa.gov/video/jsc2015m000230/jsc2015m000230~orig.mp4",
            "https://images-assets.nasa.gov/video/KSC-20200530-MH-AJW02-0005-SpaceX_Demo-2_Astronaut_Tracking_Convoy_720/KSC-20200530-MH-AJW02-0005-SpaceX_Demo-2_Astronaut_Tracking_Convoy_720~orig.mp4",
             "https://images-assets.nasa.gov/video/JPL-20230929-PSYCHEf-0002-Imager/JPL-20230929-PSYCHEf-0002-Imager~orig.mp4")

    }
}