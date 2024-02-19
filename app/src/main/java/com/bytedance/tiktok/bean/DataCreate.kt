package com.bytedance.tiktok.bean

import com.bytedance.tiktok.R
import com.bytedance.tiktok.bean.VideoBean.UserBean
import com.lib.network.http.api.TestNetworkApi
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
        mutableNumbers.forEachIndexed { it, i ->
            val  videoData =     mutableNumbers.random()
            val videoBean = VideoBean()
            videoBean.content = "# "+videoData.type+ "# "+videoData.tags
            videoBean.videoRes = videoData.videos["tiny"]?.url.toString()
            videoBean.distance = 7.9f
            videoBean.isFocused = false
            videoBean.isLiked = true
            videoBean.likeCount = 226823
            videoBean.commentCount = 3480
            videoBean.shareCount = 4252
            val userBean = UserBean()
            userBean.uid = videoData.user_id
            userBean.head = videoData.userImageURL
            userBean.nickName = videoData.user
            userBean.sign = "# "+videoData.type+ "# "+videoData.tags +"# "+videoData.type+ "# "+videoData.tags
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

        @JvmField
        var mutableNumbers = mutableSetOf<TestNetworkApi.Bean.HitsData>()
        fun addData(mutableNumbers : MutableList<TestNetworkApi.Bean.HitsData>){
            mutableNumbers.forEachIndexed { it, i ->
                val  videoData =     mutableNumbers.random()
                val videoBean = VideoBean()
                videoBean.content = "# "+videoData.type+ "# "+videoData.tags
                videoBean.videoRes = videoData.videos["tiny"]?.url.toString()
                videoBean.distance = 7.9f
                videoBean.isFocused = false
                videoBean.isLiked = true
                videoBean.likeCount = 226823
                videoBean.commentCount = 3480
                videoBean.shareCount = 4252
                val userBean = UserBean()
                userBean.uid = videoData.user_id
                userBean.head = videoData.userImageURL
                userBean.nickName = videoData.user
                userBean.sign = "# "+videoData.type+ "# "+videoData.tags +"# "+videoData.type+ "# "+videoData.tags
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
    }

}