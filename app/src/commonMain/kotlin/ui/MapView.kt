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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import model.Asteroid
import model.ZoneType
import org.jetbrains.compose.resources.painterResource
import ui.theme.defaultPadding

@Composable
fun MapView(
    asteroid: Asteroid
) {

    if (asteroid.biomePaths == null)
        return

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.defaultPadding()
    ) {

        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            val density = LocalDensity.current.density

            val maxHeightInPixels = maxHeight.value

            val viewScale = maxHeightInPixels / asteroid.sizeY

            val biomePaths = asteroid.biomePaths.split('\n')

            Canvas(
                modifier = Modifier
                    .size(
                        width = (asteroid.sizeX * viewScale).dp,
                        height = (asteroid.sizeY * viewScale).dp,
                    )
            ) {

                for (biomeEntry in biomePaths) {

                    val bioEntryArray = biomeEntry.split(':')

                    val zoneType = ZoneType.valueOf(bioEntryArray[0])

                    val pointsLists = bioEntryArray[1].split(';')

                    for (points in pointsLists) {

                        val path = Path()

                        val pairs = points.split(' ')

                        val startingPoint = pairs.first().split(',')

                        path.moveTo(
                            startingPoint[0].toFloat() * viewScale * density,
                            startingPoint[1].toFloat() * viewScale * density
                        )

                        for (pair in pairs.drop(1)) {

                            val point = pair.split(',')

                            path.lineTo(
                                point[0].toFloat() * viewScale * density,
                                point[1].toFloat() * viewScale * density
                            )
                        }

                        path.lineTo(
                            startingPoint[0].toFloat() * viewScale * density,
                            startingPoint[1].toFloat() * viewScale * density
                        )

                        drawPath(
                            path,
                            zoneType.color
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(
                        width = (asteroid.sizeX * viewScale).dp,
                        height = (asteroid.sizeY * viewScale).dp,
                    )
            ) {

                for (poi in asteroid.pointsOfInterest) {

                    Image(
                        painter = painterResource(getPointOfInterestDrawable(poi.id)),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(
                                x = (poi.posX * viewScale).dp.minus(16.dp),
                                y = (poi.posY * viewScale).dp.minus(16.dp)
                            )
                            .size(32.dp)
                    )
                }

                for (geyser in asteroid.geysers) {

                    Image(
                        painter = painterResource(getGeyserDrawable(geyser.id)),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(
                                x = (geyser.posX * viewScale).dp.minus(16.dp),
                                y = (geyser.posY * viewScale).dp.minus(16.dp)
                            )
                            .size(32.dp)
                    )
                }
            }
        }
    }
}
