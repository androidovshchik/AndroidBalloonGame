@file:Suppress("unused")

package defpackage

import android.graphics.Rect

fun ArrayList<RectB>.append(x: Int) = add(RectB().apply {
    position.left = x
})

fun ArrayList<RectB>.applyLast(value: Int): Boolean {
    lastOrNull()?.position?.apply {
        when {
            left == Int.MIN_VALUE -> left = value
            top == Int.MIN_VALUE -> top = value
            right == Int.MIN_VALUE -> right = left + value
            bottom == Int.MIN_VALUE -> bottom = top + value
            else -> return false
        }
        return true
    }
    return append(value)
}

@Suppress("MemberVisibilityCanBePrivate")
class RectB {

    val position = Rect(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

    constructor()

    constructor(x: Int, y: Int, width: Int, height: Int) {
        position.set(x, y, x + width, y + height)
    }

    val isOutOfScreen
        get() = position.bottom <= 0

    fun moveY(amount: Int) {
        position.apply {
            top -= amount
            bottom -= amount
        }
    }

    fun hasDot(x: Float, y: Float) = position.contains(x.toInt(), y.toInt())
}