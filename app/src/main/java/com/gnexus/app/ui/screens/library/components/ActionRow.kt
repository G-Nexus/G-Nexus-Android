package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun ActionRow(
    onGuideClick: (Int, androidx.compose.ui.geometry.Rect) -> Unit,
    icon: ImageVector,
    text: String
) {
    var bounds by remember { mutableStateOf<androidx.compose.ui.geometry.Rect?>(null) }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
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
                indication = ripple(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                interactionSource = remember { MutableInteractionSource() },
                onClick = {
                    bounds?.let { rect ->
                        onGuideClick(1, rect) // ✅ 传递屏幕坐标
                    }
                }
            )
            .background(
                if (pressed)
                    MaterialTheme.colorScheme.surfaceContainerHigh
                else
                    Color.Transparent
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(20.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.weight(1f))
        Icon(
            Icons.AutoMirrored.Outlined.ArrowForwardIos,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
