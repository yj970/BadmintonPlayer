package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogCommonBinding
import com.yj.badmintonplayer.databinding.DialogEditScoreMethodPointBinding

class EditScoreMethodPointDialog(context: Context) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogEditScoreMethodPointBinding
    lateinit var mClickConfirmListener: IClickConfirmListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogEditScoreMethodPointBinding.inflate(layoutInflater)
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
        mBinding.tvConfirm.setOnClickListener {
            if (mBinding.etContent.text.isEmpty()) {
                Toast.makeText(context, "请输入修改的分数", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            dismiss()
            val p = mBinding.etContent.text.toString().toInt()
            mClickConfirmListener.onClickConfirm(p)
        }
    }

    interface IClickConfirmListener {
        fun onClickConfirm(p: Int)
    }
}