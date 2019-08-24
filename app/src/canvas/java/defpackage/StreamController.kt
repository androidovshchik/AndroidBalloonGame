package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
class StreamController(context: Context) : BaseManager(context) {

    private val canvas = Canvas()

    private val textures = arrayListOf<TexturePack>()

    private val balloons = LinkedList<Balloon>()

    var lastRenderAt = 0L

    init {
        arrayOf(
            "blue_balloon",
            "green_balloon",
            "red_balloon",
            "pink_balloon",
            "yellow_balloon"
        ).forEach { name ->
            readBitmap(name)?.let { bitmap ->
                textures.add(TexturePack(bitmap))
                readText(name)?.let { text ->
                    Scanner(text).use {
                        it.useDelimiter("\\s*fish\\s*")
                        while (it.hasNext()) {
                            val line = it.nextLine()
                            textures.last().parts.applyLast(0)
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
        canvas.setBitmap(output)
        balloons.forEach {
            textures.getOrNull(it.textureIndex)?.run {
                if (!it.position.hasSize) {
                    it.position
                }
                drawBalloon(canvas, it)
            }
        }
    }

    override fun onSingleTap(x: Float, y: Float) {
        balloons.forEachReversedByIndex {
            if (it.position.hasPoint(x, y)) {
                it.tappedAt = System.currentTimeMillis()
            }
        }
    }

    override fun onDestroy() {
        textures.forEach {
            it.release()
        }
    }
}