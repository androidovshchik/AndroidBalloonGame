package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append(window: Point) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, window))
}

class Balloon(val id: Long, window: Point) {

    val textureIndex = TEXTURE_RANGE.random()

    val partIndex = NORMAL_RANGE.random()

    val position = RectB((0..window.x).random() - MAX_WIDTH / 2, window.y)

    var tappedAt = 0L

    val isExisting
        get() = tappedAt == 0L || System.currentTimeMillis() - tappedAt < BURST_TIME

    companion object {

        val TEXTURE_RANGE = 0..4

        val NORMAL_RANGE = 0..6

        val POP_RANGE = 0..6

        const val MAX_WIDTH = 190

        const val BURST_TIME = 200
    }
}