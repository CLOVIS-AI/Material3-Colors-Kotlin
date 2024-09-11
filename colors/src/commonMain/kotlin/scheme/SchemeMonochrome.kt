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

import opensavvy.material3.colors.dynamiccolor.Variant
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.palettes.TonalPalette

/**
 * A monochrome theme, colors are purely black / white / gray.
 */
fun BuiltInScheme.Companion.schemeMonochrome(
	source: Hct,
	isDark: Boolean,
	contrastLevel: Double,
) = BuiltInScheme(
	sourceColor = source,
	variant = Variant.MONOCHROME,
	isDark = isDark,
	contrastLevel = contrastLevel,
	primaryPalette = TonalPalette.fromHueAndChroma(source.hue, 0.0),
	secondaryPalette = TonalPalette.fromHueAndChroma(source.hue, 0.0),
	tertiaryPalette = TonalPalette.fromHueAndChroma(source.hue, 0.0),
	neutralPalette = TonalPalette.fromHueAndChroma(source.hue, 0.0),
	neutralVariantPalette = TonalPalette.fromHueAndChroma(source.hue, 0.0)
)
