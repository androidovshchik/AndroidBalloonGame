package defpackage.game

import android.content.Context
import android.graphics.Rect

@Suppress("MemberVisibilityCanBePrivate")
class BallRect(context: Context, val name: String) {

    var x: Int = 0
    var y: Int = 149
    var width: Int = 233
    var height: Int = 252

    var paddingLeft: Int = 59
    var paddingRight: Int = 58
    var paddingTop: Int = 46
    var paddingBottom: Int = 52

    var isPop = true
}

fun BallRect.toCanvasRect() = Rect()