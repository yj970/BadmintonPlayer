package com.yj.badmintonplayer.ui

import android.app.Application
import com.yj.badmintonplayer.ui.db.ObjectBox

class APP:Application() {

    override fun onCreate() {
        super.onCreate()
        // db
        ObjectBox.init(this)
    }
}