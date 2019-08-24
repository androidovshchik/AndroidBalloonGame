package defpackage

import java.util.*

fun LinkedList<Balloon>.append(x: Int) {
    add(Balloon().apply {
        rect.left = x
    })
}

class Balloon(val id: Long, val position: RectB) {

    val index = (0..4).random() * 10 + (0..6).random()

    val textureIndex
        get() = index / 10

    val rectIndex
        get() = index % 10
}