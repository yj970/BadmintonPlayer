package com.yj.badmintonplayer.ui.bean


class PlayerBean(var id: String, var hintNickname: String) {
    fun getName(): String {
        if (nickName.isEmpty()) {
            return hintNickname
        } else {
            return nickName
        }
    }

    // 名称
    var nickName = ""

    // 胜场
    var winCount = 0

    // 败场
    var loseCount = 0

    // 排名（从1开始）
    var rank = 0;

    // 总得分
    var winPoint = 0

    // 总失分
    var losePoint = 0;

    // 比赛
    var games = ArrayList<PlayerBattleBean>()

    // 进攻得分
    var attachPoint = 0

    // 防守得分
    var defendPoint = 0

    // 控制得分
    var controlPoint = 0

    // 其余得分
    var otherPoint = 0

    private fun getAllScorePoint(): Int {
        return attachPoint + defendPoint + controlPoint
    }

     fun getControlIndex(): String {
        val allScorePoint = getAllScorePoint()
        val f = if(allScorePoint == 0) 0f else controlPoint * 1f / allScorePoint
        return String.format("%.2f", f)
    }

     fun getDefendIndex(): String {
        val allScorePoint = getAllScorePoint()
        val f = if(allScorePoint == 0) 0f else defendPoint * 1f / allScorePoint
        return String.format("%.2f", f)
    }

     fun getAttachIndex(): String {
        val allScorePoint = getAllScorePoint()
        val f = if(allScorePoint == 0) 0f else attachPoint * 1f / allScorePoint
        return String.format("%.2f", f)
    }

}
