package defpackage

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import defpackage.game.BallSet

@Suppress("MemberVisibilityCanBePrivate")
class GameSurfaceView : BaseSurfaceView {

    private val ballSet = arrayListOf<BallSet>(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    )

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onDraw(canvas: Canvas) = canvas.run {
        super.onDraw(this)
        drawBitmap()
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return super.onSingleTapConfirmed(e)
    }

    override fun onDetachedFromWindow() {
        ballSet.forEach {
            it.release()
        }
        ballSet.clear()
        super.onDetachedFromWindow()
    }
}