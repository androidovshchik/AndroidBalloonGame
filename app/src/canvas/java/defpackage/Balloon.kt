package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append(space: Point) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, space))
}

class Balloon(val id: Long, space: Point) {

    val textureIndex = TEXTURES_RANGE.random()

    private val startPartIndex = PARTS_RANGE.random()

    fun getPartIndex(time: Long): Int {
        if (tappedAt == 0L || time - tappedAt < BURST_TIME) {
            return startPartIndex
        }
    }

    val position = RectB((0..space.x).random() - MAX_WIDTH / 2, space.y)

    var tappedAt = 0L

    companion object {

        val TEXTURES_RANGE = 0..4

        val PARTS_RANGE = 0..6

        val POP_PARTS_RANGE = 7..10

        const val MAX_WIDTH = 190

        const val BURST_TIME = 200
    }
}