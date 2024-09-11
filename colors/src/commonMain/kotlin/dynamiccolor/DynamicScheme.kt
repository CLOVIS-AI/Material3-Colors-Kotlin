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

interface DynamicScheme {

	/**
	 * `true` if this scheme is a dark scheme, `false` if it is a light scheme.
	 */
	val isDark: Boolean

	/**
	 * Contrast level, normalized to `-1…1`.
	 *
	 * Currently corresponds to a contrast ratio of 3.0 and 7.0.
	 */
	val contrastLevel: Double

	val primaryPalette: TonalPalette
	val secondaryPalette: TonalPalette
	val tertiaryPalette: TonalPalette
	val neutralPalette: TonalPalette
	val neutralVariantPalette: TonalPalette
	val errorPalette: TonalPalette

	companion object {
		val defaultErrorPalette = TonalPalette.fromHueAndChroma(25.0, 84.0)
	}
}

fun DynamicScheme.toHct(color: DynamicColor) =
	color.getHct(this)

fun DynamicScheme.getArgb(color: DynamicColor) =
	color.getArgb(this)

val DynamicScheme.primaryPaletteKeyColor: Color get() = getArgb(MaterialDynamicColors().primaryPaletteKeyColor())
val DynamicScheme.secondaryPaletteKeyColor: Color get() = getArgb(MaterialDynamicColors().secondaryPaletteKeyColor())
val DynamicScheme.tertiaryPaletteKeyColor: Color get() = getArgb(MaterialDynamicColors().tertiaryPaletteKeyColor())
val DynamicScheme.neutralPaletteKeyColor: Color get() = getArgb(MaterialDynamicColors().neutralPaletteKeyColor())
val DynamicScheme.neutralVariantPaletteKeyColor: Color get() = getArgb(MaterialDynamicColors().neutralVariantPaletteKeyColor())
val DynamicScheme.background: Color get() = getArgb(MaterialDynamicColors().background())
val DynamicScheme.onBackground: Color get() = getArgb(MaterialDynamicColors().onBackground())
val DynamicScheme.surface: Color get() = getArgb(MaterialDynamicColors().surface())
val DynamicScheme.surfaceDim: Color get() = getArgb(MaterialDynamicColors().surfaceDim())
val DynamicScheme.surfaceBright: Color get() = getArgb(MaterialDynamicColors().surfaceBright())
val DynamicScheme.surfaceContainerLowest: Color get() = getArgb(MaterialDynamicColors().surfaceContainerLowest())
val DynamicScheme.surfaceContainerLow: Color get() = getArgb(MaterialDynamicColors().surfaceContainerLow())
val DynamicScheme.surfaceContainer: Color get() = getArgb(MaterialDynamicColors().surfaceContainer())
val DynamicScheme.surfaceContainerHigh: Color get() = getArgb(MaterialDynamicColors().surfaceContainerHigh())
val DynamicScheme.surfaceContainerHighest: Color get() = getArgb(MaterialDynamicColors().surfaceContainerHighest())
val DynamicScheme.onSurface: Color get() = getArgb(MaterialDynamicColors().onSurface())
val DynamicScheme.onSurfaceVariant: Color get() = getArgb(MaterialDynamicColors().onSurfaceVariant())
val DynamicScheme.inverseSurface: Color get() = getArgb(MaterialDynamicColors().inverseSurface())
val DynamicScheme.inverseOnSurface: Color get() = getArgb(MaterialDynamicColors().inverseOnSurface())
val DynamicScheme.outline: Color get() = getArgb(MaterialDynamicColors().outline())
val DynamicScheme.outlineVariant: Color get() = getArgb(MaterialDynamicColors().outlineVariant())
val DynamicScheme.shadow: Color get() = getArgb(MaterialDynamicColors().shadow())
val DynamicScheme.scrim: Color get() = getArgb(MaterialDynamicColors().scrim())
val DynamicScheme.surfaceTint: Color get() = getArgb(MaterialDynamicColors().surfaceTint())

val DynamicScheme.primary: Color get() = getArgb(MaterialDynamicColors().primary())
val DynamicScheme.onPrimary: Color get() = getArgb(MaterialDynamicColors().onPrimary())
val DynamicScheme.primaryContainer: Color get() = getArgb(MaterialDynamicColors().primaryContainer())
val DynamicScheme.onPrimaryContainer: Color get() = getArgb(MaterialDynamicColors().onPrimaryContainer())
val DynamicScheme.inversePrimary: Color get() = getArgb(MaterialDynamicColors().inversePrimary())

