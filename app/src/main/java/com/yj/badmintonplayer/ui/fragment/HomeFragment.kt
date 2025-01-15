package com.yj.badmintonplayer.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
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
import com.yj.badmintonplayer.ui.dialog.SearchRoomDialog

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
        setVersionUI()
    }

    private fun setVersionUI() {
        mBinding.tvVersion.text = "版本号：" + getAppVersion(activity!!)
    }

    fun getAppVersion(context: Context): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            return "Version not found"
        }
    }

    private fun setListener() {
        mBinding.tvCreate.setOnClickListener {
            showCreateRoomDialog();
        }
        mBinding.tvJoin.setOnClickListener {
            showSearchRoomDialog()
        }
    }

    private fun showSearchRoomDialog() {
        val dialog = SearchRoomDialog(activity!!)
        dialog.mClickJoinListener = object : SearchRoomDialog.IClickJoinListener {
            override fun onClickJoinListener(gameBean: GameBean) {
                jump2Battle(gameBean, false)
            }
        }
        dialog.show()
    }

    private fun showCreateRoomDialog() {
        val dialog =
            CreateRoomDialog(activity!!, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog)
        dialog.iConfirmListener = object : CreateRoomDialog.IConfirmListener {
            override fun onConfirm(gameBean: GameBean) {
                jump2Battle(gameBean, true)
            }
        }
        dialog.show()
    }

    private fun jump2Battle(gameBean: GameBean, isRoomer: Boolean) {
        val intent = Intent(activity, PlayerBattleActivity::class.java)
        intent.putExtra("gameBean", gameBean)
        intent.putExtra("isRoomer", isRoomer)
        activity!!.startActivity(intent)
    }
}