package defpackage

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.matchParent

tailrec fun Context.activity(): Activity? = when {
    this is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}

class MainActivity : AppCompatActivity() {

    private lateinit var surfaceView: GameSurfaceView

    private var service: SoundService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            lparams(matchParent, matchParent)
            surfaceView = gameSurfaceView().lparams(matchParent, matchParent)
        }
    }

    override fun onStart() {
        super.onStart()
        intentFor<SoundService>().let {
            if (!activityManager.isRunning<SoundService>()) {
                startService(it)
            }
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun playBalloonPop() {
        if (!isFinishing) {
            service?.playSound("balloon_pop")
        }
    }

    override fun onStop() {
        if (service != null) {
            unbindService(connection)
            service = null
        }
        super.onStop()
    }

    override fun onDestroy() {
        surfaceView.release()
        super.onDestroy()
    }

    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }

        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as SoundService.Binder).service
        }
    }
}