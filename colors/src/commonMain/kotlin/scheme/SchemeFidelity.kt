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

package opensavvy.material3.colors.scheme

import opensavvy.material3.colors.dislike.fixIfDisliked
import opensavvy.material3.colors.dynamiccolor.Variant
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.palettes.TonalPalette
import opensavvy.material3.colors.temperature.TemperatureCache
import kotlin.math.max

/**
 * A scheme that places the source color in Scheme.primaryContainer.
 *
 * Primary Container is the source color, adjusted for color relativity. It maintains constant
 * appearance in light mode and dark mode. This adds ~5 tone in light mode, and subtracts ~5 tone in
 * dark mode.
 *
 * Tertiary Container is the complement to the source color, using TemperatureCache. It also
 * maintains constant appearance.
 */
fun BuiltInScheme.Companion.schemeFidelity(
	source: Hct,
	isDark: Boolean,
	contrastLevel: Double,
) = BuiltInScheme(
	sourceColor = source,
	variant = Variant.FIDELITY,
	isDark = isDark,
	contrastLevel = contrastLevel,
	primaryPalette = TonalPalette.fromHueAndChroma(source.hue, source.chroma),
	secondaryPalette = TonalPalette.fromHueAndChroma(
		source.hue,
		max(source.chroma - 32.0, source.chroma * 0.5)),
	tertiaryPalette = TonalPalette.fromHct(
		TemperatureCache(source).complement.fixIfDisliked()
	),
	neutralPalette = TonalPalette.fromHueAndChroma(source.hue, source.chroma / 8.0),
	neutralVariantPalette = TonalPalette.fromHueAndChroma(
		source.hue, (source.chroma / 8.0) + 4.0)
)