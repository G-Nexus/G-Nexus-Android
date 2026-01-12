package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LightbulbCircle
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gnexus.app.R

@Composable
fun GameLibraryCard() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                AsyncImage(
                    model = "https://imgs.ali213.net/oday/uploadfile/2022/12/30/20221230122222799.jpg",
                    contentDescription = null,
                    modifier = Modifier.height(140.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        "Elden Ring",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Column(
                            modifier = Modifier.height(70.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "游戏进度",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.progress_activity),
                                    contentDescription = "Progress Icon",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                    modifier = Modifier,
                                )
                                Text(
                                    "80%",
                                    modifier = Modifier.padding(start = 6.dp),
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.height(70.dp),
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                "游戏时长",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Outlined.AccessTime,
                                    contentDescription = "test",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    "100h",
                                    modifier = Modifier.padding(start = 6.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                }
            }
            HorizontalDivider(
                Modifier.fillMaxSize(0.95f),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.EmojiEvents,
                    contentDescription = "trophy",
                    tint = MaterialTheme.colorScheme.primary
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize(0.8f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        "奖杯",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(
                        Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            Icons.Filled.EmojiEvents,
                            modifier = Modifier.padding(start = 6.dp),
                            contentDescription = "platinum_trophy",
                            tint = Color(0xFFC2B280)
                        )
                        Text("0")
                        Icon(
                            Icons.Filled.EmojiEvents,
                            modifier = Modifier.padding(start = 6.dp),
                            contentDescription = "gold_trophy",
                            tint = Color(0xFFFFC2B2)
                        )
                        Text("2")
                        Icon(
                            Icons.Filled.EmojiEvents,
                            modifier = Modifier.padding(start = 6.dp),
                            contentDescription = "silver_trophy",
                            tint = Color(0xFFC2B280)
                        )
                        Text("10")
                        Icon(
                            Icons.Filled.EmojiEvents,
                            modifier = Modifier.padding(start = 6.dp),
                            contentDescription = "bronze_trophy",
                            tint = Color(0xFFC2B280)
                        )
                        Text("20")
                    }
                }

                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "trophy_forward",
                    tint = MaterialTheme.colorScheme.outlineVariant
                )
            }
            HorizontalDivider(
                Modifier.fillMaxSize(0.95f),
                DividerDefaults.Thickness,
                DividerDefaults.color
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.LightbulbCircle,
                    contentDescription = "help",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    "游戏攻略",
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowForwardIos,
                    contentDescription = "trophy_forward",
                    tint = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}