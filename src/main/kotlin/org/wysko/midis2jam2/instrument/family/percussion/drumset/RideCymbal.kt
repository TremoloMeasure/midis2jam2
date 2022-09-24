/*
 * Copyright (C) 2022 Jacob Wysko
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
package org.wysko.midis2jam2.instrument.family.percussion.drumset

import org.wysko.midis2jam2.Midis2jam2
import org.wysko.midis2jam2.midi.MidiNoteOnEvent
import org.wysko.midis2jam2.midi.RIDE_BELL

private const val BELL_POSITION = 12f
private const val EDGE_POSITION = 18f
private const val STICK_MOVE_SPEED = 30f

/** The ride cymbal. */
class RideCymbal(context: Midis2jam2, hits: MutableList<MidiNoteOnEvent>, type: CymbalType) :
    Cymbal(context, hits, type) {

    private var targetStickPosition = EDGE_POSITION
    private var stickPosition = EDGE_POSITION

    override fun tick(time: Double, delta: Float) {
        val results = stick.tick(time, delta)
        results.strike?.let {
            cymbalAnimator.strike()
        }

        // Determine if the stick should go forwards or backwards to hit the ride bell
        results.strikingFor?.let {
            targetStickPosition = if (it.note == RIDE_BELL) BELL_POSITION else EDGE_POSITION
        }

        // Update influence and position
        stickPosition += (delta * (targetStickPosition - stickPosition) * STICK_MOVE_SPEED)
        stickPosition = stickPosition.coerceIn(BELL_POSITION..EDGE_POSITION) // Prevent overshooting
        stick.node.setLocalTranslation(0f, 2f, stickPosition)

        cymbalAnimator.tick(delta)
    }
}
