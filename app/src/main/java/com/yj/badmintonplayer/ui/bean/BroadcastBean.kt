package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable

class BroadcastBean(val gameBean: GameBean, val type: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(GameBean::class.java.classLoader)!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(gameBean, flags)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BroadcastBean> {
        override fun createFromParcel(parcel: Parcel): BroadcastBean {
            return BroadcastBean(parcel)
        }

        override fun newArray(size: Int): Array<BroadcastBean?> {
            return arrayOfNulls(size)
        }
    }

    object BroadcastType {
        val TYPE_HEART_BEAT = 1// 心跳
        val TYPE_GUEST_REPLAY = 2;// 客人回复
        val TYPE_ROOMER_BROADCAST = 3;// 房主广播
    }

}