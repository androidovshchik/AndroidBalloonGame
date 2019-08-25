package defpackage

import android.graphics.Canvas
import androidovshchik.jerrygame.BuildConfig
import java.util.*
import kotlin.math.roundToInt

fun LinkedList<Balloon>.append(canvas: Canvas) {
    val id = lastOrNull()?.id ?: 0L
    add(Balloon(id + 1, canvas.width, canvas.height))
}

class Balloon(val id: Long, w: Int, h: Int) {

    val texture = TEXTURES_RANGE.random()

    val position = RectB(((0..MAX_WIDTH - MIN_WIDTH).random()..w - MAX_WIDTH).random(), h)

    var createdAt = System.currentTimeMillis()

    var tappedAt = 0L

    val hasBeenTapped
        get() = tappedAt > 0L

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

    fun move(delay: Long): Boolean {
        position.apply {
            moveY((delay * BuildConfig.SPEED / 1000f).roundToInt())
            return rect.bottom <= 0
        }
    }

    private fun swing(time: Long): Int {
        return getIndexOf((time - createdAt) % SWING_INTERVAL, SWING_INTERVAL, SWING_INDICES)
    }

    private fun burst(time: Long): Int {
        return getIndexOf(time - tappedAt, BURST_INTERVAL, BURST_INDICES)
    }

    private fun getIndexOf(interval: Long, max: Long, indices: List<Int>): Int {
        if (interval in 0..max) {
            return indices[0] + (interval * indices.size / max).toInt()
        }
        return -1
    }

    companion object {

        const val MIN_WIDTH = 170

        const val MAX_WIDTH = 188

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