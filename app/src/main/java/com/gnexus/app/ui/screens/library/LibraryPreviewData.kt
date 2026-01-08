package com.gnexus.app.ui.screens.library

import com.gnexus.app.domain.model.MetaGame
import com.gnexus.app.domain.model.PSVersion
import com.gnexus.app.domain.model.Platform
import com.gnexus.app.domain.model.Trophy
import com.gnexus.app.domain.model.TrophyGroup
import com.gnexus.app.domain.model.TrophyType

object LibraryPreviewData {
    val mockTrophy = Trophy(
        title = "艾尔登之王",
        description = "达成“艾尔登之王”结局",
        coverUrl = "",
        type = TrophyType.PLATINUM,
        rarity = 0.02f,
        earnedTime = 1704844800000L // 2026-01-10
    )

    val mockGroup = TrophyGroup(
        id = "main_game",
        name = "本体",
        coverUrl = "",
        trophies = listOf(mockTrophy)
    )

    val mockGame = MetaGame(
        id = "1",
        title = "Elden Ring",
        developerPublisher = "FromSoftware / Bandai Namco",
        coverUrl = "",
        progress = 0.85f,
        playTime = 7200, // 120h
        platform = Platform.PlayStation(PSVersion.PS5, emptyList()),
        trophyGroups = listOf(mockGroup),
        releaseDate = 1654041600000L,
        genres = listOf("Action", "Adventure", "RPG"),
        copyright = "Copyright © 2022 Bandai Namco Entertainment. All rights reserved.",
        isOwned = true,
        addedDate = 1654041600000L,
        playerCounter = "1M+",
        subscriptionSupport = emptyList(),
        additionalContents = emptyList(),
        mediaGalleries = emptyList(),
        audioLanguages = emptyList(),
        screenLanguages = emptyList(),
        accessibilityFeatures = emptyList(),
        price = "299",
        description = "This description is a placeholder.",
        edition = null,
    )
}