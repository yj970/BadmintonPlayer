package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogCommonBinding

class CommonDialog(context: Context, val content: String) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogCommonBinding
    lateinit var mClickConfirmListener: IClickConfirmListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogCommonBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(false)


        // 宽高设置
//        val screenWidth = ScreenUtils.getScreenWidth(context)
//        val screenHeight = ScreenUtils.getScreenHeight(context)
//        window?.attributes?.width = (screenWidth * 0.75f).toInt()
//        window?.attributes?.height = (screenHeight * 0.2f).toInt()

        initView()
        setListener()
    }

    private fun initView() {
        mBinding.tvContent.text = content;
    }

    private fun setListener() {
        mBinding.tvCancel.setOnClickListener { dismiss() }
        mBinding.tvConfirm.setOnClickListener {
            dismiss()
            mClickConfirmListener.onClickConfirm()
        }
    }

    interface IClickConfirmListener {
        fun onClickConfirm()
    }
}