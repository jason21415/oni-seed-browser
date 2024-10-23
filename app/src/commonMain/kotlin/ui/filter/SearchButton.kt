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

package ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oni_seed_browser.app.generated.resources.Res
import oni_seed_browser.app.generated.resources.uiSearch
import org.jetbrains.compose.resources.stringResource
import ui.noRippleClickable
import ui.theme.ctaColor
import ui.theme.defaultRoundedCornerShape

@Composable
fun SearchButton(
    enabled: Boolean,
    onClick: () -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                1.dp,
                if (enabled)
                    MaterialTheme.colorScheme.onBackground
                else
                    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3F),
                defaultRoundedCornerShape
            )
            .background(
                if (enabled)
                    ctaColor
                else
                    Color.Transparent,
                defaultRoundedCornerShape
            )
            .size(120.dp, 48.dp)
            .noRippleClickable(onClick)
    ) {

        Text(
            text = stringResource(Res.string.uiSearch),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = if (enabled)
                MaterialTheme.colorScheme.onBackground
            else
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3F)
        )
    }
}
