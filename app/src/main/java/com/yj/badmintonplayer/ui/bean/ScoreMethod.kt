package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable

class ScoreMethod() : Parcelable {
    // 进攻
    var highFar = 0// 高远球
    var lob = 0// 吊球
    var smash = 0// 杀球
    var serve = 0// 发球

    // 防守
    var flatDrive = 0// 平抽挡
    var pick = 0// 挑球

    // 控制
    var netSmall = 0// 网前小球
    var fake = 0// 假动作
    var variableSpeed = 0// 变速球
    var variableAngle = 0// 角度变化

    // 无
    var normal = 0// 无统计

    constructor(parcel: Parcel) : this() {
        highFar = parcel.readInt()
        lob = parcel.readInt()
        smash = parcel.readInt()
        serve = parcel.readInt()
        flatDrive = parcel.readInt()
        pick = parcel.readInt()
        netSmall = parcel.readInt()
        fake = parcel.readInt()
        variableSpeed = parcel.readInt()
        variableAngle = parcel.readInt()
        normal = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(highFar)
        parcel.writeInt(lob)
        parcel.writeInt(smash)
        parcel.writeInt(serve)
        parcel.writeInt(flatDrive)
        parcel.writeInt(pick)
        parcel.writeInt(netSmall)
        parcel.writeInt(fake)
        parcel.writeInt(variableSpeed)
        parcel.writeInt(variableAngle)
        parcel.writeInt(normal)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getControlPoint(): Int {
        return netSmall + fake + variableSpeed + variableAngle
    }

    fun getDefendPoint(): Int {
        return flatDrive + pick
    }

    fun getAttachPoint(): Int {
        return highFar + lob + smash + serve
    }

    fun getTitle(diff: Int): String {
        val attachPoint = getAttachPoint()
        val defendPoint = getDefendPoint()
        val controlPoint = getControlPoint()
        val LEVEL_1 = 3
        val LEVEL_2 = 6

        if (controlPoint >= attachPoint && controlPoint >= defendPoint && controlPoint > 0) {
            if (diff > LEVEL_2) {
                return "云泥之别"
            }
            if (diff > LEVEL_1) {
                return "千变万化"
            }
            return "游刃有余"
        }

        if (attachPoint >= defendPoint && attachPoint >= controlPoint && attachPoint > 0) {
            if (diff > LEVEL_2) {
                return "狂轰乱炸"
            }
            if (diff > LEVEL_1) {
                return "破竹建瓴"
            }
            return "攻势如火"
        }

        if (defendPoint >= attachPoint && defendPoint >= controlPoint && defendPoint > 0) {
            if (diff > LEVEL_2) {
                return "不动如山"
            }
            if (diff > LEVEL_1) {
                return "以守为攻"
            }
            return "以柔克刚"
        }



        return "平平无奇"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScoreMethod

        if (highFar != other.highFar) return false
        if (lob != other.lob) return false
        if (smash != other.smash) return false
        if (serve != other.serve) return false
        if (flatDrive != other.flatDrive) return false
        if (pick != other.pick) return false
        if (netSmall != other.netSmall) return false
        if (fake != other.fake) return false
        if (variableSpeed != other.variableSpeed) return false
        if (variableAngle != other.variableAngle) return false
        if (normal != other.normal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = highFar
        result = 31 * result + lob
        result = 31 * result + smash
        result = 31 * result + serve
        result = 31 * result + flatDrive
        result = 31 * result + pick
        result = 31 * result + netSmall
        result = 31 * result + fake
        result = 31 * result + variableSpeed
        result = 31 * result + variableAngle
        result = 31 * result + normal
        return result
    }

    companion object CREATOR : Parcelable.Creator<ScoreMethod> {
        override fun createFromParcel(parcel: Parcel): ScoreMethod {
            return ScoreMethod(parcel)
        }

        override fun newArray(size: Int): Array<ScoreMethod?> {
            return arrayOfNulls(size)
        }
    }



}