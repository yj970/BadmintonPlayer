package com.yj.badmintonplayer.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.yj.badmintonplayer.R
import com.yj.badmintonplayer.databinding.DialogBattleDetailBinding
import com.yj.badmintonplayer.ui.bean.PlayerBattleBean
import com.yj.badmintonplayer.ui.bean.ScoreMethod
import com.yj.badmintonplayer.ui.utils.ScreenUtils
import com.yj.badmintonplayer.ui.utils.SizeUtils

class BattleDetailDialog(context: Context, val playerBattleBean: PlayerBattleBean) :
    Dialog(context, R.style.Dialog) {
    lateinit var mBinding: DialogBattleDetailBinding
    lateinit var mPointUpdateListener: IPointUpdateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DialogBattleDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 宽高设置
        val screenWidth = ScreenUtils.getScreenWidth(context)
        window?.attributes?.width = screenWidth - SizeUtils.dp2px(30f)
        setCancelable(true)
        setCanceledOnTouchOutside(false)
        initView()
        setListener()
    }

    fun check(point: Int): Boolean {
        if (point < 0 || point > 30) {
            return false
        } else {
            return true
        }
    }

    private fun setListener() {
        mBinding.ivPlayer1AddPoint.setOnClickListener {
            if (!check(playerBattleBean.id1Point + 1)) {
                return@setOnClickListener
            }
            playerBattleBean.id1Point++
            initView()
            if (isOpenScoreMethod()) {
                showConfirmScoreMethod(playerBattleBean.id1ScoreMethod)
            } else {
                onPointUpdateListener()
            }
        }
        mBinding.ivPlayer2AddPoint.setOnClickListener {
            if (!check(playerBattleBean.id2Point + 1)) {
                return@setOnClickListener
            }
            playerBattleBean.id2Point++
            initView()
            if (isOpenScoreMethod()) {
                showConfirmScoreMethod(playerBattleBean.id2ScoreMethod)
            } else {
                onPointUpdateListener()
            }
        }
        mBinding.ivPlayer1ReducePoint.setOnClickListener {
            if (!check(playerBattleBean.id1Point - 1)) {
                return@setOnClickListener
            }
            playerBattleBean.id1Point--
            initView()
            onPointUpdateListener()
        }
        mBinding.ivPlayer2ReducePoint.setOnClickListener {
            if (!check(playerBattleBean.id2Point - 1)) {
                return@setOnClickListener
            }
            playerBattleBean.id2Point--
            initView()
            onPointUpdateListener()
        }
        mBinding.ivClose.setOnClickListener { dismiss() }

        // 技术统计
        mBinding.tv1HighFar.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.highFar = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1Lob.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.lob = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1Smash.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.smash = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1Serve.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.serve = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1FlatDrive.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.flatDrive = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1Pick.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.pick = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1NetSmall.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.netSmall = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1Fake.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.fake = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1VariableSpeed.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.variableSpeed = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv1VariableAngle.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id1ScoreMethod.variableAngle = p
                    updateAllScoreMethodUI()
                }
            })
        }

        //2
        mBinding.tv2HighFar.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.highFar = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2Lob.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.lob = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2Smash.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.smash = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2Serve.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.serve = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2FlatDrive.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.flatDrive = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2Pick.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.pick = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2NetSmall.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.netSmall = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2Fake.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.fake = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2VariableSpeed.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.variableSpeed = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.tv2VariableAngle.setOnClickListener {
            showEditScoreMethodDialog(object : EditScoreMethodPointDialog.IClickConfirmListener {
                override fun onClickConfirm(p: Int) {
                    playerBattleBean.id2ScoreMethod.variableAngle = p
                    updateAllScoreMethodUI()
                }
            })
        }
        mBinding.stScoreMethod.setOnCheckedChangeListener { buttonView, isChecked ->
            run {
                mBinding.llScoreMethod.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
        }

        //qa
        mBinding.tvHighFar.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_HIGH_FAR)
        }
        mBinding.tvLob.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_LOB)
        }
        mBinding.tvSmash.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_SMASH)
        }
        mBinding.tvServe.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_SERVE)
        }
        mBinding.tvFlatDrive.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_FLAT_DRIVE)
        }
        mBinding.tvPick.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_PICK)
        }
        mBinding.tvNetSmall.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_NET_SMALL)
        }
        mBinding.tvFake.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_FAKE)
        }
        mBinding.tvVariableSpeed.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_SPEED)
        }
        mBinding.tvVariableAngle.setOnClickListener {
            showScoreMethodMsgDialog(ScoreMethodMsgDialog.TYPE_ANGLE)
        }

    }

    private fun showScoreMethodMsgDialog(type: Int) {
        val dialog = ScoreMethodMsgDialog(context, type)
        dialog.show()
    }

    private fun showConfirmScoreMethod(s: ScoreMethod) {
        val dialog = ConfirmScoreMethodDialog(context, s)
        dialog.mClickConfirmListener = object : ConfirmScoreMethodDialog.IClickConfirmListener {
            override fun onClickConfirm() {
                updateAllScoreMethodUI()
                onPointUpdateListener()
            }
        }
        dialog.show()
    }

    private fun isOpenScoreMethod(): Boolean {
        return mBinding.stScoreMethod.isChecked
    }

    private fun updateAllScoreMethodUI() {
        // 1
        mBinding.tv1HighFar.text = playerBattleBean.id1ScoreMethod.highFar.toString()
        mBinding.tv1Lob.text = playerBattleBean.id1ScoreMethod.lob.toString()
        mBinding.tv1Smash.text = playerBattleBean.id1ScoreMethod.smash.toString()
        mBinding.tv1Serve.text = playerBattleBean.id1ScoreMethod.serve.toString()
        mBinding.tv1FlatDrive.text = playerBattleBean.id1ScoreMethod.flatDrive.toString()
        mBinding.tv1Pick.text = playerBattleBean.id1ScoreMethod.pick.toString()
        mBinding.tv1NetSmall.text = playerBattleBean.id1ScoreMethod.netSmall.toString()
        mBinding.tv1Fake.text = playerBattleBean.id1ScoreMethod.fake.toString()
        mBinding.tv1VariableSpeed.text = playerBattleBean.id1ScoreMethod.variableSpeed.toString()
        mBinding.tv1VariableAngle.text = playerBattleBean.id1ScoreMethod.variableAngle.toString()
        // 2
        mBinding.tv2HighFar.text = playerBattleBean.id2ScoreMethod.highFar.toString()
        mBinding.tv2Lob.text = playerBattleBean.id2ScoreMethod.lob.toString()
        mBinding.tv2Smash.text = playerBattleBean.id2ScoreMethod.smash.toString()
        mBinding.tv2Serve.text = playerBattleBean.id2ScoreMethod.serve.toString()
        mBinding.tv2FlatDrive.text = playerBattleBean.id2ScoreMethod.flatDrive.toString()
        mBinding.tv2Pick.text = playerBattleBean.id2ScoreMethod.pick.toString()
        mBinding.tv2NetSmall.text = playerBattleBean.id2ScoreMethod.netSmall.toString()
        mBinding.tv2Fake.text = playerBattleBean.id2ScoreMethod.fake.toString()
        mBinding.tv2VariableSpeed.text = playerBattleBean.id2ScoreMethod.variableSpeed.toString()
        mBinding.tv2VariableAngle.text = playerBattleBean.id2ScoreMethod.variableAngle.toString()
    }

    private fun showEditScoreMethodDialog(listener: EditScoreMethodPointDialog.IClickConfirmListener) {
        val dialog = EditScoreMethodPointDialog(context)
        dialog.mClickConfirmListener = listener
        dialog.show()
    }

    private fun onPointUpdateListener() {
        mPointUpdateListener.onPointUpdateListener(
            playerBattleBean.id1Point,
            playerBattleBean.id2Point
        )
    }

    private fun initView() {
        mBinding.tvPlayer1Name.text = playerBattleBean.name1
        mBinding.tvPlayer1Point.text = playerBattleBean.id1Point.toString()
        mBinding.tvPlayer2Name.text = playerBattleBean.name2
        mBinding.tvPlayer2Point.text = playerBattleBean.id2Point.toString()
        updateAllScoreMethodUI()
    }

    interface IPointUpdateListener {
        fun onPointUpdateListener(id1Point: Int, id2Point: Int)
    }
}