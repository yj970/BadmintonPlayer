package com.yj.badmintonplayer.ui

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.databinding.ActivityBattleBinding
import com.yj.badmintonplayer.ui.adapter.BattleAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.MyObjectBox
import com.yj.badmintonplayer.ui.db.ObjectBox
import com.yj.badmintonplayer.ui.dialog.CommonDialog
import com.yj.badmintonplayer.ui.dialog.TipDialog
import com.yj.badmintonplayer.ui.utils.SizeUtils
import io.objectbox.BoxStore

class PlayerBattleActivity : FragmentActivity() {
    lateinit var mBinding: ActivityBattleBinding
    lateinit var game: GameBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        initView()
        setListener()
        addOrUpdate()
    }

    private fun setListener() {
        mBinding.tvClose.setOnClickListener { finish() }
        mBinding.tvComplete.setOnClickListener { complete() }
    }

    private fun complete() {
        if (!checkData()) {
            return
        }
        if (check0Point()) {
            val dialog = CommonDialog(this, "存在比分为0:0的局，是否继续完赛？")
            dialog.mClickConfirmListener = object : CommonDialog.IClickConfirmListener {
                override fun onClickConfirm() {
                    jumpStatistics()
                }
            }
            dialog.show()
        } else {
            jumpStatistics()
        }
    }

    // 检查是否有0：0比分
    private fun check0Point(): Boolean {
        for (i in 0 until game.playerBattleBeans.size) {
            val game = game.playerBattleBeans[i]
            if ((game.id1Point == game.id2Point) && (game.id1Point == 0)) {
                return true
            }
        }
        return false
    }

    // 跳转到统计页面
    private fun jumpStatistics() {
        // 克隆对象
        val newGame = game.clone() as GameBean
        // 过滤掉0:0的记录
        newGame.playerBattleBeans.removeIf { it.id1Point == it.id2Point && it.id1Point == 0 }
        // 跳转
        val intent = Intent(this, StatisticsActivity::class.java)
        intent.putExtra("gameBean", newGame)
        startActivity(intent)
    }

    // 判断比分是否符合规则:不能相等
    private fun checkData(): Boolean {
        for (i in 0 until game.playerBattleBeans.size) {
            val game = game.playerBattleBeans[i]
            if ((game.id1Point == game.id2Point) && (game.id1Point != 0)) {
                showTipDialog("第" + (i + 1) + "场比赛比分相同，请修改！")
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
        game = intent.getParcelableExtra("gameBean")!!
        // 标题
        initTitleUI()
        // 列表
        initPlayerBattleListUI()
    }

    private fun initPlayerBattleListUI() {
        var battleAdapter = BattleAdapter(game.playerBattleBeans)
        battleAdapter.mPointChangeConfirmListener =
            object : BattleAdapter.IPointChangeConfirmListener {
                override fun onPointChangeConfirm() {
                    addOrUpdate()
                }
            }
        mBinding.rvBattle.layoutManager = LinearLayoutManager(this)
        mBinding.rvBattle.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(5f))
            }
        })
        mBinding.rvBattle.adapter = battleAdapter
    }

    private fun initTitleUI() {
        mBinding.tvTitle.text = game.getTitle()
    }

    private fun addOrUpdate() {
        ObjectBox.store.boxFor(GameBean::class.java).put(game)
    }
}