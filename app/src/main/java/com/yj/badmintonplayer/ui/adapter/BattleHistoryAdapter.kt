package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterBattleBinding
import com.yj.badmintonplayer.databinding.AdapterHistoryBattleBinding
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.dialog.NumberDialog
import com.yj.badmintonplayer.ui.utils.Utils
import java.util.ArrayList

class BattleHistoryAdapter(var dataList: List<GameBean>) :
    RecyclerView.Adapter<BattleHistoryAdapter.ViewHolder>() {
    lateinit var mClickListener: IClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_history_battle, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameBean = dataList.get(position)
        holder.mBinding.tvDate.text = "对抗日期:" + Utils.getDateFormat(gameBean.createTime)
        holder.mBinding.tvRoomName.text = "房间名:" + gameBean.roomName
        holder.mBinding.root.setOnClickListener {
            mClickListener.onClick(gameBean)
        }
        holder.mBinding.root.setOnLongClickListener {
            mClickListener.onLongClick(gameBean)
            true
        }
    }

    fun setData(games: ArrayList<GameBean>) {
        dataList = games
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterHistoryBattleBinding.bind(itemView)
    }

    interface IClickListener {
        fun onClick(game: GameBean)
        fun onLongClick(game: GameBean)
    }

}