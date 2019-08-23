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
import androidovshchik.jerrygame.BuildConfig
import kotlinx.coroutines.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
abstract class BaseSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope, GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    var isRunning = AtomicBoolean(false)
        private set

    protected abstract val manager: GameLifecycle

    private val job = Job()

    private var output: Bitmap? = null

    private val detector = GestureDetector(context, this)

    /** DEBUG PROPERTIES **/

    private val toolbarHeight = dip(56).toFloat()

    private val startMargin = dip(16).toFloat()

    private val debugPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = sp(16).toFloat()
        isFakeBoldText = true
    }

    private val textBounds = Rect().apply {
        debugPaint.getTextBounds("0", 0, 1, this)
    }

    /** DEBUG PROPERTIES **/

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
            addCallback(this@BaseSurfaceView)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        start()
    }

    @SuppressLint("WrongCall")
    fun start(): Boolean {
        job.children.count()
        if (isRunning.compareAndSet(false, true)) {
            launch {
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
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.run {
            drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            output?.let {
                manager.onRender(it)
                drawBitmap(it, 0f, 0f, null)
            }
        }
    }

    protected open fun onPostDraw(canvas: Canvas) = canvas.run {
        if (BuildConfig.DEBUG) {
            val text = "${BuildConfig.FLAVOR.toUpperCase()} FPS"
            debugPaint.color = 0x56000000
            drawRect(0f, 0f, width.toFloat(), toolbarHeight, debugPaint)
            debugPaint.color = Color.WHITE
            drawText(text, startMargin, (toolbarHeight + textBounds.height()) / 2, debugPaint)
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

    open fun release() {
        reset()
        manager.onDestroy()
        holder.removeCallback(this)
    }

    override val coroutineContext = Dispatchers.Default + job
}