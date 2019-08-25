package defpackage

import android.graphics.Bitmap

@Suppress("MemberVisibilityCanBePrivate")
class TexturePack(val bitmap: Bitmap) {

    val parts = arrayListOf<RectB>()

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}