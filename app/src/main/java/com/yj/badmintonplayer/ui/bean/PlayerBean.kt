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
    var games = ArrayList<GameBean>()
}
