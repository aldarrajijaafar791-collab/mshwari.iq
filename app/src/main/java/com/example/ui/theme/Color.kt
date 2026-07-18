package com.example.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

enum class AppThemePreset(val label: String) {
    ELEGANT_DARK("الأنيق الداكن"),
    GOLDEN_DESERT("الذهبي الصحراوي"),
    IRAQI_PALM("النخيل العراقي"),
    CLASSIC_LIGHT("الكلاسيكي الفاتح")
}

object MeshwarThemeState {
    var currentTheme by mutableStateOf(AppThemePreset.GOLDEN_DESERT)
}

// 1. Elegant Dark Theme Palette
private object ElegantDark {
    val Surface = Color(0xFF1C1B1F)
    val SurfaceDim = Color(0xFF141316)
    val SurfaceBright = Color(0xFF2C2A2F)
    val SurfaceContainerLowest = Color(0xFF2D2933)
    val SurfaceContainerLow = Color(0xFF252129)
    val SurfaceContainer = Color(0xFF2B2730)
    val SurfaceContainerHigh = Color(0xFF35303C)
    val SurfaceContainerHighest = Color(0xFF3D3846)
    val OnSurface = Color(0xFFE6E1E5)
    val OnSurfaceVariant = Color(0xFFCAC4D0)
    val InverseSurface = Color(0xFFE6E1E5)
    val InverseOnSurface = Color(0xFF313033)
    val Outline = Color(0xFF938F99)
    val OutlineVariant = Color(0xFF49454F)
    val SurfaceTint = Color(0xFFD0BCFF)

    val Primary = Color(0xFFD0BCFF)
    val OnPrimary = Color(0xFF381E72)
    val PrimaryContainer = Color(0xFFEADDFF)
    val OnPrimaryContainer = Color(0xFF21005D)
    val InversePrimary = Color(0xFF381E72)

    val Secondary = Color(0xFF938F99)
    val OnSecondary = Color(0xFF313033)
    val SecondaryContainer = Color(0xFF49454F)
    val OnSecondaryContainer = Color(0xFFE6E1E5)

    val Tertiary = Color(0xFFEFB8C8)
    val OnTertiary = Color(0xFF492532)
    val TertiaryContainer = Color(0xFF633B48)
    val OnTertiaryContainer = Color(0xFFFFD8E4)

    val Error = Color(0xFFF2B8B5)
    val OnError = Color(0xFF601410)
    val ErrorContainer = Color(0xFF8C1D18)
    val OnErrorContainer = Color(0xFFFFDAD6)

    val Background = Color(0xFF1C1B1F)
    val OnBackground = Color(0xFFE6E1E5)
    val SurfaceVariant = Color(0xFF49454F)

    val PrimaryFixed = Color(0xFFEADDFF)
    val PrimaryFixedDim = Color(0xFFD0BCFF)
    val OnPrimaryFixed = Color(0xFF21005D)
    val OnPrimaryFixedVariant = Color(0xFF4F378B)

    val SecondaryFixed = Color(0xFFE8DEF8)
    val SecondaryFixedDim = Color(0xFFCCC2DC)
    val OnSecondaryFixed = Color(0xFF1D192B)
    val OnSecondaryFixedVariant = Color(0xFF4A4458)

    val TertiaryFixed = Color(0xFFFFD8E4)
    val TertiaryFixedDim = Color(0xFFEFB8C8)
    val OnTertiaryFixed = Color(0xFF31111D)
    val OnTertiaryFixedVariant = Color(0xFF633B48)
}

// 2. Golden Desert Theme Palette
private object GoldenDesert {
    val Surface = Color(0xFF1D1B18)
    val SurfaceDim = Color(0xFF141310)
    val SurfaceBright = Color(0xFF2E2923)
    val SurfaceContainerLowest = Color(0xFF25211B)
    val SurfaceContainerLow = Color(0xFF2B2620)
    val SurfaceContainer = Color(0xFF322C25)
    val SurfaceContainerHigh = Color(0xFF3D362E)
    val SurfaceContainerHighest = Color(0xFF494037)
    val OnSurface = Color(0xFFF7F0E8)
    val OnSurfaceVariant = Color(0xFFDDCFBF)
    val InverseSurface = Color(0xFFF7F0E8)
    val InverseOnSurface = Color(0xFF322C25)
    val Outline = Color(0xFFA5937F)
    val OutlineVariant = Color(0xFF53483C)
    val SurfaceTint = Color(0xFFF5B041)

