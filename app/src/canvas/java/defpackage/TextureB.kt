package defpackage

import android.graphics.Bitmap
import android.graphics.Canvas

@Suppress("MemberVisibilityCanBePrivate")
class TextureB(val index: Int, val bitmap: Bitmap) {

    val rects = arrayListOf<RectB>()

    fun drawBalloon(canvas: Canvas, index: Int, position: RectB) {
        if (!bitmap.isRecycled) {
            canvas.drawBitmap(bitmap, rects[index].rect, position.rect, null)
        }
    }

    fun getRect(index: Int): RectB {
        if (index !in 0..6) {
            throw IndexOutOfBoundsException("Invalid index of pop rect")
        }
        return rects[index]
    }

    fun getPopRect(index: Int): RectB {
        if (index !in 0..3) {
            throw IndexOutOfBoundsException("Invalid index of pop rect")
        }
        return rects[index + 7]
    }

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}