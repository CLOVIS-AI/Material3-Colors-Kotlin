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

import opensavvy.material3.colors.dynamiccolor.DynamicScheme
import opensavvy.material3.colors.dynamiccolor.Variant
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.palettes.TonalPalette
import opensavvy.material3.colors.utils.MathUtils.sanitizeDegreesDouble

/** A playful theme - the source color's hue does not appear in the theme.  */
class SchemeRainbow(sourceColorHct: Hct, isDark: Boolean, contrastLevel: Double) : DynamicScheme(
	sourceColorHct = sourceColorHct,
	variant = Variant.RAINBOW,
	isDark = isDark,
	contrastLevel = contrastLevel,
	primaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.getHue(), 48.0),
	secondaryPalette = TonalPalette.fromHueAndChroma(sourceColorHct.getHue(), 16.0),
	tertiaryPalette = TonalPalette.fromHueAndChroma(
		sanitizeDegreesDouble(sourceColorHct.getHue() + 60.0), 24.0),
	neutralPalette = TonalPalette.fromHueAndChroma(sourceColorHct.getHue(), 0.0),
	neutralVariantPalette = TonalPalette.fromHueAndChroma(sourceColorHct.getHue(), 0.0))
