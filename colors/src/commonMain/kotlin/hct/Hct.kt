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

package opensavvy.material3.colors.hct

import opensavvy.material3.colors.utils.Color
import opensavvy.material3.colors.utils.Color.Companion.lstarFromY

/**
 * A color system built using CAM16 hue and chroma, and L* from L*a*b*.
 *
 *
 * Using L* creates a link between the color system, contrast, and thus accessibility. Contrast
 * ratio depends on relative luminance, or Y in the XYZ color space. L*, or perceptual luminance can
 * be calculated from Y.
 *
 *
 * Unlike Y, L* is linear to human perception, allowing trivial creation of accurate color tones.
 *
 *
 * Unlike contrast ratio, measuring contrast in L* is linear, and simple to calculate. A
 * difference of 40 in HCT tone guarantees a contrast ratio >= 3.0, and a difference of 50
 * guarantees a contrast ratio >= 4.5.
 */
/**
 * HCT, hue, chroma, and tone. A color system that provides a perceptually accurate color
 * measurement system that can also accurately render what colors will appear as in different
 * lighting environments.
 */
class Hct private constructor(argb: Color) {
	private var hue = 0.0
	private var chroma = 0.0
	private var tone = 0.0
	private var argb: Color = Color(0)

	init {
		setInternalState(argb)
	}

	fun getHue(): Double {
		return hue
	}

	fun getChroma(): Double {
		return chroma
	}

	fun getTone(): Double {
		return tone
	}

	fun toColor(): Color {
		return argb
	}

	/**
	 * Set the hue of this color. Chroma may decrease because chroma has a different maximum for any
	 * given hue and tone.
	 *
	 * @param newHue 0 <= newHue < 360; invalid values are corrected.
	 */
	fun setHue(newHue: Double) {
		setInternalState(HctSolver.solveToColor(newHue, chroma, tone))
	}

	/**
	 * Set the chroma of this color. Chroma may decrease because chroma has a different maximum for
	 * any given hue and tone.
	 *
	 * @param newChroma 0 <= newChroma < ?
	 */
	fun setChroma(newChroma: Double) {
		setInternalState(HctSolver.solveToColor(hue, newChroma, tone))
	}

	/**
	 * Set the tone of this color. Chroma may decrease because chroma has a different maximum for any
	 * given hue and tone.
	 *
	 * @param newTone 0 <= newTone <= 100; invalid valids are corrected.
	 */
	fun setTone(newTone: Double) {
		setInternalState(HctSolver.solveToColor(hue, chroma, newTone))
	}

	/**
	 * Translate a color into different ViewingConditions.
	 *
	 *
	 * Colors change appearance. They look different with lights on versus off, the same color, as
	 * in hex code, on white looks different when on black. This is called color relativity, most
	 * famously explicated by Josef Albers in Interaction of Color.
	 *
	 *
	 * In color science, color appearance models can account for this and calculate the appearance
	 * of a color in different settings. HCT is based on CAM16, a color appearance model, and uses it
	 * to make these calculations.
	 *
	 *
	 * See ViewingConditions.make for parameters affecting color appearance.
	 */
	fun inViewingConditions(vc: ViewingConditions): Hct {
		// 1. Use CAM16 to find XYZ coordinates of color in specified VC.
		val cam16 = Cam16.fromColor(toColor())
		val viewedInVc = cam16.xyzInViewingConditions(vc, null)

		// 2. Create CAM16 of those XYZ coordinates in default VC.
		val recastInVc =
			Cam16.fromXyzInViewingConditions(
				viewedInVc[0], viewedInVc[1], viewedInVc[2], ViewingConditions.DEFAULT)

		// 3. Create HCT from:
		// - CAM16 using default VC with XYZ coordinates in specified VC.
		// - L* converted from Y in XYZ coordinates in specified VC.
		return from(
			recastInVc.hue, recastInVc.chroma, lstarFromY(viewedInVc[1]))
	}

	private fun setInternalState(argb: Color) {
		this.argb = argb
		val cam = Cam16.fromColor(argb)
		hue = cam.hue
		chroma = cam.chroma
		this.tone = argb.toLstar()
	}

	// region Identity

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Hct) return false

		if (argb != other.argb) return false

		return true
	}

	override fun hashCode(): Int =
		argb.hashCode()

	// endregion

	override fun toString(): String =
		"Hct($hue $chroma $tone • $argb)"

	companion object {
		/**
		 * Create an HCT color from hue, chroma, and tone.
		 *
		 * @param hue 0 <= hue < 360; invalid values are corrected.
		 * @param chroma 0 <= chroma < ?; Informally, colorfulness. The color returned may be lower than
		 * the requested chroma. Chroma has a different maximum for any given hue and tone.
		 * @param tone 0 <= tone <= 100; invalid values are corrected.
		 * @return HCT representation of a color in default viewing conditions.
		 */
		fun from(hue: Double, chroma: Double, tone: Double): Hct {
			val argb: Color = HctSolver.solveToColor(hue, chroma, tone)
			return Hct(argb)
		}

		/**
		 * Create an HCT color from a color.
		 *
		 * @param argb ARGB representation of a color.
		 * @return HCT representation of a color in default viewing conditions
		 */
		fun fromInt(argb: Int): Hct {
			return Hct(Color(argb))
		}

		fun fromColor(color: Color): Hct {
			return Hct(color)
		}
	}
}
