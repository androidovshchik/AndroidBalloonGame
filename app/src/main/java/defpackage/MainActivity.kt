package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var fMainView: KSurfaceView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fMainView = KSurfaceView(applicationContext).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        setContentView(fMainView)
        //val timer = Executors.newScheduledThreadPool(1)
        /*counter = timer.scheduleWithFixedDelay({
            if (!isFinishing) {
                map.postInvalidate()
            }
        }, 0, 5, TimeUnit.MILLISECONDS)*/
    }
}

private class SkiaDrawView(ctx: Context) : View(ctx) {

    lateinit var fSkiaBitmap: Bitmap

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (!::fSkiaBitmap.isInitialized) {
            fSkiaBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (::fSkiaBitmap.isInitialized) {
            //cdraw(fSkiaBitmap)
            canvas.drawBitmap(fSkiaBitmap, 0f, 0f, null)
        }
    }
}
