package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : GameLifecycle {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    }

    private val ballSet = arrayListOf<BallSet>(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    override fun onRender(output: Bitmap?) {

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