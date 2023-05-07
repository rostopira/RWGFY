package dev.rostopira.rwgfy

import android.content.Context
import java.lang.Float.max

fun <T> CharArray.mapNotNull(transform: (Char) -> T?) =
    map(transform).mapNotNull { it }

fun Context.screenSize(): Float {
    val displayMetrics = resources.displayMetrics
    val width = displayMetrics.widthPixels / displayMetrics.density
    val height = displayMetrics.heightPixels / displayMetrics.density
    return max(width, height)
}