    val Primary = Color(0xFFF5B041)
    val OnPrimary = Color(0xFF452B00)
    val PrimaryContainer = Color(0xFFFFF1CC)
    val OnPrimaryContainer = Color(0xFF5E3F00)
    val InversePrimary = Color(0xFF452B00)

    val Secondary = Color(0xFFE59866)
    val OnSecondary = Color(0xFF2E1C0C)
    val SecondaryContainer = Color(0xFFFADBD8)
    val OnSecondaryContainer = Color(0xFF78281F)

    val Tertiary = Color(0xFFF39C12)
    val OnTertiary = Color(0xFF4A2700)
    val TertiaryContainer = Color(0xFFFCF3CF)
    val OnTertiaryContainer = Color(0xFF5D4037)

    val Error = Color(0xFFE74C3C)
    val OnError = Color(0xFFFFFFFF)
    val ErrorContainer = Color(0xFFFADBD8)
    val OnErrorContainer = Color(0xFF78281F)

    val Background = Color(0xFF1D1B18)
    val OnBackground = Color(0xFFF7F0E8)
    val SurfaceVariant = Color(0xFF53483C)

    val PrimaryFixed = Color(0xFFFDEBD0)
    val PrimaryFixedDim = Color(0xFFF5B041)
    val OnPrimaryFixed = Color(0xFF5E3F00)
    val OnPrimaryFixedVariant = Color(0xFF452B00)

    val SecondaryFixed = Color(0xFFFADBD8)
    val SecondaryFixedDim = Color(0xFFE59866)
    val OnSecondaryFixed = Color(0xFF78281F)
    val OnSecondaryFixedVariant = Color(0xFF2E1C0C)

    val TertiaryFixed = Color(0xFFFCF3CF)
    val TertiaryFixedDim = Color(0xFFF39C12)
    val OnTertiaryFixed = Color(0xFF5D4037)
    val OnTertiaryFixedVariant = Color(0xFF4A2700)
}

// 3. Iraqi Palm Theme Palette
private object IraqiPalm {
    val Surface = Color(0xFF151D16)
    val SurfaceDim = Color(0xFF0F1510)
    val SurfaceBright = Color(0xFF212C23)
    val SurfaceContainerLowest = Color(0xFF1A231C)
    val SurfaceContainerLow = Color(0xFF222F25)
    val SurfaceContainer = Color(0xFF2B3B2E)
    val SurfaceContainerHigh = Color(0xFF354838)
    val SurfaceContainerHighest = Color(0xFF415744)
    val OnSurface = Color(0xFFEDF7EE)
    val OnSurfaceVariant = Color(0xFFC3DDC5)
    val InverseSurface = Color(0xFFEDF7EE)
    val InverseOnSurface = Color(0xFF2B3B2E)
    val Outline = Color(0xFF86A588)
    val OutlineVariant = Color(0xFF415744)
    val SurfaceTint = Color(0xFF81C784)

    val Primary = Color(0xFF81C784)
    val OnPrimary = Color(0xFF0A330C)
    val PrimaryContainer = Color(0xFFC8E6C9)
    val OnPrimaryContainer = Color(0xFF0A330C)
    val InversePrimary = Color(0xFF0A330C)

    val Secondary = Color(0xFF4CAF50)
    val OnSecondary = Color(0xFFFFFFFF)
    val SecondaryContainer = Color(0xFFE8F5E9)
    val OnSecondaryContainer = Color(0xFF1B5E20)

    val Tertiary = Color(0xFF009688)
    val OnTertiary = Color(0xFFFFFFFF)
    val TertiaryContainer = Color(0xFFE0F2F1)
    val OnTertiaryContainer = Color(0xFF004D40)

    val Error = Color(0xFFEF5350)
    val OnError = Color(0xFFFFFFFF)
    val ErrorContainer = Color(0xFFFFEBEE)
    val OnErrorContainer = Color(0xFFC62828)

    val Background = Color(0xFF151D16)
    val OnBackground = Color(0xFFEDF7EE)
    val SurfaceVariant = Color(0xFF415744)

    val PrimaryFixed = Color(0xFFC8E6C9)
    val PrimaryFixedDim = Color(0xFF81C784)
    val OnPrimaryFixed = Color(0xFF0A330C)
    val OnPrimaryFixedVariant = Color(0xFF1B5E20)

    val SecondaryFixed = Color(0xFFE8F5E9)
    val SecondaryFixedDim = Color(0xFF4CAF50)
    val OnSecondaryFixed = Color(0xFF1B5E20)
    val OnSecondaryFixedVariant = Color(0xFF004D40)

