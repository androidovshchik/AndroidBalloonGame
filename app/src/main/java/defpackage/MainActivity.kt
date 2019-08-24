package defpackage

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity() {

    private lateinit var view: GameSurfaceView

    private var service: MusicService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            lparams(matchParent, matchParent)
            view = gameSurfaceView().lparams(matchParent, matchParent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (!activityManager.isRunning<MusicService>()) {
            startService(intentFor<MusicService>())
        }
        bindService(intentFor<MusicService>(), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        service?.apply {
            stopPlay()
            unbindService(connection)
            service = null
        }
        super.onStop()
    }

    override fun onDestroy() {
        view.release()
        super.onDestroy()
    }

    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }

        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as MusicService.Binder).service
        }
    }
}