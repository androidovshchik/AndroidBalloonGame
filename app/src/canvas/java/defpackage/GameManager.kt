package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidovshchik.jerrygame.BuildConfig
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : BaseManager() {

    private val canvas = Canvas()

    private val textures = arrayListOf<TexturePack>()

    private val balloons = LinkedList<Balloon>()

    private val toolbar = Toolbar(context)

    private val colorPrimary = readColor(context, "colorPrimary")

    private var lastDrawAt = 0L

    init {
        onInit(context)
    }

    @Synchronized
    override fun onInit(context: Context) {
        arrayOf(
            "blue_balloon",
            "green_balloon",
            "red_balloon",
            "pink_balloon",
            "yellow_balloon"
        ).forEach { name ->
            textures.add(TexturePack(readBitmap(context, name)).apply {
                readText(context, name).lines().forEach { line ->
                    if (PATTERN.matches(line)) {
                        line.split(":").getOrNull(1)?.let {
                            parts.applyLast(it.trim().toInt())
                        }
                    }
                }
            })
        }
    }

    override fun onDraw(output: Bitmap) {
        if (output.isRecycled) {
            return
        }
        canvas.setBitmap(output)
        canvas.drawColor(colorPrimary)
        synchronized(this) {
            if ((1..100).random() <= BuildConfig.CHANCE) {
                balloons.append(canvas)
            }
            val now = System.currentTimeMillis()
            val delay = if (lastDrawAt > 0) {
                now - lastDrawAt
            } else 0
            lastDrawAt = now
            val iterator = balloons.iterator()
            while (iterator.hasNext()) {
                iterator.next().run {
                    textures.getOrNull(texture)?.run {
                        if (!bitmap.isRecycled) {
                            parts.getOrNull(animate(now))?.let {
                                position.changeSize(it.rect)
                                if (move(delay)) {
                                    canvas.drawBitmap(bitmap, it.rect, position.rect, null)
                                } else {
                                    iterator.remove()
                                }
                            } ?: iterator.remove()
                        } else {
                            iterator.remove()
                        }
                    } ?: iterator.remove()
                }
            }
        }
        if (BuildConfig.DEBUG) {
            toolbar.onDraw(canvas)
        }
    }

    @Synchronized
    override fun onSingleTap(x: Float, y: Float): Boolean {
        balloons.forEachReversedByIndex {
            if (!it.hasBeenTapped) {
                if (it.position.hasPoint(x, y)) {
                    it.tappedAt = System.currentTimeMillis()
                    return true
                }
            }
        }
        return false
    }

    @Synchronized
    override fun onDestroy() {
        textures.forEach {
            it.release()
        }
    }

    companion object {

        @JvmStatic
        val PATTERN = "(x|y|width|height): ".toRegex()
    }
}