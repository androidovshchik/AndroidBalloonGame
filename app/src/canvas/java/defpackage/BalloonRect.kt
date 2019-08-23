@file:Suppress("unused")

package defpackage

import android.graphics.Rect

fun BalloonRect.toCanvasRect() = Rect(x, y, x + width, y + height)

fun ArrayList<BalloonRect>.append(value: Int) = add(BalloonRect(size).apply {
    x = value
})

@Suppress("MemberVisibilityCanBePrivate")
class BalloonRect(index: Int) {

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

    var x = -1

    var y = -1

    var width = -1

    var height = -1

    val isPop = index in 7..10
}