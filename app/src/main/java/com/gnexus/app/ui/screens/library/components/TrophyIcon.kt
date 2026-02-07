package com.gnexus.app.ui.screens.library.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

enum class TrophyType(
    val color: Color,
    val label: String
) {
    Platinum(Color(0xFFE5E4E2), "白金"),
    Gold(Color(0xFFFFD700), "黄金"),
    Silver(Color(0xFFC0C0C0), "白银"),
    Bronze(Color(0xFFCD7F32), "青铜")
}

@Composable
fun TrophyIcon(
    type: TrophyType,
    count: Int,
    modifier: Modifier = Modifier,
    highlight: Boolean = false
) {
    val scale by animateFloatAsState(
        targetValue = if (highlight) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "trophyScale"
    )

    Column(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.EmojiEvents,
            contentDescription = type.label,
            tint = type.color,
            modifier = Modifier.size(20.dp)
        )

        AnimatedContent(
            targetState = count,
            transitionSpec = {
                fadeIn() + slideInVertically { it / 2 } togetherWith
                        fadeOut() + slideOutVertically { -it / 2 }
            },
            label = "trophyCount"
        ) { value ->
            Text(
                value.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
