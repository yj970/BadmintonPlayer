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
}
