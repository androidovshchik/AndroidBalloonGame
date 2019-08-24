package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat
import java.io.BufferedReader

abstract class BaseManager {

    abstract fun onInit(context: Context)

    abstract fun onDraw(output: Bitmap)

    abstract fun onSingleTap(x: Float, y: Float): Boolean

    abstract fun onDestroy()

    fun readColor(context: Context, name: String) = context.run {
        val id = resources.getIdentifier(name, "color", packageName)
        ContextCompat.getColor(applicationContext, id)
    }

    fun readBitmap(context: Context, name: String): Bitmap = context.run {
        val id = resources.getIdentifier(name, "drawable", packageName)
        BitmapFactory.decodeResource(resources, id)
    }

    fun readText(context: Context, name: String) = context.run {
        val id = resources.getIdentifier(name, "raw", packageName)
        resources.openRawResource(id)
            .bufferedReader()
            .use(BufferedReader::readText)
    }
}