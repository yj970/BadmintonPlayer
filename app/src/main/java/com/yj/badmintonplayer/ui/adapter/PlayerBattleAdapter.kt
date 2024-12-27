package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterPlayerBattleBinding
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean

class PlayerBattleAdapter(val dataList: List<PlayerBattleBean>, val leftId: String) :
    RecyclerView.Adapter<PlayerBattleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_player_battle, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameBean = dataList.get(position)
        val id1Left = leftId == gameBean.id1
        holder.mBinding.tvPlayerLeftName.text = if (id1Left) gameBean.name1 else gameBean.name2
        holder.mBinding.tvPlayerLeftPoint.text = if (id1Left) gameBean.id1Point.toString() else gameBean.id2Point.toString()
        holder.mBinding.tvPlayerRightName.text = if (!id1Left) gameBean.name1 else gameBean.name2
        holder.mBinding.tvPlayerRightPoint.text = if (!id1Left) gameBean.id1Point.toString() else gameBean.id2Point.toString()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterPlayerBattleBinding.bind(itemView)
    }


}