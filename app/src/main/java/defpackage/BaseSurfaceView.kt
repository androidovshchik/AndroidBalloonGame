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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import java.util.concurrent.atomic.AtomicBoolean

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class BaseSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope, GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    var isRunning = AtomicBoolean(false)
        private set

    protected val defaultMargin = dip(16).toFloat()

    private val detector = GestureDetector(context, this)

    /** DEBUG PROPERTIES **/

    private val toolbarHeight = dip(56).toFloat()

    private val debugPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = sp(16).toFloat()
        isFakeBoldText = true
    }

    private val debugBounds = Rect().apply {
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
        holder.apply {
            setZOrderOnTop(true)
            setFormat(PixelFormat.TRANSPARENT)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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

    override fun onDraw(canvas: Canvas) = canvas.run {
        drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    }

    protected open fun onPostDraw(canvas: Canvas) = canvas.run {
        if (BuildConfig.DEBUG) {
            debugPaint.color = 0x56000000
            drawRect(0f, 0f, width.toFloat(), toolbarHeight, debugPaint)
            debugPaint.color = Color.WHITE
            val text = "${BuildConfig.FLAVOR.toUpperCase()} FPS"
            drawText(text, defaultMargin, (toolbarHeight + debugBounds.height()) / 2, debugPaint)
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

    override fun onDown(e: MotionEvent) = false

    override fun onSingleTapUp(e: MotionEvent) = false

    override fun onSingleTapConfirmed(e: MotionEvent) = false

    override fun onDoubleTap(e: MotionEvent) = false

    override fun onDoubleTapEvent(e: MotionEvent) = false

    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float) = false

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float) = false

    override fun onShowPress(e: MotionEvent) {}

    override fun onLongPress(e: MotionEvent) {}

    override fun onDetachedFromWindow() {
        holder.removeCallback(this)
        super.onDetachedFromWindow()
    }

    override val coroutineContext = Dispatchers.Default
}