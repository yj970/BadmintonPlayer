package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 对战表
 */
class PlayerBattleBean(val id1: String, val id2: String, val name1: String, val name2: String) :
    Parcelable {

    // 选手1得分
    var id1Point: Int = 0;

    // 选手2得分
    var id2Point: Int = 0;

    // 选手1得分手段
    var id1ScoreMethod = ScoreMethod()

    // 选手2得分手段
    var id2ScoreMethod = ScoreMethod()


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
        id1Point = parcel.readInt()
        id2Point = parcel.readInt()
        try {
            id1ScoreMethod = parcel.readParcelable(ScoreMethod::class.java.classLoader)!!
        } catch (e: Exception) {
            id1ScoreMethod = ScoreMethod()
        }
        try {
            id2ScoreMethod = parcel.readParcelable(ScoreMethod::class.java.classLoader)!!
        } catch (e: Exception) {
            id2ScoreMethod = ScoreMethod()
        }
    }


    override fun toString(): String {
        return id1 + "对战" + id2
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerBattleBean

        if ((id1 == other.id1 || id1 == other.id2) && (id2 == other.id1 || id2 == other.id2)) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id1.hashCode()
        result = 31 * result + id2.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id1)
        parcel.writeString(id2)
        parcel.writeString(name1)
        parcel.writeString(name2)
        parcel.writeInt(id1Point)
        parcel.writeInt(id2Point)
        parcel.writeParcelable(id1ScoreMethod, flags)
        parcel.writeParcelable(id2ScoreMethod, flags)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<PlayerBattleBean> {
        override fun createFromParcel(parcel: Parcel): PlayerBattleBean {
            return PlayerBattleBean(parcel)
        }

        override fun newArray(size: Int): Array<PlayerBattleBean?> {
            return arrayOfNulls(size)
        }
    }


}