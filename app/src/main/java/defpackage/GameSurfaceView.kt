package defpackage

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.custom.ankoView

fun ViewManager.gameSurfaceView(theme: Int = 0) = gameSurfaceView(theme) {}

inline fun ViewManager.gameSurfaceView(theme: Int = 0, init: GameSurfaceView.() -> Unit) =
    ankoView({ GameSurfaceView(it) }, theme, init)

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
class GameSurfaceView : SurfaceView, SurfaceHolder.Callback, CoroutineScope, GestureDetector.OnGestureListener {

    private val manager = GameManager(context)

    private val detector = GestureDetector(context, this)

    private var job: Job? = null

    private var output: Bitmap? = null

    val isRunning
        get() = job?.isActive == true

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
        if (isRunning) {
            return
        }
        job = launch {
            while (true) {
                holder.run {
                    lockCanvas(null)?.let {
                        try {
                            synchronized(this) {
                                onDraw(it)
                                onPostDraw(it)
                            }
                        } finally {
                            unlockCanvasAndPost(it)
                        }
                    }
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        output?.let {
            manager.onDraw(it)
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
        job?.apply {
            cancel()
            job = null
        }
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
            output = null
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        if (manager.onSingleTap(e.x, e.y)) {
            context.activity()?.let {
                if (it is MainActivity) {
                    it.playBalloonPop()
                }
            }
        }
        return false
    }

    override fun onSingleTapUp(e: MotionEvent) = false

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