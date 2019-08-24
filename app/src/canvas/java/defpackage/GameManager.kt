package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : BaseManager() {

    private val canvas = Canvas()

    private val textures = arrayListOf<TexturePack>()

    private val balloons = LinkedList<Balloon>()

    var startedAt = 0L

    var renderedAt = 0L

    init {
        onInit(context)
    }

    override fun onInit(context: Context) {
        arrayOf(
            "blue_balloon",
            "green_balloon",
            "red_balloon",
            "pink_balloon",
            "yellow_balloon"
        ).forEach { name ->
            readBitmap(name)?.let { bitmap ->
                synchronized(this) {
                    textures.add(TexturePack(bitmap))
                }
                readText(name)?.let { text ->
                    Scanner(text).use {
                        it.useDelimiter("\\s*fish\\s*")
                        while (it.hasNext()) {
                            val line = it.nextLine()
                            synchronized(this) {
                                textures.last().parts.applyLast(0)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRender(output: Bitmap) {
        if (output.isRecycled) {
            return
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvas.setBitmap(output)
        synchronized(this) {
            renderedAt = System.currentTimeMillis()
            val iterator = balloons.iterator()
            while (iterator.hasNext()) {
                val balloon = iterator.next()
                textures.getOrNull(balloon.textureIndex)?.run {
                    if (!drawBalloon(canvas, renderedAt, balloon)) {
                        iterator.remove()
                    }
                } ?: iterator.remove()
            }
        }
    }

    @Synchronized
    override fun onSingleTap(x: Float, y: Float) {
        balloons.forEachReversedByIndex {
            if (!it.hasBeenTapped) {
                if (it.position.hasPoint(x, y)) {
                    it.tappedAt = System.currentTimeMillis()
                    return
                }
            }
        }
    }

    @Synchronized
    override fun onDestroy() {
        textures.forEach {
            it.release()
        }
    }
}