package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable
import com.yj.badmintonplayer.ui.utils.Utils

class GameBean(
    val id: String,
    val roomName: String,
    val createTime: Long,
    val playerBattleBeans: ArrayList<PlayerBattleBean>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.createTypedArrayList(PlayerBattleBean.CREATOR) as ArrayList<PlayerBattleBean>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(roomName)
        parcel.writeLong(createTime)
        parcel.writeTypedList(playerBattleBeans)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameBean> {
        override fun createFromParcel(parcel: Parcel): GameBean {
            return GameBean(parcel)
        }

        override fun newArray(size: Int): Array<GameBean?> {
            return arrayOfNulls(size)
        }
    }

    fun getTitle(): String {
        val roomName = roomName
        val date = Utils.getDateFormat(createTime)
        return "日期:" + date + "\n" + "房间名:" + roomName
    }
}