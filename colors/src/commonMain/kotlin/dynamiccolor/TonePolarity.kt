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
 * Describes the relationship in lightness between two colors.
 *
 *
 * 'nearer' and 'farther' describes closeness to the surface roles. For instance,
 * ToneDeltaPair(A, B, 10, 'nearer', stayTogether) states that A should be 10 lighter than B in
 * light mode, and 10 darker than B in dark mode.
 *
 *
 * See `ToneDeltaPair` for details.
 */
enum class TonePolarity {
	DARKER,
	LIGHTER,
	NEARER,
	FARTHER
}
