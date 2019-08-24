package defpackage

import android.app.ActivityManager
import android.app.Service
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.RawResourceDataSource

@Suppress("DEPRECATION")
inline fun <reified T : Service> ActivityManager.isRunning(): Boolean {
    for (service in getRunningServices(Integer.MAX_VALUE)) {
        if (T::class.java.name == service.service.className) {
            return true
        }
    }
    return false
}

class MusicService : Service() {

    private lateinit var exoPlayer: SimpleExoPlayer

    private lateinit var rawSoundFactory: ProgressiveMediaSource.Factory

    private val binder = Binder()

    override fun onCreate() {
        super.onCreate()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(applicationContext)
        rawSoundFactory = ProgressiveMediaSource.Factory(DataSource.Factory {
            RawResourceDataSource(applicationContext)
        })
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        return true
    }

    fun playSound(name: String) {
        exoPlayer.apply {
            prepare(
                rawSoundFactory.createMediaSource(
                    Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://$packageName/raw/$name")
                )
            )
            playWhenReady = true
        }
    }

    fun stopPlay() {
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        stopPlay()
        exoPlayer.apply {
            stop()
            release()
        }
        super.onDestroy()
    }

    @Suppress("unused")
    inner class Binder : android.os.Binder() {

        val service: MusicService
            get() = this@MusicService
    }
}
