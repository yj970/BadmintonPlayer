package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogDeleteBinding
import com.yj.badmintonplayer.databinding.DialogTipBinding
import com.yj.badmintonplayer.ui.utils.ScreenUtils

class DeleteDialog(context: Context) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogDeleteBinding
    lateinit var mClickDeleteListener: IClickDeleteListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogDeleteBinding.inflate(layoutInflater)
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
    }

    private fun setListener() {
        mBinding.tvCancel.setOnClickListener { dismiss() }
        mBinding.tvDelete.setOnClickListener {
            dismiss()
            mClickDeleteListener.onClickDelete() }
    }

    interface IClickDeleteListener {
        fun onClickDelete()
    }
}