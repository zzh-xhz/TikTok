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

    init {

        val videoBeanOne = VideoBean()
        videoBeanOne.content = "#街坊 #颜值打分 给自己颜值打100分的女生集合"
        videoBeanOne.videoRes = "https://vod-progressive.akamaized.net/exp=1707027538~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F385%2F18%2F451928865%2F1988500490.mp4~hmac=e5108d415f071154d3ac09868645bdb0465758647934a0d694dbb9f1e853a718/vimeo-prod-skyfire-std-us/01/385/18/451928865/1988500490.mp4"
        videoBeanOne.distance = 7.9f
        videoBeanOne.isFocused = false
        videoBeanOne.isLiked = true
        videoBeanOne.likeCount = 226823
        videoBeanOne.commentCount = 3480
        videoBeanOne.shareCount = 4252

        val userBeanOne = UserBean()
        userBeanOne.uid = 1
        userBeanOne.head = R.mipmap.head1
        userBeanOne.nickName = "南京街坊"
        userBeanOne.sign = "你们喜欢的话题，就是我们采访的内容"
        userBeanOne.subCount = 119323
        userBeanOne.focusCount = 482
        userBeanOne.fansCount = 32823
        userBeanOne.workCount = 42
        userBeanOne.dynamicCount = 42
        userBeanOne.likeCount = 821
        userList.add(userBeanOne)
        videoBeanOne.userBean = userBeanOne

        val videoBeanTwo = VideoBean()
        videoBeanTwo.content = "400 户摊主开进济南环联夜市，你们要的烟火气终于来了！"
        videoBeanTwo.videoRes = "https://vod-progressive.akamaized.net/exp=1707027706~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3385%2F16%2F416928233%2F1796694596.mp4~hmac=29f90b2f3188b6b05cf51f3028bac3cdff0a651c260ee60b9580010d24036e1f/vimeo-prod-skyfire-std-us/01/3385/16/416928233/1796694596.mp4"
        videoBeanTwo.distance = 19.7f
        videoBeanTwo.isFocused = true
        videoBeanTwo.isLiked = false
        videoBeanTwo.likeCount = 1938230
        videoBeanTwo.commentCount = 8923
        videoBeanTwo.shareCount = 5892

        val userBeanTwo = UserBean()
        userBeanTwo.uid = 2
        userBeanTwo.head = R.mipmap.head2
        userBeanTwo.nickName = "民生直通车"
        userBeanTwo.sign = "直通现场新闻，直击社会热点，深入事件背后，探寻事实真相"
        userBeanTwo.subCount = 20323234
        userBeanTwo.focusCount = 244
        userBeanTwo.fansCount = 1938232
        userBeanTwo.workCount = 123
        userBeanTwo.dynamicCount = 123
        userBeanTwo.likeCount = 344
        userList.add(userBeanTwo)
        videoBeanTwo.userBean = userBeanTwo

        val videoBeanThree = VideoBean()
        videoBeanThree.content = "科比生涯霸气庆祝动作，最后动作诠释了一生荣耀 #科比 @路人王篮球 "
        videoBeanThree.videoRes = "https://vod-progressive.akamaized.net/exp=1707027413~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4437%2F14%2F372187775%2F1546286750.mp4~hmac=2333d4ac52e153cc15dc40381d2e7d584d2794bcf642f8bec59c2ed97ee20429/vimeo-prod-skyfire-std-us/01/4437/14/372187775/1546286750.mp4"
        videoBeanThree.distance = 15.9f
        videoBeanThree.isFocused = false
        videoBeanThree.isLiked = false
        videoBeanThree.likeCount = 592032
        videoBeanThree.commentCount = 9221
        videoBeanThree.shareCount = 982

        val userBeanThree = UserBean()
        userBeanThree.uid = 3
        userBeanThree.head = R.mipmap.head3
        userBeanThree.nickName = "七叶篮球"
        userBeanThree.sign = "老科的视频会一直保留，想他了就回来看看"
        userBeanThree.subCount = 1039232
        userBeanThree.focusCount = 159
        userBeanThree.fansCount = 29232323
        userBeanThree.workCount = 171
        userBeanThree.dynamicCount = 173
        userBeanThree.likeCount = 1724
        userList.add(userBeanThree)
        videoBeanThree.userBean = userBeanThree

        val videoBeanFour = VideoBean()
        videoBeanFour.content = "美好的一天，从发现美开始 #莉莉柯林斯 "
        videoBeanFour.videoRes = "https://vod-progressive.akamaized.net/exp=1706703617~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F1970%2F17%2F434854822%2F1894717252.mp4~hmac=186a4850b29527f783a2ef197676070dfe744514a664a3d096679bcf5a95da95/vimeo-prod-skyfire-std-us/01/1970/17/434854822/1894717252.mp4"
        videoBeanFour.distance = 25.2f
        videoBeanFour.isFocused = false
        videoBeanFour.isLiked = false
        videoBeanFour.likeCount = 887232
        videoBeanFour.commentCount = 2731
        videoBeanFour.shareCount = 8924

        val userBeanFour = UserBean()
        userBeanFour.uid = 4
        userBeanFour.head = R.mipmap.head4
        userBeanFour.nickName = "一只爱修图的剪辑师"
        userBeanFour.sign = "接剪辑，活动拍摄，修图单\n 合作私信"
        userBeanFour.subCount = 2689424
        userBeanFour.focusCount = 399
        userBeanFour.fansCount = 360829
        userBeanFour.workCount = 562
        userBeanFour.dynamicCount = 570
        userBeanFour.likeCount = 4310
        userList.add(userBeanFour)
        videoBeanFour.userBean = userBeanFour

        val videoBeanFive = VideoBean()
        videoBeanFive.content = "有梦就去追吧，我说到做到。 #网球  #网球小威 "
        videoBeanFive.videoRes = "https://vod-progressive.akamaized.net/exp=1707027466~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2419%2F15%2F387095389%2F1632039168.mp4~hmac=28dc840af1f2006caab5e4d7812d54bca415beaea542c6283cb2ede09dd2f38a/vimeo-prod-skyfire-std-us/01/2419/15/387095389/1632039168.mp4"
        videoBeanFive.distance = 9.2f
        videoBeanFive.isFocused = false
        videoBeanFive.isLiked = false
        videoBeanFive.likeCount = 8293241
        videoBeanFive.commentCount = 982
        videoBeanFive.shareCount = 8923

        val userBeanFive = UserBean()
        userBeanFive.uid = 5
        userBeanFive.head = R.mipmap.head5
        userBeanFive.nickName = "国际网球联合会"
        userBeanFive.sign = "ITF国际网球联合会负责制定统一的网球规则，在世界范围内普及网球运动"
        userBeanFive.subCount = 1002342
        userBeanFive.focusCount = 87
        userBeanFive.fansCount = 520232
        userBeanFive.workCount = 89
        userBeanFive.dynamicCount = 122
        userBeanFive.likeCount = 9
        userList.add(userBeanFive)
        videoBeanFive.userBean = userBeanFive

        val videoBeanSix = VideoBean()
        videoBeanSix.content = "能力越大，责任越大，英雄可能会迟到，但永远不会缺席  #蜘蛛侠 "
        videoBeanSix.videoRes = "https://vod-progressive.akamaized.net/exp=1707027518~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F318%2F16%2F401591831%2F1715345490.mp4~hmac=ba392097e0cdf0bf7f301e4fe3633392f0b8a3cfb35c9614d6bb6cd4de28759d/vimeo-prod-skyfire-std-us/01/318/16/401591831/1715345490.mp4"
        videoBeanSix.distance = 16.4f
        videoBeanSix.isFocused = true
        videoBeanSix.isLiked = true
        videoBeanSix.likeCount = 2109823
        videoBeanSix.commentCount = 9723
        videoBeanFive.shareCount = 424

        val userBeanSix = UserBean()
        userBeanSix.uid = 6
        userBeanSix.head = R.mipmap.head6
        userBeanSix.nickName = "罗鑫颖"
        userBeanSix.sign = "一个行走在Tr与剪辑之间的人\n 有什么不懂的可以来直播间问我"
        userBeanSix.subCount = 29342320
        userBeanSix.focusCount = 67
        userBeanSix.fansCount = 7028323
        userBeanSix.workCount = 5133
        userBeanSix.dynamicCount = 5159
        userBeanSix.likeCount = 0
        userList.add(userBeanSix)
        videoBeanSix.userBean = userBeanSix

        val videoBeanSeven = VideoBean()
        videoBeanSeven.content = "真的拍不出来你的神颜！现场看大屏帅疯！#陈情令南京演唱会 #王一博 😭"
        videoBeanSeven.videoRes = "https://vod-progressive.akamaized.net/exp=1707027529~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4371%2F18%2F471856883%2F2101203658.mp4~hmac=9b22d108cd46cfdad29a4282a248511196dfd7f587c316792647a76d2c19f260/vimeo-prod-skyfire-std-us/01/4371/18/471856883/2101203658.mp4"
        videoBeanSeven.distance = 16.4f
        videoBeanSeven.isFocused = false
        videoBeanSeven.isLiked = false
        videoBeanSeven.likeCount = 185782
        videoBeanSeven.commentCount = 2452
        videoBeanSeven.shareCount = 3812

        val userBeanSeven = UserBean()
        userBeanSeven.uid = 7
        userBeanSeven.head = R.mipmap.head7
        userBeanSeven.nickName = "Sean"
        userBeanSeven.sign = "云深不知处"
        userBeanSeven.subCount = 471932
        userBeanSeven.focusCount = 482
        userBeanSeven.fansCount = 371423
        userBeanSeven.workCount = 242
        userBeanSeven.dynamicCount = 245
        userBeanSeven.likeCount = 839
        userList.add(userBeanSeven)
        videoBeanSeven.userBean = userBeanSeven

        val videoBeanEight = VideoBean()
        videoBeanEight.content = "逆序只是想告诉大家，学了舞蹈的她气质开了挂！"
        videoBeanEight.videoRes = "https://vod-progressive.akamaized.net/exp=1707027528~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2429%2F15%2F387149499%2F1632322452.mp4~hmac=d9a66768b6f466b79f4707cff7be9e61d9ee1eecd6bdcb3aeaace37b4ea05735/vimeo-prod-skyfire-std-us/01/2429/15/387149499/1632322452.mp4"
        videoBeanEight.distance = 8.4f
        videoBeanEight.isFocused = false
        videoBeanEight.isLiked = false
        videoBeanEight.likeCount = 1708324
        videoBeanEight.commentCount = 8372
        videoBeanEight.shareCount = 982

        val userBeanEight = UserBean()
        userBeanEight.uid = 8
        userBeanEight.head = R.mipmap.head8
        userBeanEight.nickName = "曹小宝"
        userBeanEight.sign = "一个晒娃狂魔麻麻，平日里没啥爱好！喜欢拿着手机记录孩子成长片段，风格不喜勿喷！"
        userBeanEight.subCount = 1832342
        userBeanEight.focusCount = 397
        userBeanEight.fansCount = 1394232
        userBeanEight.workCount = 164
        userBeanEight.dynamicCount = 167
        userBeanEight.likeCount = 0
        userList.add(userBeanEight)
        videoBeanEight.userBean = userBeanEight

        datas.add(videoBeanOne)
        datas.add(videoBeanTwo)
        datas.add(videoBeanThree)
        datas.add(videoBeanFour)
        datas.add(videoBeanFive)
        datas.add(videoBeanSix)
        datas.add(videoBeanSeven)
        datas.add(videoBeanEight)
        datas.add(videoBeanOne)
        datas.add(videoBeanTwo)
        datas.add(videoBeanThree)
        datas.add(videoBeanFour)
        datas.add(videoBeanFive)
        datas.add(videoBeanSix)
        datas.add(videoBeanSeven)
        datas.add(videoBeanEight)

        val mutableNumbers = mutableListOf(
            "https://vod-progressive.akamaized.net/exp=1707027538~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F385%2F18%2F451928865%2F1988500490.mp4~hmac=e5108d415f071154d3ac09868645bdb0465758647934a0d694dbb9f1e853a718/vimeo-prod-skyfire-std-us/01/385/18/451928865/1988500490.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027829~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2428%2F15%2F387140873%2F1632278061.mp4~hmac=59b7f2d30715a18fa452f918ba550601fd3cf2b02b934e551921ff58758940d1/vimeo-prod-skyfire-std-us/01/2428/15/387140873/1632278061.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027861~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2387%2F17%2F436938860%2F1906077980.mp4~hmac=dee35de7867b7b2756ab11c6eedd8aedd249b2153547d4fc079525408495dd4c/vimeo-prod-skyfire-std-us/01/2387/17/436938860/1906077980.mp",
            "https://vod-progressive.akamaized.net/exp=1707027785~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F540%2F17%2F427702632%2F1855285443.mp4~hmac=693423470221ced0988ddc797b14c3d46be604936c8977183db75bf186b1d206/vimeo-prod-skyfire-std-us/01/540/17/427702632/1855285443.mp4",
            "https://vod-progressive.akamaized.net/exp=1707027861~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2387%2F17%2F436938860%2F1906077980.mp4~hmac=dee35de7867b7b2756ab11c6eedd8aedd249b2153547d4fc079525408495dd4c/vimeo-prod-skyfire-std-us/01/2387/17/436938860/1906077980.mp",
            "https://vod-progressive.akamaized.net/exp=1706704666~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F324%2F16%2F401621673%2F1715502240.mp4~hmac=c708b28e0de534f4163e100a97e9ae53692a6e9da25b0fd00a25419c65c3774a/vimeo-prod-skyfire-std-us/01/324/16/401621673/1715502240.mp4")
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

    companion object {
        @JvmField
        var datas = ArrayList<VideoBean>()
        @JvmField
        var userList = ArrayList<UserBean>()
    }
}