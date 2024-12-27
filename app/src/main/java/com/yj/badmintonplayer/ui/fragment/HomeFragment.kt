package com.yj.badmintonplayer.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yj.badmintonplayer.databinding.FragmentHomeBinding
import com.yj.badmintonplayer.ui.PlayerBattleActivity
import com.yj.badmintonplayer.ui.bean.GameBean
import com.yj.badmintonplayer.ui.dialog.CreateRoomDialog

class HomeFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListener()
    }

    private fun setListener() {
        mBinding.tvCreate.setOnClickListener {
            showCreateRoomDialog();
        }
        mBinding.tvJoin.setOnClickListener {
            Toast.makeText(activity, "敬请期待！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCreateRoomDialog() {
        val dialog =
            CreateRoomDialog(activity!!, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog)
        dialog.iConfirmListener = object : CreateRoomDialog.IConfirmListener {
            override fun onConfirm(gameBean: GameBean) {
                jump2Battle(gameBean)
            }
        }
        dialog.show()
    }

    private fun jump2Battle(gameBean: GameBean) {
        val intent = Intent(activity, PlayerBattleActivity::class.java)
        intent.putExtra("gameBean", gameBean)
        activity!!.startActivity(intent)
    }
}