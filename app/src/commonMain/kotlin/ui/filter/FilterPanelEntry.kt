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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import model.filter.FilterRule
import org.jetbrains.compose.resources.painterResource
import ui.getAsteroidTypeDrawable
import ui.noRippleClickable
import ui.onHover
import ui.theme.DefaultSpacer
import ui.theme.defaultRoundedCornerShape
import ui.theme.defaultSpacing
import ui.theme.hoverColor

@Composable
fun FilterPanelEntry(
    rule: FilterRule,
    onAsteroidFilterClicked: () -> Unit,
    onItemFilterClicked: () -> Unit,
    onConditionFilterClicked: () -> Unit,
    onValueChanged: (String) -> Unit,
    onDeleteClicked: () -> Unit
) {

    Row {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(1.dp, Color.White, defaultRoundedCornerShape)
                .height(40.dp)
        ) {

            val hoveredAsteroid = remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = defaultSpacing)
                    .fillMaxHeight()
                    .onHover(hoveredAsteroid)
                    .noRippleClickable(onAsteroidFilterClicked)
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.width(40.dp)
                ) {

                    if (rule.asteroid == null) {

                        Text(
                            text = "ANY",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (hoveredAsteroid.value)
                                hoverColor
                            else
                                MaterialTheme.colorScheme.onBackground
                        )

                    } else {

                        Image(
                            painter = painterResource(getAsteroidTypeDrawable(rule.asteroid)),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

            VerticalSeparator()

            val hoveredItem = remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = defaultSpacing)
                    .fillMaxHeight()
                    .onHover(hoveredItem)
                    .noRippleClickable(onItemFilterClicked)
            ) {

                Box(
                    modifier = Modifier.width(200.dp)
                ) {

                    Text(
                        text = rule.getItemDescription(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (hoveredItem.value)
                            hoverColor
                        else
                            MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (rule.geyserCount != null || rule.geyserOutput != null || rule.worldTrait != null) {

                VerticalSeparator()

                val hoveredCondition = remember { mutableStateOf(false) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = defaultSpacing)
                        .fillMaxHeight()
                        .onHover(hoveredCondition)
                        .noRippleClickable(onConditionFilterClicked)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.width(40.dp)
                    ) {

                        Text(
                            text = rule.getConditionDescription(),
                            style = if (rule.worldTrait != null)
                                MaterialTheme.typography.bodyLarge
                            else
                                MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (hoveredCondition.value)
                                hoverColor
                            else
                                MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                if (rule.geyserCount != null || rule.geyserOutput != null) {

                    VerticalSeparator()

                    DefaultSpacer()

                    val value = when {
                        rule.geyserCount != null -> rule.geyserCount.count
                        rule.geyserOutput != null -> rule.geyserOutput.outputInGramPerSecond
                        else -> 0
                    }

                    BasicTextField(
                        value = value?.toString() ?: "",
                        onValueChange = onValueChanged,
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.width(60.dp)
                    )

                    DefaultSpacer()
                }
            }
        }

        DefaultSpacer()

        val deleteButtonHovered = remember { mutableStateOf(false) }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .onHover(deleteButtonHovered)
                .noRippleClickable(onDeleteClicked)
        ) {

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = if (deleteButtonHovered.value)
                    Color.Red
                else
                    MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun VerticalSeparator() {

    Box(
        modifier = Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.onBackground)
    )
}
