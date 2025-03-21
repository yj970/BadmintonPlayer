package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogCreateRoomBinding
import com.yj.badmintonplayer.ui.adapter.CreateRoomPlayerAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.bean.PlayerBean
import com.yj.badmintonplayer.ui.utils.ScreenUtils
import com.yj.badmintonplayer.ui.utils.SizeUtils
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

class CreateRoomDialog(context: Context, res: Int) : Dialog(context, res) {
    private lateinit var mBinding: DialogCreateRoomBinding
    private var players = ArrayList<PlayerBean>()
    private lateinit var adapetr: CreateRoomPlayerAdapter
    private var count = 0
    var iConfirmListener: IConfirmListener? = null
    private var round = 2;// 默认2轮

    private enum class Model {
        SINGLE_NORMAL
    }

    private var model = Model.SINGLE_NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogCreateRoomBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCancelable(false)

        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        val screenHeight = ScreenUtils.getScreenHeight(context)
        window?.attributes?.width = screenWidth - SizeUtils.dp2px(30f)
        window?.attributes?.height = (screenHeight * 0.75f).toInt()

        initView()
        setListener()
    }

    private fun setListener() {
        mBinding.ivClose.setOnClickListener { dismiss() }
        mBinding.ivAddPlayer.setOnClickListener { addPlayer() }
        mBinding.tvStart.setOnClickListener {
            if (check()) {
                startGame()
            }
        }
        adapetr.iClickDeleteListener = object : CreateRoomPlayerAdapter.IClickDeleteListener {
            override fun onDelete(playerBean: PlayerBean) {
                players.remove(playerBean)
                adapetr.notifyDataSetChanged()
            }
        }
        mBinding.tvRound.setOnClickListener {
            showNumberPicker()
        }
    }

    // 检查数据
    private fun check(): Boolean {
        // 轮数必须大于1
        if (round < 1) {
            showTipDialog("循环轮数必须大于1")
            return false
        }
        // 人数必须大于2
        if (players.size < 2) {
            showTipDialog("参赛选手人数必须大于2")
            return false
        }
        return true;
    }

    private fun showTipDialog(tip: String) {
        val dialog =
            TipDialog(context, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog, tip)
        dialog.show()
    }


    // 展示选择轮数弹窗
    private fun showNumberPicker() {
        val numberDialog = NumberDialog(
            context,
            androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog,
            round, "循环轮数"
        )
        numberDialog.mConfirmListener = object : NumberDialog.IConfirmListener {
            override fun onConFirm(value: Int) {
                round = value
                mBinding.tvRound.text = round.toString()
            }
        }
        numberDialog.show()
    }

    private fun startGame() {
        dismiss()
        // 根据模式，安排对战表
        var finalList = ArrayList<PlayerBattleBean>();
        when (model) {
            Model.SINGLE_NORMAL -> {
                finalList = getSingleNormalGames()
            }
        }
        // 启动对战页面
        iConfirmListener?.onConfirm(
            GameBean(
                0,
                UUID.randomUUID().toString(),
                getRoomName(),
                System.currentTimeMillis(),
                finalList,
                getDefaultWinPoint()
            ),getAutoSaveBattlePic()
        )
    }

    // 获取获胜的默认分数
    private fun getDefaultWinPoint(): Int {
        when (mBinding.rgWin.checkedRadioButtonId) {
            R.id.rb_win_15 -> return 15
            R.id.rb_win_21 -> return 21
            else -> return 21
        }
    }

    // 开局保存对战表截图
    private fun getAutoSaveBattlePic(): Boolean {
        return mBinding.cbSaveBattle.isChecked
    }

    // 获取房名
    private fun getRoomName(): String {
        val text = mBinding.etRoomName.text.toString()
        return if (text.isEmpty()) mBinding.etRoomName.hint.toString() else text
    }

    private fun getSingleNormalGames(): ArrayList<PlayerBattleBean> {
        var finalList = ArrayList<PlayerBattleBean>()
        // 进行排序，尽量不连续出战
        var tempPlayerBattleBean: PlayerBattleBean? = null
        // 轮数
        for (i in 1..round) {
            val originList = CopyOnWriteArrayList<PlayerBattleBean>()
            for (p1 in players) {
                for (p2 in players) {
                    if (p1 == p2) {
                        continue
                    }
                    val playerBattleBean =
                        PlayerBattleBean(p1.id, p2.id, p1.getName(), p2.getName())
                    if (originList.contains(playerBattleBean)) {
                        continue
                    }
                    originList.add(playerBattleBean)
                }
            }
            // 打断顺序
            originList.shuffle();

            var resultList = CopyOnWriteArrayList<PlayerBattleBean>()
            while (originList.size > 0) {
                // 上次没有对战，随机取1个
                if (tempPlayerBattleBean == null) {
                    val index = (0..<originList.size).random()
                    val g = originList[index]
                    tempPlayerBattleBean = g
                    originList.remove(g)
                    resultList.add(g)
                }
                // 是否添加
                var find = false
                // 完全不匹配
                for (g in originList) {
                    if (tempPlayerBattleBean?.id1 != g.id1 && tempPlayerBattleBean?.id1 != g.id2 && tempPlayerBattleBean?.id2 != g.id1 && tempPlayerBattleBean?.id2 != g.id2) {
                        originList.remove(g)
                        resultList.add(g)
                        find = true
                        break
                    }
                }

                //  允许匹配1个
                if (!find) {
                    for (g in originList) {
                        if ((tempPlayerBattleBean?.id1 == g.id1 && tempPlayerBattleBean?.id2 != g.id2) || (tempPlayerBattleBean?.id1 != g.id1 && tempPlayerBattleBean?.id2 == g.id2) ||
                            (tempPlayerBattleBean?.id2 == g.id1 && tempPlayerBattleBean?.id1 != g.id2) || (tempPlayerBattleBean?.id2 != g.id1 && tempPlayerBattleBean?.id1 == g.id2)
                        ) {
                            originList.remove(g)
                            resultList.add(g)
                            find = true
                            break
                        }

                    }
                }

                // 直接加
                if (!find) {
                    for (g in originList) {
                        originList.remove(g)
                        resultList.add(g)
                        break
                    }
                }
                // 设置上一个对战名单
                if (resultList.size > 0) {
                    tempPlayerBattleBean = resultList[resultList.size - 1]
                }
            }
            finalList.addAll(resultList)
        }
        return finalList
    }

    private fun addPlayer() {
        count++
        val id = count.toString()
        val nickName = count.toString() + "号"
        players.add(PlayerBean(id, nickName))
        adapetr.notifyDataSetChanged()
        // 滑动到最下方，方便使用
        mBinding.ivAddPlayer.post({
            mBinding.nsv.fullScroll(View.FOCUS_DOWN)
        })
    }

    private fun initView() {
        // 初始化房间名
        val randomNum = (100000..999999).random()
        mBinding.etRoomName.hint = randomNum.toString()
        // 轮次
        mBinding.tvRound.text = round.toString()
        // 人员列表
        adapetr = CreateRoomPlayerAdapter(players)
        mBinding.rvPlayer.layoutManager = LinearLayoutManager(context)
        // 禁止复用
        mBinding.rvPlayer.recycledViewPool.setMaxRecycledViews(0, 0)
        mBinding.rvPlayer.adapter = adapetr
    }

    interface IConfirmListener {
        fun onConfirm(gameBattle: GameBean, autoSaveBattlePic:Boolean)
    }

}