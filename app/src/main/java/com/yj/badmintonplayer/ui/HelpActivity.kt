package com.yj.badmintonplayer.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.yj.badmintonplayer.databinding.ActivityHelpBinding

class HelpActivity : FragmentActivity() {
   lateinit var mBinding : ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setListener()
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener {
            finish()
        }
    }
}