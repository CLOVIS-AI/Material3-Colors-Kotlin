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

package opensavvy.material3.colors.palettes

import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.utils.Color
import kotlin.math.abs

/**
 * A convenience class for retrieving colors that are constant in hue and chroma, but vary in tone.
 *
 *
 * TonalPalette is intended for use in a single thread due to its stateful caching.
 */
class TonalPalette private constructor(
	/** The hue of the Tonal Palette, in HCT. Ranges from 0 to 360.  */
	var hue: Double,
	/** The chroma of the Tonal Palette, in HCT. Ranges from 0 to ~130 (for sRGB gamut).  */
	var chroma: Double,
	/** The key color is the first tone, starting from T50, that matches the palette's chroma.  */
	var keyColor: Hct,
) {
	var cache: MutableMap<Int, Color> = HashMap()

	/**
	 * Create an ARGB color with HCT hue and chroma of this Tones instance, and the provided HCT tone.
	 *
	 * @param tone HCT tone, measured from 0 to 100.
	 * @return ARGB representation of a color with that tone.
	 */
	fun tone(tone: Int): Color {
		var color = cache[tone]
		if (color == null) {
			color = Hct(this.hue, this.chroma, tone.toDouble()).argb
			cache[tone] = color
		}
		return color
	}

	/** Given a tone, use hue and chroma of palette to create a color, and return it as HCT.  */
	fun getHct(tone: Double): Hct {
		return Hct(this.hue, this.chroma, tone)
	}

	/** Key color is a color that represents the hue and chroma of a tonal palette.  */
	private class KeyColor
	/** Key color is a color that represents the hue and chroma of a tonal palette  */(private val hue: Double, private val requestedChroma: Double) {
		// Cache that maps tone to max chroma to avoid duplicated HCT calculation.
		private val chromaCache = HashMap<Int, Double>()

		/**
		 * Creates a key color from a [hue] and a [chroma]. The key color is the first tone, starting
		 * from T50, matching the given hue and chroma.
		 *
		 * @return Key color [Hct]
		 */
		fun create(): Hct {
			// Pivot around T50 because T50 has the most chroma available, on
			// average. Thus it is most likely to have a direct answer.
			val pivotTone = 50
			val toneStepSize = 1
			// Epsilon to accept values slightly higher than the requested chroma.
			val epsilon = 0.01

			// Binary search to find the tone that can provide a chroma that is closest
			// to the requested chroma.
			var lowerTone = 0
			var upperTone = 100
			while (lowerTone < upperTone) {
				val midTone = (lowerTone + upperTone) / 2
				val isAscending = maxChroma(midTone) < maxChroma(midTone + toneStepSize)
				val sufficientChroma = maxChroma(midTone) >= requestedChroma - epsilon

				if (sufficientChroma) {
					// Either range [lowerTone, midTone] or [midTone, upperTone] has
					// the answer, so search in the range that is closer the pivot tone.
					if (abs((lowerTone - pivotTone).toDouble()) < abs((upperTone - pivotTone).toDouble())) {
						upperTone = midTone
					} else {
						if (lowerTone == midTone) {
							return Hct(this.hue, this.requestedChroma, lowerTone.toDouble())
						}
						lowerTone = midTone
					}
				} else {
					// As there is no sufficient chroma in the midTone, follow the direction to the chroma
					// peak.
					if (isAscending) {
						lowerTone = midTone + toneStepSize
					} else {
						// Keep midTone for potential chroma peak.
						upperTone = midTone
					}
				}
			}

			return Hct(this.hue, this.requestedChroma, lowerTone.toDouble())
		}

		// Find the maximum chroma for a given tone
		fun maxChroma(tone: Int): Double {
			if (chromaCache[tone] == null) {
				val newChroma = Hct(hue, MAX_CHROMA_VALUE, tone.toDouble()).chroma
				if (newChroma != null) {
					chromaCache[tone] = newChroma
				}
			}
			return chromaCache[tone]!!
		}

		companion object {
			private const val MAX_CHROMA_VALUE = 200.0
		}
	}

	companion object {
		/**
		 * Create tones using the HCT hue and chroma from a color.
		 *
		 * @param argb ARGB representation of a color
		 * @return Tones matching that color's hue and chroma.
		 */
		fun fromInt(argb: Int): TonalPalette {
			return fromHct(Hct(Color(argb)))
		}

		/**
		 * Create tones using a HCT color.
		 *
		 * @param hct HCT representation of a color.
		 * @return Tones matching that color's hue and chroma.
		 */
		fun fromHct(hct: Hct): TonalPalette {
			return TonalPalette(hct.hue, hct.chroma, hct)
		}

		/**
		 * Create tones from a defined HCT hue and chroma.
		 *
		 * @param hue HCT hue
		 * @param chroma HCT chroma
		 * @return Tones matching hue and chroma.
		 */
		fun fromHueAndChroma(hue: Double, chroma: Double): TonalPalette {
			val keyColor = KeyColor(hue, chroma).create()
			return TonalPalette(hue, chroma, keyColor)
		}
	}
}
