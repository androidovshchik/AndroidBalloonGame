package defpackage

import android.graphics.Bitmap
import android.graphics.Canvas

@Suppress("MemberVisibilityCanBePrivate")
class TexturePack(val bitmap: Bitmap) {

    val parts = arrayListOf<RectB>()

    fun drawBalloon(canvas: Canvas, balloon: Balloon) = balloon.run {
        if (!bitmap.isRecycled) {
            parts.getOrNull(partIndex)?.let {
                canvas.drawBitmap(bitmap, it.rect, position.rect, null)
            }
        }
    }

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}