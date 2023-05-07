package dev.rostopira.rwgfy

import androidx.annotation.DrawableRes
import androidx.wear.tiles.ResourceBuilders.*

fun Resources.Builder.addDrawable(id: String, @DrawableRes drawable: Int): Resources.Builder {
    val res = AndroidImageResourceByResId.Builder().setResourceId(drawable).build()
    val image = ImageResource.Builder().setAndroidResourceByResId(res).build()
    return addIdToImageMapping(id, image)
}