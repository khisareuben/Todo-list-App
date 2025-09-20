package android.khisareuben.todolist.presentation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomTheme(
    val backgroundColor: Color,
    val buttonColor: Color,
    val filterChip: Color,
    val textColor: Color,
    val filterChipText: Color,
)

val lightThemeColors = CustomTheme(
    backgroundColor = Color(0xFFFFFFFF),
    buttonColor = Color(0xFFFFAB00),
    filterChip = Color(0xFFECE6DA),
    textColor = Color(0xFF050505),
    filterChipText = Color(0xFF050505)


)

val darkThemColors = CustomTheme(
    backgroundColor = Color(0xFF050505),
    buttonColor = Color(0xFFFFAB00),
    filterChip = Color(0xFF312F2F),
    textColor = Color(0xFFFFFFFF),
    filterChipText = Color(0xFF050505)
)

val LocalThemeColor = staticCompositionLocalOf <CustomTheme>{
    error("No theme provided")
}
