package defpackage

import android.graphics.Bitmap

interface GameLifecycle {

    fun onDraw(output: Bitmap?)

    fun onSingleTap(x: Float, y: Float)

    fun onDestroy()
}