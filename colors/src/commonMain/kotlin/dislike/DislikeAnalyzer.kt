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

package opensavvy.material3.colors.dislike

import opensavvy.material3.colors.hct.Hct
import kotlin.math.round

/**
 * Checks whether a color is universally disliked.
 *
 * Color science studies of color preference indicate universal distaste for dark yellow-greens,
 * and also show this is correlated to distate for biological waste and rotting food.
 *
 * See Palmer and Schloss, 2010 or Schloss and Palmer's Chapter 21 in Handbook of Color
 * Psychology (2015).
 *
 * @return `true` if a color is disliked.
 */
fun Hct.isDisliked(): Boolean =
	round(hue) in 90.0..111.0 &&
		round(chroma) > 16.0 &&
		round(tone) < 65.0

/**
 * If a color is [disliked][isDisliked], lighten it to make it likable.
 */
fun Hct.fixIfDisliked(): Hct =
	if (isDisliked()) Hct(hue, chroma, 70.0)
	else this
