package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogBattleDetailBinding
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.utils.ScreenUtils
import com.yj.badmintonplayer.ui.utils.SizeUtils

class BattleDetailDialog(context: Context, val playerBattleBean: PlayerBattleBean) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogBattleDetailBinding
    lateinit var mPointUpdateListener: IPointUpdateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DialogBattleDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        window?.attributes?.width = screenWidth - SizeUtils.dp2px(30f)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        initView()
        setListener()
    }

    private fun setListener() {
        mBinding.ivPlayer1AddPoint.setOnClickListener {
            playerBattleBean.id1Point++
            initView()
            onPointUpdateListener()
        }
        mBinding.ivPlayer2AddPoint.setOnClickListener {
            playerBattleBean.id2Point++
            initView()
            onPointUpdateListener()
        }
        mBinding.ivPlayer1ReducePoint.setOnClickListener {
            playerBattleBean.id1Point--
            initView()
            onPointUpdateListener()
        }
        mBinding.ivPlayer2ReducePoint.setOnClickListener {
            playerBattleBean.id2Point--
            initView()
            onPointUpdateListener()
        }
        mBinding.ivClose.setOnClickListener { dismiss() }
    }

    private fun onPointUpdateListener() {
        mPointUpdateListener.onPointUpdateListener()
    }

    private fun initView() {
        mBinding.tvPlayer1Name.text = playerBattleBean.name1
        mBinding.tvPlayer1Point.text = playerBattleBean.id1Point.toString()
        mBinding.tvPlayer2Name.text = playerBattleBean.name2
        mBinding.tvPlayer2Point.text = playerBattleBean.id2Point.toString()
    }

    interface IPointUpdateListener {
        fun onPointUpdateListener()
    }
}