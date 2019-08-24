package defpackage

import android.content.Context
import android.graphics.*
import androidovshchik.jerrygame.BuildConfig
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class Toolbar(context: Context) {

    private val height = context.dip(56).toFloat()

    private val startMargin = context.dip(16).toFloat()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = context.sp(16).toFloat()
        isFakeBoldText = true
    }

    private val textBounds = Rect().apply {
        paint.getTextBounds("0", 0, 1, this)
    }

    fun drawText(canvas: Canvas, window: Point, fps: Int) {
        val text = "${BuildConfig.FLAVOR.toUpperCase()} $fps FPS"
        paint.color = 0x56000000
        canvas.drawRect(0f, 0f, window.x.toFloat(), height, paint)
        paint.color = Color.WHITE
        canvas.drawText(text, startMargin, (height + textBounds.height()) / 2, paint)
    }
}