/*
 * Copyright (c) 2024, Google LLC, OpenSavvy and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opensavvy.material3.colors.temperature

import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.utils.Color
import opensavvy.prepared.runner.kotest.PreparedSpec

class TemperatureCacheTest : PreparedSpec({
	suite("Complement") {
		val colors = listOf(
			Color.BLACK to Hct.from(0.0, 0.0, 0.0),
			Color.WHITE to Hct.from(209.0, 2.0, 100.0),
			Color.RED to Hct.from(265.0170142303846, 69.66359963486161, 53.1613744829945),
			Color.fromRgb(126, 208, 98) to Hct.from(6.861709070925022, 39.34080922281131, 76.31165352750943)
		)

		for ((input, expected) in colors) {
			test("The complement of $input should be ${expected.toColor()}") {
				check(TemperatureCache(Hct.fromColor(input)).complement == expected)
			}
		}
	}
})
