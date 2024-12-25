package com.yj.badmintonplayer.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yj.badmintonplayer.databinding.ActivityStatisticsBinding
import com.yj.badmintonplayer.ui.adapter.RankingAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBean

class StatisticsActivity : FragmentActivity() {
    lateinit var mBinding: ActivityStatisticsBinding
    lateinit var games: ArrayList<GameBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initListener()
        initView()
    }

    private fun initView() {
        games = intent.getParcelableArrayListExtra("games")!!

        initRanking()
    }

    // 排名
    private fun initRanking() {
        // 先创建选手
        var players = ArrayList<PlayerBean>()
        for (game in games) {
            val id1 = game.id1
            val id2 = game.id2
            val existId1 = isExistPlayer(id1, players)
            if (!existId1) {
                players.add(PlayerBean(game.id1, game.name1))
            }
            val existId2 = isExistPlayer(id2, players)
            if (!existId2) {
                players.add(PlayerBean(game.id2, game.name2))
            }
        }
        // 统计胜负场
        for (game in games) {
            val id1 = game.id1
            val id2 = game.id2
            val player1 = getPlayer(id1, players)
            val player2 = getPlayer(id2, players)
            val player1win = game.id1Point > game.id2Point
            if (player1win) {
                player1!!.winCount += 1
                player2!!.loseCount += 1
            } else {
                player1!!.loseCount += 1
                player2!!.winCount += 1
            }
        }
        // 根据胜场排位,降序
        players.sortWith(
            compareByDescending {
                it.winCount
            }
        )
        // 展示UI
        val adapetr = RankingAdapter(players)
        mBinding.rvRanking.layoutManager = LinearLayoutManager(this)
        mBinding.rvRanking.adapter = adapetr
    }

    // 获取选手
    private fun getPlayer(id1: String, players: java.util.ArrayList<PlayerBean>): PlayerBean? {
        for (player in players) {
            if (player.id == id1) {
                return player
            }
        }
        return null
    }

    // 是否存在该选手
    private fun isExistPlayer(id1: String, players: java.util.ArrayList<PlayerBean>): Boolean {
        for (player in players) {
            if (player.id == id1) {
                return true
            }
        }
        return false
    }


    private fun initListener() {

    }
}