package com.gnexus.app.ui.screens.library.motion

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.gnexus.app.ui.screens.library.state.LibraryPaneState

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun PaneMotionHost(
	pane: LibraryPaneState,
	content: @Composable () -> Unit
) {
	AnimatedContent(
		targetState = pane,
		transitionSpec = {

			slideInHorizontally(
				initialOffsetX = { it / 3 }
			) + fadeIn() togetherWith
					slideOutHorizontally(
						targetOffsetX = { -it / 3 }
					) + fadeOut()
		}
	) {
		content()
	}
}
