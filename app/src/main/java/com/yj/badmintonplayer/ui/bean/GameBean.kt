package com.yj.badmintonplayer.ui.bean

import android.os.Parcel
import android.os.Parcelable
import com.yj.badmintonplayer.ui.utils.JsonUtil
import com.yj.badmintonplayer.ui.utils.Utils
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter

@Entity
class GameBean(
    @Id
    var id: Long,
    val gameId: String,
    val roomName: String,
    val createTime: Long,
    @Convert(converter = PlayerBattlesBeansConverter::class, dbType = String::class)
    val playerBattleBeans: ArrayList<PlayerBattleBean>
) : Parcelable, Cloneable {


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.createTypedArrayList(PlayerBattleBean.CREATOR) as ArrayList<PlayerBattleBean>
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(gameId)
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

    class PlayerBattlesBeansConverter : PropertyConverter<ArrayList<PlayerBattleBean>, String> {
        override fun convertToEntityProperty(databaseValue: String): ArrayList<PlayerBattleBean> {
            return JsonUtil.fromJsonList<PlayerBattleBean>(databaseValue)
        }

        override fun convertToDatabaseValue(entityProperty: ArrayList<PlayerBattleBean>): String {
            return JsonUtil.listToJson(entityProperty)
        }
    }

    fun getTitle(): String {
        val roomName = roomName
        val date = Utils.getDateFormat(createTime)
        return "竞技日期:" + date + "\n" + "房间名:" + roomName
    }

    fun getTitle2(): String {
        val roomName = roomName
        val date = Utils.getDateFormat(createTime)
        return "竞技日期:" + date + " " + "房间名:" + roomName
    }

    public override fun clone(): Any {
        return super.clone()
    }
}