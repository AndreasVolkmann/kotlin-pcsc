/*
 * MultiString.kt
 * MultiString utility functions for PC/SC API
 *
 * Copyright 2019 Michael Farrell <micolous+git@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.id.micolous.kotlin.pcsc

/**
 * Encodes a [Collection] of [String] as a "multi-string" (`msz`) type.
 *
 * This is a list of null terminated strings, that is then terminated with a
 * null byte.
 *
 * Text output is presumed to be encoded as UTF-8.
 */
internal fun Collection<String>.asMultiString() : ByteArray {
    val buf = mutableListOf<Byte>()
    for (group in this) {
        group.encodeToByteArray(0, group.length, true).toCollection(buf)

        // Null terminator for entry
        buf.add(0)
    }

    // Null terminator for list
    buf.add(0)

    return buf.toByteArray()
}

private const val ZERO: Byte = 0

internal fun ByteArray.toMultiString(
    off: Int = 0, len: Int = (size - off)): Sequence<String> {
    require(off >= 0) { "off must be greater than or equal to 0" }
    require(len >= 0) { "len must be greater than or equal to 0" }
    require(off <= size) { "off must be less than or equal to $size" }
    require((off + len) <= size) { "length must be less than or equal to ${size - off}" }

    val array = this
    return sequence {
        var start = off

        for (index in (off..off + len)) {
            if (array[index] == ZERO) {
                // terminator
                if (index == start) {
                    // final terminator
                    break
                }

                // Return the substring
                yield(array.decodeToString(start, index, true))
                start = index + 1
            }
        }
    }
}
