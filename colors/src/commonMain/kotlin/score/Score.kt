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

package opensavvy.material3.colors.score

import opensavvy.material3.colors.argb.Argb
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.utils.differenceDegrees
import opensavvy.material3.colors.utils.sanitizeDegreesInt
import kotlin.jvm.JvmOverloads
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * Given a large set of colors, remove colors that are unsuitable for a UI theme, and rank the rest
 * based on suitability.
 *
 *
 * Enables use of a high cluster count for image quantization, thus ensuring colors aren't
 * muddied, while curating the high cluster count to a much smaller number of appropriate choices.
 */
object Score {
	private const val TARGET_CHROMA = 48.0 // A1 Chroma
	private const val WEIGHT_PROPORTION = 0.7
	private const val WEIGHT_CHROMA_ABOVE = 0.3
	private const val WEIGHT_CHROMA_BELOW = 0.1
	private const val CUTOFF_CHROMA = 5.0
	private const val CUTOFF_EXCITED_PROPORTION = 0.01

	/**
	 * Given a map with keys of colors and values of how often the color appears, rank the colors
	 * based on suitability for being used for a UI theme.
	 *
	 * @param colorsToPopulation map with keys of colors and values of how often the color appears,
	 * usually from a source image.
	 * @param desired max count of colors to be returned in the list.
	 * @param fallbackColorArgb color to be returned if no other options available.
	 * @param filter whether to filter out undesireable combinations.
	 * @return Colors sorted by suitability for a UI theme. The most suitable color is the first item,
	 * the least suitable is the last. There will always be at least one color returned.
	 * If none of the input colors were suitable for a theme, a default fallback color will be provided,
	 * [Argb.GoogleBlue].
	 */
	@JvmOverloads
	fun score(
		colorsToPopulation: Map<Argb, Int>,
		desired: Int = 4,
		fallbackColorArgb: Argb = Argb.GoogleBlue,
		filter: Boolean = true,
	): List<Argb> {
		// Get the HCT color for each Argb value, while finding the per hue count and
		// total count.

		val colorsHct: MutableList<Hct> = ArrayList()
		val huePopulation = IntArray(360)
		var populationSum = 0.0
		for ((key, value) in colorsToPopulation) {
			val hct = Hct(key)
			colorsHct.add(hct)
			val hue = floor(hct.hue).toInt()
			huePopulation[hue] += value
			populationSum += value.toDouble()
		}

		// Hues with more usage in neighboring 30 degree slice get a larger number.
		val hueExcitedProportions = DoubleArray(360)
		for (hue in 0..359) {
			val proportion = huePopulation[hue] / populationSum
			for (i in hue - 14 until hue + 16) {
				val neighborHue = sanitizeDegreesInt(i)
				hueExcitedProportions[neighborHue] += proportion
			}
		}

		// Scores each HCT color based on usage and chroma, while optionally
		// filtering out values that do not have enough chroma or usage.
		val scoredHcts = ArrayList<ScoredHCT>()
		for (hct in colorsHct) {
			val hue = sanitizeDegreesInt(hct.hue.roundToInt())
			val proportion = hueExcitedProportions[hue]
			if (filter && (hct.chroma < CUTOFF_CHROMA || proportion <= CUTOFF_EXCITED_PROPORTION)) {
				continue
			}

			val proportionScore = proportion * 100.0 * WEIGHT_PROPORTION
			val chromaWeight =
				if (hct.chroma < TARGET_CHROMA) WEIGHT_CHROMA_BELOW else WEIGHT_CHROMA_ABOVE
			val chromaScore = (hct.chroma - TARGET_CHROMA) * chromaWeight
			val score = proportionScore + chromaScore
			scoredHcts.add(ScoredHCT(hct, score))
		}
		// Sorted so that colors with higher scores come first.
		scoredHcts.sortBy { it.score }

		// Iterates through potential hue differences in degrees in order to select
		// the colors with the largest distribution of hues possible. Starting at
		// 90 degrees(maximum difference for 4 colors) then decreasing down to a
		// 15 degree minimum.
		val chosenColors = ArrayList<Hct>()
		for (differenceDegrees in 90 downTo 15) {
			chosenColors.clear()
			for (entry in scoredHcts) {
				val hct = entry.hct
				var hasDuplicateHue = false
				for (chosenHct in chosenColors) {
					if (differenceDegrees(hct.hue, chosenHct.hue) < differenceDegrees) {
						hasDuplicateHue = true
						break
					}
				}
				if (!hasDuplicateHue) {
					chosenColors.add(hct)
				}
				if (chosenColors.size >= desired) {
					break
				}
			}
			if (chosenColors.size >= desired) {
				break
			}
		}
		val colors = ArrayList<Argb>()
		if (chosenColors.isEmpty()) {
			colors.add(fallbackColorArgb)
		}
		for (chosenHct in chosenColors) {
			colors.add(chosenHct.argb)
		}
		return colors
	}

	private class ScoredHCT(val hct: Hct, val score: Double)
}
