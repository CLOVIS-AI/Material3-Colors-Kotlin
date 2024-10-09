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

import opensavvy.material3.colors.argb.Argb
import opensavvy.material3.colors.argb.ArgbArray

/**
 * The simplest [Quantizer] implementation: simply counts the pixels, without doing any color elimination.
 */
object QuantizerMap : Quantizer {

	/**
	 * Counts how many times each color appears in [pixels].
	 *
	 * [maxColors] is ignored.
	 */
	override fun quantize(pixels: ArgbArray, maxColors: Int): Quantization =
		quantize(pixels)

	/**
	 * Counts how many times each color appears in [pixels].
	 */
	fun quantize(pixels: ArgbArray): Quantization {
		val result = LinkedHashMap<Argb, Int>()
		for (pixel in pixels) {
			result[pixel] = (result[pixel] ?: 0) + 1
		}
		return result
	}
}