val DynamicScheme.secondary: Color get() = getArgb(MaterialDynamicColors().secondary())
val DynamicScheme.onSecondary: Color get() = getArgb(MaterialDynamicColors().onSecondary())
val DynamicScheme.secondaryContainer: Color get() = getArgb(MaterialDynamicColors().secondaryContainer())
val DynamicScheme.onSecondaryContainer: Color get() = getArgb(MaterialDynamicColors().onSecondaryContainer())

val DynamicScheme.tertiary: Color get() = getArgb(MaterialDynamicColors().tertiary())
val DynamicScheme.onTertiary: Color get() = getArgb(MaterialDynamicColors().onTertiary())
val DynamicScheme.tertiaryContainer: Color get() = getArgb(MaterialDynamicColors().tertiaryContainer())
val DynamicScheme.onTertiaryContainer: Color get() = getArgb(MaterialDynamicColors().onTertiaryContainer())

val DynamicScheme.error: Color get() = getArgb(MaterialDynamicColors().error())
val DynamicScheme.onError: Color get() = getArgb(MaterialDynamicColors().onError())
val DynamicScheme.errorContainer: Color get() = getArgb(MaterialDynamicColors().errorContainer())
val DynamicScheme.onErrorContainer: Color get() = getArgb(MaterialDynamicColors().onErrorContainer())

val DynamicScheme.primaryFixed: Color get() = getArgb(MaterialDynamicColors().primaryFixed())
val DynamicScheme.primaryFixedDim: Color get() = getArgb(MaterialDynamicColors().primaryFixedDim())
val DynamicScheme.onPrimaryFixed: Color get() = getArgb(MaterialDynamicColors().onPrimaryFixed())
val DynamicScheme.onPrimaryFixedVariant: Color get() = getArgb(MaterialDynamicColors().onPrimaryFixedVariant())

val DynamicScheme.secondaryFixed: Color get() = getArgb(MaterialDynamicColors().secondaryFixed())
val DynamicScheme.secondaryFixedDim: Color get() = getArgb(MaterialDynamicColors().secondaryFixedDim())
val DynamicScheme.onSecondaryFixed: Color get() = getArgb(MaterialDynamicColors().onSecondaryFixed())
val DynamicScheme.onSecondaryFixedVariant: Color get() = getArgb(MaterialDynamicColors().onSecondaryFixedVariant())

val DynamicScheme.tertiaryFixed: Color get() = getArgb(MaterialDynamicColors().tertiaryFixed())
val DynamicScheme.tertiaryFixedDim: Color get() = getArgb(MaterialDynamicColors().tertiaryFixedDim())
val DynamicScheme.onTertiaryFixed: Color get() = getArgb(MaterialDynamicColors().onTertiaryFixed())
val DynamicScheme.onTertiaryFixedVariant: Color get() = getArgb(MaterialDynamicColors().onTertiaryFixedVariant())

val DynamicScheme.controlActivated: Color get() = getArgb(MaterialDynamicColors().controlActivated())
val DynamicScheme.controlNormal: Color get() = getArgb(MaterialDynamicColors().controlNormal())
val DynamicScheme.controlHighlight: Color get() = getArgb(MaterialDynamicColors().controlHighlight())

val DynamicScheme.textPrimaryInverse: Color get() = getArgb(MaterialDynamicColors().textPrimaryInverse())
val DynamicScheme.textSecondaryAndTertiaryInverse: Color get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverse())
val DynamicScheme.textPrimaryInverseDisableOnly: Color get() = getArgb(MaterialDynamicColors().textPrimaryInverseDisableOnly())
val DynamicScheme.textSecondaryAndTertiaryInverseDisabled: Color get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverseDisabled())
val DynamicScheme.textHintInverse: Color get() = getArgb(MaterialDynamicColors().textHintInverse())

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
fun DynamicScheme.Companion.getRotatedHue(sourceColorHct: Hct, hues: DoubleArray, rotations: DoubleArray): Double {
	val sourceHue = sourceColorHct.hue
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
