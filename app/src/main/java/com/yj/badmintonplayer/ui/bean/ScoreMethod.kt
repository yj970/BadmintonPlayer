package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable

class ScoreMethod() :Parcelable{
    // 进攻
    var highFar = 0// 高远球
    var lob = 0// 吊球
    var smash = 0// 杀球
    var serve = 0// 发球
    // 防守
    var flatDrive = 0// 平抽挡
    var pick = 0// 挑球
    var netSmall = 0// 网前小球
    // 超一流
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

    companion object CREATOR : Parcelable.Creator<ScoreMethod> {
        override fun createFromParcel(parcel: Parcel): ScoreMethod {
            return ScoreMethod(parcel)
        }

        override fun newArray(size: Int): Array<ScoreMethod?> {
            return arrayOfNulls(size)
        }
    }
}