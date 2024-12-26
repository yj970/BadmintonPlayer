package com.yj.badmintonplayer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

class MineFragment: Fragment()  {

    companion object {
        fun newInstance(): Fragment {
            val args = Bundle()
            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}