/*
 * ONI Seed Browser
 * Copyright (C) 2024 Stefan Oltmann
 * https://stefan-oltmann.de/oni-seed-browser
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import model.Asteroid
import model.AsteroidType
import org.jetbrains.compose.resources.painterResource
import ui.theme.DefaultSpacer
import ui.theme.DoubleSpacer
import ui.theme.FillSpacer
import ui.theme.HalfSpacer
import ui.theme.defaultPadding
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AsteroidDisplay(
    asteroid: Asteroid,
    isStarterAstroid: Boolean
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surfaceVariant,
            defaultRoundedCornerShape
        ).border(
            if (isStarterAstroid) 2.dp else 0.dp,
            Color.Black,
            defaultRoundedCornerShape
        ).defaultPadding().fillMaxWidth()
    ) {

        val asteroidType = AsteroidType.of(asteroid.id)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(124.dp)
                .background(Color.Black, defaultRoundedCornerShape)
        ) {

            Image(
                painter = painterResource(getAsteroidTypeDrawable(asteroidType)),
                contentDescription = null,
                modifier = Modifier.defaultPadding()
            )
        }

        DefaultSpacer()

        Column(
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(defaultSpacing),
                modifier = Modifier.height(24.dp)
            ) {

                Text(
                    text = asteroidType.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                for (worldTrait in asteroid.worldTraits) {

                    Image(
                        painter = painterResource(getWorldTraitDrawable(worldTrait)),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            HalfSpacer()

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(overLapping),
                modifier = Modifier.height(48.dp)
            ) {

                val geyserByTypeMap = asteroid.geysers.groupBy { it.id }

                val sortedGeyserTypes = geyserByTypeMap.keys.sorted()

                for (geyserType in sortedGeyserTypes) {

                    val count = geyserByTypeMap[geyserType]!!.size

//                    if (count > 1) {
//
//                        Text(
//                            text = "${count}x",
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    }

                    val hovered = remember { mutableStateOf(false) }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                if (hovered.value)
                                    MaterialTheme.colorScheme.surfaceVariant
                                else
                                    MaterialTheme.colorScheme.surface,
                                CircleShape
                            )
                            .border(
                                1.dp,
                                Color.Black,
                                CircleShape
                            )
                            .onPointerEvent(PointerEventType.Enter) {
                                hovered.value = true
                            }
                            .onPointerEvent(PointerEventType.Exit) {
                                hovered.value = false
                            }
                    ) {

                        Image(
                            painter = painterResource(getGeyserDrawable(geyserType)),
                            contentDescription = null,
                            alignment = Alignment.BottomCenter,
                            modifier = Modifier.padding()
                        )
                    }
                }

                if (isStarterAstroid)
                    FillSpacer()
            }

            HalfSpacer()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(overLapping),
                modifier = Modifier.height(48.dp)
            ) {

                if (asteroid.pointsOfInterest.isEmpty()) {

                    DoubleSpacer()

                    Text(
                        text = "No POIs",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5F)
                    )

                    DoubleSpacer()

                } else {

                    val poisByTypeMap = asteroid.pointsOfInterest.groupBy { it.id }

                    val sortedPoiTypes = poisByTypeMap.keys.sorted()

                    for (poiType in sortedPoiTypes) {

                        val count = poisByTypeMap[poiType]!!.size

//                    if (count > 1) {
//
//                        Text(
//                            text = "${count}x",
//                            style = MaterialTheme.typography.bodyLarge,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    CircleShape
                                )
                                .border(
                                    1.dp,
                                    Color.Black,
                                    CircleShape
                                )
                        ) {

                            Image(
                                painter = painterResource(getPointOfInterestDrawable(poiType)),
                                contentDescription = null,
                                modifier = Modifier.padding(defaultSpacing)
                            )
                        }
                    }

                    if (isStarterAstroid)
                        FillSpacer()
                }
            }
        }
    }
}