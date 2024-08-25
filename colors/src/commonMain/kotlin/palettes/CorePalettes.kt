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

package opensavvy.material3.colors.palettes

import opensavvy.material3.colors.dynamiccolor.DynamicScheme

/**
 * Comprises foundational palettes to build a color scheme.
 *
 *
 * Generated from a source color, these palettes will then be part of a [DynamicScheme] together
 * with appearance preferences.
 */
class CorePalettes
/** Creates a new CorePalettes.  */(
	var primary: TonalPalette,
	var secondary: TonalPalette,
	var tertiary: TonalPalette,
	var neutral: TonalPalette,
	var neutralVariant: TonalPalette,
)
