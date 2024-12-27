package com.yj.badmintonplayer.ui.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yj.badmintonplayer.databinding.FragmentMineBinding
import com.yj.badmintonplayer.ui.PlayerBattleActivity
import com.yj.badmintonplayer.ui.adapter.BattleAdapter
import com.yj.badmintonplayer.ui.adapter.BattleHistoryAdapter
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.bean.MyObjectBox
import com.yj.badmintonplayer.ui.db.ObjectBox
import com.yj.badmintonplayer.ui.utils.SizeUtils
import io.objectbox.BoxStore

class MineFragment : Fragment() {

    lateinit var mBinding: FragmentMineBinding

    companion object {
        fun newInstance(): Fragment {
            val args = Bundle()
            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMineBinding.inflate(layoutInflater)
        return mBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
    }

    private fun initData() {
        // todo  携程优化
        val games: List<GameBean> =
            ObjectBox.store.boxFor(GameBean::class.java).query().build().find()
        var battleAdapter = BattleHistoryAdapter(games)
        battleAdapter.mClickListener = object :BattleHistoryAdapter.IClickListener{
            override fun onClick(game: GameBean) {
                jump2Battle(game)
            }
        }
        mBinding.rvData.layoutManager = LinearLayoutManager(activity)
        mBinding.rvData.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(
                    SizeUtils.dp2px(16f),
                    SizeUtils.dp2px(5f),
                    SizeUtils.dp2px(16f),
                    SizeUtils.dp2px(5f)
                )
            }
        })
        mBinding.rvData.adapter = battleAdapter

    }

    private fun jump2Battle(gameBean: GameBean) {
        val intent = Intent(activity, PlayerBattleActivity::class.java)
        intent.putExtra("gameBean", gameBean)
        activity!!.startActivity(intent)
    }

}