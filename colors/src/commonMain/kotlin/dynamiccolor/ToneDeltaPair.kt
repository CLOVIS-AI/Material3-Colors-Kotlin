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

/**
 * Documents a constraint between two DynamicColors, in which their tones must have a certain
 * distance from each other.
 *
 *
 * Prefer a DynamicColor with a background, this is for special cases when designers want tonal
 * distance, literally contrast, between two colors that don't have a background / foreground
 * relationship or a contrast guarantee.
 */
class ToneDeltaPair(
	roleA: DynamicColor,
	roleB: DynamicColor,
	/** Required difference between tones. Absolute value, negative values have undefined behavior.  */
	val delta: Double,
	/** The relative relation between tones of roleA and roleB, as described above.  */
	val polarity: TonePolarity,
	/**
	 * Whether these two roles should stay on the same side of the "awkward zone" (T50-59). This is
	 * necessary for certain cases where one role has two backgrounds.
	 */
	val stayTogether: Boolean,
) {
	/** The first role in a pair.  */
	private val roleA: DynamicColor = roleA

	/** The second role in a pair.  */
	private val roleB: DynamicColor = roleB

	fun getRoleA(): DynamicColor {
		return roleA
	}

	fun getRoleB(): DynamicColor {
		return roleB
	}
}
