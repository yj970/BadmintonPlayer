package com.yj.badmintonplayer.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.ActivityBattleBinding
import com.yj.badmintonplayer.ui.adapter.BattleAdapter
import com.yj.badmintonplayer.ui.bean.BroadcastBean
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.db.ObjectBox
import com.yj.badmintonplayer.ui.dialog.CommonDialog
import com.yj.badmintonplayer.ui.dialog.TipDialog
import com.yj.badmintonplayer.ui.utils.DataLock
import com.yj.badmintonplayer.ui.utils.SizeUtils
import com.yj.badmintonplayer.ui.utils.UDPUtil
import java.net.InetAddress
import java.util.Timer
import java.util.TimerTask

class PlayerBattleActivity : FragmentActivity() {
    lateinit var mBinding: ActivityBattleBinding
    lateinit var game: GameBean
    var isRoomer = true// 是否是房主
    val udpUtil = UDPUtil()
    lateinit var battleAdapter: BattleAdapter
    var checkOnlineTimer: Timer? = null

    // 回复房主的地址
    var replayInetAddress: InetAddress? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        initView()
        setListener()
        addOrUpdate()
        startReceiverData()
    }

    private fun startReceiverData() {
        udpUtil.startReceive(object : UDPUtil.IReceiverListener {
            override fun onReceiver(broadcastBean: BroadcastBean, address: InetAddress) {
                // 更新在线状态
                updateOnlineUIIfNotRoomer()
                // 客人设置回复地址
                setReplayAddressIfNotRoomer(address)
                // 过滤非本房间数据
                syncGameDataIfThisRoomGame(broadcastBean)
                // 同步数据给客人
//                sendRoomerBroadcastIfIsRoomer(broadcastBean)
            }
        })
    }

    private fun updateOnlineUIIfNotRoomer() {
        mBinding.tvOnlineState.post {
            mBinding.tvOnlineState.text = "在线"
            mBinding.tvOnlineState.setTextColor(Color.GREEN)
            if (checkOnlineTimer != null) {
                checkOnlineTimer?.cancel()
            }
            checkOnlineTimer = Timer()
            checkOnlineTimer?.schedule(object : TimerTask() {
                override fun run() {
                    updateOfflineUIIfNotRoomer()
                }
            }, 5000)
        }
    }

    private fun updateOfflineUIIfNotRoomer() {
        mBinding.tvOnlineState.post {
            mBinding.tvOnlineState.text = "离线"
            mBinding.tvOnlineState.setTextColor(Color.GRAY)
        }
    }

    private fun setReplayAddressIfNotRoomer(address: InetAddress) {
        if (!isRoomer) {
            replayInetAddress = address
        }
    }

    // 同步房间数据
    private fun syncGameDataIfThisRoomGame(
        broadcastBean: BroadcastBean
    ) {
        synchronized(DataLock.dataLock) {
            // 默认不过滤，是房主且是心跳包，则过滤
            var filter = false
            if (isRoomer && broadcastBean.type == BroadcastBean.BroadcastType.TYPE_HEART_BEAT) {
                filter = true
            }
            // 是否过滤
            if (!filter) {
                val gameBean = broadcastBean.gameBean
                // 判断是否是改房间数据
                if (gameBean.gameId == game.gameId) {
                    // 判断远程比分与本地比分是否相同
                    var pointChange = false
                    for (i in 0 until game.playerBattleBeans.size) {
                        val remotePoint1 = gameBean.playerBattleBeans[i].id1Point
                        val remotePoint2 = gameBean.playerBattleBeans[i].id2Point
                        val remoteScoreMethod1 = gameBean.playerBattleBeans[i].id1ScoreMethod
                        val remoteScoreMethod2 = gameBean.playerBattleBeans[i].id2ScoreMethod
                        val localPoint1 = game.playerBattleBeans[i].id1Point
                        val localPoint2 = game.playerBattleBeans[i].id2Point
                        val localScoreMethod1 = game.playerBattleBeans[i].id1ScoreMethod
                        val localScoreMethod2 = game.playerBattleBeans[i].id2ScoreMethod
                        if (remotePoint1 != localPoint1 || remotePoint2 != localPoint2 || remoteScoreMethod1 != localScoreMethod1 || remoteScoreMethod2 != localScoreMethod2) {
                            pointChange = true
                            break
                        }
                    }
                    if (pointChange) {
                        // 同步对战数据
                        game.playerBattleBeans = gameBean.playerBattleBeans
                        battleAdapter.dataList = game.playerBattleBeans
                        // 保存数据
                        addOrUpdate()
                        // 刷新UI
                        mBinding.rvBattle.post {
                            battleAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener { finish() }
        mBinding.tvComplete.setOnClickListener { complete() }
        mBinding.btnOpenRoom.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                if (isChecked) {
                    mBinding.btnOpenRoom.setTextColor(getColor(R.color.color_main))
                    openRoom()
                } else {
                    mBinding.btnOpenRoom.setTextColor(getColor(R.color.color_333))
                    closeRoom()
                }
            }
        }
    }

    private fun closeRoom() {
        udpUtil.stopStartBroadcast()
    }

    private fun openRoom() {
        udpUtil.startHeartbeatBroadcastTask(game)
    }

//    private fun sendRoomerBroadcastIfIsRoomer(broadcastBean: BroadcastBean) {
//        if (broadcastBean.type == BroadcastBean.BroadcastType.TYPE_GUEST_REPLAY) {
//            sendRoomerBroadcastIfIsRoomer()
//        }
//    }

//    private fun sendRoomerBroadcastIfIsRoomer() {
//        if (isRoomer) {
//            udpUtil.sendRoomerBroadcast(game)
//        }
//    }

    private fun stopReceiverData() {
        udpUtil.stopReceive()
    }

    private fun complete() {
        if (!checkData()) {
            return
        }
        if (check0Point()) {
            val dialog = CommonDialog(this, "存在比分为0:0的局，是否继续完赛？")
            dialog.mClickConfirmListener = object : CommonDialog.IClickConfirmListener {
                override fun onClickConfirm() {
                    jumpStatistics()
                }
            }
            dialog.show()
        } else {
            jumpStatistics()
        }
    }

    // 检查是否有0：0比分
    private fun check0Point(): Boolean {
        for (i in 0 until game.playerBattleBeans.size) {
            val game = game.playerBattleBeans[i]
            if ((game.id1Point == game.id2Point) && (game.id1Point == 0)) {
                return true
            }
        }
        return false
    }

    // 跳转到统计页面
    private fun jumpStatistics() {
        // 克隆对象
        val newGame = game.clone() as GameBean
        // 过滤掉0:0的记录
        newGame.playerBattleBeans.removeIf { it.id1Point == it.id2Point && it.id1Point == 0 }
        // 跳转
        val intent = Intent(this, StatisticsActivity::class.java)
        intent.putExtra("gameBean", newGame)
        startActivity(intent)
    }

    // 判断比分是否符合规则:不能相等
    private fun checkData(): Boolean {
        for (i in 0 until game.playerBattleBeans.size) {
            val game = game.playerBattleBeans[i]
            if ((game.id1Point == game.id2Point) && (game.id1Point != 0)) {
                showTipDialog("第" + (i + 1) + "场比赛比分相同，请修改！")
                return false
            }
        }
        return true
    }

    private fun showTipDialog(tip: String) {
        val dialog = TipDialog(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog, tip)
        dialog.show()
    }

    private fun initView() {
        isRoomer = intent.getBooleanExtra("isRoomer", true)
        game = intent.getParcelableExtra("gameBean")!!
        // 标题
        initTitleUI()
        // 列表
        initPlayerBattleListUI()
        // 房间相关UI
        initRoomUI()
    }

    private fun initRoomUI() {
        mBinding.btnOpenRoom.visibility = if (isRoomer) View.VISIBLE else View.GONE
        mBinding.tvOnlineState.visibility = if (!isRoomer) View.VISIBLE else View.GONE
    }

    private fun initPlayerBattleListUI() {
        battleAdapter = BattleAdapter(game.playerBattleBeans)
        battleAdapter.mPointChangeConfirmListener =
            object : BattleAdapter.IPointChangeConfirmListener {
                override fun onPointChangeConfirm() {
                    // 同步数据给房主
                    sendData2RoomerIfNotRoomer()
//                     同步数据给客人
//                    sendRoomerBroadcastIfIsRoomer()
                    // 保存数据
                    addOrUpdate()
                }
            }
        mBinding.rvBattle.layoutManager = LinearLayoutManager(this)
        mBinding.rvBattle.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(5f))
            }
        })
        mBinding.rvBattle.adapter = battleAdapter
    }

    private fun sendData2RoomerIfNotRoomer() {
        if (!isRoomer) {
            if (replayInetAddress != null) {
                udpUtil.guessReplyToRoomer(replayInetAddress!!, game)
            }
        }
    }

    private fun initTitleUI() {
        mBinding.tvTitle.text = game.getTitle2()
    }

    private fun addOrUpdate() {
        ObjectBox.store.boxFor(GameBean::class.java).put(game)
    }

    override fun onDestroy() {
        super.onDestroy()
        closeRoom()
        stopReceiverData()
        udpUtil.release()
    }
}