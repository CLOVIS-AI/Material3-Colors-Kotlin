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
import opensavvy.material3.colors.dynamiccolor.Variant.*
import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.palettes.TonalPalette

/**
 * Parent class for all built-in scheme generators.
 *
 * Each generator is available as an extension function on [BuiltInScheme][BuiltInScheme.Companion].
 */
class BuiltInScheme(
	val sourceColor: Hct,
	val variant: Variant,
	override val isDark: Boolean,
	override val contrastLevel: Double,
	override val primaryPalette: TonalPalette,
	override val secondaryPalette: TonalPalette,
	override val tertiaryPalette: TonalPalette,
	override val neutralPalette: TonalPalette,
	override val neutralVariantPalette: TonalPalette,
	override val errorPalette: TonalPalette = DynamicScheme.defaultErrorPalette,
) : DynamicScheme {

	/**
	 * Built-in scheme generators.
	 */
	companion object {

		/**
		 * Builds a color scheme based on the passed [variant].
		 */
		fun ofVariant(
			variant: Variant,
			source: Hct,
			isDark: Boolean,
			contrastLevel: Double,
		): BuiltInScheme = when (variant) {
			MONOCHROME -> schemeMonochrome(source, isDark, contrastLevel)
			NEUTRAL -> schemeNeutral(source, isDark, contrastLevel)
			TONAL_SPOT -> schemeTonalSpot(source, isDark, contrastLevel)
			VIBRANT -> schemeVibrant(source, isDark, contrastLevel)
			EXPRESSIVE -> schemeExpressive(source, isDark, contrastLevel)
			FIDELITY -> schemeFidelity(source, isDark, contrastLevel)
			CONTENT -> schemeContent(source, isDark, contrastLevel)
			RAINBOW -> schemeRainbow(source, isDark, contrastLevel)
			FRUIT_SALAD -> schemeFruitSalad(source, isDark, contrastLevel)
		}
	}
}
