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

import org.wysko.midis2jam2.Midis2jam2
import org.wysko.midis2jam2.instrument.Instrument
import org.wysko.midis2jam2.instrument.family.animusic.SpaceLaser
import org.wysko.midis2jam2.instrument.family.animusic.SpaceLaserType
import org.wysko.midis2jam2.instrument.family.brass.FrenchHorn
import org.wysko.midis2jam2.instrument.family.brass.StageHorns
import org.wysko.midis2jam2.instrument.family.brass.StageHornsType
import org.wysko.midis2jam2.instrument.family.brass.Trombone
import org.wysko.midis2jam2.instrument.family.brass.Trumpet
import org.wysko.midis2jam2.instrument.family.brass.TrumpetType
import org.wysko.midis2jam2.instrument.family.brass.Tuba
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.Kalimba
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.Mallets
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.Mallets.MalletType
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.MusicBox
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.TinkleBell
import org.wysko.midis2jam2.instrument.family.chromaticpercussion.TubularBells
import org.wysko.midis2jam2.instrument.family.ensemble.ApplauseChoir
import org.wysko.midis2jam2.instrument.family.ensemble.PizzicatoStrings
import org.wysko.midis2jam2.instrument.family.ensemble.StageChoir
import org.wysko.midis2jam2.instrument.family.ensemble.StageStrings
import org.wysko.midis2jam2.instrument.family.ensemble.StageStrings.StageStringBehavior
import org.wysko.midis2jam2.instrument.family.ensemble.StageStrings.StageStringsType
import org.wysko.midis2jam2.instrument.family.ensemble.Timpani
import org.wysko.midis2jam2.instrument.family.guitar.Banjo
import org.wysko.midis2jam2.instrument.family.guitar.BassGuitar
import org.wysko.midis2jam2.instrument.family.guitar.BassGuitar.BassGuitarType
import org.wysko.midis2jam2.instrument.family.guitar.Guitar
import org.wysko.midis2jam2.instrument.family.guitar.Guitar.GuitarType
import org.wysko.midis2jam2.instrument.family.guitar.Shamisen
import org.wysko.midis2jam2.instrument.family.organ.Accordion
import org.wysko.midis2jam2.instrument.family.organ.Accordion.Type
import org.wysko.midis2jam2.instrument.family.organ.Harmonica
import org.wysko.midis2jam2.instrument.family.percussion.Agogo
import org.wysko.midis2jam2.instrument.family.percussion.AuxiliaryPercussion
import org.wysko.midis2jam2.instrument.family.percussion.Bongos
import org.wysko.midis2jam2.instrument.family.percussion.Cabasa
import org.wysko.midis2jam2.instrument.family.percussion.Castanets
import org.wysko.midis2jam2.instrument.family.percussion.Claves
import org.wysko.midis2jam2.instrument.family.percussion.Congas
import org.wysko.midis2jam2.instrument.family.percussion.Cowbell
import org.wysko.midis2jam2.instrument.family.percussion.Cuica
import org.wysko.midis2jam2.instrument.family.percussion.Guiro
import org.wysko.midis2jam2.instrument.family.percussion.HandClap
import org.wysko.midis2jam2.instrument.family.percussion.HighQ
import org.wysko.midis2jam2.instrument.family.percussion.JingleBell
import org.wysko.midis2jam2.instrument.family.percussion.Maracas
import org.wysko.midis2jam2.instrument.family.percussion.Metronome
import org.wysko.midis2jam2.instrument.family.percussion.Shaker
import org.wysko.midis2jam2.instrument.family.percussion.Slap
import org.wysko.midis2jam2.instrument.family.percussion.SquareClick
import org.wysko.midis2jam2.instrument.family.percussion.Sticks
import org.wysko.midis2jam2.instrument.family.percussion.Surdo
import org.wysko.midis2jam2.instrument.family.percussion.Tambourine
import org.wysko.midis2jam2.instrument.family.percussion.Timbales
import org.wysko.midis2jam2.instrument.family.percussion.Triangle
import org.wysko.midis2jam2.instrument.family.percussion.Whistle
import org.wysko.midis2jam2.instrument.family.percussion.Woodblock
import org.wysko.midis2jam2.instrument.family.percussion.drumset.BrushDrumSet
import org.wysko.midis2jam2.instrument.family.percussion.drumset.DrumSet
import org.wysko.midis2jam2.instrument.family.percussion.drumset.ElectronicDrumSet
import org.wysko.midis2jam2.instrument.family.percussion.drumset.OrchestraDrumSet
import org.wysko.midis2jam2.instrument.family.percussion.drumset.TypicalDrumSet
import org.wysko.midis2jam2.instrument.family.percussion.drumset.kit.ShellStyle
import org.wysko.midis2jam2.instrument.family.percussion.drumset.kit.ShellStyle.TypicalDrumShell
import org.wysko.midis2jam2.instrument.family.percussive.Agogos
import org.wysko.midis2jam2.instrument.family.percussive.MelodicTom
import org.wysko.midis2jam2.instrument.family.percussive.SteelDrums
import org.wysko.midis2jam2.instrument.family.percussive.SynthDrum
import org.wysko.midis2jam2.instrument.family.percussive.TaikoDrum
import org.wysko.midis2jam2.instrument.family.percussive.Woodblocks
import org.wysko.midis2jam2.instrument.family.piano.FifthsKeyboard
import org.wysko.midis2jam2.instrument.family.piano.Keyboard
import org.wysko.midis2jam2.instrument.family.piano.KeyboardSkin
import org.wysko.midis2jam2.instrument.family.pipe.BlownBottle
import org.wysko.midis2jam2.instrument.family.pipe.Flute
import org.wysko.midis2jam2.instrument.family.pipe.Ocarina
import org.wysko.midis2jam2.instrument.family.pipe.PanFlute
import org.wysko.midis2jam2.instrument.family.pipe.Piccolo
import org.wysko.midis2jam2.instrument.family.pipe.Recorder
import org.wysko.midis2jam2.instrument.family.pipe.Whistles
import org.wysko.midis2jam2.instrument.family.reed.Clarinet
import org.wysko.midis2jam2.instrument.family.reed.Oboe
import org.wysko.midis2jam2.instrument.family.reed.sax.AltoSax
import org.wysko.midis2jam2.instrument.family.reed.sax.BaritoneSax
import org.wysko.midis2jam2.instrument.family.reed.sax.SopranoSax
import org.wysko.midis2jam2.instrument.family.reed.sax.TenorSax
import org.wysko.midis2jam2.instrument.family.soundeffects.BirdTweet
import org.wysko.midis2jam2.instrument.family.soundeffects.Helicopter
import org.wysko.midis2jam2.instrument.family.soundeffects.ReverseCymbal
import org.wysko.midis2jam2.instrument.family.soundeffects.TelephoneRing
import org.wysko.midis2jam2.instrument.family.strings.AcousticBass
import org.wysko.midis2jam2.instrument.family.strings.AcousticBass.PlayingStyle
import org.wysko.midis2jam2.instrument.family.strings.Cello
import org.wysko.midis2jam2.instrument.family.strings.Fiddle
import org.wysko.midis2jam2.instrument.family.strings.Harp
import org.wysko.midis2jam2.instrument.family.strings.Viola
import org.wysko.midis2jam2.instrument.family.strings.Violin
import org.wysko.midis2jam2.midi.MidiChannelEvent
import org.wysko.midis2jam2.midi.MidiControlChangeEvent
import org.wysko.midis2jam2.midi.MidiFile
import org.wysko.midis2jam2.midi.MidiNoteEvent
import org.wysko.midis2jam2.midi.MidiNoteEvent.Companion.maximumPolyphony
import org.wysko.midis2jam2.midi.MidiNoteOffEvent
import org.wysko.midis2jam2.midi.MidiNoteOnEvent
import org.wysko.midis2jam2.midi.MidiProgramEvent
import org.wysko.midis2jam2.util.logger
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Assigns instruments to MIDI data.
 */
