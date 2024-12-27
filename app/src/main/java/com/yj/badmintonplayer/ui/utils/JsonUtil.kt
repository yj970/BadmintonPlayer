package com.yj.badmintonplayer.ui.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtil {

    fun toJson(entity: Any): String {
        val gson = Gson()
        return gson.toJson(entity)
    }

    inline fun <reified T> fromJson(json: String): T {
        val gson = Gson()
        return gson.fromJson(json, T::class.java)
    }

    fun <T> listToJson(list: ArrayList<T>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    inline fun <reified T> fromJsonList(json: String): ArrayList<T> {
        val type = object : TypeToken<ArrayList<T>>() {}.type
        return Gson().fromJson(json, type)
    }
}