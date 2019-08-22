package defpackage

import android.app.Application

class MainApp : Application() {

    //external fun cdraw(image: Bitmap)

    //external fun cdraw(image: Bitmap)

    companion object {

        init {
            System.loadLibrary("viewer")
            System.loadLibrary("main")
        }
    }
}
