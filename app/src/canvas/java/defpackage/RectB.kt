@file:Suppress("unused")

package defpackage

import android.graphics.Rect

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

@Suppress("MemberVisibilityCanBePrivate")
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

    val hasZeroPoint
        get() = rect.left != Int.MIN_VALUE && rect.top != Int.MIN_VALUE

    val hasSize
        get() = rect.right != Int.MIN_VALUE && rect.bottom != Int.MIN_VALUE

    fun moveY(amount: Int) {
        if (hasZeroPoint && hasSize) {
            rect.top -= amount
            rect.bottom -= amount
        }
    }

    fun changeSize(size: Rect) {
        if (hasZeroPoint) {
            rect.apply {
                if (hasSize) {
                    left -= (size.width() - width()) / 2
                    top -= (size.width() - height()) / 2
                }
                right = left + size.width()
                bottom = top + size.height()
            }
        }
    }

    fun hasPoint(x: Float, y: Float): Boolean {
        if (hasZeroPoint && hasSize) {
            return rect.contains(x.toInt(), y.toInt())
        }
        return false
    }
}