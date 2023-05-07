package dev.rostopira.rwgfy

import androidx.annotation.DrawableRes
import androidx.wear.tiles.LayoutElementBuilders

enum class DrawableFont(
    val id: String,
    val char: Char,
    @DrawableRes val resId: Int,
    val aspect: Float,
) {
    digit_0("digit_0", '0', R.drawable.digit_0,27.03f / 48),
    digit_1("digit_1", '1', R.drawable.digit_1,26.67f / 48),
    digit_2("digit_2", '2', R.drawable.digit_2,27.03f / 48),
    digit_3("digit_3", '3', R.drawable.digit_3,24.47f / 48),
    digit_4("digit_4", '4', R.drawable.digit_4,27.03f / 48),
    digit_5("digit_5", '5', R.drawable.digit_5,27.03f / 48),
    digit_6("digit_6", '6', R.drawable.digit_6,27.03f / 48),
    digit_7("digit_7", '7', R.drawable.digit_7,24.34f / 48),
    digit_8("digit_8", '8', R.drawable.digit_8,27.03f / 48),
    digit_9("digit_9", '9', R.drawable.digit_9,27.03f / 48),
    plus_sign("plus_sign", '+', R.drawable.plus_sign,16.31f / 48);

    fun asImage(size: Int) = image {
        setResourceId(id)
        setHeight(size.dp)
        setWidth((size * (aspect + .1f)).dp) // +10% padding
        setContentScaleMode(LayoutElementBuilders.CONTENT_SCALE_MODE_FIT)
    }

    companion object {
        fun byChar(char: Char) =
            values().firstOrNull { it.char == char }
    }

}