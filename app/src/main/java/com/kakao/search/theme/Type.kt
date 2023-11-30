package com.kakao.search.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kakao.search.R

@Immutable
data class SearchTypography(
    private val colors: SearchColors,
    val baseTextStyle: TextStyle = TextStyle(
        color = colors.primary,
        fontFamily = SearchGrotesk
    ),
    val h1: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp
    ),
    val h2: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp
    ),
    val h3: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp
    ),
    val h4: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    val body1: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    val body2: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),

    val subtitle1: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    val subtitle2: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.sp
    ),
    val button: TextStyle = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 1.25.sp
    )
)

private val SearchGrotesk = FontFamily(
    Font(R.font.nanum_gothic_extra_bold, FontWeight.W700),
    Font(R.font.nanum_gothic_bold, FontWeight.W600),
    Font(R.font.nanum_gothic_light, FontWeight.W200),
    Font(R.font.nanum_gothic, FontWeight.W500),
)
