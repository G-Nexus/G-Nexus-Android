package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gnexus.app.R
import com.gnexus.app.data.db.GameEntity

@Composable
fun GameLibraryCard(
    game: GameEntity?,
    onGameClick: (Int) -> Unit,
    onTrophyClick: (Int) -> Unit,
    onGuideClick: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = if (pressed) 0.98f else 1f
                    scaleY = if (pressed) 0.98f else 1f
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onGameClick(1)
                },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // ── 顶部：封面 + 基本信息 ─────────────────────
                Row {
                    AsyncImage(
                        model = game?.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .width(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = game?.name ?: game?.sortableName ?: game?.localizedName?: 0.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            InfoBlock(
                                title = "游戏进度",
                                value = "80%",
                                icon = R.drawable.progress_activity
                            )
                            InfoBlock(
                                title = "游戏时长",
                                value = game?.playDuration ?: 0.toString(),
                                iconVector = Icons.Outlined.AccessTime
                            )
                        }
                    }
                }

                // ── 进度条（Expressive 承托） ─────────────────
                Spacer(Modifier.height(15.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow
                ) {
                    LinearProgressIndicator(
                        progress = { 0.8f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest
                    )
                }

                // ── 奖杯区 ──────────────────────────────────
                Spacer(Modifier.height(9.dp))
                TrophyRow(onTrophyClick)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                )

                // ── 攻略入口 ────────────────────────────────
                ActionRow(
                    onGuideClick,
                    icon = Icons.Filled.LightbulbCircle,
                    text = "游戏攻略"
                )
            }
        }
    }
}