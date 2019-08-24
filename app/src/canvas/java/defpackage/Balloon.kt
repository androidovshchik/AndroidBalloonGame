package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append(space: Point) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, space))
}

class Balloon(val id: Long, space: Point) {

    val textureIndex = TEXTURES_RANGE.random()

    private val initialPartIndex = NORMAL_PARTS_RANGE.random()

    val position = RectB((0..space.x).random() - MAX_WIDTH / 2, space.y)

    var tappedAt = 0L

    val hasBeenTapped
        get() = tappedAt > 0L

    fun getPartIndex(time: Long): Int {
        if (!hasBeenTapped) {
            return initialPartIndex
        }
        val interval = time - tappedAt
        if (interval in 0..BURST_TIME) {
            POP_PARTS_RANGE.apply {
                forEachIndexed { i, value ->
                    if (interval <= BURST_TIME * (i + 1) / size) {
                        return value
                    }
                }
            }
        }
        return -1
    }

    companion object {

        const val MAX_WIDTH = 190

        @JvmStatic
        val TEXTURES_RANGE = 0..4

        @JvmStatic
        val NORMAL_PARTS_RANGE = 0..6

        @JvmStatic
        val POP_PARTS_RANGE = (7..10).toList()

        const val BURST_TIME = 200L
    }
}