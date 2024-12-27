package com.yj.badmintonplayer.ui.db

import android.content.Context
import com.yj.badmintonplayer.ui.bean.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context)
            .build()
    }
}