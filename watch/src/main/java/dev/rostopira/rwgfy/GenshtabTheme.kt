package dev.rostopira.rwgfy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import dev.rostopira.rwgfy.shared.R as S

@Immutable
data class CustomTypography(
    val title: TextStyle,
    val total: TextStyle,
    val increase: TextStyle
)

val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        title = TextStyle.Default,
        total = TextStyle.Default,
        increase = TextStyle.Default,
    )
}

@Composable
fun GenshtabTheme(content: @Composable () -> Unit) {
    val res = LocalContext.current.resources
    @Suppress("DEPRECATION")
    val typography = CustomTypography(
        title = TextStyle(
            color = Color(res.getColor(S.color.genshtab_yellow)),
            fontFamily = FontFamily(Font(S.font.open_24_display_st)),
            fontSize = 18f.sp,
        ),
        total = TextStyle(
            color = Color(res.getColor(S.color.genshtab_yellow)),
            fontFamily = FontFamily(Font(S.font.open_24_display_st)),
            fontSize = 24f.sp,
        ),
        increase = TextStyle(
            color = Color(res.getColor(S.color.genshtab_yellow)),
            fontFamily = FontFamily(Font(S.font.open_24_display_st)),
            fontSize = 16f.sp,
        ),
    )
    CompositionLocalProvider(
        LocalCustomTypography provides typography,
        content = content,
    )
}

object CustomTheme {
    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}