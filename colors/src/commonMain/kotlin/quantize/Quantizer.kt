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
 * A utility to create a [Quantization] from an [ArgbArray].
 *
 * Quantizers analyze an image to extract the most important colors by combining similar colors together.
 */
fun interface Quantizer {

	/**
	 * Analyze [pixels] to extract the most important colors.
	 *
	 * @param pixels A collection of pixels in [Argb] format.
	 * @param maxColors The maximum number of colors that the returned quantization is allowed to use.
	 * Note that some quantizers ignore this parameter.
	 */
	fun quantize(pixels: ArgbArray, maxColors: Int): Quantization
}
