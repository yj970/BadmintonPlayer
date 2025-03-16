package com.yj.badmintonplayer.ui.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.io.OutputStream
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

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap, imageName: String): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            val outputStream: OutputStream? = resolver.openOutputStream(uri)
            outputStream?.use {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
            return true
        } else {
            return false
        }
    }


    fun getRecyclerViewFullScreenshot(recyclerView: RecyclerView): Bitmap {
        // 获取 RecyclerView 的适配器
        val adapter = recyclerView.adapter

        // 计算所有 Item 的总高度
        var totalHeight = 0
        for (i in 0 until adapter!!.itemCount) {
            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i))
            adapter.onBindViewHolder(holder, i)
            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            totalHeight += holder.itemView.measuredHeight
        }

        // 创建一个足够大的 Bitmap
        val bitmap = Bitmap.createBitmap(recyclerView.width, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)

        // 遍历所有 Item，将它们绘制到 Bitmap 上
        var currentHeight = 0
        for (i in 0 until adapter.itemCount) {
            val holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i))
            adapter.onBindViewHolder(holder, i)
            holder.itemView.measure(
                View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            holder.itemView.layout(0, 0, recyclerView.width, holder.itemView.measuredHeight)

            // 将 ItemView 绘制到 Canvas 上
            canvas.save()
            canvas.translate(0f, currentHeight.toFloat())
            holder.itemView.draw(canvas)
            canvas.restore()

            // 更新当前高度
            currentHeight += holder.itemView.measuredHeight
        }

        return bitmap
    }
}