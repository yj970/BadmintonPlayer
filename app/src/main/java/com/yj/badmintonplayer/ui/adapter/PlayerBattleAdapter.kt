package com.yj.badmintonplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.AdapterPlayerBattleBinding
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.bean.ScoreMethod

class PlayerBattleAdapter(val dataList: List<PlayerBattleBean>, val leftId: String) :
    RecyclerView.Adapter<PlayerBattleAdapter.ViewHolder>() {

    var showScoreMethod = false

    fun setShowScoreMethodAndRefresh(checked: Boolean) {
        showScoreMethod = checked
        notifyDataSetChanged()
    }

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
        holder.mBinding.tvPlayerRightName.text = if (!id1Left) gameBean.name1 else gameBean.name2

        val point1 = if (id1Left) gameBean.id1Point else gameBean.id2Point
        val point2 = if (!id1Left) gameBean.id1Point else gameBean.id2Point
        holder.mBinding.tvPlayerLeftPoint.text = point1.toString()
        holder.mBinding.tvPlayerRightPoint.text = point2.toString()

        // 统计
        holder.mBinding.llScoreMethod.visibility = if (showScoreMethod) View.VISIBLE else View.GONE
        val scoreMethod1 = if (id1Left) gameBean.id1ScoreMethod else gameBean.id2ScoreMethod
        val scoreMethod2 = if (!id1Left) gameBean.id1ScoreMethod else gameBean.id2ScoreMethod

        holder.mBinding.llScoreMethodTitle.visibility =
            if (point1 > point2) View.VISIBLE else View.INVISIBLE
        holder.mBinding.tvTitle.text = scoreMethod1.getTitle(point1 - point2)

        holder.mBinding.tv1HighFarPoint.text = scoreMethod1.highFar.toString()
        holder.mBinding.tv2HighFarPoint.text = scoreMethod2.highFar.toString()
        holder.mBinding.hr1HighFar.max = scoreMethod1.highFar + scoreMethod2.highFar
        holder.mBinding.hr1HighFar.value = scoreMethod1.highFar
        holder.mBinding.hr2HighFar.max = scoreMethod1.highFar + scoreMethod2.highFar
        holder.mBinding.hr2HighFar.value = scoreMethod2.highFar
        holder.mBinding.hr2HighFar.left = false

        holder.mBinding.tv1LobPoint.text = scoreMethod1.lob.toString()
        holder.mBinding.tv2LobPoint.text = scoreMethod2.lob.toString()
        holder.mBinding.hr1Lob.max = scoreMethod1.lob + scoreMethod2.lob
        holder.mBinding.hr1Lob.value = scoreMethod1.lob
        holder.mBinding.hr2Lob.max = scoreMethod1.lob + scoreMethod2.lob
        holder.mBinding.hr2Lob.value = scoreMethod2.lob
        holder.mBinding.hr2Lob.left = false

        holder.mBinding.tv1SmashPoint.text = scoreMethod1.smash.toString()
        holder.mBinding.tv2SmashPoint.text = scoreMethod2.smash.toString()
        holder.mBinding.hr1Smash.max = scoreMethod1.smash + scoreMethod2.smash
        holder.mBinding.hr1Smash.value = scoreMethod1.smash
        holder.mBinding.hr2Smash.max = scoreMethod1.smash + scoreMethod2.smash
        holder.mBinding.hr2Smash.value = scoreMethod2.smash
        holder.mBinding.hr2Smash.left = false

        holder.mBinding.tv1ServePoint.text = scoreMethod1.serve.toString()
        holder.mBinding.tv2ServePoint.text = scoreMethod2.serve.toString()
        holder.mBinding.hr1Serve.max = scoreMethod1.serve + scoreMethod2.serve
        holder.mBinding.hr1Serve.value = scoreMethod1.serve
        holder.mBinding.hr2Serve.max = scoreMethod1.serve + scoreMethod2.serve
        holder.mBinding.hr2Serve.value = scoreMethod2.serve
        holder.mBinding.hr2Serve.left = false

        holder.mBinding.tv1FlatDrivePoint.text = scoreMethod1.flatDrive.toString()
        holder.mBinding.tv2FlatDrivePoint.text = scoreMethod2.flatDrive.toString()
        holder.mBinding.hr1FlatDrive.max = scoreMethod1.flatDrive + scoreMethod2.flatDrive
        holder.mBinding.hr1FlatDrive.value = scoreMethod1.flatDrive
        holder.mBinding.hr2FlatDrive.max = scoreMethod1.flatDrive + scoreMethod2.flatDrive
        holder.mBinding.hr2FlatDrive.value = scoreMethod2.flatDrive
        holder.mBinding.hr2FlatDrive.left = false

        holder.mBinding.tv1PickPoint.text = scoreMethod1.pick.toString()
        holder.mBinding.tv2PickPoint.text = scoreMethod2.pick.toString()
        holder.mBinding.hr1Pick.max = scoreMethod1.pick + scoreMethod2.pick
        holder.mBinding.hr1Pick.value = scoreMethod1.pick
        holder.mBinding.hr2Pick.max = scoreMethod1.pick + scoreMethod2.pick
        holder.mBinding.hr2Pick.value = scoreMethod2.pick
        holder.mBinding.hr2Pick.left = false

        holder.mBinding.tv1NetSmallPoint.text = scoreMethod1.netSmall.toString()
        holder.mBinding.tv2NetSmallPoint.text = scoreMethod2.netSmall.toString()
        holder.mBinding.hr1NetSmall.max = scoreMethod1.netSmall + scoreMethod2.netSmall
        holder.mBinding.hr1NetSmall.value = scoreMethod1.netSmall
        holder.mBinding.hr2NetSmall.max = scoreMethod1.netSmall + scoreMethod2.netSmall
        holder.mBinding.hr2NetSmall.value = scoreMethod2.netSmall
        holder.mBinding.hr2NetSmall.left = false

        holder.mBinding.tv1FakePoint.text = scoreMethod1.fake.toString()
        holder.mBinding.tv2FakePoint.text = scoreMethod2.fake.toString()
        holder.mBinding.hr1Fake.max = scoreMethod1.fake + scoreMethod2.fake
        holder.mBinding.hr1Fake.value = scoreMethod1.fake
        holder.mBinding.hr2Fake.max = scoreMethod1.fake + scoreMethod2.fake
        holder.mBinding.hr2Fake.value = scoreMethod2.fake
        holder.mBinding.hr2Fake.left = false

        holder.mBinding.tv1VaSpeedPoint.text = scoreMethod1.variableSpeed.toString()
        holder.mBinding.tv2VaSpeedPoint.text = scoreMethod2.variableSpeed.toString()
        holder.mBinding.hr1VaSpeed.max = scoreMethod1.variableSpeed + scoreMethod2.variableSpeed
        holder.mBinding.hr1VaSpeed.value = scoreMethod1.variableSpeed
        holder.mBinding.hr2VaSpeed.max = scoreMethod1.variableSpeed + scoreMethod2.variableSpeed
        holder.mBinding.hr2VaSpeed.value = scoreMethod2.variableSpeed
        holder.mBinding.hr2VaSpeed.left = false

        holder.mBinding.tv1VaAnglePoint.text = scoreMethod1.variableAngle.toString()
        holder.mBinding.tv2VaAnglePoint.text = scoreMethod2.variableAngle.toString()
        holder.mBinding.hr1VaAngle.max = scoreMethod1.variableAngle + scoreMethod2.variableAngle
        holder.mBinding.hr1VaAngle.value = scoreMethod1.variableAngle
        holder.mBinding.hr2VaAngle.max = scoreMethod1.variableAngle + scoreMethod2.variableAngle
        holder.mBinding.hr2VaAngle.value = scoreMethod2.variableAngle
        holder.mBinding.hr2VaAngle.left = false
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = AdapterPlayerBattleBinding.bind(itemView)
    }


}