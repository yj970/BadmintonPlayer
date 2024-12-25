package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterBattleBinding
import com.yj.badmintonplayer.databinding.AdapterRankingBinding
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBean
import com.yj.badmintonplayer.ui.dialog.NumberDialog

class RankingAdapter(val dataList: List<PlayerBean>) :
    RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_ranking, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = dataList.get(position)
        holder.mBinding.tvRank.text = "第"+(position+1)+"名"
        holder.mBinding.tvPlayerName.text = player.getName()
        holder.mBinding.tvWinCount.text = "胜场:" + player.winCount
        holder.mBinding.tvLoseCount.text = "负场:" + player.loseCount
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterRankingBinding.bind(itemView)
    }


}