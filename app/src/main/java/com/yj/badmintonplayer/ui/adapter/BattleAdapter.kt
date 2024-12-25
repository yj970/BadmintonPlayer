package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterBattleBinding
import com.yj.badmintonplayer.databinding.AdapterCreateRoomPlayerBinding
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBean

class BattleAdapter(val dataList: List<GameBean>) :
    RecyclerView.Adapter<BattleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_battle, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameBean = dataList.get(position)
        holder.mBinding.tvPlayer1Name.text = gameBean.name1
        holder.mBinding.tvPlayer1Point.text = gameBean.id1Point.toString()
        holder.mBinding.tvPlayer2Name.text = gameBean.name2
        holder.mBinding.tvPlayer2Point.text = gameBean.id2Point.toString()
        holder.mBinding.tvPlayer1Win.visibility =
            if (gameBean.id1Point > gameBean.id2Point) View.VISIBLE else View.INVISIBLE
        holder.mBinding.tvPlayer2Win.visibility =
            if (gameBean.id2Point > gameBean.id1Point) View.VISIBLE else View.INVISIBLE
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterBattleBinding.bind(itemView)
    }


}