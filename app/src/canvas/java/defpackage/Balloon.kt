package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append(window: Point) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, window))
}

class Balloon(val id: Long, window: Point) {

    val texture = TEXTURES_RANGE.random()

    val position = RectB((0..window.x).random() - MAX_WIDTH / 2, window.y)

    var createdAt = System.currentTimeMillis()

    var tappedAt = 0L

    val hasBeenTapped
        get() = tappedAt > 0L

    fun calculatePart(time: Long): Int {
        val indexes: List<Int>
        val maxTime: Long
        var interval: Long
        if (hasBeenTapped) {
            indexes = BURST_INDEXES
            maxTime = BURST_TIME
            interval = time - tappedAt
        } else {
            indexes = SWING_INDEXES
            maxTime = SWING_TIME
            interval = time - createdAt
            while (interval > SWING_TIME) {
                interval -= SWING_TIME
            }
        }
        if (interval in 0..maxTime) {
            indexes.apply {
                forEachIndexed { i, value ->
                    if (interval <= maxTime * (i + 1) / size) {
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
        val SWING_INDEXES = (0..6).toList()

        @JvmStatic
        val BURST_INDEXES = (7..10).toList()

        const val SWING_TIME = 350L

        const val BURST_TIME = 200L
    }
}