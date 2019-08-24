package defpackage

import android.graphics.Rect

@Suppress("MemberVisibilityCanBePrivate")
class Balloon(val id: Long, var zIndex: Int) {

    val position = Rect(x, y, x + width, y + height)

    var isAlive = true
}