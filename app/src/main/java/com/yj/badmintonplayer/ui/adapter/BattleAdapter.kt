package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterBattleBinding
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.dialog.BattleDetailDialog
import com.yj.badmintonplayer.ui.dialog.NumberDialog
import com.yj.badmintonplayer.ui.utils.DataLock

class BattleAdapter(var dataList: List<PlayerBattleBean>) :
    RecyclerView.Adapter<BattleAdapter.ViewHolder>() {
    lateinit var mPointChangeConfirmListener: IPointChangeConfirmListener
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
        holder.mBinding.tvSession.text = "第" + (position + 1) + "场"
        holder.mBinding.tvPlayer1Name.text = gameBean.name1
        holder.mBinding.tvPlayer1Point.text = gameBean.id1Point.toString()
        holder.mBinding.tvPlayer2Name.text = gameBean.name2
        holder.mBinding.tvPlayer2Point.text = gameBean.id2Point.toString()
        holder.mBinding.ivPlayer1Win.visibility =
            if (gameBean.id1Point > gameBean.id2Point) View.VISIBLE else View.INVISIBLE
        holder.mBinding.ivPlayer2Win.visibility =
            if (gameBean.id2Point > gameBean.id1Point) View.VISIBLE else View.INVISIBLE

        holder.mBinding.tvPlayer1Point.setOnClickListener {
            val numberDialog = NumberDialog(
                holder.mBinding.tvPlayer1Point.context,
                androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog,
                getNumberPickerDefaultPoint(gameBean.id1Point), "得分"
            )
            numberDialog.mConfirmListener = object : NumberDialog.IConfirmListener {
                override fun onConFirm(value: Int) {
                    synchronized(DataLock.dataLock) {
                        val g = dataList[position]
                        g.id1Point = value
                        notifyDataSetChanged()
                        mPointChangeConfirmListener.onPointChangeConfirm()
                    }
                }
            }
            numberDialog.show()
        }

        holder.mBinding.tvPlayer2Point.setOnClickListener {
            val numberDialog = NumberDialog(
                holder.mBinding.tvPlayer2Point.context,
                androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog,
                getNumberPickerDefaultPoint(gameBean.id2Point), "得分"
            )
            numberDialog.mConfirmListener = object : NumberDialog.IConfirmListener {
                override fun onConFirm(value: Int) {
                    synchronized(DataLock.dataLock) {
                        val g = dataList[position]
                        g.id2Point = value
                        notifyDataSetChanged()
                        mPointChangeConfirmListener.onPointChangeConfirm()
                    }
                }
            }
            numberDialog.show()
        }

        holder.mBinding.ivVs.setOnClickListener {
            val battleDialog = BattleDetailDialog(holder.mBinding.tvPlayer2Point.context, gameBean)
            battleDialog.mPointUpdateListener = object : BattleDetailDialog.IPointUpdateListener {

                override fun onPointUpdateListener(id1Point: Int, id2Point: Int) {
                    synchronized(DataLock.dataLock) {
                        val g = dataList[position]
                        g.id1Point = id1Point
                        g.id2Point = id2Point
                        notifyDataSetChanged()
                        mPointChangeConfirmListener.onPointChangeConfirm()
                    }
                }
            }
            battleDialog.show()
        }
    }

    // 获取默认展示得分
    private fun getNumberPickerDefaultPoint(point: Int): Int {
        return if (point == 0) 21 else point
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterBattleBinding.bind(itemView)
    }

    interface IPointChangeConfirmListener {
        fun onPointChangeConfirm()
    }


}