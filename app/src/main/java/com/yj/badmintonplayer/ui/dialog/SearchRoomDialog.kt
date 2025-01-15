package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogBattleDetailBinding
import com.yj.badmintonplayer.databinding.DialogSearchRoomBinding
import com.yj.badmintonplayer.ui.PlayerBattleActivity
import com.yj.badmintonplayer.ui.adapter.PlayerDataStatisticsAdapter
import com.yj.badmintonplayer.ui.adapter.SearchRoomAdapter
import com.yj.badmintonplayer.ui.bean.BroadcastBean
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.GameBean_
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.db.ObjectBox
import com.yj.badmintonplayer.ui.utils.ScreenUtils
import com.yj.badmintonplayer.ui.utils.SizeUtils
import com.yj.badmintonplayer.ui.utils.UDPUtil
import io.objectbox.query.QueryBuilder
import java.net.InetAddress

class SearchRoomDialog(context: Context) :
    Dialog(context, R.style.Dialog) {

    lateinit var mBinding: DialogSearchRoomBinding
    lateinit var adapetr: SearchRoomAdapter
    val udpUtil = UDPUtil()
    lateinit var mClickJoinListener: IClickJoinListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DialogSearchRoomBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        window?.attributes?.width = screenWidth - SizeUtils.dp2px(30f)
        window?.attributes?.height = SizeUtils.dp2px(300f)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        initView()
        setListener()
        startSearchRoom()
    }

    private fun setListener() {
        adapetr.mClickListener = object : SearchRoomAdapter.IClickListener {
            override fun onClick(gameBean: GameBean) {
                dismiss()
                var resultGameBean = gameBean;
                // 同步game数据
                // 先判断是否已存在
                val builder = ObjectBox.store.boxFor(GameBean::class.java).query().equal(
                    GameBean_.gameId,
                    gameBean.gameId,
                    QueryBuilder.StringOrder.CASE_INSENSITIVE
                ).build()
                val list = builder.find()
                if (list.size > 0) {
                    // 已存在，覆盖旧数据
                    resultGameBean.id = list[0].id
                } else {
                    // 不存在，新数据
                    resultGameBean.id = 0
                }
                mClickJoinListener.onClickJoinListener(resultGameBean)
            }
        }
        mBinding.ivClose.setOnClickListener { dismiss() }
    }

    private fun initView() {
        adapetr = SearchRoomAdapter(ArrayList())
        mBinding.rvSearchRoom.layoutManager = LinearLayoutManager(context)
        mBinding.rvSearchRoom.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(20f))
            }
        })
        mBinding.rvSearchRoom.adapter = adapetr
    }

    private fun startSearchRoom() {
        udpUtil.startReceive(object : UDPUtil.IReceiverListener {
            override fun onReceiver(broadcastBean: BroadcastBean, address: InetAddress) {
                // 只要心跳数据
                if (broadcastBean.type == BroadcastBean.BroadcastType.TYPE_HEART_BEAT) {
                    val data = adapetr.dataList
                    val gameBean = broadcastBean.gameBean
                    if (!data.contains(gameBean)) {
                        data.add(gameBean)
                        mBinding.rvSearchRoom.post {
                            adapetr.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }

    private fun stopSearchRoom() {
        udpUtil.stopReceive()
        udpUtil.release()
    }

    override fun dismiss() {
        super.dismiss()
        stopSearchRoom()
    }

    interface IClickJoinListener {
        fun onClickJoinListener(gameBean: GameBean)
    }


}