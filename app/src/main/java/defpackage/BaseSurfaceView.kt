package defpackage

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidovshchik.jerrygame.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class BaseSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope, GestureDetector.OnGestureListener {

    var isRunning = AtomicBoolean(false)
        private set

    private val detector = GestureDetector(context, this)

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
        keepScreenOn = true
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        start()
    }

    @SuppressLint("WrongCall")
    fun start() {
        if (isRunning.compareAndSet(false, true)) {
            launch {
                while (isRunning.get()) {
                    holder.lock {
                        onDraw(it)
                    }
                }
                cancel()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (BuildConfig.DEBUG) {

        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    fun stop() {
        isRunning.set(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent) = true

    override fun onDown(e: MotionEvent) = true

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float) = true

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float) = true

    override fun onLongPress(e: MotionEvent) {}

    override val coroutineContext = Dispatchers.Default
}

inline fun <T : SurfaceHolder> T.lock(crossinline block: (Canvas) -> Unit) {
    var canvas: Canvas? = null
    try {
        canvas = lockCanvas(null)?.also {
            synchronized(this) {
                block(it)
            }
        }
    } finally {
        canvas?.let {
            unlockCanvasAndPost(it)
        }
    }
}