package com.yj.badmintonplayer.ui.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {
    fun getDateFormat(millis: Long): String {
        // 获取日期
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
        // 定义日期格式化器
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        // 将日期按照指定格式进行格式化
        val formattedDate = dateTime.format(formatter)
        return formattedDate
    }
}