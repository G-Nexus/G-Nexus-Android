package com.gnexus.app.domain.model

import com.gnexus.app.domain.model.GameFeature.ADAPTIVE_TRIGGERS
import com.gnexus.app.domain.model.GameFeature.HAPTIC_FEEDBACK
import com.gnexus.app.domain.model.GameFeature.PS5_PRO_ENHANCED

/**
 * 游戏平台
 * @property PlayStation
 * @property Xbox
 * @property Steam
 * @property Epic
 * @property NintendoSwitch
 */
sealed class Platform {
    data class PlayStation(val version: PSVersion, val features: List<GameFeature>) : Platform()
    data class Xbox(val version: XboxVersion, val features: List<GameFeature>) : Platform()
    object Steam : Platform()
    object Epic : Platform()
    object NintendoSwitch : Platform()
}

/**
 * 游戏特性
 * @property PS5_PRO_ENHANCED Pro 增强
 * @property HAPTIC_FEEDBACK 触感反馈
 * @property ADAPTIVE_TRIGGERS 自适应扳机
 * @property RAY_TRACING 光追
 */
enum class GameFeature {
    PS5_PRO_ENHANCED,
    HAPTIC_FEEDBACK,
    ADAPTIVE_TRIGGERS,
    RAY_TRACING
}

/**
 * PlayStation Version
 * @property PS5
 * @property PS4
 * @property PS3
 */
enum class PSVersion {
    PS5,
    PS4,
    PS3,
}

/**
 * Xbox Version
 * @property Xbox360
 * @property XboxOne
 * @property XSX_XSS
 */
enum class XboxVersion {
    Xbox360,
    XboxOne,
    XSX_XSS,
}