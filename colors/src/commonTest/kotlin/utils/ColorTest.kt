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

package opensavvy.material3.colors.utils

import io.kotest.matchers.shouldBe
import opensavvy.material3.colors.utils.ColorUtils.alphaFromArgb
import opensavvy.material3.colors.utils.ColorUtils.argbFromLab
import opensavvy.material3.colors.utils.ColorUtils.argbFromLinrgb
import opensavvy.material3.colors.utils.ColorUtils.argbFromRgb
import opensavvy.material3.colors.utils.ColorUtils.argbFromXyz
import opensavvy.material3.colors.utils.ColorUtils.blueFromArgb
import opensavvy.material3.colors.utils.ColorUtils.greenFromArgb
import opensavvy.material3.colors.utils.ColorUtils.labFromArgb
import opensavvy.material3.colors.utils.ColorUtils.redFromArgb
import opensavvy.material3.colors.utils.ColorUtils.xyzFromArgb
import opensavvy.prepared.runner.kotest.PreparedSpec

class ColorTest : PreparedSpec({
	suite("RGB") {
		test("From RGB") {
			check(argbFromRgb(217, 13, 33) == -2552543)
		}

		test("Red component") {
			check(redFromArgb(-2552543) == 217)
		}

		test("Green component") {
			check(greenFromArgb(-2552543) == 13)
		}

		test("Blue component") {
			check(blueFromArgb(-2552543) == 33)
		}
	}

	suite("LinRGB") {
		test("From LinRGB") {
			check(argbFromLinrgb(doubleArrayOf(0.2, 0.7, 0.6)) == -16313326)
		}
	}

	suite("XYZ") {
		test("From XYZ") {
			check(argbFromXyz(0.7, 0.9, 0.2) == -15328766)
		}

		test("To XYZ") {
			val (x, y, z) = xyzFromArgb(-15328766)

			checkDouble(x, 0.7112012460746406)
			checkDouble(y, 0.9137449555906486)
			checkDouble(z, 0.19628711421236336)
		}
	}

	suite("Lab") {
		test("From Lab") {
			check(argbFromLab(1.7, 1.9, 1.3) == -15989501)
		}

		test("To Lab") {
			val (l, a, b) = labFromArgb(-15989501)

			checkDouble(l, 1.7458747272115325)
			checkDouble(a, 1.5813095009121065)
			checkDouble(b, 1.412073285576354)
		}
	}
})
