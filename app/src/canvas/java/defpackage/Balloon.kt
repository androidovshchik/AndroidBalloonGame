package defpackage

import android.graphics.Point
import java.util.*

fun LinkedList<Balloon>.append() {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1))
}

class Balloon(val id: Long) {

    val texture = TEXTURES_RANGE.random()

    val position = RectB()

    var createdAt = System.currentTimeMillis()

    var tappedAt = 0L

    val hasBeenTapped
        get() = tappedAt > 0L

    /**
     * @return current part of texture or -1
     */
    fun update(window: Point, time: Long): Int {
        return if (hasBeenTapped) {
            burst(time)
        } else {
            move(window, time)
            swing(time)
        }
    }

    private fun move(window: Point, time: Long) {
        if () {

            //(0..window.x).random() - MAX_WIDTH / 2, window.y
        }
        position.moveY()
    }

    private fun swing(time: Long): Int {
        return getIndex((time - createdAt) % SWING_TIME, SWING_TIME, SWING_INDEXES)
    }

    private fun burst(time: Long): Int {
        return getIndex(time - tappedAt, BURST_TIME, BURST_INDEXES)
    }

    private fun getIndex(interval: Long, maxInterval: Long, indexes: List<Int>): Int {
        if (interval in 0..maxInterval) {
            return indexes[0] + (interval * indexes.size / maxInterval).toInt()
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