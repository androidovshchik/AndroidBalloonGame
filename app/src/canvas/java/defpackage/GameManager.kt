package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

fun BallSet.drawBitmap(canvas: Canvas, index: Int) = canvas.run {
    drawBitmap(bitmap, rects[index].toCanvasRect(), RectF(), null)
}

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : BaseManager(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    }

    private val ballSet = arrayListOf<BallSet>(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    override fun onRender(output: Bitmap) {

    }

    override fun onSingleTap(x: Float, y: Float) {

    }

    override fun onDestroy() {
        ballSet.forEach {
            it.release()
        }
        ballSet.clear()
    }

    companion object {

        @JvmStatic
        val PATTERN = "(x|y|width|height): ".toRegex()
    }
}