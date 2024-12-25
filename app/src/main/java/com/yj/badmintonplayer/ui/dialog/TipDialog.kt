package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.yj.badmintonplayer.databinding.DialogTipBinding
import com.yj.badmintonplayer.ui.utils.ScreenUtils

class TipDialog(context: Context, themeResId: Int, val tip: String) : Dialog(context, themeResId) {
    lateinit var mBinding: DialogTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogTipBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(false)


        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)
        window?.attributes?.width = (screenWidth * 0.75f).toInt()
        window?.attributes?.height = (screenHeight * 0.2f).toInt()

        initView()
        setListener()
    }

    private fun initView() {
        mBinding.tvTip.text = tip
    }

    private fun setListener() {
        mBinding.tvOk.setOnClickListener { dismiss() }
    }
}