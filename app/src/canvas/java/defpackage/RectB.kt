@file:Suppress("unused")

package defpackage

import android.graphics.Rect

fun ArrayList<RectB>.append(x: Int) = add(RectB().apply {
    rect.left = x
})

fun ArrayList<RectB>.applyLast(value: Int): Boolean {
    lastOrNull()?.rect?.apply {
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

    val rect = Rect(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

    constructor()

    constructor(x: Int, y: Int, width: Int, height: Int) {
        rect.set(x, y, x + width, y + height)
    }

    val isOutOfScreen
        get() = rect.bottom <= 0

    fun moveY(amount: Int) {
        rect.apply {
            top -= amount
            bottom -= amount
        }
    }

    fun hasPoint(x: Float, y: Float) = rect.contains(x.toInt(), y.toInt())
}