/*
 * Copyright (C) 2024 Jacob Wysko
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */
package org.wysko.midis2jam2.instrument.algorithmic

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.wysko.midis2jam2.instrument.Instrument
import org.wysko.midis2jam2.instrument.family.brass.Trombone
import org.wysko.midis2jam2.util.Utils
import kotlin.reflect.KClass

/**
 * Manages and defines the position of the [Trombone] slide for each note.
 */
@Serializable
class SlidePositionManager private constructor() : FingeringManager<List<Int>> {

    private val slideTable = HashMap<Int, List<Int>>()

    override fun fingering(midiNote: Int): List<Int>? = slideTable[midiNote]

    companion object {
        /** Loads the slide position manager from a file based on the class name. */
        fun from(klass: KClass<out Instrument>): SlidePositionManager =
            Json.decodeFromString(Utils.resourceToString("/instrument/${klass.simpleName}.json"))
    }
}
