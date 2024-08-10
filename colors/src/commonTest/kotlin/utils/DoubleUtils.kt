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

package opensavvy.material3.colors.utils

import opensavvy.prepared.runner.kotest.PreparedSpec
import kotlin.js.JsName

@JsName("plusMinus")
fun checkDouble(actual: Double, expected: Double, epsilon: Double = 0.000_000_000_000_001) {
	val expectedMin = expected - epsilon
	val expectedMax = expected + epsilon
	check(actual in expectedMin..expectedMax) { "Expected a value of $actual ± $epsilon, but found $expected" }
}
