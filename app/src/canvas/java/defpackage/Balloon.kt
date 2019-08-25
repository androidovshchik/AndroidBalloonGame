package defpackage

import android.graphics.Canvas
import androidovshchik.jerrygame.BuildConfig
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

    fun move(canvas: Canvas, time: Long): Boolean {
        val lifetime = time - createdAt
        if (lifetime in 0..BuildConfig.INTERVAL) {
            position.apply {
                if (rect.left == Int.MIN_VALUE) {
                    rect.left = (0..window.x).random() - MAX_WIDTH / 2
                }

                BuildConfig.INTERVAL
                if (!position.hasZeroPoint) {
                    position.rect.apply {
                        left = (0..window.x).random() - MAX_WIDTH / 2
                        top = window.y
                    }
                }
                position.moveY()
            }
            return true
        }
        return false
    }

    /**
     * @return current part of texture or -1
     */
    fun animate(time: Long): Int {
        return if (hasBeenTapped) {
            burst(time)
        } else {
            swing(time)
        }
    }

    private fun swing(time: Long): Int {
        return getIndex((time - createdAt) % SWING_INTERVAL, SWING_INTERVAL, SWING_INDICES)
    }

    private fun burst(time: Long): Int {
        return getIndex(time - tappedAt, BURST_INTERVAL, BURST_INDICES)
    }

    private fun getIndex(interval: Long, max: Long, indices: List<Int>): Int {
        if (interval in 0..max) {
            return indices[0] + (interval * indices.size / max).toInt()
        }
        return -1
    }

    companion object {

        const val MIN_WIDTH = 170

        const val MAX_WIDTH = 188

        const val MAX_HEIGHT = 213

        @JvmStatic
        val TEXTURES_RANGE = 0..4

        @JvmStatic
        val SWING_INDICES = (0..6).toList()

        @JvmStatic
        val BURST_INDICES = (7..10).toList()

        const val SWING_INTERVAL = 350L

        const val BURST_INTERVAL = 200L
    }
}