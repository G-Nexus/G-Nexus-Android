package com.gnexus.app.domain.model

data class MetaGame(
    val id: String,
    val title: String,
    val developerPublisher: String, // 开发商-发行商
    val coverUrl: String,
    val description: String,        // 游戏具体描述
    val releaseDate: Long,          // 发售时间
    val genres: List<String>,       // 游戏类型：动作, 冒险等
    val copyright: String,          // 版权信息

    // 资产状态
    val isOwned: Boolean,
    val addedDate: Long?,           // 入库时间
    val playTime: Int,              // 游戏时长 (分钟)
    val progress: Float,            // 0.0 - 1.0
    val playerCounter: String,      // 玩家游玩数量 (如 "1M+")

    // 平台与版本
    val platform: Platform,
    val edition: GameEdition?,       // 版本信息
    val subscriptionSupport: List<SubscriptionType>, // PS Plus / XGP

    // 奖杯与 DLC
    val trophyGroups: List<TrophyGroup>,
    val additionalContents: List<String>, // DLC追加内容名称列表

    // 媒体与语言
    val mediaGalleries: List<MediaResource>, // 宣传片与截图轮播组
    val audioLanguages: List<String>,        // 语音支持
    val screenLanguages: List<String>,       // 屏幕语言/字幕支持
    val accessibilityFeatures: List<String>, // 辅助功能支持

    // 商业
    val price: String               // 游戏售价
)

data class GameEdition(
    val name: String,         // 普通版, 豪华版
    val description: String   // 版本区别描述
)

enum class SubscriptionType { PS_PLUS_ESSENTIAL, PS_PLUS_EXTRA, PS_PLUS_DELUXE, XGP, EA_PLAY }

data class MediaResource(
    val url: String,
    val type: MediaType
)
enum class MediaType { IMAGE, VIDEO }