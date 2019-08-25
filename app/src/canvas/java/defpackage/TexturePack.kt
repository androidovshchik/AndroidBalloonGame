package defpackage

import android.graphics.Bitmap
import android.graphics.Canvas

@Suppress("MemberVisibilityCanBePrivate")
class TexturePack(val bitmap: Bitmap) {

    val parts = arrayListOf<RectB>()

    /**
     * @return if false then balloon will be removed
     */
    fun onDraw(canvas: Canvas, balloon: Balloon, time: Long, delay: Long): Boolean {
        if (!bitmap.isRecycled) {
            balloon.run {
                // must be before changing size
                // return part of texture to draw
                parts.getOrNull(animate(time))?.let {
                    // must be before move
                    // changes size of rect to draw
                    position.changeSize(it.rect)
                    if (!move(delay)) {
                        return false
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