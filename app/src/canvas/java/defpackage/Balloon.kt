package defpackage

import android.graphics.Rect

@Suppress("MemberVisibilityCanBePrivate")
class Balloon(x: Int, y: Int, width: Int, height: Int, var zIndex: Int) {

    val position = Rect(x, y, x + width, y + height)

    var isAlive = true

    val isOutOfBox
        get() = position.bottom > 0

    fun moveY(amount: Int) {
        position.apply {
            top += amount
            bottom -= amount
        }
    }

    fun hasDot(x: Float, y: Float) = position.contains(x.toInt(), y.toInt())
}