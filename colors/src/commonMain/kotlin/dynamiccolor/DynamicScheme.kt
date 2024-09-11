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
import opensavvy.material3.colors.utils.Argb
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

val DynamicScheme.primaryPaletteKeyColor: Argb get() = getArgb(MaterialDynamicColors().primaryPaletteKeyColor())
val DynamicScheme.secondaryPaletteKeyColor: Argb get() = getArgb(MaterialDynamicColors().secondaryPaletteKeyColor())
val DynamicScheme.tertiaryPaletteKeyColor: Argb get() = getArgb(MaterialDynamicColors().tertiaryPaletteKeyColor())
val DynamicScheme.neutralPaletteKeyColor: Argb get() = getArgb(MaterialDynamicColors().neutralPaletteKeyColor())
val DynamicScheme.neutralVariantPaletteKeyColor: Argb get() = getArgb(MaterialDynamicColors().neutralVariantPaletteKeyColor())
val DynamicScheme.background: Argb get() = getArgb(MaterialDynamicColors().background())
val DynamicScheme.onBackground: Argb get() = getArgb(MaterialDynamicColors().onBackground())
val DynamicScheme.surface: Argb get() = getArgb(MaterialDynamicColors().surface())
val DynamicScheme.surfaceDim: Argb get() = getArgb(MaterialDynamicColors().surfaceDim())
val DynamicScheme.surfaceBright: Argb get() = getArgb(MaterialDynamicColors().surfaceBright())
val DynamicScheme.surfaceContainerLowest: Argb get() = getArgb(MaterialDynamicColors().surfaceContainerLowest())
val DynamicScheme.surfaceContainerLow: Argb get() = getArgb(MaterialDynamicColors().surfaceContainerLow())
val DynamicScheme.surfaceContainer: Argb get() = getArgb(MaterialDynamicColors().surfaceContainer())
val DynamicScheme.surfaceContainerHigh: Argb get() = getArgb(MaterialDynamicColors().surfaceContainerHigh())
val DynamicScheme.surfaceContainerHighest: Argb get() = getArgb(MaterialDynamicColors().surfaceContainerHighest())
val DynamicScheme.onSurface: Argb get() = getArgb(MaterialDynamicColors().onSurface())
val DynamicScheme.onSurfaceVariant: Argb get() = getArgb(MaterialDynamicColors().onSurfaceVariant())
val DynamicScheme.inverseSurface: Argb get() = getArgb(MaterialDynamicColors().inverseSurface())
val DynamicScheme.inverseOnSurface: Argb get() = getArgb(MaterialDynamicColors().inverseOnSurface())
val DynamicScheme.outline: Argb get() = getArgb(MaterialDynamicColors().outline())
val DynamicScheme.outlineVariant: Argb get() = getArgb(MaterialDynamicColors().outlineVariant())
val DynamicScheme.shadow: Argb get() = getArgb(MaterialDynamicColors().shadow())
val DynamicScheme.scrim: Argb get() = getArgb(MaterialDynamicColors().scrim())
val DynamicScheme.surfaceTint: Argb get() = getArgb(MaterialDynamicColors().surfaceTint())

val DynamicScheme.primary: Argb get() = getArgb(MaterialDynamicColors().primary())
val DynamicScheme.onPrimary: Argb get() = getArgb(MaterialDynamicColors().onPrimary())
val DynamicScheme.primaryContainer: Argb get() = getArgb(MaterialDynamicColors().primaryContainer())
val DynamicScheme.onPrimaryContainer: Argb get() = getArgb(MaterialDynamicColors().onPrimaryContainer())
val DynamicScheme.inversePrimary: Argb get() = getArgb(MaterialDynamicColors().inversePrimary())

val DynamicScheme.secondary: Argb get() = getArgb(MaterialDynamicColors().secondary())
val DynamicScheme.onSecondary: Argb get() = getArgb(MaterialDynamicColors().onSecondary())
val DynamicScheme.secondaryContainer: Argb get() = getArgb(MaterialDynamicColors().secondaryContainer())
val DynamicScheme.onSecondaryContainer: Argb get() = getArgb(MaterialDynamicColors().onSecondaryContainer())

val DynamicScheme.tertiary: Argb get() = getArgb(MaterialDynamicColors().tertiary())
val DynamicScheme.onTertiary: Argb get() = getArgb(MaterialDynamicColors().onTertiary())
val DynamicScheme.tertiaryContainer: Argb get() = getArgb(MaterialDynamicColors().tertiaryContainer())
val DynamicScheme.onTertiaryContainer: Argb get() = getArgb(MaterialDynamicColors().onTertiaryContainer())

val DynamicScheme.error: Argb get() = getArgb(MaterialDynamicColors().error())
val DynamicScheme.onError: Argb get() = getArgb(MaterialDynamicColors().onError())
val DynamicScheme.errorContainer: Argb get() = getArgb(MaterialDynamicColors().errorContainer())
val DynamicScheme.onErrorContainer: Argb get() = getArgb(MaterialDynamicColors().onErrorContainer())

val DynamicScheme.primaryFixed: Argb get() = getArgb(MaterialDynamicColors().primaryFixed())
val DynamicScheme.primaryFixedDim: Argb get() = getArgb(MaterialDynamicColors().primaryFixedDim())
val DynamicScheme.onPrimaryFixed: Argb get() = getArgb(MaterialDynamicColors().onPrimaryFixed())
val DynamicScheme.onPrimaryFixedVariant: Argb get() = getArgb(MaterialDynamicColors().onPrimaryFixedVariant())

val DynamicScheme.secondaryFixed: Argb get() = getArgb(MaterialDynamicColors().secondaryFixed())
val DynamicScheme.secondaryFixedDim: Argb get() = getArgb(MaterialDynamicColors().secondaryFixedDim())
val DynamicScheme.onSecondaryFixed: Argb get() = getArgb(MaterialDynamicColors().onSecondaryFixed())
val DynamicScheme.onSecondaryFixedVariant: Argb get() = getArgb(MaterialDynamicColors().onSecondaryFixedVariant())

val DynamicScheme.tertiaryFixed: Argb get() = getArgb(MaterialDynamicColors().tertiaryFixed())
val DynamicScheme.tertiaryFixedDim: Argb get() = getArgb(MaterialDynamicColors().tertiaryFixedDim())
val DynamicScheme.onTertiaryFixed: Argb get() = getArgb(MaterialDynamicColors().onTertiaryFixed())
val DynamicScheme.onTertiaryFixedVariant: Argb get() = getArgb(MaterialDynamicColors().onTertiaryFixedVariant())

val DynamicScheme.controlActivated: Argb get() = getArgb(MaterialDynamicColors().controlActivated())
val DynamicScheme.controlNormal: Argb get() = getArgb(MaterialDynamicColors().controlNormal())
val DynamicScheme.controlHighlight: Argb get() = getArgb(MaterialDynamicColors().controlHighlight())

val DynamicScheme.textPrimaryInverse: Argb get() = getArgb(MaterialDynamicColors().textPrimaryInverse())
val DynamicScheme.textSecondaryAndTertiaryInverse: Argb get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverse())
val DynamicScheme.textPrimaryInverseDisableOnly: Argb get() = getArgb(MaterialDynamicColors().textPrimaryInverseDisableOnly())
val DynamicScheme.textSecondaryAndTertiaryInverseDisabled: Argb get() = getArgb(MaterialDynamicColors().textSecondaryAndTertiaryInverseDisabled())
val DynamicScheme.textHintInverse: Argb get() = getArgb(MaterialDynamicColors().textHintInverse())

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
