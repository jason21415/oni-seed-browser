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

import kotlin.test.Test
import kotlin.test.assertEquals
import model.BiomePaths
import model.BiomePathsTest

class BiomePathOptimizerTest {

    @Test
    fun testOptimize() {

        val biomePaths = BiomePaths.parse(BiomePathsTest.rawBiomePathsString)

        val optimizedBiomePaths = biomePaths.optimize()

        val actual = optimizedBiomePaths.serialize()

        assertEquals(
            expected = optimizedBiomePathsString,
            actual = actual
        )
    }

    companion object {

        val optimizedBiomePathsString = """
            Sandstone:39,286 30,265 0,268 0,293 30,296;164,182 168,193 145,208 145,213 124,223 111,212 111,204 94,188 72,198 68,193 71,169 75,167 85,144 93,143 104,131 123,142 138,132 151,142 150,154 169,168
            MagmaCore:52,160 47,142 36,138 20,155 25,170 33,174;211,299 202,284 194,283 178,300 183,313 203,316;204,255 190,254 182,226 185,222 205,222 214,238;202,174 183,162 187,142 205,138 215,157;240,380 240,327 209,328 206,343 181,347 177,345 173,327 148,324 143,345 122,350 113,344 92,356 73,344 73,332 93,319 111,330 126,313 117,298 122,282 130,278 133,252 124,246 106,253 104,271 90,280 90,301 64,306 60,319 41,326 42,343 31,353 0,345 0,380
            OilField:64,306 52,287 39,286 30,296 0,293 0,345 31,353 42,343 41,326 60,319;240,327 240,299 211,299 203,316 183,313 178,300 155,292 143,316 126,313 111,330 93,319 73,332 73,344 92,356 113,344 122,350 143,345 148,324 173,327 177,345 181,347 206,343 209,328
            FrozenWastes:69,268 66,256 59,250 34,255 23,233 0,233 0,268 30,265 39,286 52,287;154,85 138,73 120,85 120,104 138,113 153,103;182,263 167,263 154,247 160,231 182,226 190,254;240,184 240,159 215,157 202,174 204,184 212,191;211,299 202,284 211,270 240,271 240,299
            Wasteland:36,216 33,203 0,200 0,233 23,233;54,106 36,108 24,90 34,72 61,74 62,76;71,169 52,160 33,174 38,193 68,193;104,131 103,114 88,106 67,118 67,128 85,144 93,143;111,212 111,204 94,188 72,198 72,214 90,226;216,205 212,191 204,184 180,199 185,222 205,222
            SugarWoods:36,108 24,90 0,90 0,122 29,122;38,193 33,174 25,170 0,179 0,200 33,203;106,253 91,242 66,256 69,268 90,280 104,271;103,114 88,106 67,118 54,106 62,76 87,85 101,74 120,85 120,104;153,287 130,278 133,252 154,247 167,263;215,157 205,138 212,126 240,126 240,159
            Radioactive:25,170 20,155 0,151 0,179;202,284 194,283 182,263 190,254 204,255 211,270;240,208 240,184 212,191 216,205
            BoggyMarsh:36,138 29,122 0,122 0,151 20,155;91,242 90,226 72,214 72,198 68,193 38,193 33,203 36,216 58,224 59,250 66,256;138,132 138,113 120,104 103,114 104,131 123,142;160,231 145,213 124,223 124,246 133,252 154,247;205,138 187,142 173,129 174,115 153,103 154,85 178,74 182,77 186,106 205,111 212,126
            Barren:240,93 240,57 213,60 200,37 197,37 175,59 156,48 138,60 120,47 100,61 85,49 65,59 45,36 39,36 27,58 0,56 0,90 24,90 34,72 61,74 62,76 87,85 101,74 120,85 138,73 154,85 178,74 182,77 207,73 219,92
            Ocean:59,250 58,224 36,216 23,233 34,255;47,142 36,138 29,122 36,108 54,106 67,118 67,128;90,301 90,280 69,268 52,287 64,306;178,300 155,292 143,316 126,313 117,298 122,282 130,278 153,287 167,263 182,263 194,283;219,92 207,73 182,77 186,106 205,111;240,239 240,208 216,205 205,222 214,238
            ToxicJungle:85,144 67,128 47,142 52,160 71,169 75,167;124,246 124,223 111,212 90,226 91,242 106,253;204,184 202,174 183,162 187,142 173,129 174,115 153,103 138,113 138,132 151,142 150,154 169,168 164,182 168,193 145,208 145,213 160,231 182,226 185,222 180,199;240,126 240,93 219,92 205,111 212,126;240,271 240,239 214,238 204,255 211,270
        """.trimIndent()
    }
}
