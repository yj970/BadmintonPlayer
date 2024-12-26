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
        val rank = player.rank
        holder.mBinding.tvRank.visibility = if (rank > 3) View.VISIBLE else View.GONE
        holder.mBinding.ivRank.visibility = if (rank > 3) View.GONE else View.VISIBLE
        holder.mBinding.tvRank.text = "第" + rank + "名"
        holder.mBinding.ivRank.setImageResource(getRankImage(rank))
        holder.mBinding.tvPlayerName.text = player.getName()
        holder.mBinding.tvWinCount.text = "胜场:" + player.winCount
        holder.mBinding.tvLoseCount.text = "负场:" + player.loseCount
    }

    private fun getRankImage(rank: Int): Int {
        when (rank) {
            1 -> {
                return R.mipmap.icon_rank_1
            }

            2 -> {
                return R.mipmap.icon_rank_2
            }

            3 -> {
                return R.mipmap.icon_rank_3
            }
        }
        return 0
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterRankingBinding.bind(itemView)
    }


}