package com.yj.badmintonplayer.ui.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterPlayerDataStatisticsBinding
import com.yj.badmintonplayer.databinding.AdapterRankingBinding
import com.yj.badmintonplayer.ui.bean.PlayerBean
import com.yj.badmintonplayer.ui.utils.SizeUtils

class PlayerDataStatisticsAdapter(val dataList: List<PlayerBean>) :
    RecyclerView.Adapter<PlayerDataStatisticsAdapter.ViewHolder>() {

    var showScoreMethod = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_player_data_statistics, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = dataList.get(position)
        holder.mBinding.tvPlayerName.text = player.getName()
        holder.mBinding.tvWinCount.text = "胜场:" + player.winCount
        holder.mBinding.tvLoseCount.text = "负场:" + player.loseCount
        holder.mBinding.tvWinningRate.text =
            "胜率:" + ((player.winCount * 1f / (player.winCount + player.loseCount)) * 100).toInt() + "%"
        holder.mBinding.tvWinPoint.text = "得分:" + player.winPoint
        holder.mBinding.tvLosePoint.text = "失分:" + player.losePoint
        holder.mBinding.tvNetVictoryScore.text =
            "净胜分:" + (player.winPoint - player.losePoint)
        holder.mBinding.tvAttachPoint.text = "进攻得分:" + player.attachPoint
        holder.mBinding.tvAttachIndex.text = "进攻占比:" + player.getAttachIndex()
        holder.mBinding.tvDefendPoint.text = "防守得分:" + player.defendPoint
        holder.mBinding.tvDefendIndex.text = "防守占比:" + player.getDefendIndex()
        holder.mBinding.tvControlPoint.text = "控制得分:" + player.controlPoint
        holder.mBinding.tvControlIndex.text = "控制占比:" + player.getControlIndex()
        holder.mBinding.llScoreMethod.visibility = if (showScoreMethod) View.VISIBLE else View.GONE

//        比分数据
        val adapter = PlayerBattleAdapter(player.games, player.id)
        adapter.setShowScoreMethodAndRefresh(showScoreMethod)
        holder.mBinding.rvGame.layoutManager = GridLayoutManager(holder.mBinding.rvGame.context, 3)
        if (holder.mBinding.rvGame.itemDecorationCount  == 0) {
            holder.mBinding.rvGame.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.set(
                        SizeUtils.dp2px(5f),
                        SizeUtils.dp2px(5f),
                        SizeUtils.dp2px(5f),
                        SizeUtils.dp2px(5f)
                    )
                }
            })
        }
        holder.mBinding.rvGame.adapter = adapter
    }

    fun setShowScoreMethodAndRefresh(checked: Boolean) {
        showScoreMethod = checked
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterPlayerDataStatisticsBinding.bind(itemView)
    }


}