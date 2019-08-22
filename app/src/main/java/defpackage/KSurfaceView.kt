package defpackage

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class KSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope {

    private val job = Job()

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

    override fun onDraw(canvas: Canvas) {

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (isRunning.compareAndSet(false, true)) {
            launch {
                while (isRunning.get()) {

                    var c: Canvas? = null
                    try {
                        c = holder.lockCanvas(null)
                        synchronized(holder) {
                            draw(c)
                        }
                    } finally {
                        // do this in a finally so that if an exception is thrown
                        // during the above, we don't leave the Surface in an
                        // inconsistent state
                        if (c != null) {
                            holder.unlockCanvasAndPost(c)
                        }
                    }
                }
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        job.complete()
        job.isActive
        job.cancel()
    }

    override val coroutineContext = Dispatchers.Main + job
}