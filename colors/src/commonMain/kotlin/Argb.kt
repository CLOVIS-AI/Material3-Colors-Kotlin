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

package opensavvy.material3.colors

import opensavvy.material3.colors.utils.clampInt
import opensavvy.material3.colors.utils.matrixMultiply
import kotlin.jvm.JvmInline
import kotlin.math.pow
import kotlin.math.round

/**
 * Lightweight representation of a color.
 *
 * Internally, this color is represented in ARGB space.
 * Conversions to and from other color spaces are available as well.
 */
@JvmInline
value class Argb(
	/**
	 * ARGB 32-bit representation of this color.
	 */
	val argb: Int,
) {

	/**
	 * Constructs an [Argb] color from its [red], [green] and [blue] components.
	 *
	 * Each component should be expressed in the range `0..255`.
	 */
	constructor(red: Int, green: Int, blue: Int) : this(
		(255 shl 24) or ((red and 255) shl 16) or ((green and 255) shl 8) or (blue and 255)
	) {
		require(red in 0..255) { "The red component should be between 0 and 255" }
		require(green in 0..255) { "The green component should be between 0 and 255" }
		require(blue in 0..255) { "The blue component should be between 0 and 255" }
	}

	// region ARGB accessors

	/**
	 * Transparency.
	 *
	 * From 0 (fully transparent) to 255 (fully opaque).
	 */
	inline val alpha: Int
		get() = argb shr 24 and 255

	/**
	 * Red component.
	 *
	 * From 0 (no red) to 255 (max red).
	 */
	inline val red: Int
		get() = argb shr 16 and 255

	/**
	 * Green component.
	 *
	 * From 0 (no green) to 255 (max green).
	 */
	inline val green: Int
		get() = argb shr 8 and 255

	/**
	 * Blue component.
	 *
	 * From 0 (no blue) to 255 (max blue).
	 */
	inline val blue: Int
		get() = argb and 255

	/**
	 * `true` is this color is fully opaque.
	 */
	inline val isOpaque: Boolean
		get() = alpha >= 255

	// endregion
	// region Converters

	/**
	 * Converts this color to the [XYZ color space](https://en.wikipedia.org/wiki/CIE_1931_color_space).
	 */
	fun toXyz(): DoubleArray {
		val r = linearized(red)
		val g = linearized(green)
		val b = linearized(blue)
		return matrixMultiply(doubleArrayOf(r, g, b), SRGB_TO_XYZ)
	}

	/**
	 * Converts this color to the [Lab color space](https://en.wikipedia.org/wiki/CIELAB_color_space).
	 */
	fun toLab(): DoubleArray {
		val linearR = linearized(red)
		val linearG = linearized(green)
		val linearB = linearized(blue)
		val matrix = SRGB_TO_XYZ
		val x = matrix[0][0] * linearR + matrix[0][1] * linearG + matrix[0][2] * linearB
		val y = matrix[1][0] * linearR + matrix[1][1] * linearG + matrix[1][2] * linearB
		val z = matrix[2][0] * linearR + matrix[2][1] * linearG + matrix[2][2] * linearB
		val whitePoint = whitePointD65
		val xNormalized = x / whitePoint[0]
		val yNormalized = y / whitePoint[1]
		val zNormalized = z / whitePoint[2]
		val fx = labF(xNormalized)
		val fy = labF(yNormalized)
		val fz = labF(zNormalized)
		val l = 116.0 * fy - 16
		val a = 500.0 * (fx - fy)
		val b = 200.0 * (fy - fz)
		return doubleArrayOf(l, a, b)
	}

	/**
	 * Converts to an L* value.
	 */
	fun toLstar(): Double {
		val y = toXyz()[1]
		return 116.0 * labF(y / 100.0) - 16.0
	}

	// endregion
	// region String representation

	private fun hexFromColor(color: Int): String {
		return color.toString(16)
			.padStart(2, '0')
	}

	override fun toString(): String =
		"#" + hexFromColor(red) + hexFromColor(green) + hexFromColor(blue)

	// endregion

	companion object {
		// region Converters

		/**
		 * Converts from linear RGB components.
		 *
		 * @param linrgb Must be an array of 3 values (RGB).
		 */
		fun fromLinrgb(linrgb: DoubleArray): Argb {
			val r = delinearized(linrgb[0])
			val g = delinearized(linrgb[1])
			val b = delinearized(linrgb[2])
			return Argb(r, g, b)
		}

		/**
		 * Converts this color from the [XYZ color space](https://en.wikipedia.org/wiki/CIE_1931_color_space).
		 */
		fun fromXyz(x: Double, y: Double, z: Double): Argb {
			val matrix = XYZ_TO_SRGB
			val linearR = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2] * z
			val linearG = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2] * z
			val linearB = matrix[2][0] * x + matrix[2][1] * y + matrix[2][2] * z
			val r = delinearized(linearR)
			val g = delinearized(linearG)
			val b = delinearized(linearB)
			return Argb(r, g, b)
		}

		/**
		 * Converts this color from the [Lab color space](https://en.wikipedia.org/wiki/CIELAB_color_space).
		 */
		fun fromLab(l: Double, a: Double, b: Double): Argb {
			val whitePoint = whitePointD65
			val fy = (l + 16.0) / 116.0
			val fx = a / 500.0 + fy
			val fz = fy - b / 200.0
			val xNormalized = labInvf(fx)
			val yNormalized = labInvf(fy)
			val zNormalized = labInvf(fz)
			val x = xNormalized * whitePoint[0]
			val y = yNormalized * whitePoint[1]
			val z = zNormalized * whitePoint[2]
			return fromXyz(x, y, z)
		}

		/**
		 * Converts from an L* value.
		 */
		fun fromLstar(lstar: Double): Argb {
			val y = yFromLstar(lstar)
			val component = delinearized(y)
			return Argb(component, component, component)
		}

		// endregion
		// region Y <-> L*


		/**
		 * Converts an L* value to a Y value.
		 *
		 *
		 * L* in L*a*b* and Y in XYZ measure the same quantity, luminance.
		 *
		 *
		 * L* measures perceptual luminance, a linear scale. Y in XYZ measures relative luminance, a
		 * logarithmic scale.
		 *
		 * @param lstar L* in L*a*b*
		 * @return Y in XYZ
		 */
		fun yFromLstar(lstar: Double): Double {
			return 100.0 * labInvf((lstar + 16.0) / 116.0)
		}

		/**
		 * Converts a Y value to an L* value.
		 *
		 *
		 * L* in L*a*b* and Y in XYZ measure the same quantity, luminance.
		 *
		 *
		 * L* measures perceptual luminance, a linear scale. Y in XYZ measures relative luminance, a
		 * logarithmic scale.
		 *
		 * @param y Y in XYZ
		 * @return L* in L*a*b*
		 */
		fun lstarFromY(y: Double): Double {
			return labF(y / 100.0) * 116.0 - 16.0
		}

		// endregion
		// region Well-known colors

		val BLACK = Argb(0, 0, 0)
		val WHITE = Argb(255, 255, 255)
		val RED = Argb(255, 0, 0)
		val GREEN = Argb(0, 255, 0)
		val BLUE = Argb(0, 0, 255)

		// endregion

		/**
		 * The standard white point; white on a sunny day.
		 */
		val whitePointD65: DoubleArray
			get() = doubleArrayOf(95.047, 100.0, 108.883)
	}
}

