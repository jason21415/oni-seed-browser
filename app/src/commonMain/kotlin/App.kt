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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.Asteroid
import model.AsteroidType
import model.Cluster
import model.Geyser
import model.GeyserType
import model.PointOfInterestType
import model.World
import model.WorldTrait
import oni_seed_browser.app.generated.resources.Res
import oni_seed_browser.app.generated.resources.asteroid_folia
import oni_seed_browser.app.generated.resources.asteroid_glowood_wasteland
import oni_seed_browser.app.generated.resources.asteroid_irradiated_forest
import oni_seed_browser.app.generated.resources.asteroid_irradiated_marsh
import oni_seed_browser.app.generated.resources.asteroid_irradiated_swampy
import oni_seed_browser.app.generated.resources.asteroid_marshy
import oni_seed_browser.app.generated.resources.asteroid_moo
import oni_seed_browser.app.generated.resources.asteroid_oily_swamp
import oni_seed_browser.app.generated.resources.asteroid_quagmiris
import oni_seed_browser.app.generated.resources.asteroid_radioactive_forest
import oni_seed_browser.app.generated.resources.asteroid_radioactive_swamp
import oni_seed_browser.app.generated.resources.asteroid_radioactive_terra
import oni_seed_browser.app.generated.resources.asteroid_radioactive_terrabog
import oni_seed_browser.app.generated.resources.asteroid_regolith
import oni_seed_browser.app.generated.resources.asteroid_rusty_oil
import oni_seed_browser.app.generated.resources.asteroid_stinko_swamp
import oni_seed_browser.app.generated.resources.asteroid_superconductive
import oni_seed_browser.app.generated.resources.asteroid_terrania
import oni_seed_browser.app.generated.resources.asteroid_tundra
import oni_seed_browser.app.generated.resources.asteroid_water
import oni_seed_browser.app.generated.resources.building_anti_entropy_thermo_nullifier
import oni_seed_browser.app.generated.resources.building_neural_vacillator
import oni_seed_browser.app.generated.resources.building_printing_pod
import oni_seed_browser.app.generated.resources.building_sap_tree
import oni_seed_browser.app.generated.resources.building_supply_teleporter_input
import oni_seed_browser.app.generated.resources.building_supply_teleporter_output
import oni_seed_browser.app.generated.resources.building_teleporter_receiver
import oni_seed_browser.app.generated.resources.building_teleporter_transmitter
import oni_seed_browser.app.generated.resources.cluster_base_arboria
import oni_seed_browser.app.generated.resources.cluster_base_aridio
import oni_seed_browser.app.generated.resources.cluster_base_oasisse
import oni_seed_browser.app.generated.resources.cluster_base_oceania
import oni_seed_browser.app.generated.resources.cluster_base_rime
import oni_seed_browser.app.generated.resources.cluster_base_terra
import oni_seed_browser.app.generated.resources.cluster_base_the_badlands
import oni_seed_browser.app.generated.resources.cluster_base_verdante
import oni_seed_browser.app.generated.resources.cluster_base_volcanea
import oni_seed_browser.app.generated.resources.cluster_spacedout_arboria
import oni_seed_browser.app.generated.resources.cluster_spacedout_aridio
import oni_seed_browser.app.generated.resources.cluster_spacedout_flipped_moonlet
import oni_seed_browser.app.generated.resources.cluster_spacedout_folia
import oni_seed_browser.app.generated.resources.cluster_spacedout_frozen_forest_moonlet
import oni_seed_browser.app.generated.resources.cluster_spacedout_metallic_swampy_moonlet
import oni_seed_browser.app.generated.resources.cluster_spacedout_oasisse
import oni_seed_browser.app.generated.resources.cluster_spacedout_oceania
import oni_seed_browser.app.generated.resources.cluster_spacedout_quagmiris
import oni_seed_browser.app.generated.resources.cluster_spacedout_radioactive_ocean_moonlet
import oni_seed_browser.app.generated.resources.cluster_spacedout_rime
import oni_seed_browser.app.generated.resources.cluster_spacedout_squelchy
import oni_seed_browser.app.generated.resources.cluster_spacedout_terra
import oni_seed_browser.app.generated.resources.cluster_spacedout_terrania
import oni_seed_browser.app.generated.resources.cluster_spacedout_the_badlands
import oni_seed_browser.app.generated.resources.cluster_spacedout_the_desolands_moonlet
import oni_seed_browser.app.generated.resources.cluster_spacedout_verdante
import oni_seed_browser.app.generated.resources.cluster_spacedout_volcanea
import oni_seed_browser.app.generated.resources.dlc_frosty_planet
import oni_seed_browser.app.generated.resources.dlc_spaced_out
import oni_seed_browser.app.generated.resources.geyser_aluminum_volcano
import oni_seed_browser.app.generated.resources.geyser_carbon_dioxide
import oni_seed_browser.app.generated.resources.geyser_carbon_dioxide_vent
import oni_seed_browser.app.generated.resources.geyser_chlorine_gas_vent
import oni_seed_browser.app.generated.resources.geyser_cobalt_volcano
import oni_seed_browser.app.generated.resources.geyser_cool_salt_slush_geyser
import oni_seed_browser.app.generated.resources.geyser_cool_slush_geyser
import oni_seed_browser.app.generated.resources.geyser_cool_steam_vent
import oni_seed_browser.app.generated.resources.geyser_copper_volcano
import oni_seed_browser.app.generated.resources.geyser_gold_volcano
import oni_seed_browser.app.generated.resources.geyser_hot_polluted_oxygen_vent
import oni_seed_browser.app.generated.resources.geyser_hydrogen_vent
import oni_seed_browser.app.generated.resources.geyser_infectious_polluted_oxygen_vent
import oni_seed_browser.app.generated.resources.geyser_iron_volcano
import oni_seed_browser.app.generated.resources.geyser_leaky_oil_fissure
import oni_seed_browser.app.generated.resources.geyser_liquid_sulfur_geyser
import oni_seed_browser.app.generated.resources.geyser_minor_volcano
import oni_seed_browser.app.generated.resources.geyser_natural_gas_geyser
import oni_seed_browser.app.generated.resources.geyser_niobium_volcano
import oni_seed_browser.app.generated.resources.geyser_oil_reservoir
import oni_seed_browser.app.generated.resources.geyser_polluted_water_vent
import oni_seed_browser.app.generated.resources.geyser_salt_water
import oni_seed_browser.app.generated.resources.geyser_steam_vent
import oni_seed_browser.app.generated.resources.geyser_tungsten_volcano
import oni_seed_browser.app.generated.resources.geyser_volcano
import oni_seed_browser.app.generated.resources.geyser_water
import oni_seed_browser.app.generated.resources.oni_logo
import oni_seed_browser.app.generated.resources.worldtrait_boulders_large
import oni_seed_browser.app.generated.resources.worldtrait_boulders_medium
import oni_seed_browser.app.generated.resources.worldtrait_boulders_mixed
import oni_seed_browser.app.generated.resources.worldtrait_boulders_small
import oni_seed_browser.app.generated.resources.worldtrait_crashed_satellites
import oni_seed_browser.app.generated.resources.worldtrait_deep_oil
import oni_seed_browser.app.generated.resources.worldtrait_distress_signal
import oni_seed_browser.app.generated.resources.worldtrait_frozen_core
import oni_seed_browser.app.generated.resources.worldtrait_geoactive
import oni_seed_browser.app.generated.resources.worldtrait_geodes
import oni_seed_browser.app.generated.resources.worldtrait_geodormant
import oni_seed_browser.app.generated.resources.worldtrait_glaciers_large
import oni_seed_browser.app.generated.resources.worldtrait_irregular_oil
import oni_seed_browser.app.generated.resources.worldtrait_lush_core
import oni_seed_browser.app.generated.resources.worldtrait_magma_vents
import oni_seed_browser.app.generated.resources.worldtrait_metal_caves
import oni_seed_browser.app.generated.resources.worldtrait_metal_poor
import oni_seed_browser.app.generated.resources.worldtrait_metal_rich
import oni_seed_browser.app.generated.resources.worldtrait_misaligned_start
import oni_seed_browser.app.generated.resources.worldtrait_radioactive_crust
import oni_seed_browser.app.generated.resources.worldtrait_slime_splats
import oni_seed_browser.app.generated.resources.worldtrait_subsurface_ocean
import oni_seed_browser.app.generated.resources.worldtrait_volcanoes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import service.DummyWebClient
import theme.AppTypography
import theme.DefaultSpacer
import theme.DoubleSpacer
import theme.FillSpacer
import theme.HalfSpacer
import theme.appColorScheme
import theme.darkGreen
import theme.darkRed
import theme.defaultPadding
import theme.defaultRoundedCornerShape
import theme.defaultSpacing
import theme.doubleSpacing
import theme.white

