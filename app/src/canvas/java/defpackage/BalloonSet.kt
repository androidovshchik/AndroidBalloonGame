package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF

fun BallSet.drawBitmap(canvas: Canvas, index: Int) = canvas.run {
    drawBitmap(bitmap, rects[index].toCanvasRect(), RectF(), null)
}

@Suppress("MemberVisibilityCanBePrivate")
class BallSet(context: Context, val name: String) {

    val bitmap: Bitmap

    val rects = arrayListOf<RectB>()

    val isPop = index in 7..10

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }

    companion object {

        @JvmStatic
        val PATTERN = "(x|y|width|height): ".toRegex()
    }
}