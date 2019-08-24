package defpackage

import android.content.Context
import android.graphics.Bitmap

class GameManager(context: Context) : BaseManager(context) {

    external override fun onRender(output: Bitmap)

    external override fun onSingleTap(x: Float, y: Float)

    external override fun onDestroy()

    companion object {

        init {
            System.loadLibrary("viewer")
            System.loadLibrary("main")
        }
    }
}