@file:Suppress("unused")

package defpackage

import android.graphics.Rect
import kotlin.math.absoluteValue

fun ArrayList<RectB>.append(x: Int) = add(RectB(x))

fun ArrayList<RectB>.applyLast(value: Int): Boolean {
    lastOrNull()?.rect?.apply {
        when {
            left == Int.MIN_VALUE -> left = value
            top == Int.MIN_VALUE -> top = value
            right == Int.MIN_VALUE -> right = left + value
            bottom == Int.MIN_VALUE -> bottom = top + value
            else -> return append(value)
        }
        return true
    }
    return append(value)
}

class RectB {

    val rect = Rect(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)

    constructor(x: Int) {
        rect.left = x
    }

    constructor(x: Int, y: Int) {
        rect.apply {
            left = x
            top = y
        }
    }

    constructor(x: Int, y: Int, width: Int, height: Int) {
        rect.set(x, y, x + width, y + height)
    }

    val hasSize
        get() = rect.right != Int.MIN_VALUE && rect.bottom != Int.MIN_VALUE

    val isOutOfBox: Boolean
        get() {
            if (!hasSize) {
                throw IllegalStateException("This method requires the size of rect must be set")
            }
            return rect.bottom <= 0
        }

    fun moveY(amount: Int) {
        if (hasSize) {
            rect.apply {
                top -= amount
                bottom -= amount
            }
        }
    }

    fun changeSize(size: Rect) {
        rect.apply {
            if (hasSize) {
                val x = (size.width() - width()).absoluteValue / 2
                val y = (size.width() - height()).absoluteValue / 2
                set(left - x, top - y, right + x, bottom + y)
            } else {
                right = left + size.width()
                bottom = top + size.height()
            }
        }
    }

    fun hasPoint(x: Float, y: Float): Boolean {
        if (hasSize) {
            return rect.contains(x.toInt(), y.toInt())
        }
        return false
    }
}