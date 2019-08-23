package defpackage

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent

@Suppress("MemberVisibilityCanBePrivate")
class GameSurfaceView : BaseSurfaceView {

    var output: Bitmap? = null

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        output = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
    }

    override fun onDraw(canvas: Canvas) = canvas.run {
        super.onDraw(this)
        output?.let {
            onDraw(it)
            canvas.drawBitmap(output, 0f, 0f, null)
        }
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        onSingleTap(e.x, e.y)
        return super.onSingleTapConfirmed(e)
    }

    override fun release() {
        onDestroy()
        super.release()
    }

    private external fun onDraw(output: Bitmap)

    private external fun onSingleTap(x: Float, y: Float)

    private external fun onDestroy()

    companion object {

        init {
            System.loadLibrary("viewer")
            System.loadLibrary("main")
        }
    }
}