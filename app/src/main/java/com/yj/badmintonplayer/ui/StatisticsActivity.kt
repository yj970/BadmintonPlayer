package com.yj.badmintonplayer.ui

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.ActivityStatisticsBinding
import com.yj.badmintonplayer.ui.adapter.PlayerDataStatisticsAdapter
import com.yj.badmintonplayer.ui.adapter.RankingAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.bean.PlayerBean
import com.yj.badmintonplayer.ui.bean.ScoreMethod
import com.yj.badmintonplayer.ui.dialog.CommonDialog
import com.yj.badmintonplayer.ui.dialog.LoadingDialog
import com.yj.badmintonplayer.ui.utils.SizeUtils
import com.yj.badmintonplayer.ui.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

class StatisticsActivity : FragmentActivity() {
    lateinit var mBinding: ActivityStatisticsBinding
    lateinit var game: GameBean
    lateinit var playerDataStatisticsAdapter: PlayerDataStatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initListener()

        mBinding.tvTitle.post {
            initView()
        }
    }

    private fun initView() {
        game = intent.getParcelableExtra("gameBean")!!

        initTitleUI()
        initRanking()
    }

    private fun initTitleUI() {
        mBinding.tvTitle.text = game.getTitle()
    }

    // 排名
    private fun initRanking() {
        // 先创建选手
        var players = ArrayList<PlayerBean>()
        for (game in game.playerBattleBeans) {
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
        for (game in game.playerBattleBeans) {
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
            // 技术统计
            player1!!.attachPoint = game.id1ScoreMethod.getAttachPoint()
            player1!!.defendPoint = game.id1ScoreMethod.getDefendPoint()
            player1!!.controlPoint = game.id1ScoreMethod.getControlPoint()
            player2!!.attachPoint = game.id2ScoreMethod.getAttachPoint()
            player2!!.defendPoint = game.id2ScoreMethod.getDefendPoint()
            player2!!.controlPoint = game.id2ScoreMethod.getControlPoint()
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
        playerDataStatisticsAdapter = PlayerDataStatisticsAdapter(players)
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
        mBinding.rvData.adapter = playerDataStatisticsAdapter
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
        mBinding.tvDownload.setOnClickListener { save2Phone() }
        mBinding.stScoreMethod.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                playerDataStatisticsAdapter.setShowScoreMethodAndRefresh(isChecked)
            }
        }
    }

    // 保存图片到手机
    private fun save2Phone() {
        val loadingDialog = LoadingDialog(this);
        loadingDialog.show()
        // 启动一个协程
        GlobalScope.launch(Dispatchers.IO) {
            // 在 IO 线程中执行耗时操作
            val bitmap = getScrollViewBitmap(mBinding.vBody)
            val fileName = UUID.randomUUID().toString()
            val save = Utils.saveBitmapToGallery(this@StatisticsActivity, bitmap, fileName)
            // 切换到主线程
            withContext(Dispatchers.Main) {
                // 在主线程中更新 UI
                val msg = if (save) "保存成功" else "保存失败"
                Toast.makeText(this@StatisticsActivity, msg, Toast.LENGTH_SHORT).show()
                loadingDialog.dismiss()
            }
        }
    }

    fun getScrollViewBitmap(scrollView: NestedScrollView): Bitmap {
        var height = 0
        for (i in 0 until scrollView.childCount) {
            height += scrollView.getChildAt(i).height
        }
        val bitmap = Bitmap.createBitmap(scrollView.width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        var backgroundBitmap = BitmapFactory.decodeResource(resources, R.mipmap.bg_statistics)
        val newW = bitmap.width
        val newH = bitmap.height * backgroundBitmap.width / bitmap.width
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, newW, newH, true)
        var y = 0f
        while (y < scrollView.height) {
            canvas.drawBitmap(backgroundBitmap, 0f, y, null)
            y += backgroundBitmap.height
        }
        // 绘制背景
        // 绘制内容
        scrollView.draw(canvas)
        return bitmap
    }

//    fun viewToBitmap(view: View): Bitmap {
//        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//        return bitmap
//    }


}