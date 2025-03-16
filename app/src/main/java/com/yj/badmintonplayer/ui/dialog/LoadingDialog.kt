package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogLoadingBinding
import com.yj.badmintonplayer.ui.utils.SizeUtils


class LoadingDialog(context: Context) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        // 宽高设置
        window?.attributes?.width = SizeUtils.dp2px(100f)
        window?.attributes?.height = SizeUtils.dp2px(100f)
    }


}