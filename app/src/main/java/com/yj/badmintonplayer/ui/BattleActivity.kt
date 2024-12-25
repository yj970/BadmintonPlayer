package com.yj.badmintonplayer.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yj.badmintonplayer.databinding.ActivityBattleBinding
import com.yj.badmintonplayer.ui.adapter.BattleAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.dialog.TipDialog

class BattleActivity : FragmentActivity() {
    lateinit var mBinding: ActivityBattleBinding
    lateinit var games: ArrayList<GameBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        initView()
        setListener()
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener { finish() }
        mBinding.tvComplete.setOnClickListener { complete() }
    }

    private fun complete() {
        if (checkData()) {
            jumpStatistics()
        }
    }

    // 跳转到统计页面
    private fun jumpStatistics() {
        val intent = Intent(this, StatisticsActivity::class.java)
        intent.putExtra("games", games)
        startActivity(intent)
    }

    // 判断比分是否符合规则:不能相等
    private fun checkData(): Boolean {
        for (game in games) {
            if (game.id1Point == game.id2Point) {
                showTipDialog(game.samePointTip())
                return false
            }
        }
        return true
    }

    private fun showTipDialog(tip: String) {
        val dialog = TipDialog(this, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog, tip)
        dialog.show()
    }

    private fun initView() {
        games = intent.getParcelableArrayListExtra("games")!!
        var battleAdapter = BattleAdapter(games)
        mBinding.rvBattle.layoutManager = LinearLayoutManager(this)
        mBinding.rvBattle.adapter = battleAdapter
    }
}