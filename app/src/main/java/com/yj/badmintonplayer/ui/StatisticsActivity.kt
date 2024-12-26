package com.yj.badmintonplayer.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.databinding.ActivityStatisticsBinding
import com.yj.badmintonplayer.ui.adapter.PlayerDataStatisticsAdapter
import com.yj.badmintonplayer.ui.adapter.RankingAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBean
import com.yj.badmintonplayer.ui.utils.SizeUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

        initTitleUI()
        initRanking()
    }

    private fun initTitleUI() {
        // 获取当前日期
        val currentDate = LocalDate.now()
        // 定义日期格式化器
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        // 将日期按照指定格式进行格式化
        val formattedDate = currentDate.format(formatter)
        mBinding.tvTitle.text = formattedDate
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
            // 得分
            player1!!.winPoint += game.id1Point
            player1!!.losePoint += game.id2Point
            player2!!.winPoint += game.id2Point
            player2!!.losePoint += game.id1Point
            // 胜场
            if (player1win) {
                player1!!.winCount += 1
                player2!!.loseCount += 1
            } else {
                player1!!.loseCount += 1
                player2!!.winCount += 1
            }
            // 比赛
            player1!!.games.add(game)
            player2!!.games.add(game)
        }
        // 1、胜场降序 2、总得分降序 3、总失分升序
        players.sortWith(
            compareByDescending<PlayerBean> {
                it.winCount
            }.thenByDescending {
                it.winPoint
            }.thenBy {
                it.losePoint
            }
        )
        // 排名
        var rank = 1
        for (i in 0 until players.size) {
            val playerNow = players[i]
            val playerPre = if (i > 0) players[i - 1] else null
            if (playerPre != null) {
                rank = if (playerPre.winCount > playerNow.winCount) ++rank else rank
            }
            playerNow.rank = rank
        }


        // 展示UI
        val adapetr = RankingAdapter(players)
        mBinding.rvRanking.layoutManager = LinearLayoutManager(this)
        mBinding.rvRanking.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(5f))
            }
        })
        mBinding.rvRanking.adapter = adapetr

        // 展示UI
        val adapetr2 = PlayerDataStatisticsAdapter(players)
        mBinding.rvData.layoutManager = LinearLayoutManager(this)
        mBinding.rvData.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(20f))
            }
        })
        mBinding.rvData.adapter = adapetr2
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
        mBinding.tvClose.setOnClickListener { finish() }
    }
}