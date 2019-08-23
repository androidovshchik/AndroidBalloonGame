package defpackage

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
class GameSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope, GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    var isRunning = AtomicBoolean(false)
        private set

    private val manager = GameManager(context)

    private val printer = DebugPrinter(context)

    private val detector = GestureDetector(context, this)

    private var job: Job? = null

    private var output: Bitmap? = null

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
        setZOrderOnTop(true)
        holder.apply {
            // todo without below
            setFormat(PixelFormat.TRANSPARENT)
            addCallback(this@GameSurfaceView)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        start()
    }

    @SuppressLint("WrongCall")
    fun start(): Boolean {
        if (job?.isActive == true) {
            return false
        }
        if (!isRunning.compareAndSet(false, true)) {
            return false
        }
        job = launch {
            while (isRunning.get()) {
                holder.apply {
                    lockCanvas(null)?.let {
                        onDraw(it)
                        onPostDraw(it)
                        unlockCanvasAndPost(it)
                    }
                }
            }
            cancel()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        output?.let {
            manager.onRender(it)
            printer.onRender(it)
        }
    }

    private fun onPostDraw(canvas: Canvas) {
        output?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    fun stop() {
        isRunning.set(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        reset()
        output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    }

    private fun reset() {
        output?.apply {
            if (!isRecycled) {
                recycle()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent) = false

    override fun onSingleTapUp(e: MotionEvent) = false

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        manager.onSingleTap(e.x, e.y)
        return false
    }

    override fun onDoubleTap(e: MotionEvent) = false

    override fun onDoubleTapEvent(e: MotionEvent) = false

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float) = false

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float) = false

    override fun onShowPress(e: MotionEvent) {}

    override fun onLongPress(e: MotionEvent) {}

    fun release() {
        reset()
        manager.onDestroy()
        holder.removeCallback(this)
    }

    override val coroutineContext = Dispatchers.Default
}