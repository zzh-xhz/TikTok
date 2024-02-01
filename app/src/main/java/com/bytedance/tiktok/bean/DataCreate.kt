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
        videoBeanOne.videoRes = "https://vod-progressive.akamaized.net/exp=1706770931~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2432%2F15%2F387164408%2F1632401348.mp4~hmac=ffee913d8ab18981ad15cbb210ea1bc7b1d530a4f3687632c40fa8954033e38f/vimeo-prod-skyfire-std-us/01/2432/15/387164408/1632401348.mp4"
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
        videoBeanTwo.videoRes = "https://vod-progressive.akamaized.net/exp=1706770958~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F1970%2F17%2F434854024%2F1894716308.mp4~hmac=76e7ac3aadf81ffb22b3b91af97a114a0e2130ab1864d2fbfb358b6b461cbb22/vimeo-prod-skyfire-std-us/01/1970/17/434854024/1894716308.mp4"
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
        videoBeanThree.videoRes = "https://vod-progressive.akamaized.net/exp=1706703442~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2448%2F15%2F387242416%2F1632812977.mp4~hmac=0b43e59c501151d3ba8e268b8ef85ae282119e1d7093828ec8ff4daaf4c5765e/vimeo-prod-skyfire-std-us/01/2448/15/387242416/1632812977.mp4"
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
        videoBeanFive.videoRes = "https://vod-progressive.akamaized.net/exp=1706703667~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4380%2F14%2F371900288%2F1544660896.mp4~hmac=9ed9297ca2a5d0844b0fa1468e1470296ca073e0a209b0f1956574f37421b88a/vimeo-prod-skyfire-std-us/01/4380/14/371900288/1544660896.mp4"
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
        videoBeanSix.videoRes = "https://vod-progressive.akamaized.net/exp=1706703702~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3034%2F15%2F390174124%2F1649517015.mp4~hmac=c07c01e27be43c30f9c15219acdedbaacdf42e87016d27bf59698e6974871fde/vimeo-prod-skyfire-std-us/01/3034/15/390174124/1649517015.mp4"
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
        videoBeanSeven.videoRes = "https://vod-progressive.akamaized.net/exp=1706703724~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4379%2F14%2F371899351%2F1544655187.mp4~hmac=fcc6ff53ecfdf4906a24d881366fe776dbb9854665761cd6e83f060d90eb0234/vimeo-prod-skyfire-std-us/01/4379/14/371899351/1544655187.mp4"
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
        videoBeanEight.videoRes = "https://vod-progressive.akamaized.net/exp=1706703748~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2432%2F15%2F387164408%2F1632401348.mp4~hmac=cdd979c4f5536edd93046d9c697bfe8f1d3a96c6459a2c106a57a00bdf2c8950/vimeo-prod-skyfire-std-us/01/2432/15/387164408/1632401348.mp4"
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
            "https://vod-progressive.akamaized.net/exp=1706705566~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F543%2F22%2F552717826%2F2614710679.mp4~hmac=834d716b5459bd58ecb6ad3d03e3e6b40d9af83b7da97eb64431c6d34707b2d9/vimeo-prod-skyfire-std-us/01/543/22/552717826/2614710679.mp4",
            "https://vod-progressive.akamaized.net/exp=1706705543~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F279%2F18%2F451398635%2F1985523194.mp4~hmac=f630479e4f7281816f1d52c9be711171ada2517a2a9a8b367b54c7641091a01f/vimeo-prod-skyfire-std-us/01/279/18/451398635/1985523194.mp4",
            "https://vod-progressive.akamaized.net/exp=1706705523~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3443%2F21%2F542216234%2F2571367001.mp4~hmac=3e78bd083758b28c9bca4468a84410225e209cd78dc121ce122a0fbf0391c555/vimeo-prod-skyfire-std-us/01/3443/21/542216234/2571367001.mp4",
            "https://vod-progressive.akamaized.net/exp=1706705453~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F812%2F20%2F504060275%2F2305073769.mp4~hmac=292acf8a8b18b3ff5909aa39ea455a3359bb1157b6a2471f70b9da8aa615819b/vimeo-prod-skyfire-std-us/01/812/20/504060275/2305073769.mp4" ,
            "https://vod-progressive.akamaized.net/exp=1706704195~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2808%2F17%2F439043656%2F1917625040.mp4~hmac=e4f6a5430cb2eb6d49cbe11b9def1559eccfe50bcc5bc0639b5e166c4fab614a/vimeo-prod-skyfire-std-us/01/2808/17/439043656/1917625040.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704243~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F649%2F17%2F428245187%2F1858143718.mp4~hmac=bb725c0d6c27505b100e7746a7c1ad68bdfb259c3df99370dddd1c9b41b619ef/vimeo-prod-skyfire-std-us/01/649/17/428245187/1858143718.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704309~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F1882%2F19%2F484412280%2F2169186157.mp4~hmac=e1901a39fc13804fb8c0b7a0ef0ec94ef0b32ac6397e4b7505abfdc00ba94bb2/vimeo-prod-skyfire-std-us/01/1882/19/484412280/2169186157.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704326~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F4996%2F16%2F424984094%2F1840391873.mp4~hmac=ef56d169986b54414b798fba672f0981310bb345d77b132ecbf91edb750901f0/vimeo-prod-skyfire-std-us/01/4996/16/424984094/1840391873.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704351~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F651%2F17%2F428257309%2F1858618230.mp4~hmac=3cb03034c4547a6f9b3dcabb38c376a1167881c63be633e53ed8df1bb3469aaf/vimeo-prod-skyfire-std-us/01/651/17/428257309/1858618230.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704367~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2347%2F17%2F436735203%2F1904908857.mp4~hmac=093f38f9d211d799274fa6e559c0fd367faa84f775a1cf3e9f9e2172d9f6ac96/vimeo-prod-skyfire-std-us/01/2347/17/436735203/1904908857.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704390~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F1882%2F19%2F484411812%2F2169183918.mp4~hmac=dec8e847d18436436f3b5bfea5e94805bc3afcaa76cc65b3439becc7e423fde6/vimeo-prod-skyfire-std-us/01/1882/19/484411812/2169183918.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704426~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3492%2F19%2F492460509%2F2216204811.mp4~hmac=907b9cabda9468738d55802e9546d3606a92e8d032bdab0352c4fba7184f3930/vimeo-prod-skyfire-std-us/01/3492/19/492460509/2216204811.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704446~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F1882%2F19%2F484411406%2F2169182695.mp4~hmac=df65edfcb7f1406b12ff4f215df79224ca48cf7f3e055fbb8073a68fd5ec74d2/vimeo-prod-skyfire-std-us/01/1882/19/484411406/2169182695.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704501~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3770%2F18%2F468853833%2F2084161726.mp4~hmac=28802ae2737a5959d54d9cef1ee4dd03afd19b676d52691f70968ababd36ac05/vimeo-prod-skyfire-std-us/01/3770/18/468853833/2084161726.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704574~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3527%2F22%2F567639933%2F2683768232.mp4~hmac=3be078a08e5671900ac9a073cfcdbe708dad31facd59dded308865b83b4047f0/vimeo-prod-skyfire-std-us/01/3527/22/567639933/2683768232.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704597~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F651%2F17%2F428256520%2F1858201956.mp4~hmac=a8692dd46784770ecdffe76d0a088ea4ad9a265bdffc0f1056ffab8fde2f4e95/vimeo-prod-skyfire-std-us/01/651/17/428256520/1858201956.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704622~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F2217%2F21%2F536085414%2F2535869884.mp4~hmac=a94ed91901fef08de62890dee21e2976075ff6bf0136df368b701558149e253d/vimeo-prod-skyfire-std-us/01/2217/21/536085414/2535869884.mp4",
            "https://vod-progressive.akamaized.net/exp=1706704652~acl=%2Fvimeo-prod-skyfire-std-us%2F01%2F3770%2F18%2F468854001%2F2084162434.mp4~hmac=b52b161df6b238d95acaf320f601563ee25324bb1a2f988b38320002ca9faae0/vimeo-prod-skyfire-std-us/01/3770/18/468854001/2084162434.mp4",
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