object InstrumentAssignment {
    /**
     * Given a [midiFile], determines the appropriate instruments to properly visualize the events within.
     */
    fun assign(
        context: Midis2jam2,
        midiFile: MidiFile,
    ): List<Instrument> {
        // Begin by extracting events from tracks and assign them to their target channels.
        val channels =
            with(midiFile.tracks.flatMap { it.events }.filterIsInstance<MidiChannelEvent>()) {
                Array(16) { arr -> this.filter { it.channel == arr }.toMutableList() }
            }

        // As a safety precaution, we will sort each channel by the time of each event.
        channels.onEach { channel -> channel.sortBy { it.time } }

        // Create a place for instruments to go.
        val instruments = mutableListOf<Instrument>()

        // Create a place for auxiliary percussion to go.
        val auxiliary =
            mutableMapOf<KClass<out AuxiliaryPercussion>, MutableList<MutableList<MidiChannelEvent>>>()

        // For each channel,
        channels.forEachIndexed { channel, channelSpecificEvents ->
            // Instrument created relies on the program change events.
            val programEvents = channelSpecificEvents.filterIsInstance<MidiProgramEvent>().toMutableList()
            if (programEvents.isEmpty()) { // If there are no program events, we default to instrument 0.
                programEvents += MidiProgramEvent(0, channel, 0)
            }
            programEvents.removeDuplicateProgramEvents()

            // We create "bins" that events fall into based on their corresponding program event.
            val programBins =
                buildMap<Int, MutableList<MidiChannelEvent>> {
                    programEvents.distinctBy { it.program }.forEach { this += it.program to mutableListOf() }
                }

            // Since a program change event can occur in between an ON and OFF event, we also need to keep track of the
            // current program when an ON event occurs, so that the corresponding OFF event can be assigned to the same
            // instrument.
            val programPerNote = mutableMapOf<Int, Int>()

            channelSpecificEvents.forEach { event ->
                if (event !is MidiNoteOffEvent) { // If the event is not an OFF event,
                    // Determine the last program event
                    val currentProgram = programEvents.lastOrNull { it.time <= event.time }?.program ?: 0

                    // Add the event to the correct bin
                    programBins[currentProgram]?.plusAssign(event)

                    // Keep track of current program for ON events.
                    if (event is MidiNoteOnEvent) programPerNote[event.note] = currentProgram
                } else {
                    programBins[programPerNote[event.note]]?.plusAssign(event) ?: kotlin.run {
                        logger().warn("Unbalanced MIDI note events.")
                    }
                }
            }

            if (channel == 9) {
                programBins.entries.forEach { (key, value) ->
                    buildDrumSet(context, key, value)?.let { instruments += it }
                    buildSpecialCases(context, key, value).let { instruments += it }
                    collectAuxiliary(key, value).let {
                        it.forEach { (t, u) ->
                            if (auxiliary[t] == null) {
                                auxiliary[t] = u.map { it.toMutableList() }.toMutableList()
                            } else {
                                u.forEachIndexed { index, list ->
                                    auxiliary[t]!![index].addAll(list)
                                    auxiliary[t]!![index] = auxiliary[t]!![index].sortedBy { it.time }.toMutableList()
                                }
                            }
                        }
                    }
                }
            } else {
                // Convert lists of events to their corresponding instrument.
                programBins.entries.forEach { (key, value) ->
                    buildInstrument(context, key, value, channelSpecificEvents)?.let { instruments += it }
                }
            }
        }

        // Add auxiliary percussion
        instruments +=
            auxiliary.map { (k, v) ->
                k.primaryConstructor?.call(context, *v.toTypedArray()) ?: error("Invalid auxiliary percussion")
            }

        return instruments
    }

