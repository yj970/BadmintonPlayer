package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogCommonBinding
import com.yj.badmintonplayer.databinding.DialogConfirmScoreMethodBinding
import com.yj.badmintonplayer.databinding.DialogEditScoreMethodPointBinding
import com.yj.badmintonplayer.ui.bean.ScoreMethod
import com.yj.badmintonplayer.ui.utils.ScreenUtils
import com.yj.badmintonplayer.ui.utils.SizeUtils

class ConfirmScoreMethodDialog(context: Context, val scoreMethod: ScoreMethod) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogConfirmScoreMethodBinding
    lateinit var mClickConfirmListener: IClickConfirmListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogConfirmScoreMethodBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)


        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        window?.attributes?.width = screenWidth - SizeUtils.dp2px(30f)
//        window?.attributes?.height = SizeUtils.dp2px(300f)

        initView()
        setListener()
    }

    private fun initView() {
    }

    private fun setListener() {
        mBinding.tvHighFar.setOnClickListener {
            ++scoreMethod.highFar
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvLob.setOnClickListener {
            ++scoreMethod.lob
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvSmash.setOnClickListener {
            ++scoreMethod.smash
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvServe.setOnClickListener {
            ++scoreMethod.serve
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvFlatDrive.setOnClickListener {
            ++scoreMethod.flatDrive
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvPick.setOnClickListener {
            ++scoreMethod.pick
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvNetSmall.setOnClickListener {
            ++scoreMethod.netSmall
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvFake.setOnClickListener {
            ++scoreMethod.fake
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvVariableSpeed.setOnClickListener {
            ++scoreMethod.variableSpeed
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvVariableAngle.setOnClickListener {
            ++scoreMethod.variableAngle
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
        mBinding.tvNormal.setOnClickListener {
            mClickConfirmListener.onClickConfirm()
            dismiss()
        }
    }

    interface IClickConfirmListener {
        fun onClickConfirm()
    }
}