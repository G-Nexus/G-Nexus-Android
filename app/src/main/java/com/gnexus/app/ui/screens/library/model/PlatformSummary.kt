package com.gnexus.app.ui.screens.library.model

import androidx.annotation.DrawableRes


/** Type alias for a String that represents a URL for an avatar image. */
typealias AvatarUrl = String

/** Type alias for a String that represents a user's unique online ID. */
typealias OnlineId = String

/**
 * Represents the summary information for a specific gaming platform profile,
 * typically displayed in a header or card.
 *
 * @property userName The display name of the user (e.g., "FujiCat").
 * @property onlineId The unique online identifier (e.g., "jonas-lang-garfi").
 * @property avatarUrl The URL for the user's profile picture.
 * @property trophyCount The total number of trophies, achievements, or equivalent points.
 * @property subscriptionIcon A drawable resource ID for the platform's subscription service
 * icon (e.g., R.drawable.psn_plus), if the user is a subscriber. Null if not.
 */
data class PlatformSummary(
	val userName: String,
	val onlineId: OnlineId,
	val avatarUrl: AvatarUrl,
	val trophyCount: Int,
	@get:DrawableRes val subscriptionIcon: Int?
)