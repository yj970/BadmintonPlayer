package com.yj.badmintonplayer.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterCreateRoomPlayerBinding
import com.yj.badmintonplayer.ui.bean.PlayerBean

class CreateRoomPlayerAdapter(val dataList: List<PlayerBean>) :
    RecyclerView.Adapter<CreateRoomPlayerAdapter.ViewHolder>() {
    var iClickDeleteListener: IClickDeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_create_room_player, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = dataList.get(position)
        holder.mBinding.etNickName.hint = player.hintNickname
        holder.mBinding.etNickName.setText(player.nickName)
        holder.mBinding.etNickName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                player.nickName = s.toString()
            }
        })
        holder.mBinding.tvDelete.setOnClickListener {
            iClickDeleteListener?.onDelete(player)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterCreateRoomPlayerBinding.bind(itemView)
    }

    interface IClickDeleteListener {
        fun onDelete(playerBean: PlayerBean);
    }


}