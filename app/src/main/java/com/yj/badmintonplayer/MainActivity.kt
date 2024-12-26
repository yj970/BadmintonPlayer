package com.yj.badmintonplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.yj.badmintonplayer.databinding.ActivityMainBinding
import com.yj.badmintonplayer.ui.fragment.HomeFragment
import com.yj.badmintonplayer.ui.fragment.MineFragment
import com.yj.badmintonplayer.ui.utils.BaseFragmentPagerAdapter
import kotlin.collections.ArrayList

class MainActivity : FragmentActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initView();
        initListener()
    }

    private fun initListener() {
        mBinding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mBinding.tablayout))
        mBinding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.viewpager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }


    private fun initView() {
        // viewpager
        var list = ArrayList<Fragment>();
        list.add(HomeFragment.newInstance())
        list.add(MineFragment.newInstance())
        mBinding.viewpager.adapter = BaseFragmentPagerAdapter(supportFragmentManager, list);
        // init tab
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("首页"))
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("我的"))
    }
}
