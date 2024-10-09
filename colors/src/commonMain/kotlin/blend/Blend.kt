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

package opensavvy.material3.colors.blend

import opensavvy.material3.colors.Argb
import opensavvy.material3.colors.hct.Cam16
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.utils.differenceDegrees
import opensavvy.material3.colors.utils.rotationDirection
import opensavvy.material3.colors.utils.sanitizeDegreesDouble
import kotlin.math.min

/** Functions for blending in HCT and CAM16.  */
object Blend {
	/**
	 * Blend the design color's HCT hue towards the key color's HCT hue, in a way that leaves the
	 * original color recognizable and recognizably shifted towards the key color.
	 *
	 * @param designArgb ARGB representation of an arbitrary color.
	 * @param sourceColor ARGB representation of the main theme color.
	 * @return The design color with a hue shifted towards the system's color, a slightly
	 * warmer/cooler variant of the design color's hue.
	 */
	fun harmonize(designArgb: Argb, sourceColor: Argb): Argb {
		val fromHct = Hct(designArgb)
		val toHct = Hct(sourceColor)
		val differenceDegrees = differenceDegrees(fromHct.hue, toHct.hue)
		val rotationDegrees = min(differenceDegrees * 0.5, 15.0)
		val outputHue =
			sanitizeDegreesDouble(
				fromHct.hue
					+ rotationDegrees * rotationDirection(fromHct.hue, toHct.hue))
		return Hct(outputHue, fromHct.chroma, fromHct.tone).argb
	}

	/**
	 * Blends hue from one color into another. The chroma and tone of the original color are
	 * maintained.
	 *
	 * @param from ARGB representation of color
	 * @param to ARGB representation of color
	 * @param amount how much blending to perform; 0.0 >= and <= 1.0
	 * @return from, with a hue blended towards to. Chroma and tone are constant.
	 */
	fun hctHue(from: Argb, to: Argb, amount: Double): Argb {
		val ucs = cam16Ucs(from, to, amount)
		val ucsCam = Cam16.fromArgb(ucs)
		val fromCam = Cam16.fromArgb(from)
		val blended = Hct(ucsCam.hue, fromCam.chroma, from.toLstar())
		return blended.argb
	}

	/**
	 * Blend in CAM16-UCS space.
	 *
	 * @param from ARGB representation of color
	 * @param to ARGB representation of color
	 * @param amount how much blending to perform; 0.0 >= and <= 1.0
	 * @return from, blended towards to. Hue, chroma, and tone will change.
	 */
	fun cam16Ucs(from: Argb, to: Argb, amount: Double): Argb {
		val fromCam = Cam16.fromArgb(from)
		val toCam = Cam16.fromArgb(to)
		val fromJ = fromCam.jstar
		val fromA = fromCam.astar
		val fromB = fromCam.bstar
		val toJ = toCam.jstar
		val toA = toCam.astar
		val toB = toCam.bstar
		val jstar = fromJ + (toJ - fromJ) * amount
		val astar = fromA + (toA - fromA) * amount
		val bstar = fromB + (toB - fromB) * amount
		return Cam16.fromUcs(jstar, astar, bstar).toColor()
	}
}
