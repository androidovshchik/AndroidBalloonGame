package defpackage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.content.res.Resources.NotFoundException

@Suppress("MemberVisibilityCanBePrivate")
class GameManager(context: Context) : GameLifecycle {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

    }

    private val ballSet = arrayListOf<BallSet>(
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet(),
        BallSet()
    )

    fun loadRawResource(id: Int): ByteArray? {
        try {
            val stream = context.getResources().openRawResource(id)
            try {
                val data = ByteArray(stream.available())
                stream.read(data, 0, data.size)
                return data
            } catch (e: IOException) {
                Log.e("mygame", e.toString())
            } finally {
                try {
                    stream.close()
                } catch (e: IOException) {
                    Log.e("mygame", e.toString())
                }

            }
            return null
        } catch (e: NotFoundException) {
            Log.e("mygame", "raw resource not found.")
            return null
        }

    }

    external override fun onRender(output: Bitmap)

    external override fun onSingleTap(x: Float, y: Float)

    external override fun onDestroy()

    companion object {

        init {
            System.loadLibrary("viewer")
            System.loadLibrary("main")
        }
    }
}