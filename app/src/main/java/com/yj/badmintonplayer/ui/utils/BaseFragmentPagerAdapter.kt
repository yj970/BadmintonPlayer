package com.yj.badmintonplayer.ui.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BaseFragmentPagerAdapter : FragmentPagerAdapter {
    protected var fragments: List<Fragment>

    constructor(fm: FragmentManager, fragments: List<Fragment>) : super(fm) {
        this.fragments = fragments
    }

    constructor(fm: FragmentManager, behavior: Int, fragments: List<Fragment>) : super(
        fm,
        behavior
    ) {
        this.fragments = fragments
    }

    override fun getCount(): Int {
        return fragments.size
    }


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }
}