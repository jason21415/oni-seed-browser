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
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ZoneType
import org.jetbrains.compose.resources.painterResource
import ui.theme.*

@Composable
fun ZoneTypeDetail(
    zoneType: ZoneType
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = doubleSpacing)
            .background(
                cardColorBackground,
                defaultRoundedCornerShape
            )
            .border(
                0.dp,
                lightGrayTransparentBorderColor,
                defaultRoundedCornerShape
            )
            .fillMaxWidth()
            .height(160.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HalfSpacer()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(96.dp)
            ) {

                DefaultSpacer()

                Image(
                    painter = painterResource(getZoneTypeDrawable(zoneType)),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp)
                )

                DefaultSpacer()

                Text(
                    text = zoneType.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}