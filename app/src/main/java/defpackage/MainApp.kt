package defpackage

import android.app.Application
import androidovshchik.jerrygame.BuildConfig
import timber.log.Timber

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
