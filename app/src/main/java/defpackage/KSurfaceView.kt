package defpackage

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class KSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope {

    @Volatile
    var isRunning = AtomicBoolean(false)

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

    init {
        isFocusable = true
        holder.addCallback(this)
    }

    @SuppressLint("WrongCall")
    override fun surfaceCreated(holder: SurfaceHolder) {
        isRunning.set(true)
        launch {
            while (isRunning.get()) {
                holder.run {
                    lockCanvas(null)?.let {
                        try {
                            synchronized(this) {
                                onDraw(it)
                            }
                        } finally {
                            unlockCanvasAndPost(it)
                        }
                    }
                }
            }
            cancel()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.RED)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        isRunning.set(false)
    }

    override val coroutineContext = Dispatchers.Default
}