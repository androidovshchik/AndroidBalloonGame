@file:Suppress("unused")

package defpackage

import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

fun ViewManager.gameSurfaceView(theme: Int = 0) = gameSurfaceView(theme) {}

inline fun ViewManager.gameSurfaceView(theme: Int = 0, init: GameSurfaceView.() -> Unit) =
    ankoView({ GameSurfaceView(it) }, theme, init)
