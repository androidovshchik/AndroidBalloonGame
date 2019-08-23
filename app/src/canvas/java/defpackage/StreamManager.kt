package defpackage

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

@Suppress("MemberVisibilityCanBePrivate")
class StreamManager(context: Context) {

    private val ballSet = arrayListOf<BallSet>(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    fun onDraw(canvas: Canvas, paint: Paint) = canvas.run {

    }

    fun onSingleTap(e: MotionEvent) {

    }

    fun release() {
        ballSet.forEach {
            it.release()
        }
        ballSet.clear()
    }
}