    @Suppress("CyclomaticComplexMethod", "LongMethod")
    private fun buildInstrument(
        context: Midis2jam2,
        program: Int,
        events: MutableList<MidiChannelEvent>,
        allChannelEvents: List<MidiChannelEvent>,
    ): Instrument? {
        val midiNoteEvents = events.filterIsInstance<MidiNoteEvent>()

        // We need to also get Pitch Bend RPN events because they are "sticky" meaning if the events occurred during one
        // instrument, it should apply to the next if the channel program changes. We can do this by just collecting all
        // the controller events.
        // Eliminates any duplicate events from adding the two together
        @Suppress("NAME_SHADOWING")
        val events =
            (events + allChannelEvents.filterIsInstance<MidiControlChangeEvent>()).distinct().sortedBy { it.time }
                .toMutableList()

        if (midiNoteEvents.isEmpty()) return null
        return when (program) {
            0 -> Keyboard(context, events, KeyboardSkin["piano"])
            1 -> Keyboard(context, events, KeyboardSkin["bright"])
            2 -> Keyboard(context, events, KeyboardSkin["electric_grand"])
            3 -> Keyboard(context, events, KeyboardSkin["honky_tonk"])
            4 -> Keyboard(context, events, KeyboardSkin["electric_1"])
            5 -> Keyboard(context, events, KeyboardSkin["electric_2"])
            6 -> Keyboard(context, events, KeyboardSkin["harpsichord"])
            7 -> Keyboard(context, events, KeyboardSkin["clavichord"])
            8 -> Keyboard(context, events, KeyboardSkin["celesta"])
            9 -> Mallets(context, events, MalletType.Glockenspiel)
            10 -> MusicBox(context, events)
            11 -> Mallets(context, events, MalletType.Vibraphone)
            12 -> Mallets(context, events, MalletType.Marimba)
            13 -> Mallets(context, events, MalletType.Xylophone)
            14, 98 -> TubularBells(context, events)
            15, 16, 17, 18, 19, 20, 55 -> Keyboard(context, events, KeyboardSkin["wood"])
            21 -> Accordion(context, events, Type.ACCORDION)
            22 -> Harmonica(context, events)
            23 -> Accordion(context, events, Type.BANDONEON)
            24, 25 -> Guitar(context, events, GuitarType.Acoustic)
            26, 27, 28, 29, 30, 31, 120 -> Guitar(context, events, GuitarType.Electric)
            32 -> AcousticBass(context, events, PlayingStyle.PIZZICATO)
            33, 34, 36, 37 -> BassGuitar(context, events, BassGuitarType.Standard)
            35 -> BassGuitar(context, events, BassGuitarType.Fretless)
            38 -> BassGuitar(context, events, BassGuitarType.Synth1)
            39 -> BassGuitar(context, events, BassGuitarType.Synth2)
            40 -> Violin(context, events)
            41 -> Viola(context, events)
            42 -> Cello(context, events)
            43 -> AcousticBass(context, events, PlayingStyle.ARCO)
            44 -> StageStrings(context, events, StageStringsType.StringEnsemble1, StageStringBehavior.Tremolo)
            45 -> PizzicatoStrings(context, events)
            46 -> Harp(context, events)
            47 -> Timpani(context, events)
            48 -> StageStrings(context, events, StageStringsType.StringEnsemble1, StageStringBehavior.Normal)
            49 -> StageStrings(context, events, StageStringsType.StringEnsemble2, StageStringBehavior.Normal)
            50 -> StageStrings(context, events, StageStringsType.SynthStrings1, StageStringBehavior.Normal)
            51 -> StageStrings(context, events, StageStringsType.SynthStrings2, StageStringBehavior.Normal)
            52 -> StageChoir(context, events, StageChoir.ChoirType.ChoirAahs)
            53 -> StageChoir(context, events, StageChoir.ChoirType.VoiceOohs)
            54 -> StageChoir(context, events, StageChoir.ChoirType.SynthVoice)
            56 -> Trumpet(context, events, TrumpetType.Normal)
            57 -> Trombone(context, events)
            58 -> Tuba(context, events)
            59 -> Trumpet(context, events, TrumpetType.Muted)
            60 -> FrenchHorn(context, events)
            61 -> StageHorns(context, events, StageHornsType.BrassSection)
            62 -> StageHorns(context, events, StageHornsType.SynthBrass1)
            63 -> StageHorns(context, events, StageHornsType.SynthBrass2)
            64 -> SopranoSax(context, events)
            65 -> AltoSax(context, events)
            66 -> TenorSax(context, events)
            67 -> BaritoneSax(context, events)
            68 -> Oboe(context, events)
            71 -> Clarinet(context, events)
            72 -> Piccolo(context, events)
            73 -> Flute(context, events)
            74 -> Recorder(context, events)
            75 -> PanFlute(context, events, PanFlute.PipeSkin.WOOD)
            76 -> BlownBottle(context, events)
            78 -> Whistles(context, events)
            79 -> Ocarina(context, events)
            80 -> { // square
                if (midiNoteEvents.maximumPolyphony > 4) {
                    Keyboard(context, events, KeyboardSkin["square_wave"])
                } else {
                    SpaceLaser(context, events, SpaceLaserType.Square)
                }
            }

            81 -> { // sawtooth
                if (midiNoteEvents.maximumPolyphony > 4) {
                    Keyboard(context, events, KeyboardSkin["saw_wave"])
                } else {
                    SpaceLaser(context, events, SpaceLaserType.Saw)
                }
            }

            82 -> PanFlute(context, events, PanFlute.PipeSkin.GOLD) // calliope
            83 -> Keyboard(context, events, KeyboardSkin["chiff"]) // chiff
            84 -> Keyboard(context, events, KeyboardSkin["charang"]) // charang
            85 -> StageChoir(context, events, StageChoir.ChoirType.VoiceSynth)
            86 -> FifthsKeyboard(context, events, KeyboardSkin["synth"]) // fifths
            87 -> Keyboard(context, events, KeyboardSkin["bass_and_lead"]) // bass + lead
            88 -> Keyboard(context, events, KeyboardSkin["new_age"]) // new age
            89 -> Keyboard(context, events, KeyboardSkin["warm"]) // warm
            90 -> Keyboard(context, events, KeyboardSkin["polysynth"]) // polysynth
            91 -> Keyboard(context, events, KeyboardSkin["choir"]) // choir
            92 -> StageStrings(context, events, StageStringsType.BowedSynth, StageStringBehavior.Normal) // bowed
            93 -> Keyboard(context, events, KeyboardSkin["metallic"]) // metallic
            94 -> StageChoir(context, events, StageChoir.ChoirType.HaloSynth) // halo
            95 -> Keyboard(context, events, KeyboardSkin["synth"]) // sweep
            96 -> Keyboard(context, events, KeyboardSkin["synth"]) // rain
            97 -> Keyboard(context, events, KeyboardSkin["synth"]) // soundtrack
            99 -> Keyboard(context, events, KeyboardSkin["synth"]) // atmosphere
            100 -> Keyboard(context, events, KeyboardSkin["synth"]) // brightness
            101 -> StageChoir(context, events, StageChoir.ChoirType.GoblinSynth) // goblins
            102 -> Keyboard(context, events, KeyboardSkin["synth"]) // echoes
            103 -> Keyboard(context, events, KeyboardSkin["synth"]) // sci-fi
            105 -> Banjo(context, events)
            106 -> Shamisen(context, events)
            108 -> Kalimba(context, events)
            110 -> Fiddle(context, events)
            112 -> TinkleBell(context, events)
            113 -> Agogos(context, events)
            114 -> SteelDrums(context, events)
            115 -> Woodblocks(context, events)
            116 -> TaikoDrum(context, events)
            117 -> MelodicTom(context, events)
            118 -> SynthDrum(context, events)
            119 -> ReverseCymbal(context, events)
            121 -> StageChoir(context, events, StageChoir.ChoirType.SynthVoice)
            123 -> BirdTweet(context, events)
            124 -> TelephoneRing(context, events)
            125 -> Helicopter(context, events)
            126 -> ApplauseChoir(context, events)
            else -> null
        }
    }

