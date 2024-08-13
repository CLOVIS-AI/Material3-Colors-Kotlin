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

package opensavvy.material3.colors.dynamiccolor

import opensavvy.material3.colors.hct.Hct
import opensavvy.material3.colors.palettes.TonalPalette
import opensavvy.material3.colors.utils.Color
import opensavvy.material3.colors.utils.sanitizeDegreesDouble
import kotlin.jvm.JvmOverloads

/**
 * Provides important settings for creating colors dynamically, and 6 color palettes. Requires: 1. A
 * color. (source color) 2. A theme. (Variant) 3. Whether or not its dark mode. 4. Contrast level.
 * (-1 to 1, currently contrast ratio 3.0 and 7.0)
 */
open class DynamicScheme @JvmOverloads constructor(
	val sourceColorHct: Hct,
	val variant: Variant,
	val isDark: Boolean,
	val contrastLevel: Double,
	val primaryPalette: TonalPalette,
	val secondaryPalette: TonalPalette,
	val tertiaryPalette: TonalPalette,
	val neutralPalette: TonalPalette,
	val neutralVariantPalette: TonalPalette,
	errorPalette: TonalPalette? = null,
) {
	val sourceColorArgb: Color = sourceColorHct.toColor()

	val errorPalette: TonalPalette = errorPalette ?: TonalPalette.fromHueAndChroma(25.0, 84.0)

	fun getHct(dynamicColor: DynamicColor): Hct {
		return dynamicColor.getHct(this)
	}

	fun getArgb(dynamicColor: DynamicColor): Color {
		return dynamicColor.getArgb(this)
	}

	val primaryPaletteKeyColor: Color
		get() = getArgb(MaterialDynamicColors().primaryPaletteKeyColor())

	val secondaryPaletteKeyColor: Color
		get() = getArgb(MaterialDynamicColors().secondaryPaletteKeyColor())

	val tertiaryPaletteKeyColor: Color
		get() = getArgb(MaterialDynamicColors().tertiaryPaletteKeyColor())

	val neutralPaletteKeyColor: Color
		get() = getArgb(MaterialDynamicColors().neutralPaletteKeyColor())

	val neutralVariantPaletteKeyColor: Color
		get() = getArgb(MaterialDynamicColors().neutralVariantPaletteKeyColor())

	val background: Color
		get() = getArgb(MaterialDynamicColors().background())

	val onBackground: Color
		get() = getArgb(MaterialDynamicColors().onBackground())

	val surface: Color
		get() = getArgb(MaterialDynamicColors().surface())

	val surfaceDim: Color
		get() = getArgb(MaterialDynamicColors().surfaceDim())

	val surfaceBright: Color
		get() = getArgb(MaterialDynamicColors().surfaceBright())

	val surfaceContainerLowest: Color
		get() = getArgb(MaterialDynamicColors().surfaceContainerLowest())

	val surfaceContainerLow: Color
		get() = getArgb(MaterialDynamicColors().surfaceContainerLow())

	val surfaceContainer: Color
		get() = getArgb(MaterialDynamicColors().surfaceContainer())

	val surfaceContainerHigh: Color
		get() = getArgb(MaterialDynamicColors().surfaceContainerHigh())

	val surfaceContainerHighest: Color
		get() = getArgb(MaterialDynamicColors().surfaceContainerHighest())

	val onSurface: Color
		get() = getArgb(MaterialDynamicColors().onSurface())

	val surfaceVariant: Color
		get() = getArgb(MaterialDynamicColors().surfaceVariant())

	val onSurfaceVariant: Color
		get() = getArgb(MaterialDynamicColors().onSurfaceVariant())

	val inverseSurface: Color
		get() = getArgb(MaterialDynamicColors().inverseSurface())

	val inverseOnSurface: Color
		get() = getArgb(MaterialDynamicColors().inverseOnSurface())

	val outline: Color
		get() = getArgb(MaterialDynamicColors().outline())

	val outlineVariant: Color
		get() = getArgb(MaterialDynamicColors().outlineVariant())

	val shadow: Color
		get() = getArgb(MaterialDynamicColors().shadow())

	val scrim: Color
		get() = getArgb(MaterialDynamicColors().scrim())

	val surfaceTint: Color
		get() = getArgb(MaterialDynamicColors().surfaceTint())

	val primary: Color
		get() = getArgb(MaterialDynamicColors().primary())

	val onPrimary: Color
		get() = getArgb(MaterialDynamicColors().onPrimary())

	val primaryContainer: Color
		get() = getArgb(MaterialDynamicColors().primaryContainer())

	val onPrimaryContainer: Color
		get() = getArgb(MaterialDynamicColors().onPrimaryContainer())

	val inversePrimary: Color
		get() = getArgb(MaterialDynamicColors().inversePrimary())

	val secondary: Color
		get() = getArgb(MaterialDynamicColors().secondary())

	val onSecondary: Color
		get() = getArgb(MaterialDynamicColors().onSecondary())

	val secondaryContainer: Color
		get() = getArgb(MaterialDynamicColors().secondaryContainer())

	val onSecondaryContainer: Color
		get() = getArgb(MaterialDynamicColors().onSecondaryContainer())

	val tertiary: Color
		get() = getArgb(MaterialDynamicColors().tertiary())

	val onTertiary: Color
		get() = getArgb(MaterialDynamicColors().onTertiary())

	val tertiaryContainer: Color
		get() = getArgb(MaterialDynamicColors().tertiaryContainer())

	val onTertiaryContainer: Color
		get() = getArgb(MaterialDynamicColors().onTertiaryContainer())

	val error: Color
		get() = getArgb(MaterialDynamicColors().error())

	val onError: Color
		get() = getArgb(MaterialDynamicColors().onError())

	val errorContainer: Color
		get() = getArgb(MaterialDynamicColors().errorContainer())

	val onErrorContainer: Color
		get() = getArgb(MaterialDynamicColors().onErrorContainer())

	val primaryFixed: Color
		get() = getArgb(MaterialDynamicColors().primaryFixed())

	val primaryFixedDim: Color
		get() = getArgb(MaterialDynamicColors().primaryFixedDim())

	val onPrimaryFixed: Color
		get() = getArgb(MaterialDynamicColors().onPrimaryFixed())

	val onPrimaryFixedVariant: Color
		get() = getArgb(MaterialDynamicColors().onPrimaryFixedVariant())

	val secondaryFixed: Color
		get() = getArgb(MaterialDynamicColors().secondaryFixed())

	val secondaryFixedDim: Color
		get() = getArgb(MaterialDynamicColors().secondaryFixedDim())

	val onSecondaryFixed: Color
		get() = getArgb(MaterialDynamicColors().onSecondaryFixed())

	val onSecondaryFixedVariant: Color
		get() = getArgb(MaterialDynamicColors().onSecondaryFixedVariant())

	val tertiaryFixed: Color
		get() = getArgb(MaterialDynamicColors().tertiaryFixed())

	val tertiaryFixedDim: Color
		get() = getArgb(MaterialDynamicColors().tertiaryFixedDim())

	val onTertiaryFixed: Color
		get() = getArgb(MaterialDynamicColors().onTertiaryFixed())

	val onTertiaryFixedVariant: Color
		get() = getArgb(MaterialDynamicColors().onTertiaryFixedVariant())

	val controlActivated: Color
		get() = getArgb(MaterialDynamicColors().controlActivated())

	val controlNormal: Color
		get() = getArgb(MaterialDynamicColors().controlNormal())

	val controlHighlight: Color
		get() = getArgb(MaterialDynamicColors().controlHighlight())

	val textPrimaryInverse: Color
		get() = getArgb(MaterialDynamicColors().textPrimaryInverse())

	val textSecondaryAndTertiaryInverse: Color
		get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverse())

	val textPrimaryInverseDisableOnly: Color
		get() = getArgb(MaterialDynamicColors().textPrimaryInverseDisableOnly())

	val textSecondaryAndTertiaryInverseDisabled: Color
		get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverseDisabled())

	val textHintInverse: Color
		get() = getArgb(MaterialDynamicColors().textHintInverse())

	companion object {
		/**
		 * Given a set of hues and set of hue rotations, locate which hues the source color's hue is
		 * between, apply the rotation at the same index as the first hue in the range, and return the
		 * rotated hue.
		 *
		 * @param sourceColorHct The color whose hue should be rotated.
		 * @param hues A set of hues.
		 * @param rotations A set of hue rotations.
		 * @return Color's hue with a rotation applied.
		 */
		fun getRotatedHue(sourceColorHct: Hct, hues: DoubleArray, rotations: DoubleArray): Double {
			val sourceHue = sourceColorHct.getHue()
			if (rotations.size == 1) {
				return sanitizeDegreesDouble(sourceHue + rotations[0])
			}
			val size = hues.size
			for (i in 0..(size - 2)) {
				val thisHue = hues[i]
				val nextHue = hues[i + 1]
				if (thisHue < sourceHue && sourceHue < nextHue) {
					return sanitizeDegreesDouble(sourceHue + rotations[i])
				}
			}
			// If this statement executes, something is wrong, there should have been a rotation
			// found using the arrays.
			return sourceHue
		}
	}
}
