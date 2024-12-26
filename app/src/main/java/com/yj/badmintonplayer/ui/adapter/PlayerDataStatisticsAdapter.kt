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

//        比分数据
        val adapter = PlayerBattleAdapter(player.games, player.id)
        holder.mBinding.rvGame.layoutManager = GridLayoutManager(holder.mBinding.rvGame.context, 3)
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
        holder.mBinding.rvGame.adapter = adapter
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterPlayerDataStatisticsBinding.bind(itemView)
    }


}