package defpackage.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

@Suppress("MemberVisibilityCanBePrivate")
class BallSet(context: Context, val name: String) {

    val bitmap: Bitmap = context.resources.run {
        BitmapFactory.decodeResource(this, getIdentifier(name, "drawable", context.packageName))
    }

    val rect = run {

        val id = it.resources.getIdentifier(name, "raw", it.packageName)
        it.resources.openRawResource(id)
    }

    private val ballSet = arrayListOf(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    init {
        context.resources.run {
            openRawResource(getIdentifier(name, "raw", context.packageName))
                .bufferedReader()
                .use {
                    var i = 0
                    while (true) {
                        val line = readLine() ?: break
                        if (BallRect.PATTERN.matches(line)) {
                            val field = javaClass.getField("foo")
                            field.set(this@BallRect, line.split(":")[1])
                        }
                    }
                }
        }
    }

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }

    companion object {

        @JvmStatic
        val PATTERN = "(x|y|width|height): ".toRegex()
    }
}