package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterPlayerBattleBinding
import com.yj.badmintonplayer.databinding.AdapterSearchRoomBinding
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean

class SearchRoomAdapter(val dataList: ArrayList<GameBean>) :
    RecyclerView.Adapter<SearchRoomAdapter.ViewHolder>() {
    lateinit var mClickListener: IClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_search_room, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gameBean = dataList.get(position)
        holder.mBinding.tvRoomName.text = "房间名："+gameBean.roomName
        holder.mBinding.tvJoinRoom.setOnClickListener {
            mClickListener.onClick(gameBean)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterSearchRoomBinding.bind(itemView)
    }

    interface IClickListener {
        fun onClick(gameBean: GameBean)
    }

}