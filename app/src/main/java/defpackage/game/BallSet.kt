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

    fun release() {
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
    }
}