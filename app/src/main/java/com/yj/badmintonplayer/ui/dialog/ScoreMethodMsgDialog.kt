package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogCommonBinding
import com.yj.badmintonplayer.databinding.DialogScoreMethodMsgBinding

class ScoreMethodMsgDialog(context: Context, val type: Int) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogScoreMethodMsgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogScoreMethodMsgBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(true)
        setCanceledOnTouchOutside(true)


        // 宽高设置
//        val screenWidth = ScreenUtils.getScreenWidth(context)
//        val screenHeight = ScreenUtils.getScreenHeight(context)
//        window?.attributes?.width = (screenWidth * 0.75f).toInt()
//        window?.attributes?.height = (screenHeight * 0.2f).toInt()

        initView()
        setListener()
    }

    private fun initView() {
        if (type == TYPE_HIGH_FAR) {
            mBinding.llHighFar.visibility = View.VISIBLE
        }
        if (type == TYPE_LOB) {
            mBinding.llLob.visibility = View.VISIBLE
        }
        if (type == TYPE_SMASH) {
            mBinding.llSmash.visibility = View.VISIBLE
        }
        if (type == TYPE_SERVE) {
            mBinding.llServe.visibility = View.VISIBLE
        }
        if (type == TYPE_FLAT_DRIVE) {
            mBinding.llFlatDrive.visibility = View.VISIBLE
        }
        if (type == TYPE_PICK) {
            mBinding.llPick.visibility = View.VISIBLE
        }
        if (type == TYPE_NET_SMALL) {
            mBinding.llNetSmall.visibility = View.VISIBLE
        }
        if (type == TYPE_FAKE) {
            mBinding.llFake.visibility = View.VISIBLE
        }
        if (type == TYPE_SPEED) {
            mBinding.llVariableSpeed.visibility = View.VISIBLE
        }
        if (type == TYPE_ANGLE) {
            mBinding.llVariableAngle.visibility = View.VISIBLE
        }
    }

    private fun setListener() {
        mBinding.tvConfirm.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        val TYPE_HIGH_FAR = 0
        val TYPE_LOB = 1
        val TYPE_SMASH = 2
        val TYPE_SERVE = 3
        val TYPE_FLAT_DRIVE = 4
        val TYPE_PICK = 5
        val TYPE_NET_SMALL = 6
        val TYPE_FAKE = 7
        val TYPE_SPEED = 8
        val TYPE_ANGLE = 9
    }

}