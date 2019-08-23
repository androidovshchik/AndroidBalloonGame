package defpackage

import android.graphics.Bitmap

interface GameLifecycle {

    fun onRender(output: Bitmap)

    fun onSingleTap(x: Float, y: Float)

    fun onDestroy()
}