    val TertiaryFixed = Color(0xFFE0F2F1)
    val TertiaryFixedDim = Color(0xFF009688)
    val OnTertiaryFixed = Color(0xFF004D40)
    val OnTertiaryFixedVariant = Color(0xFF004D40)
}

// 4. Classic Light Theme Palette
private object ClassicLight {
    val Surface = Color(0xFFF8F9FF)
    val SurfaceDim = Color(0xFFD6DBE9)
    val SurfaceBright = Color(0xFFF8F9FF)
    val SurfaceContainerLowest = Color(0xFFFFFFFF)
    val SurfaceContainerLow = Color(0xFFEFF3FD)
    val SurfaceContainer = Color(0xFFE5ECF9)
    val SurfaceContainerHigh = Color(0xFFDAE2F2)
    val SurfaceContainerHighest = Color(0xFFCCD5E6)
    val OnSurface = Color(0xFF1C2230)
    val OnSurfaceVariant = Color(0xFF495264)
    val InverseSurface = Color(0xFF1C2230)
    val InverseOnSurface = Color(0xFFF8F9FF)
    val Outline = Color(0xFF758195)
    val OutlineVariant = Color(0xFFCCD5E6)
    val SurfaceTint = Color(0xFF1F618D)

    val Primary = Color(0xFF1F618D)
    val OnPrimary = Color(0xFFFFFFFF)
    val PrimaryContainer = Color(0xFFEBF5FB)
    val OnPrimaryContainer = Color(0xFF1B4F72)
    val InversePrimary = Color(0xFFD4E6F1)

    val Secondary = Color(0xFF5499C7)
    val OnSecondary = Color(0xFFFFFFFF)
    val SecondaryContainer = Color(0xFFEBF5FB)
    val OnSecondaryContainer = Color(0xFF1F618D)

    val Tertiary = Color(0xFFE67E22)
    val OnTertiary = Color(0xFFFFFFFF)
    val TertiaryContainer = Color(0xFFFDF2E9)
    val OnTertiaryContainer = Color(0xFF6E2C00)

    val Error = Color(0xFFC0392B)
    val OnError = Color(0xFFFFFFFF)
    val ErrorContainer = Color(0xFFFDEDEC)
    val OnErrorContainer = Color(0xFF78281F)

    val Background = Color(0xFFF8F9FF)
    val OnBackground = Color(0xFF1C2230)
    val SurfaceVariant = Color(0xFFDAE2F2)

    val PrimaryFixed = Color(0xFFEBF5FB)
    val PrimaryFixedDim = Color(0xFF1F618D)
    val OnPrimaryFixed = Color(0xFF1B4F72)
    val OnPrimaryFixedVariant = Color(0xFF1F618D)

    val SecondaryFixed = Color(0xFFEBF5FB)
    val SecondaryFixedDim = Color(0xFF5499C7)
    val OnSecondaryFixed = Color(0xFF1F618D)
    val OnSecondaryFixedVariant = Color(0xFF1B4F72)

    val TertiaryFixed = Color(0xFFFDF2E9)
    val TertiaryFixedDim = Color(0xFFE67E22)
    val OnTertiaryFixed = Color(0xFF6E2C00)
    val OnTertiaryFixedVariant = Color(0xFFE67E22)
}

val SurfaceColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Surface
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Surface
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Surface
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Surface
    }

val SurfaceDimColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceDim
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceDim
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceDim
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceDim
    }

val SurfaceBrightColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceBright
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceBright
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceBright
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceBright
    }

val SurfaceContainerLowestColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceContainerLowest
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceContainerLowest
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceContainerLowest
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceContainerLowest
    }

val SurfaceContainerLowColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceContainerLow
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceContainerLow
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceContainerLow
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceContainerLow
    }

val SurfaceContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceContainer
    }

val SurfaceContainerHighColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceContainerHigh
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceContainerHigh
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceContainerHigh
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceContainerHigh
    }

val SurfaceContainerHighestColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceContainerHighest
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceContainerHighest
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceContainerHighest
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceContainerHighest
    }

val OnSurfaceColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSurface
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSurface
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSurface
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSurface
    }

val OnSurfaceVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSurfaceVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSurfaceVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSurfaceVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSurfaceVariant
    }

val InverseSurfaceColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.InverseSurface
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.InverseSurface
        AppThemePreset.IRAQI_PALM -> IraqiPalm.InverseSurface
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.InverseSurface
    }

val InverseOnSurfaceColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.InverseOnSurface
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.InverseOnSurface
        AppThemePreset.IRAQI_PALM -> IraqiPalm.InverseOnSurface
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.InverseOnSurface
    }

val OutlineColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Outline
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Outline
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Outline
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Outline
    }

val OutlineVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OutlineVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OutlineVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OutlineVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OutlineVariant
    }

val SurfaceTintColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceTint
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceTint
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceTint
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceTint
    }

val PrimaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Primary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Primary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Primary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Primary
    }

val OnPrimaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnPrimary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnPrimary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnPrimary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnPrimary
    }

val PrimaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.PrimaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.PrimaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.PrimaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.PrimaryContainer
    }

val OnPrimaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnPrimaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnPrimaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnPrimaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnPrimaryContainer
    }

val InversePrimaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.InversePrimary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.InversePrimary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.InversePrimary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.InversePrimary
    }

val SecondaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Secondary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Secondary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Secondary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Secondary
    }

val OnSecondaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSecondary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSecondary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSecondary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSecondary
    }

val SecondaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SecondaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SecondaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SecondaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SecondaryContainer
    }

val OnSecondaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSecondaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSecondaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSecondaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSecondaryContainer
    }

val TertiaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Tertiary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Tertiary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Tertiary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Tertiary
    }

val OnTertiaryColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnTertiary
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnTertiary
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnTertiary
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnTertiary
    }

val TertiaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.TertiaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.TertiaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.TertiaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.TertiaryContainer
    }

val OnTertiaryContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnTertiaryContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnTertiaryContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnTertiaryContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnTertiaryContainer
    }

val ErrorColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Error
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Error
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Error
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Error
    }

val OnErrorColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnError
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnError
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnError
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnError
    }

val ErrorContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.ErrorContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.ErrorContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.ErrorContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.ErrorContainer
    }

val OnErrorContainerColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnErrorContainer
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnErrorContainer
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnErrorContainer
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnErrorContainer
    }

val PrimaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.PrimaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.PrimaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.PrimaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.PrimaryFixed
    }

val PrimaryFixedDimColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.PrimaryFixedDim
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.PrimaryFixedDim
        AppThemePreset.IRAQI_PALM -> IraqiPalm.PrimaryFixedDim
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.PrimaryFixedDim
    }

val OnPrimaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnPrimaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnPrimaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnPrimaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnPrimaryFixed
    }

val OnPrimaryFixedVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnPrimaryFixedVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnPrimaryFixedVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnPrimaryFixedVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnPrimaryFixedVariant
    }

val SecondaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SecondaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SecondaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SecondaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SecondaryFixed
    }

val SecondaryFixedDimColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SecondaryFixedDim
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SecondaryFixedDim
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SecondaryFixedDim
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SecondaryFixedDim
    }

val OnSecondaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSecondaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSecondaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSecondaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSecondaryFixed
    }

val OnSecondaryFixedVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnSecondaryFixedVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnSecondaryFixedVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnSecondaryFixedVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnSecondaryFixedVariant
    }

val TertiaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.TertiaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.TertiaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.TertiaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.TertiaryFixed
    }

val TertiaryFixedDimColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.TertiaryFixedDim
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.TertiaryFixedDim
        AppThemePreset.IRAQI_PALM -> IraqiPalm.TertiaryFixedDim
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.TertiaryFixedDim
    }

val OnTertiaryFixedColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnTertiaryFixed
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnTertiaryFixed
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnTertiaryFixed
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnTertiaryFixed
    }

val OnTertiaryFixedVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnTertiaryFixedVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnTertiaryFixedVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnTertiaryFixedVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnTertiaryFixedVariant
    }

val BackgroundColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.Background
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.Background
        AppThemePreset.IRAQI_PALM -> IraqiPalm.Background
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.Background
    }

val OnBackgroundColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.OnBackground
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.OnBackground
        AppThemePreset.IRAQI_PALM -> IraqiPalm.OnBackground
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.OnBackground
    }

val SurfaceVariantColor: Color
    get() = when (MeshwarThemeState.currentTheme) {
        AppThemePreset.ELEGANT_DARK -> ElegantDark.SurfaceVariant
        AppThemePreset.GOLDEN_DESERT -> GoldenDesert.SurfaceVariant
        AppThemePreset.IRAQI_PALM -> IraqiPalm.SurfaceVariant
        AppThemePreset.CLASSIC_LIGHT -> ClassicLight.SurfaceVariant
    }


