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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import model.Asteroid
import model.BiomePaths
import oni_seed_browser.app.generated.resources.Res
import oni_seed_browser.app.generated.resources.background_space
import org.jetbrains.compose.resources.painterResource
import ui.theme.defaultPadding
import ui.theme.lightGrayTransparentBorderColor
import kotlin.math.min

@Composable
fun AsteroidMapPopup(
    asteroid: Asteroid,
    onCloseClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .background(Color.Black)
            .noRippleClickable {
                /* Ignore clicks */
            }
    ) {

        /* Background */
        Image(
            painter = painterResource(Res.drawable.background_space),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        CloseButton(
            onClick = onCloseClicked
        )

        Column {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = asteroid.id.displayName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.defaultPadding()
            ) {

                AsteroidMap(asteroid)
            }
        }
    }
}

@Composable
fun AsteroidMap(
    asteroid: Asteroid,
    iconSize: Dp = 32.dp,
    contentAlignment: Alignment = Alignment.Center
) {

    val halfIconSize: Dp = iconSize.div(2)

    BoxWithConstraints(
        contentAlignment = contentAlignment,
        modifier = Modifier.fillMaxSize()
    ) {

        val density = LocalDensity.current.density

        val viewScale = min(
            maxHeight.value / asteroid.sizeY,
            maxWidth.value / asteroid.sizeX
        )

        val biomePaths = BiomePaths.parse(asteroid.biomePaths)

        Canvas(
            modifier = Modifier
                .size(
                    width = (asteroid.sizeX * viewScale).dp,
                    height = (asteroid.sizeY * viewScale).dp,
                )
                .border(0.dp, lightGrayTransparentBorderColor)
        ) {

            for ((zoneType, pointsLists) in biomePaths.polygonMap) {

                for (points in pointsLists) {

                    val path = Path()

                    val startingPoint = points.first()

                    path.moveTo(
                        startingPoint.x * viewScale * density,
                        startingPoint.y * viewScale * density
                    )

                    for (point in points.drop(1)) {

                        path.lineTo(
                            point.x * viewScale * density,
                            point.y * viewScale * density
                        )
                    }

                    path.lineTo(
                        startingPoint.x * viewScale * density,
                        startingPoint.y * viewScale * density
                    )

                    drawPath(path, zoneType.color)
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
                            x = (poi.x * viewScale).dp - halfIconSize,
                            y = (poi.y * viewScale).dp - halfIconSize
                        )
                        .size(iconSize)
                )
            }

            for (geyser in asteroid.geysers) {

                Image(
                    painter = painterResource(getGeyserDrawable(geyser.id)),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(
                            x = (geyser.x * viewScale).dp - halfIconSize,
                            y = (geyser.y * viewScale).dp - halfIconSize
                        )
                        .size(iconSize)
                )
            }
        }
    }
}