    private fun List<MidiNoteOnEvent>.groupNotes(vararg programNums: Int): List<List<MidiNoteOnEvent>> =
        programNums.map { program -> this.filter { it.note == program } }

    @Suppress("CyclomaticComplexMethod", "LongMethod")
    private fun collectAuxiliary(
        program: Int,
        events: MutableList<MidiChannelEvent>,
    ): MutableMap<KClass<out AuxiliaryPercussion>, List<List<MidiChannelEvent>>> {
        val eventMap = mutableMapOf<KClass<out AuxiliaryPercussion>, List<List<MidiChannelEvent>>>()
        when (program) {
            0, 8, 16, 24, 25, 32, 40 -> {
                events.hits(27)?.let { eventMap.put(HighQ::class, it.groupNotes(27)) }
                events.hits(28)?.let { eventMap.put(Slap::class, it.groupNotes(28)) }
                events.hits(31)?.let { eventMap.put(Sticks::class, it.groupNotes(31)) }
                events.hits(32)?.let { eventMap.put(SquareClick::class, it.groupNotes(32)) }
                events.hits(33, 34)?.let { eventMap.put(Metronome::class, it.groupNotes(33, 34)) }
                events.hits(39)?.let { eventMap.put(HandClap::class, it.groupNotes(39)) }
                events.hits(54)?.let { eventMap.put(Tambourine::class, it.groupNotes(54)) }
                events.hits(56)?.let { eventMap.put(Cowbell::class, it.groupNotes(56)) }
                events.hits(60, 61)?.let { eventMap.put(Bongos::class, it.groupNotes(60, 61)) }
                events.hits(62, 63, 64)?.let { eventMap.put(Congas::class, it.groupNotes(62, 63, 64)) }
                events.hits(65, 66)?.let { eventMap.put(Timbales::class, it.groupNotes(65, 66)) }
                events.hits(67, 68)?.let { eventMap.put(Agogo::class, it.groupNotes(67, 68)) }
                events.hits(69)?.let { eventMap.put(Cabasa::class, it.groupNotes(69)) }
                events.hits(70)?.let { eventMap.put(Maracas::class, it.groupNotes(70)) }
                events.hits(71, 72)?.let { eventMap.put(Whistle::class, it.groupNotes(71, 72)) }
                events.hits(73, 74)?.let { eventMap.put(Guiro::class, it.groupNotes(73, 74)) }
                events.hits(75)?.let { eventMap.put(Claves::class, it.groupNotes(75)) }
                events.hits(76, 77)?.let { eventMap.put(Woodblock::class, it.groupNotes(76, 77)) }
                events.hits(78, 79)?.let { eventMap.put(Cuica::class, it.groupNotes(78, 79)) }
                events.hits(80, 81)?.let { eventMap.put(Triangle::class, it.groupNotes(80, 81)) }
                events.hits(82)?.let { eventMap.put(Shaker::class, it.groupNotes(82)) }
                events.hits(83)?.let { eventMap.put(JingleBell::class, it.groupNotes(83)) }
                events.hits(85)?.let { eventMap.put(Castanets::class, it.groupNotes(85)) }
                events.hits(86, 87)?.let { eventMap.put(Surdo::class, it.groupNotes(86, 87)) }
            }

            48 -> {
                events.hits(31)?.let { eventMap.put(Sticks::class, it.groupNotes(31)) }
                events.hits(32)?.let { eventMap.put(SquareClick::class, it.groupNotes(32)) }
                events.hits(33, 34)?.let { eventMap.put(Metronome::class, it.groupNotes(33, 34)) }
                // Castanets are special because they appear twice
                events.hits(39, 85)?.let { eventMap.put(Castanets::class, listOf(it)) }
                events.hits(54)?.let { eventMap.put(Tambourine::class, it.groupNotes(54)) }
                events.hits(56)?.let { eventMap.put(Cowbell::class, it.groupNotes(56)) }
                events.hits(60, 61)?.let { eventMap.put(Bongos::class, it.groupNotes(60, 61)) }
                events.hits(62, 63, 64)?.let { eventMap.put(Congas::class, it.groupNotes(62, 63, 64)) }
                events.hits(65, 66)?.let { eventMap.put(Timbales::class, it.groupNotes(65, 66)) }
                events.hits(67, 68)?.let { eventMap.put(Agogo::class, it.groupNotes(67, 68)) }
                events.hits(69)?.let { eventMap.put(Cabasa::class, it.groupNotes(69)) }
                events.hits(70)?.let { eventMap.put(Maracas::class, it.groupNotes(70)) }
                events.hits(71, 72)?.let { eventMap.put(Whistle::class, it.groupNotes(71, 72)) }
                events.hits(73, 74)?.let { eventMap.put(Guiro::class, it.groupNotes(73, 74)) }
                events.hits(75)?.let { eventMap.put(Claves::class, it.groupNotes(75)) }
                events.hits(76, 77)?.let { eventMap.put(Woodblock::class, it.groupNotes(76, 77)) }
                events.hits(78, 79)?.let { eventMap.put(Cuica::class, it.groupNotes(78, 79)) }
                events.hits(80, 81)?.let { eventMap.put(Triangle::class, it.groupNotes(80, 81)) }
                events.hits(82)?.let { eventMap.put(Shaker::class, it.groupNotes(82)) }
                events.hits(83)?.let { eventMap.put(JingleBell::class, it.groupNotes(83)) }
                events.hits(86, 87)?.let { eventMap.put(Surdo::class, it.groupNotes(86, 87)) }
            }

            56 -> {
                events.hits(39)?.let { eventMap.put(HighQ::class, it.groupNotes(39)) }
                events.hits(40)?.let { eventMap.put(Slap::class, it.groupNotes(40)) }
                events.hits(43)?.let { eventMap.put(Sticks::class, it.groupNotes(43)) }
                events.hits(44)?.let { eventMap.put(SquareClick::class, it.groupNotes(44)) }
                events.hits(45, 46)?.let { eventMap.put(Metronome::class, it.groupNotes(45, 46)) }
            }
        }

        return eventMap
    }