/**
 * Linearizes an RGB component.
 *
 * @param rgbComponent 0 <= rgb_component <= 255, represents R/G/B channel
 * @return 0.0 <= output <= 100.0, color channel converted to linear RGB space
 */
internal fun linearized(rgbComponent: Int): Double {
	val normalized = rgbComponent / 255.0
	return if (normalized <= 0.040449936) {
		normalized / 12.92 * 100.0
	} else {
		((normalized + 0.055) / 1.055).pow(2.4) * 100.0
	}
}

/**
 * Delinearizes an RGB component.
 *
 * @param rgbComponent 0.0 <= rgb_component <= 100.0, represents linear R/G/B channel
 * @return 0 <= output <= 255, color channel converted to regular RGB space
 */
internal fun delinearized(rgbComponent: Double): Int {
	val normalized = rgbComponent / 100.0
	var delinearized = 0.0
	delinearized = if (normalized <= 0.0031308) {
		normalized * 12.92
	} else {
		1.055 * normalized.pow(1.0 / 2.4) - 0.055
	}
	return clampInt(0, 255, round(delinearized * 255.0).toInt())
}

private val SRGB_TO_XYZ: Array<DoubleArray> = arrayOf(
	doubleArrayOf(0.41233895, 0.35762064, 0.18051042),
	doubleArrayOf(0.2126, 0.7152, 0.0722),
	doubleArrayOf(0.01932141, 0.11916382, 0.95034478),
)

private val XYZ_TO_SRGB: Array<DoubleArray> = arrayOf(
	doubleArrayOf(
		3.2413774792388685, -1.5376652402851851, -0.49885366846268053,
	),
	doubleArrayOf(
		-0.9691452513005321, 1.8758853451067872, 0.04156585616912061,
	),
	doubleArrayOf(
		0.05562093689691305, -0.20395524564742123, 1.0571799111220335,
	),
)

private fun labF(t: Double): Double {
	val e = 216.0 / 24389.0
	val kappa = 24389.0 / 27.0
	return if (t > e) {
		t.pow(1.0 / 3.0)
	} else {
		(kappa * t + 16) / 116
	}
}

private fun labInvf(ft: Double): Double {
	val e = 216.0 / 24389.0
	val kappa = 24389.0 / 27.0
	val ft3 = ft * ft * ft
	return if (ft3 > e) {
		ft3
	} else {
		(116 * ft - 16) / kappa
	}
}
