package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun TrophyRow(
    onTrophyClick: (Int, androidx.compose.ui.geometry.Rect) -> Unit
) {
    var bounds by remember { mutableStateOf<androidx.compose.ui.geometry.Rect?>(null) }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .onGloballyPositioned { coordinates ->
                val position = coordinates.localToWindow(Offset.Zero)
                val size = coordinates.size.toSize()
                bounds = androidx.compose.ui.geometry.Rect(
                    position.x,
                    position.y,
                    position.x + size.width,
                    position.y + size.height
                )
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = {
                    bounds?.let { rect ->
                        onTrophyClick(1, rect) // ✅ 传递坐标
                    }
                }
            )
            .padding(8.dp)
            .background(
                if (pressed)
                    MaterialTheme.colorScheme.surfaceContainerHigh
                else
                    Color.Transparent
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.EmojiEvents,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(20.dp))

        Column {
            Text(
                "奖杯",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp)
            ) {
                TrophyIcon(TrophyType.Platinum, 0, highlight = true)
                TrophyIcon(TrophyType.Gold, 2, highlight = true)
                TrophyIcon(TrophyType.Silver, 10)
                TrophyIcon(TrophyType.Bronze, 20)
            }
        }
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