    private fun buildSpecialCases(
        context: Midis2jam2,
        program: Int,
        events: MutableList<MidiChannelEvent>,
    ): List<Instrument> {
        return when (program) {
            24 -> // Electronic
                events.notes(52)?.let { notes ->
                    mutableListOf(
                        ReverseCymbal(
                            context,
                            notes.map {
                                // Change the note to 60 (C4) so that it is "standardized"
                                when (it) {
                                    is MidiNoteOnEvent -> it.copy(note = 60)
                                    is MidiNoteOffEvent -> it.copy(note = 60)
                                }
                            }.also { context.file.registerEvents(it) },
                        ),
                    )
                } ?: mutableListOf()

            48 -> // Orchestra
                mutableListOf(
                    events.notes(41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53)?.let { Timpani(context, it) },
                    events.notes(88)?.let { notes ->
                        StageChoir(
                            context,
                            notes.map {
                                // Change the note to 60 (C4) so that it is "standardized"
                                when (it) {
                                    is MidiNoteOnEvent -> it.copy(note = 60)
                                    is MidiNoteOffEvent -> it.copy(note = 60)
                                }
                            }.also { context.file.registerEvents(it) },
                            StageChoir.ChoirType.SynthVoice,
                        )
                    },
                ).filterNotNull()

            56 -> // SFX
                mutableListOf(
                    events.notes(58)?.let { ApplauseChoir(context, it) },
                    events.notes(70)?.let { Helicopter(context, it) },
                ).filterNotNull()

            else -> mutableListOf()
        }
    }

