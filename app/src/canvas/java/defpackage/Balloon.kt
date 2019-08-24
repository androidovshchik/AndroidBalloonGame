package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append(window: Point) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, window))
}

class Balloon(val id: Long, window: Point) {

    val textureIndex = (0..4).random()

    val partIndex = (0..6).random()

    val position = RectB((0..window.x).random() - MAX_WIDTH / 2, window.y)

    var tappedAt = 0L

    companion object {

        const val MAX_WIDTH = 190
    }
}