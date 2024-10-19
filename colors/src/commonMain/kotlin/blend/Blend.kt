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

import opensavvy.material3.colors.argb.Argb
import opensavvy.material3.colors.hct.Cam16
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.utils.differenceDegrees
import opensavvy.material3.colors.utils.rotationDirection
import opensavvy.material3.colors.utils.sanitizeDegreesDouble
import kotlin.math.min

/**
 * Blend this color's HCT hue towards the [keyColor]'s HCT hue, in a way that leaves the
 * original color recognizable and recognizably shifted towards the [keyColor].
 *
 * @return The current color, with a hue shifted towards the [keyColor]:
 * a slightly warmer/cooler variant of the current color, that harmonizes better with [keyColor].
 */
fun Argb.harmonizeWith(keyColor: Argb): Argb {
	val design = Hct(this)
	val key = Hct(keyColor)
	val differenceDegrees = differenceDegrees(design.hue, key.hue)
	val rotationDegrees = min(differenceDegrees * 0.5, 15.0)
	val outputHue = sanitizeDegreesDouble(
		design.hue + rotationDegrees * rotationDirection(design.hue, key.hue)
	)
	return Hct(outputHue, design.chroma, design.tone).argb
}

/**
 * Blends hue from the current color into [other]. The chroma and tone of the original color are
 * maintained.
 *
 * @param amount How much blending to perform, in `0.0 .. 1.0`.
 * @return The current color, with a hue blended towards [other]. Chroma and tone are not modified.
 */
fun Argb.blendHueTowards(other: Argb, amount: Double = 0.5): Argb {
	val ucs = this.blendInCam16Ucs(other, amount)
	val ucsCam = Cam16.fromArgb(ucs)
	val fromCam = Cam16.fromArgb(this)
	val blended = Hct(ucsCam.hue, fromCam.chroma, other.toLstar())
	return blended.argb
}

/**
 * Blend the current color with [other] in CAM16-UCS space.
 *
 * @param amount How much blending to perform, in `0.0 .. 1.0`.
 * @return The current color, blended towards [other]. Hue, chroma and tone will change.
 */
fun Argb.blendInCam16Ucs(other: Argb, amount: Double = 0.5): Argb {
	val fromCam = Cam16.fromArgb(this)
	val toCam = Cam16.fromArgb(other)
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
