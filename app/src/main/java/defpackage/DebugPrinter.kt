package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidovshchik.jerrygame.BuildConfig

@Suppress("MemberVisibilityCanBePrivate")
class DebugPrinter(context: Context) : GameLifecycle {

    private val toolbarHeight = dip(56).toFloat()

    private val startMargin = dip(16).toFloat()

    private val debugPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = sp(16).toFloat()
        isFakeBoldText = true
    }

    private val textBounds = Rect().apply {
        debugPaint.getTextBounds("0", 0, 1, this)
    }

    override fun onRender(output: Bitmap?) {
        if (BuildConfig.DEBUG) {
            val text = "${BuildConfig.FLAVOR.toUpperCase()} FPS"
            debugPaint.color = 0x56000000
            drawRect(0f, 0f, width.toFloat(), toolbarHeight, debugPaint)
            debugPaint.color = Color.WHITE
            drawText(text, startMargin, (toolbarHeight + textBounds.height()) / 2, debugPaint)
        }
    }

    override fun onSingleTap(x: Float, y: Float) {

    }

    override fun onDestroy() {
        ballSet.forEach {
            it.release()
        }
        ballSet.clear()
    }
}