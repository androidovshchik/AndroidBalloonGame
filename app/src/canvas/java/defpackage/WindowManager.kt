/*
 * Copyright (c) 2019. Vlad Kalyuzhnyu <vladkalyuzhnyu@gmail.com>
 */

@file:Suppress("unused")

package defpackage

import android.graphics.Point
import android.view.WindowManager

val WindowManager.windowSize: Point
    get() {
        val size = Point()
        defaultDisplay.getSize(size)
        return size
    }

val WindowManager.screenSize: Point
    get() {
        val size = Point()
        defaultDisplay.getRealSize(size)
        return size
    }