val logoIconHeight = 80.dp

val overLapping = -16.dp

@Composable
fun App() {
    MaterialTheme(
        colorScheme = appColorScheme,
        typography = AppTypography()
    ) {

        val string = produceState<SearchResponse?>(null) {

            value = DummyWebClient.search(
                SearchRequest(
                    selectedWorld = "null",
                    worldTraits = emptyList(),
                    page = 0,
                    vanilla = true
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {

            Text(
                text = "ONI Map Explorer",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.defaultPadding()
            )

            Text(
                text = "This is a non-functional work-in-progress prototype.",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )

            DefaultSpacer()

            val filterPanelOpen = remember { mutableStateOf(false) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = doubleSpacing)
                    .background(MaterialTheme.colorScheme.surface, defaultRoundedCornerShape)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(
                            Color.Black,
                            if (filterPanelOpen.value)
                                RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp
                                )
                            else
                                defaultRoundedCornerShape
                        )
                        .clickable {
                            filterPanelOpen.value = !filterPanelOpen.value
                        }
                ) {

                    DoubleSpacer()

                    Text(
                        text = "Filter",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    FillSpacer()

                    Icon(
                        imageVector = if (filterPanelOpen.value)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(48.dp)
                    )

                    DefaultSpacer()
                }

                AnimatedVisibility(filterPanelOpen.value) {

                    Row(
                        modifier = Modifier.defaultPadding(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(64.dp)
                    ) {

                        Image(
                            painter = painterResource(Res.drawable.oni_logo),
                            contentDescription = null,
                            modifier = Modifier.height(logoIconHeight)
                        )

                        Image(
                            painter = painterResource(Res.drawable.dlc_spaced_out),
                            contentDescription = null,
                            modifier = Modifier.height(logoIconHeight)
                        )

                        Image(
                            painter = painterResource(Res.drawable.dlc_frosty_planet),
                            contentDescription = null,
                            modifier = Modifier.height(logoIconHeight)
                        )
                    }
                }
            }


            Box {

                val lazyListState = rememberLazyListState()

                val response = string.value

                if (response != null) {

                    LazyColumn(
                        state = lazyListState,
                        contentPadding = PaddingValues(defaultSpacing),
                        verticalArrangement = Arrangement.spacedBy(doubleSpacing),
                        modifier = Modifier.defaultPadding()
                    ) {

                        items(response.worlds) { world ->

                            WorldView(world)
                        }
                    }
                }

                VerticalScrollbar(
                    adapter = rememberScrollbarAdapter(lazyListState),
                    modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd),
                    style = defaultScrollbarStyle().copy(
                        unhoverColor = white.copy(alpha = 0.4f),
                        hoverColor = white
                    ),
                )
            }
        }
    }

}

@Composable
fun WorldView(
    world: World
) {

    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, defaultRoundedCornerShape)
    ) {

        val gridLayoutColumnCount = maxWidth.div(450.dp).toInt()

        Column(
            modifier = Modifier.defaultPadding(),
            verticalArrangement = Arrangement.spacedBy(defaultSpacing)
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                SelectionContainer {

                    Text(
                        text = world.coordinate,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            val firstAsteroid = world.asteroids.first()

            /* First Asteroid should span the whole column. */
            AsteroidDisplay(
                asteroid = firstAsteroid,
                isStarterAstroid = true
            )

            val remainingAsteroids = world.asteroids.drop(1)

            val asteroidsPerColumn = remainingAsteroids.chunked(gridLayoutColumnCount)

            for (asteroidsInColumn in asteroidsPerColumn) {

                Row(
                    horizontalArrangement = Arrangement.spacedBy(defaultSpacing)
                ) {

                    for (asteroid in asteroidsInColumn) {

                        Box(
                            modifier = Modifier.weight(1F)
                        ) {

                            AsteroidDisplay(
                                asteroid = asteroid,
                                isStarterAstroid = false
                            )
                        }
                    }

                    val requiredSpacerCount = gridLayoutColumnCount - asteroidsInColumn.size

                    repeat(requiredSpacerCount) {
                        FillSpacer()
                    }
                }
            }
        }
    }
}

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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.GeysersView(geysers: List<Geyser>) {

    FlowRow(
        modifier = Modifier
            .weight(1F)
            .defaultPadding()
    ) {

        val geysersCountOfStarter =
            geysers.groupBy { it.id }.mapValues { (_, value) -> value.size }

        for (geyserType in GeyserType.entries) {

            val count = geysersCountOfStarter[geyserType] ?: 0

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(4.dp)
                    .width(250.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {

                Image(
                    painter = painterResource(getGeyserDrawable(geyserType)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .scale(1.2f)
                )

                DefaultSpacer()

                Text(
                    text = geyserType.displayName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1F)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(26.dp)
                        .background(
                            color = if (count > 0) darkGreen else darkRed,
                            shape = CircleShape
                        )
                ) {

                    Text(
                        text = count.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.offset(y = -2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))
            }
        }

    }
}

fun getWorldTraitDrawable(worldTrait: WorldTrait): DrawableResource =
    when (worldTrait) {
        WorldTrait.BouldersLarge -> Res.drawable.worldtrait_boulders_large
        WorldTrait.BouldersMedium -> Res.drawable.worldtrait_boulders_medium
        WorldTrait.BouldersMixed -> Res.drawable.worldtrait_boulders_mixed
        WorldTrait.BouldersSmall -> Res.drawable.worldtrait_boulders_small
        WorldTrait.DeepOil -> Res.drawable.worldtrait_deep_oil
        WorldTrait.FrozenCore -> Res.drawable.worldtrait_frozen_core
        WorldTrait.GeoActive -> Res.drawable.worldtrait_geoactive
        WorldTrait.Geodes -> Res.drawable.worldtrait_geodes
        WorldTrait.GeoDormant -> Res.drawable.worldtrait_geodormant
        WorldTrait.GlaciersLarge -> Res.drawable.worldtrait_glaciers_large
        WorldTrait.IrregularOil -> Res.drawable.worldtrait_irregular_oil
        WorldTrait.MagmaVents -> Res.drawable.worldtrait_magma_vents
        WorldTrait.MetalPoor -> Res.drawable.worldtrait_metal_poor
        WorldTrait.MetalRich -> Res.drawable.worldtrait_metal_rich
        WorldTrait.MisalignedStart -> Res.drawable.worldtrait_misaligned_start
        WorldTrait.SlimeSplats -> Res.drawable.worldtrait_slime_splats
        WorldTrait.SubsurfaceOcean -> Res.drawable.worldtrait_subsurface_ocean
        WorldTrait.Volcanoes -> Res.drawable.worldtrait_volcanoes
        WorldTrait.CrashedSatellites -> Res.drawable.worldtrait_crashed_satellites
        WorldTrait.DistressSignal -> Res.drawable.worldtrait_distress_signal
        WorldTrait.LushCore -> Res.drawable.worldtrait_lush_core
        WorldTrait.MetalCaves -> Res.drawable.worldtrait_metal_caves
        WorldTrait.RadioactiveCrust -> Res.drawable.worldtrait_radioactive_crust
    }

fun getGeyserDrawable(geyserType: GeyserType): DrawableResource =
    when (geyserType) {
        GeyserType.COOL_STEAM -> Res.drawable.geyser_cool_steam_vent
        GeyserType.HOT_STEAM -> Res.drawable.geyser_steam_vent
        GeyserType.WATER -> Res.drawable.geyser_water
        GeyserType.COOL_SLUSH_WATER -> Res.drawable.geyser_cool_slush_geyser
        GeyserType.POLLUTED_WATER -> Res.drawable.geyser_polluted_water_vent
        GeyserType.COOL_SALT_WATER -> Res.drawable.geyser_cool_salt_slush_geyser
        GeyserType.HOT_SALT_WATER -> Res.drawable.geyser_salt_water
        GeyserType.MINOR_VOLCANO -> Res.drawable.geyser_minor_volcano
        GeyserType.VOLCANO -> Res.drawable.geyser_volcano
        GeyserType.LIQUID_CO2 -> Res.drawable.geyser_carbon_dioxide
        GeyserType.HOT_CO2 -> Res.drawable.geyser_carbon_dioxide_vent
        GeyserType.HYDROGEN -> Res.drawable.geyser_hydrogen_vent
        GeyserType.HOT_POLLUTED_O2 -> Res.drawable.geyser_hot_polluted_oxygen_vent
        GeyserType.INFECTIOUS_POLLUTED_O2 -> Res.drawable.geyser_infectious_polluted_oxygen_vent
        GeyserType.CHLORINE -> Res.drawable.geyser_chlorine_gas_vent
        GeyserType.NATURAL_GAS -> Res.drawable.geyser_natural_gas_geyser
        GeyserType.COPPER_VOLCANO -> Res.drawable.geyser_copper_volcano
        GeyserType.IRON_VOLCANO -> Res.drawable.geyser_iron_volcano
        GeyserType.GOLD_VOLCANO -> Res.drawable.geyser_gold_volcano
        GeyserType.LEAKY_OIL_FISSURE -> Res.drawable.geyser_leaky_oil_fissure
        GeyserType.ALUMINIUM_VOLCANO -> Res.drawable.geyser_aluminum_volcano
        GeyserType.COBALT_VOLCANO -> Res.drawable.geyser_cobalt_volcano
        GeyserType.SULFUR_GEYSER -> Res.drawable.geyser_liquid_sulfur_geyser
        GeyserType.TUNGSTEN_VOLCANO -> Res.drawable.geyser_tungsten_volcano
        GeyserType.NIOBIUM_VOLCANO -> Res.drawable.geyser_niobium_volcano
        GeyserType.OIL_WELL -> Res.drawable.geyser_oil_reservoir
    }

fun getPointOfInterestDrawable(pointOfInterestType: PointOfInterestType): DrawableResource =
    when (pointOfInterestType) {
        PointOfInterestType.PRINTING_POD -> Res.drawable.building_printing_pod
        PointOfInterestType.SUPPLY_TELEPORTER_INPUT -> Res.drawable.building_supply_teleporter_input
        PointOfInterestType.SUPPLY_TELEPORTER_OUTPUT -> Res.drawable.building_supply_teleporter_output
        PointOfInterestType.TELEPORTER_TRANSMITTER -> Res.drawable.building_teleporter_transmitter
        PointOfInterestType.TELEPORTER_RECEIVER -> Res.drawable.building_teleporter_receiver
        PointOfInterestType.NEURAL_VACILLATOR -> Res.drawable.building_neural_vacillator
        PointOfInterestType.ANTI_ENTROPY_THERMO_NULLIFIER -> Res.drawable.building_anti_entropy_thermo_nullifier
        PointOfInterestType.EXPERIMENT_52B -> Res.drawable.building_sap_tree
    }

@Composable
fun getClusterDrawable(cluster: Cluster): DrawableResource =
    when (cluster) {
        Cluster.BASE_TERRA -> Res.drawable.cluster_base_terra
        Cluster.BASE_OCEANIA -> Res.drawable.cluster_base_oceania
        Cluster.BASE_RIME -> Res.drawable.cluster_base_rime
        Cluster.BASE_VERDANTE -> Res.drawable.cluster_base_verdante
        Cluster.BASE_ARBORIA -> Res.drawable.cluster_base_arboria
        Cluster.BASE_VOLCANEA -> Res.drawable.cluster_base_volcanea
        Cluster.BASE_THE_BADLANDS -> Res.drawable.cluster_base_the_badlands
        Cluster.BASE_ARIDIO -> Res.drawable.cluster_base_aridio
        Cluster.BASE_OASISSE -> Res.drawable.cluster_base_oasisse
        Cluster.DLC_TERRA -> Res.drawable.cluster_spacedout_terra
        Cluster.DLC_OCEANIA -> Res.drawable.cluster_spacedout_oceania
        Cluster.DLC_SQUELCHY -> Res.drawable.cluster_spacedout_squelchy
        Cluster.DLC_RIME -> Res.drawable.cluster_spacedout_rime
        Cluster.DLC_VERDANTE -> Res.drawable.cluster_spacedout_verdante
        Cluster.DLC_ARBORIA -> Res.drawable.cluster_spacedout_arboria
        Cluster.DLC_VOLCANEA -> Res.drawable.cluster_spacedout_volcanea
        Cluster.DLC_THE_BADLANDS -> Res.drawable.cluster_spacedout_the_badlands
        Cluster.DLC_ARIDIO -> Res.drawable.cluster_spacedout_aridio
        Cluster.DLC_OASISSE -> Res.drawable.cluster_spacedout_oasisse
        Cluster.DLC_TERRANIA -> Res.drawable.cluster_spacedout_terrania
        Cluster.DLC_FOLIA -> Res.drawable.cluster_spacedout_folia
        Cluster.DLC_QUAGMIRIS -> Res.drawable.cluster_spacedout_quagmiris
        Cluster.DLC_METALLIC_SWAMPY_MOONLET -> Res.drawable.cluster_spacedout_metallic_swampy_moonlet
        Cluster.DLC_THE_DESOLANDS_MOONLET -> Res.drawable.cluster_spacedout_the_desolands_moonlet
        Cluster.DLC_FROZEN_FOREST_MOONLET -> Res.drawable.cluster_spacedout_frozen_forest_moonlet
        Cluster.DLC_FLIPPED_MOONLET -> Res.drawable.cluster_spacedout_flipped_moonlet
        Cluster.DLC_RADIOACTIVE_OCEAN_MOONLET -> Res.drawable.cluster_spacedout_radioactive_ocean_moonlet
    }

@Composable
fun getAsteroidTypeDrawable(asteroidType: AsteroidType): DrawableResource =
    when (asteroidType) {

        // TODO Correct?
        AsteroidType.TERRA -> Res.drawable.cluster_base_terra
        AsteroidType.OCEANIA -> Res.drawable.cluster_base_oceania
        AsteroidType.RIME -> Res.drawable.cluster_base_rime
        AsteroidType.VERDANTE -> Res.drawable.cluster_base_verdante
        AsteroidType.ARBORIA -> Res.drawable.cluster_base_arboria
        AsteroidType.VOLCANEA -> Res.drawable.cluster_base_volcanea
        AsteroidType.THE_BADLANDS -> Res.drawable.cluster_base_the_badlands
        AsteroidType.ARIDIO -> Res.drawable.cluster_base_aridio
        AsteroidType.OASISSE -> Res.drawable.cluster_base_oasisse
        AsteroidType.SQUELCHY -> Res.drawable.cluster_spacedout_squelchy

        AsteroidType.TERRANIA -> Res.drawable.asteroid_terrania
        AsteroidType.FOLIA -> Res.drawable.asteroid_folia
        AsteroidType.QUAGMIRIS -> Res.drawable.asteroid_quagmiris
        AsteroidType.METALLIC_SWAMPY -> Res.drawable.cluster_spacedout_metallic_swampy_moonlet
        AsteroidType.THE_DESOLANDS -> Res.drawable.cluster_spacedout_the_desolands_moonlet
        AsteroidType.FROZEN_FOREST -> Res.drawable.cluster_spacedout_frozen_forest_moonlet
        AsteroidType.FLIPPED -> Res.drawable.cluster_spacedout_flipped_moonlet
        AsteroidType.RADIOACTIVE_OCEAN -> Res.drawable.cluster_spacedout_radioactive_ocean_moonlet
        AsteroidType.RADIOACTIVE_SWAMP -> Res.drawable.asteroid_radioactive_swamp
        AsteroidType.GLOWOOD_WASTELAND -> Res.drawable.asteroid_glowood_wasteland
        AsteroidType.RADIOACTIVE_FOREST -> Res.drawable.asteroid_radioactive_forest
        AsteroidType.STINKO_SWAMP -> Res.drawable.asteroid_stinko_swamp
        AsteroidType.RADIOACTIVE_TERRA -> Res.drawable.asteroid_radioactive_terra
        AsteroidType.RADIOACTIVE_TERRABOG_ASTEROID -> Res.drawable.asteroid_radioactive_terrabog
        AsteroidType.OILY_SWAMP -> Res.drawable.asteroid_oily_swamp
        AsteroidType.RUSTY_OIL -> Res.drawable.asteroid_rusty_oil
        AsteroidType.IRRADIATED_FOREST -> Res.drawable.asteroid_irradiated_forest
        AsteroidType.IRRADIATED_SWAMPY -> Res.drawable.asteroid_irradiated_swampy
        AsteroidType.IRRADIATED_MARSH_ASTEROID -> Res.drawable.asteroid_irradiated_marsh
        AsteroidType.TUNDRA -> Res.drawable.asteroid_tundra
        AsteroidType.MARSHY -> Res.drawable.asteroid_marshy
        AsteroidType.SUPERCONDUCTIVE -> Res.drawable.asteroid_superconductive
        AsteroidType.MOO -> Res.drawable.asteroid_moo
        AsteroidType.WATER -> Res.drawable.asteroid_water
        AsteroidType.REGOLITH -> Res.drawable.asteroid_regolith
    }