    private fun buildDrumSet(
        context: Midis2jam2,
        program: Int,
        events: MutableList<MidiChannelEvent>,
    ): DrumSet? {
        if (events.hits().isEmpty()) return null
        return when (program) {
            24 -> // Electronic
                ElectronicDrumSet(
                    context,
                    events.hits(),
                )

            25 -> // Analog
                TypicalDrumSet(
                    context,
                    ShellStyle.AlternativeDrumShell.Analog,
                    events.hits(),
                )

            40 -> // Brush
                BrushDrumSet(
                    context,
                    events.hits(),
                )

            48 -> // Orchestra
                OrchestraDrumSet(
                    context,
                    events.hits(),
                )

            else ->
                TypicalDrumSet(
                    context,
                    TypicalDrumShell.fromProgramNumber(program) ?: TypicalDrumShell.Standard,
                    events.hits(),
                )
        }
    }

    private fun List<MidiChannelEvent>.notes(vararg notes: Int): List<MidiNoteEvent>? =
        this.filterIsInstance<MidiNoteEvent>().filter { it.note in notes }.ifEmpty { null }

    private fun List<MidiChannelEvent>.hits(vararg notes: Int): List<MidiNoteOnEvent>? =
        this.filterIsInstance<MidiNoteOnEvent>().filter { it.note in notes }.ifEmpty { null }

    private fun List<MidiChannelEvent>.hits(): List<MidiNoteOnEvent> = this.filterIsInstance<MidiNoteOnEvent>()

    fun MutableList<MidiProgramEvent>.removeDuplicateProgramEvents() {
        // Remove program events at same time (keep the last one)
        for (i in size - 2 downTo 0) {
            while (i < size - 1 && this[i].time == this[i + 1].time) {
                removeAt(i)
            }
        }

        // Remove program events with same value (keep the first one)
        for (i in size - 2 downTo 0) {
            while (i != size - 1 && this[i].program == this[i + 1].program) {
                removeAt(i + 1)
            }
        }
    }
}

/**
 * Each channel is either in a melodic or rhythmic state.
 */
sealed class ChannelState {
    /** Melodic state. */
    data object Melody : ChannelState()

    /** Rhythmic state. */
    data object Rhythm : ChannelState()

    companion object {
        val DEFAULT_STATE: Array<ChannelState> = Array(16) { if (it == 9) Rhythm else Melody }
    }
}
