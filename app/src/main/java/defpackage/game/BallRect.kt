@file:Suppress("unused")

package defpackage.game

import android.graphics.Rect

fun BallRect.toCanvasRect() = Rect(x, y, x + width, y + height)

@Suppress("MemberVisibilityCanBePrivate")
class BallRect(
    val index: Int,
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
) {

    val isPop
        get() = index in 7..10
}