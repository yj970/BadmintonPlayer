package com.yj.badmintonplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.yj.badmintonplayer.databinding.ActivityMainBinding
import com.yj.badmintonplayer.ui.fragment.HomeFragment
import com.yj.badmintonplayer.ui.utils.BaseFragmentPagerAdapter
import java.util.ArrayList
import java.util.Random

class MainActivity : FragmentActivity() {

    lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initView();
    }



    private fun initView() {
        // viewpager
        var list = ArrayList<Fragment>();
        list.add(HomeFragment.newInstance())
        list.add(HomeFragment.newInstance())
        mBinding.viewpager.adapter = BaseFragmentPagerAdapter(supportFragmentManager, list);
        // init tab
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("首页"))
        mBinding.tablayout.addTab(mBinding.tablayout.newTab().setText("我的"))
    }
}
