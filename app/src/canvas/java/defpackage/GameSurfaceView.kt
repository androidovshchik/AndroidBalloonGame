package defpackage

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent

@Suppress("MemberVisibilityCanBePrivate")
class GameSurfaceView : BaseSurfaceView {

    private val gameManager = GameManager(context)

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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gameManager.onDraw(output ?: return)
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        gameManager.onSingleTap(e)
        return super.onSingleTapConfirmed(e)
    }

    override fun onDetachedFromWindow() {
        gameManager.release()
        super.onDetachedFromWindow()
    }
}