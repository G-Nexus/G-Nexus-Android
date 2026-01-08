package com.gnexus.app.domain.model

data class TrophyGroup(
    val id: String,
    val name: String,         // 本体 / DLC 1 / DLC 2
    val coverUrl: String,
    val trophies: List<Trophy>
) {
    val totalCount: Int get() = trophies.size
    val earnedCount: Int get() = trophies.count { it.earnedTime != null }
    // 逻辑：本体组且全部达成，则视为获得白金（针对支持白金的游戏）
    val isPlatinumAchieved: Boolean get() = trophies.any { it.type == TrophyType.PLATINUM } && earnedCount == totalCount
}

data class Trophy(
    val title: String,
    val description: String,
    val coverUrl: String,
    val type: TrophyType,
    val rarity: Float,         // 达成率 (0.05 代表 5%)
    val earnedTime: Long?      // 获得时间，null 为未获得
)

enum class TrophyType { PLATINUM, GOLD, SILVER, BRONZE }