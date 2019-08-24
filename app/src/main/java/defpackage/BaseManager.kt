package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import org.jetbrains.anko.windowManager
import java.io.BufferedReader
import java.lang.ref.WeakReference

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseManager(context: Context) {

    var reference = WeakReference(context)

    abstract fun onRender(output: Bitmap)

    abstract fun onSingleTap(x: Float, y: Float)

    abstract fun onDestroy()

    val windowSize: Point?
        get() = reference.get()?.run {
            val size = Point()
            windowManager.defaultDisplay.getSize(size)
            return size
        }

    fun readBitmap(name: String) = reference.get()?.run {
        val id = resources.getIdentifier(name, "drawable", packageName)
        BitmapFactory.decodeResource(resources, id)
    }

    fun readText(name: String) = reference.get()?.run {
        val id = resources.getIdentifier(name, "raw", packageName)
        resources.openRawResource(id)
            .bufferedReader()
            .use(BufferedReader::readText)
    }
}