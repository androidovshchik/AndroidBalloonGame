package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import org.jetbrains.anko.collections.forEachReversedByIndex
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : BaseManager(context) {

    private val canvas = Canvas()

    private val textures = arrayListOf<TexturePack>()

    private val balloons = LinkedList<Balloon>()

    override fun onRender(output: Bitmap) {
        if (output.isRecycled) {
            return
        }
        canvas.setBitmap(output)
        balloons.forEach {
            textures[it.textureIndex].run {
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

    companion object {

        @JvmStatic
        val PATTERN = "(x|y|width|height): ".toRegex()
    }
}