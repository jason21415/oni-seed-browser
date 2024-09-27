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

package model

import kotlinx.serialization.Serializable

/*
 * See https://oxygennotincluded.fandom.com/wiki/World_Trait
 */

@Serializable
enum class WorldTrait(
    val displayName: String
) {

    BouldersLarge(
        displayName = "Large Boulders"
    ),
    BouldersMedium(
        displayName = "Medium Boulders"
    ),
    BouldersMixed(
        displayName = "Mixed Boulders"
    ),
    BouldersSmall(
        displayName = "Small Boulders"
    ),
    DeepOil(
        displayName = "Trapped Oil"
    ),
    FrozenCore(
        displayName = "Frozen Core"
    ),
    GeoActive(
        displayName = "Geoactive"
    ),
    Geodes(
        displayName = "Geodes"
    ),
    GeoDormant(
        displayName = "Geodormant"
    ),
    GlaciersLarge(
        displayName = "Large Glaciers"
    ),
    IrregularOil(
        displayName = "Irregular Oil"
    ),
    MagmaVents(
        displayName = "Magma Channels"
    ),
    MetalPoor(
        displayName = "Metal Poor"
    ),
    MetalRich(
        displayName = "Metal Rich"
    ),
    MisalignedStart(
        displayName = "Alternate Pod Location"
    ),
    SlimeSplats(
        displayName = "Slime Molds"
    ),
    SubsurfaceOcean(
        displayName = "Subsurface Ocean"
    ),
    Volcanoes(
        displayName = "Volcanoes"
    ),
    CrashedSatellites(
        displayName = "Crashed Satellites"
    ),
    DistressSignal(
        displayName = "Frozen Friend"
    ),
    LushCore(
        displayName = "Lush Core"
    ),
    MetalCaves(
        displayName = "Metallic Caves"
    ),
    RadioactiveCrust(
        displayName = "Radioactive Crust"
    )
}

