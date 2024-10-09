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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import model.Asteroid
import model.ZoneType
import ui.theme.defaultPadding

@Composable
fun MapView(
    asteroid: Asteroid
) {

    if (asteroid.biomePaths == null)
        return

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .defaultPadding()
            .fillMaxWidth()
    ) {

        BoxWithConstraints(
            contentAlignment = Alignment.Center
        ) {

            val density = LocalDensity.current

            val maxHeightInPixels = maxHeight.div(density.density).value

            val viewScale = maxHeightInPixels / asteroid.sizeY

            val scale = viewScale * density.density

            val biomePaths = asteroid.biomePaths.split('\n')

            Canvas(
                modifier = Modifier
                    .size(
                        asteroid.sizeX.times(scale).dp,
                        asteroid.sizeY.times(scale).dp
                    )
                    .border(1.dp, Color.Green)
            ) {

                for (biomeEntry in biomePaths) {

                    if (biomeEntry.isBlank())
                        continue

                    val bioEntryArray = biomeEntry.split(':')

                    val zoneType = ZoneType.valueOf(bioEntryArray[0])

                    val pointsLists = bioEntryArray[1].split(';')

                    for (points in pointsLists) {

                        val path = Path()

                        val pairs = points.split(' ')

                        val startingPoint = pairs.first().split(',')

                        path.moveTo(
                            startingPoint[0].toFloat() * scale * density.density,
                            startingPoint[1].toFloat() * scale * density.density
                        )

                        for (pair in pairs.drop(1)) {

                            val point = pair.split(',')

                            path.lineTo(
                                point[0].toFloat() * scale * density.density,
                                point[1].toFloat() * scale * density.density
                            )
                        }

                        path.lineTo(
                            startingPoint[0].toFloat() * scale * density.density,
                            startingPoint[1].toFloat() * scale * density.density
                        )

                        drawPath(
                            path,
                            zoneType.color
                        )
                    }
                }

//                for (poi in asteroid.pointsOfInterest) {
//
//
//
//                }

            }

//            Box(
//                modifier = Modifier
//                    .size(
//                        asteroid.sizeX.dp.times(density.density * scale),
//                        asteroid.sizeY.dp.times(density.density * scale)
//                    )
//            ) {
//
//                for (poi in asteroid.pointsOfInterest) {
//
//                    Image(
//                        painter = painterResource(getPointOfInterestDrawable(poi.id)),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .offset(
//                                x = poi.posX.dp.times(density.density * scale),
//                                y = poi.posY.dp.times(density.density * scale)
//                            )
//                            .size(32.dp)
//                    )
//                }
//
//                for (geyser in asteroid.geysers) {
//
//                    Image(
//                        painter = painterResource(getGeyserDrawable(geyser.id)),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .offset(
//                                x = geyser.posX.dp.times(density.density * scale),
//                                y = geyser.posY.dp.times(density.density * scale)
//                            )
//                            .size(32.dp)
//                    )
//                }
//            }
        }
    }
}
