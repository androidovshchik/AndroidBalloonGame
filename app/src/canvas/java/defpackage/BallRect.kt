@file:Suppress("unused")

package defpackage

import android.graphics.Rect

fun BallRect.toCanvasRect() = Rect(x, y, x + width, y + height)

fun ArrayList<BallRect>.append(value: Int) = add(BallRect(size).apply {
    x = value
})

@Suppress("MemberVisibilityCanBePrivate")
class BallRect(index: Int) {

    var x = -1

    var y = -1

    var width = -1

    var height = -1

    val isPop = index in 7..10
}