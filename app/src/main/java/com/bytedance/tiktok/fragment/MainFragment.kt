package com.bytedance.tiktok.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.androidkun.xtablayout.XTabLayout
import com.bytedance.tiktok.R
import com.bytedance.tiktok.base.BaseBindingFragment
import com.bytedance.tiktok.base.CommPagerAdapter
import com.bytedance.tiktok.bean.MainPageChangeEvent
import com.bytedance.tiktok.bean.MainTabChangeEvent
import com.bytedance.tiktok.bean.PauseVideoEvent
import com.bytedance.tiktok.databinding.FragmentMainBinding
import com.bytedance.tiktok.utils.RxBus
import java.util.*


/**
 * create by libo
 * create on 2020-05-19
 * description 主页fragment
 */
class MainFragment : BaseBindingFragment<FragmentMainBinding>({FragmentMainBinding.inflate(it)}) {
    private var currentLocationFragment: CurrentLocationFragment? = null
    private var recommendFragment: RecommendFragment? = null
    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragments()
        setMainMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    private fun addChildFragment(childFragment: Fragment, containerId: Int) {
        // 获取 childFragmentManager
        val fragmentManager = childFragmentManager
        // 开启事务
        val fragmentTransaction = fragmentManager.beginTransaction()
        // 将子 Fragment 替换到指定容器中
        fragmentTransaction.replace(containerId, childFragment)
        // 提交事务
        fragmentTransaction.commitAllowingStateLoss ()
    }
    private fun setFragments() {
        currentLocationFragment = CurrentLocationFragment()
        recommendFragment = RecommendFragment()
        fragments.add(currentLocationFragment!!)
        fragments.add(recommendFragment!!)
        binding.tabTitle!!.addTab(binding.tabTitle!!.newTab().setText("海淀"))
        binding.tabTitle!!.addTab(binding.tabTitle!!.newTab().setText("推荐"))
        pagerAdapter = CommPagerAdapter(childFragmentManager, fragments, arrayOf("海淀", "推荐"))
        binding.viewPager!!.adapter = pagerAdapter
        binding.tabTitle!!.setupWithViewPager(binding.viewPager)
        binding.tabTitle!!.getTabAt(1)!!.select()
        binding.viewPager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                curPage = position
                if (position == 1) {
                    //继续播放
                    RxBus.getDefault().post(PauseVideoEvent(true))
                } else {
                    //切换到其他页面，需要暂停视频
                    RxBus.getDefault().post(PauseVideoEvent(false))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setMainMenu() {
        with(binding.tabMainMenu) {
            addTab(newTab().setText("首页"))
            addTab(newTab().setText("朋友"))
            addTab(newTab().setText(""))
            addTab(newTab().setText("消息"))
            addTab(newTab().setText("我"))
        }
        binding.tabMainMenu.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                // 处理选中标签的操作
                val position = tab?.position
                if (position != null) {
                    selectIndexFragment(position)
                }
                RxBus.getDefault().post(MainTabChangeEvent((position == 0 || position==1)))
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {
                // 处理取消选中标签的操作
            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {
                // 处理重新选中标签的操作
            }
        })
    }

    private fun selectIndexFragment(position :Int ){
        if (position == 0 ){
            binding.viewPager.visibility = View.VISIBLE
            binding.tabTitle.visibility = View.VISIBLE
            binding.fragmentContainer.visibility = View.GONE
            return
        }
        if (position == 2 ){
            return
        }
        if (position == 1 ||position == 3 || position == 4){
            binding.viewPager.visibility = View.GONE
            binding.tabTitle.visibility = View.GONE
            binding.fragmentContainer.visibility = View.VISIBLE
        }
        addChildFragment(getFragment(position), R.id.fragmentContainer)
    }
    private fun getFragment(position :Int):Fragment{
        if (position == 1 ){
            return FriendFragment()
        }
        if (position == 3 ){
            return MessageFragment()
        }
        if (position == 4 ){
            return MineFragment()
        }
        return FriendFragment()
    }

    companion object {
        /** 默认显示第一页推荐页  */
        var curPage = 1
    }
}