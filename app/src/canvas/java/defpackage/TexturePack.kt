package defpackage

import android.graphics.Bitmap
import android.graphics.Canvas

@Suppress("MemberVisibilityCanBePrivate")
class TexturePack(val index: Int, val bitmap: Bitmap) {

    val parts = arrayListOf<RectB>()

    fun drawBalloon(canvas: Canvas, balloon: Balloon) {
        if (!bitmap.isRecycled) {
            canvas.drawBitmap(bitmap, parts[index].rect, position.rect, null)
            val rect = textureB[textureIndex].packs[packIndex].rect
        }
    }

    fun getRect(index: Int): RectB {
        if (index !in 0..6) {
            throw IndexOutOfBoundsException("Invalid index of pop rect")
        }
        return parts[index]
    }

    fun getPopRect(index: Int): RectB {
        if (index !in 0..3) {
            throw IndexOutOfBoundsException("Invalid index of pop rect")
        }
        return parts[index + 7]
    }

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}