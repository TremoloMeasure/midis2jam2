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
package org.wysko.midis2jam2.midi

/**
 * Indicates the start of a note.
 *
 * @property time The time this event occurs, in MIDI ticks.
 * @property channel The channel this event occurs on.
 * @property note The note that is being played.
 * @property velocity The non-zero velocity of the note.
 */
data class MidiNoteOnEvent(
    override val time: Long,
    override val channel: Int,
    override val note: Int,
    val velocity: Int,
) : MidiNoteEvent(time, channel, note)
