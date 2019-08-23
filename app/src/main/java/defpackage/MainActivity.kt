package defpackage

import android.os.Bundle
import android.view.ViewManager
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

fun ViewManager.gameSurfaceView(theme: Int = 0) = gameSurfaceView(theme) {}

inline fun ViewManager.gameSurfaceView(theme: Int = 0, init: GameSurfaceView.() -> Unit) =
    ankoView({ GameSurfaceView(it) }, theme, init)

class MainActivity : AppCompatActivity() {

    private lateinit var view: GameSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout {
            lparams(matchParent, matchParent)
            view = gameSurfaceView().lparams(matchParent, matchParent)
        }
    }

    override fun onDestroy() {
        view.release()
        super.onDestroy()
    }
}