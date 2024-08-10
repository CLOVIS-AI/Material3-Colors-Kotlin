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

import opensavvy.material3.colors.utils.ColorUtils.blueFromArgb
import opensavvy.material3.colors.utils.ColorUtils.greenFromArgb
import opensavvy.material3.colors.utils.ColorUtils.redFromArgb

/** Utility methods for string representations of colors.  */
internal object StringUtils {
	private fun hexFromColor(color: Int): String {
		return color.toString(16)
			.padStart(2, '0')
	}

	/**
	 * Hex string representing color, ex. #ff0000 for red.
	 *
	 * @param argb ARGB representation of a color.
	 */
	fun hexFromArgb(argb: Int): String {
		val red = redFromArgb(argb)
		val blue = blueFromArgb(argb)
		val green = greenFromArgb(argb)
		return "#" + hexFromColor(red) + hexFromColor(green) + hexFromColor(blue)
	}
}
