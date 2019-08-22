package defpackage

import android.app.Application
import android.graphics.Bitmap

class MainApp : Application() {

    external fun cdraw(image: Bitmap)

    external fun cdraw(image: Bitmap)

    companion object {

        init {
            System.loadLibrary("viewer")
            System.loadLibrary("main")
        }
    }
}
