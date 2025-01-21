package com.yj.badmintonplayer.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class HRectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var max = 0
    var value = 0
    var left = true

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var f = (value * 1f / max) * measuredWidth
        if (left) {
            canvas.drawRect(
                (measuredWidth - f),
                0f,
                measuredWidth * 1f,
                measuredHeight * 1f,
                paint
            )
        } else {
            canvas.drawRect(0f, 0f, f, measuredHeight * 1f, paint)
        }
    }
}