package defpackage

import android.graphics.Bitmap
import android.graphics.Canvas
import defpackage.models.Balloon
import defpackage.models.RectB

@Suppress("MemberVisibilityCanBePrivate")
class TexturePack(val bitmap: Bitmap) {

    val parts = arrayListOf<RectB>()

    /**
     * @return if false then balloon will be removed
     */
    fun drawBalloon(canvas: Canvas, time: Long, balloon: Balloon): Boolean {
        if (!bitmap.isRecycled) {
            balloon.run {
                parts.getOrNull(getPartIndex(time))?.let {
                    if (!position.hasSize) {
                        position.changeSize(it.rect)
                    }
                    canvas.drawBitmap(bitmap, it.rect, position.rect, null)
                    return true
                }
            }
        }
        return false
    }

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}