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

/**
 * Represents the results of quantizing an image.
 *
 * A quantization is a mapping from a color to a number of occurrences of that color.
 *
 * The goal of a quantization is to reduce the number of colors extracted from an image.
 * By using quantization, similar colors are combined into a single color.
 *
 * To obtain a quantization, see [Quantizer].
 */
typealias Quantization = Map<Argb, Int>
