package defpackage

import android.app.Application
import android.graphics.Bitmap
import androidovshchik.jerrygame.BuildConfig
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    external fun cdraw(image: Bitmap)

    @Suppress("ConstantConditionIf")
    companion object {

        init {
            if (BuildConfig.FLAVOR == "skia") {
                System.loadLibrary("viewer")
                System.loadLibrary("main")
            }
        }
    }
}
