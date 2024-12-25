package com.yj.badmintonplayer.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yj.badmintonplayer.databinding.ActivityBattleBinding
import com.yj.badmintonplayer.ui.adapter.BattleAdapter
import com.yj.badmintonplayer.ui.bean.GameBean

class BattleActivity : FragmentActivity() {
    lateinit var mBinding: ActivityBattleBinding
    lateinit var games: List<GameBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        initView()
        setListener()
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener { finish() }
    }

    private fun initView() {
        games = intent.getParcelableArrayListExtra("games")!!
        var battleAdapter = BattleAdapter(games)
        mBinding.rvBattle.layoutManager = LinearLayoutManager(this)
        mBinding.rvBattle.adapter = battleAdapter
    }
}