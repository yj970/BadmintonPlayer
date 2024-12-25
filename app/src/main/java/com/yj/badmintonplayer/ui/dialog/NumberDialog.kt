package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import com.yj.badmintonplayer.databinding.DialogNumberBinding
import com.yj.badmintonplayer.ui.utils.ScreenUtils

class NumberDialog(context: Context, themeResId: Int, val default: Int, val title: String) :
    Dialog(context, themeResId) {
    lateinit var mBinding: DialogNumberBinding
    var mConfirmListener: IConfirmListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogNumberBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(false)

        // 设置底部
        window?.setGravity(Gravity.BOTTOM)

        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)
        window?.attributes?.width = screenWidth
        window?.attributes?.height = (screenHeight * 0.33f).toInt()

        initView()
        setListener()
    }

    private fun initView() {
        mBinding.tvTitle.text = title
        mBinding.numberpicker.minValue = 0
        mBinding.numberpicker.maxValue = 30
        mBinding.numberpicker.value = default
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener { dismiss() }
        mBinding.tvConfirm.setOnClickListener {
            mConfirmListener?.onConFirm(mBinding.numberpicker.value)
            dismiss()
        }
    }

    interface IConfirmListener {
        fun onConFirm(value: Int)
    }
}