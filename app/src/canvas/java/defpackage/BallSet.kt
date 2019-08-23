package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF

fun BallSet.drawBitmap(canvas: Canvas, index: Int) = canvas.run {
    drawBitmap(bitmap, rects[index].toCanvasRect(), RectF(), null)
}

@Suppress("MemberVisibilityCanBePrivate")
class BallSet(context: Context, val name: String) {

    val bitmap: Bitmap

    val rects = arrayListOf<BallRect>()

    init {
        context.resources.run {
            val drawableId = getIdentifier(name, "drawable", context.packageName)
            val rawId = getIdentifier(name, "raw", context.packageName)
            bitmap = BitmapFactory.decodeResource(this, drawableId)
            openRawResource(rawId)
                .bufferedReader()
                .use {
                    while (true) {
                        val line = readLine() ?: break
                        if (PATTERN.matches(line)) {
                            rects.lastOrNull()?.let {
                                rects.append(0)
                            } ?: rects.append(0)
                            val field = javaClass.getField("foo")
                            field.set(this@BallRect, line.split(":")[1])
                        }
                    }
                }
        }
    }

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