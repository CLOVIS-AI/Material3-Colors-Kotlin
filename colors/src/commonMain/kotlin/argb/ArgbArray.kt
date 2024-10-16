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

package opensavvy.material3.colors.argb

import kotlin.js.JsName
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmName

/**
 * Efficient representation of an `Array<Argb>`.
 */
@JvmInline
value class ArgbArray(
	val data: IntArray,
) : Iterable<Argb> {

	constructor(size: Int) : this(IntArray(size))

	/**
	 * Returns the number of elements in the array.
	 */
	val size: Int
		get() = data.size

	/**
	 * Returns the element at [index].
	 */
	inline operator fun get(index: Int): Argb =
		Argb(data[index])

	/**
	 * Sets the element at [index] to [value].
	 */
	inline operator fun set(index: Int, value: Argb) {
		data[index] = value.argb
	}

	/**
	 * Creates an iterator for iterating over the elements of the array.
	 */
	override fun iterator(): Iterator<Argb> =
		ArgbIterator(data.iterator())

	fun asSequence() =
		iterator().asSequence()

	/**
	 * Returns the range of valid indices for the array.
	 */
	val indices: IntRange
		get() = data.indices

	/**
	 * Returns the last valid index for the array.
	 */
	val lastIndex: Int
		get() = data.lastIndex

	/**
	 * Returns `true` if [element] is found in the array.
	 */
	operator fun contains(element: Argb): Boolean =
		data.contains(element.argb)

	override fun toString(): String =
		joinToString(prefix = "[", postfix = "]", separator = ", ")

	fun isEmpty() = data.isEmpty()
	fun isNotEmpty() = data.isNotEmpty()
}

/**
 * Initializes an [ArgbArray] of a given [size] where each element is
 * built using the output of the [init] function.
 */
inline fun ArgbArray(size: Int, init: (Int) -> Argb): ArgbArray {
	val array = ArgbArray(size)

	repeat(size) {
		array[it] = init(it)
	}

	return array
}

fun Collection<Argb>.toArgbArray(): ArgbArray {
	val array = ArgbArray(size)

	var index = 0
	for (item in this) {
		array[index] = item
		index++
	}

	return array
}

@JsName("intsToArgbArray")
@JvmName("intsToArgbArray")
fun Collection<Int>.toArgbArray(): ArgbArray {
	val array = ArgbArray(size)

	var index = 0
	for (item in this) {
		array[index] = Argb(item)
		index++
	}

	return array
}

private class ArgbIterator(
	val iterator: IntIterator,
) : Iterator<Argb> {
	override fun hasNext(): Boolean =
		iterator.hasNext()

	override fun next(): Argb =
		Argb(iterator.next())
}
