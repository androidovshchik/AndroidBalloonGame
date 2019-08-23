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

@Suppress("MemberVisibilityCanBePrivate")
class KSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope {

    var isRunning = AtomicBoolean(false)
        private set

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

    override fun surfaceCreated(holder: SurfaceHolder) {
        start()
    }

    @SuppressLint("WrongCall")
    fun start() {
        if (!isRunning.compareAndSet(false, true)) {
            return
        }
        launch {
            while (isRunning.get()) {
                holder.run {
                    var canvas: Canvas? = null
                    try {
                        canvas = lockCanvas(null)
                        canvas?.let {
                            synchronized(this) {
                                onDraw(it)
                            }
                        }
                    } finally {
                        canvas?.let {
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
        stop()
    }

    fun stop() {
        isRunning.set(false)
    }

    override val coroutineContext = Dispatchers.Default
}