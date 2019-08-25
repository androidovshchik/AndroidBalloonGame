package defpackage

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidovshchik.jerrygame.BuildConfig
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class Toolbar(context: Context) {

    private val height = context.dip(56).toFloat()

    private val margin = context.dip(16).toFloat()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = context.sp(16).toFloat()
        isFakeBoldText = true
    }

    private val bounds = Rect().apply {
        paint.getTextBounds("0", 0, 1, this)
    }

    fun onDraw(canvas: Canvas, fps: Int) {
        val text = "${BuildConfig.FLAVOR.toUpperCase()} $fps FPS"
        paint.color = 0x56000000
        canvas.drawRect(0f, 0f, canvas.width.toFloat(), height, paint)
        paint.color = Color.WHITE
        canvas.drawText(text, margin, (height + bounds.height()) / 2, paint)
    }
}