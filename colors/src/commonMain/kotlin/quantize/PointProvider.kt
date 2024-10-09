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

package opensavvy.material3.colors.quantize

import opensavvy.material3.colors.Argb

/** An interface to allow use of different color spaces by quantizers.  */
interface PointProvider {

	/**
	 * The four components in the color space, from a sRGB [argb].
	 */
	fun fromArgb(argb: Argb): DoubleArray

	/**
	 * The color representation of this [point].
	 */
	fun toArgb(point: DoubleArray): Argb

	/**
	 * Squared distance between two colors. Distance is defined by scientific color spaces and
	 * referred to as delta E.
	 */
	fun distance(one: DoubleArray, two: DoubleArray): Double
}
