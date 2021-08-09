/*
 * Copyright (C) 2021 Jacob Wysko
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

package org.wysko.midis2jam2.instrument.family.chromaticpercussion;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import org.jetbrains.annotations.NotNull;
import org.wysko.midis2jam2.Midis2jam2;
import org.wysko.midis2jam2.instrument.DecayedInstrument;
import org.wysko.midis2jam2.instrument.family.percussive.Stick;
import org.wysko.midis2jam2.midi.MidiChannelSpecificEvent;
import org.wysko.midis2jam2.midi.MidiNoteOnEvent;
import org.wysko.midis2jam2.world.Axis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.wysko.midis2jam2.instrument.family.piano.Keyboard.midiValueToColor;
import static org.wysko.midis2jam2.instrument.family.piano.KeyedInstrument.KeyColor.WHITE;
import static org.wysko.midis2jam2.util.Utils.rad;
import static org.wysko.midis2jam2.world.ShadowController.shadow;

/**
 * Any one of vibraphone, glockenspiel, marimba, or xylophone.
 */
public class Mallets extends DecayedInstrument {
	
	/**
	 * The mallet case is scaled by this value to appear correct.
	 */
	public static final float MALLET_CASE_SCALE = 0.667F;
	
	/**
	 * The number of bars on the mallets instrument.
	 */
	private static final int MALLET_BAR_COUNT = 88;
	
	/**
	 * The lowest note mallets can play.
	 */
	private static final int RANGE_LOW = 21;
	
	/**
	 * The highest note mallets can play.
	 */
	private static final int RANGE_HIGH = 108;
	
	/**
	 * List of lists, where each list contains the strikes corresponding to that bar's MIDI note.
	 */
	private final List<List<MidiNoteOnEvent>> barStrikes;
	
	/**
	 * The type of mallets.
	 */
	private final MalletType type;
	
	/**
	 * Each bar of the instrument. There are {@link #MALLET_BAR_COUNT} bars.
	 */
	private final MalletBar[] bars = new MalletBar[MALLET_BAR_COUNT];
	
	public Mallets(Midis2jam2 context, List<MidiChannelSpecificEvent> eventList, MalletType type) {
		super(context, eventList);
		this.type = type;
		
		Spatial malletCase = context.loadModel("XylophoneCase.obj", "Black.bmp");
		malletCase.setLocalScale(MALLET_CASE_SCALE);
		instrumentNode.attachChild(malletCase);
		
		var whiteCount = 0;
		for (var i = 0; i < MALLET_BAR_COUNT; i++) {
			if (midiValueToColor(i + RANGE_LOW) == WHITE) {
				bars[i] = new MalletBar(i + RANGE_LOW, whiteCount);
				whiteCount++;
			} else {
				bars[i] = new MalletBar(i + RANGE_LOW, i);
			}
		}
		Arrays.stream(bars).forEach(bar -> instrumentNode.attachChild(bar.noteNode));
		
		barStrikes = new ArrayList<>();
		
		for (var i = 0; i < 88; i++) {
			barStrikes.add(new ArrayList<>());
		}
		
		eventList.forEach((MidiChannelSpecificEvent event) -> {
			if (event instanceof MidiNoteOnEvent) {
				int midiNote = ((MidiNoteOnEvent) event).note;
				if (midiNote >= RANGE_LOW && midiNote <= RANGE_HIGH) {
					barStrikes.get(midiNote - RANGE_LOW).add(((MidiNoteOnEvent) event));
				}
			}
		});
		
		highestLevel.setLocalTranslation(18, 0, -5);
		
		/* Add shadow */
		Spatial shadow = shadow(context, "Assets/XylophoneShadow.obj", "Assets/XylophoneShadow.png");
		shadow.setLocalScale(2 / 3f);
		instrumentNode.attachChild(shadow);
		shadow.setLocalTranslation(0, -22, 0);
	}
	
	@Override
	public void tick(double time, float delta) {
		super.tick(time, delta);
		for (int i = 0, barsLength = bars.length; i < barsLength; i++) {
			bars[i].tick(delta);
			var stickStatus = Stick.handleStick(context, bars[i].malletNode, time, delta,
					barStrikes.get(i),
					Stick.STRIKE_SPEED, Stick.MAX_ANGLE, Axis.X);
			if (stickStatus.justStruck()) {
				bars[i].recoilBar();
			}
			bars[i].shadow.setLocalScale((float)
					((1 - (Math.toDegrees(stickStatus.getRotationAngle()) / Stick.MAX_ANGLE)) / 2));
		}
	}
	
	@Override
	protected void moveForMultiChannel(float delta) {
		float i1 = indexForMoving(delta) - 2;
		instrumentNode.setLocalTranslation(-50, 26.5F + (2 * i1), 0);
		highestLevel.setLocalRotation(new Quaternion().fromAngles(0, rad(-18) * i1, 0));
	}
	
	/**
	 * The type of mallets.
	 */
	public enum MalletType {
		
		/**
		 * The vibraphone.
		 */
		VIBES("VibesBar.bmp"),
		
		/**
		 * The marimba.
		 */
		MARIMBA("MarimbaBar.bmp"),
		
		/**
		 * The glockenspiel.
		 */
		GLOCKENSPIEL("GlockenspielBar.bmp"),
		
		/**
		 * The xylophone.
		 */
		XYLOPHONE("XylophoneBar.bmp");
		
		/**
		 * The texture file of this type of mallet.
		 */
		private final String textureFile;
		
		MalletType(String textureFile) {
			this.textureFile = textureFile;
		}
	}
	
	/**
	 * A single bar out of the 88 for the mallets.
	 */
	public class MalletBar {
		
		/**
		 * The model in the up position.
		 */
		private final Spatial upBar;
		
		/**
		 * The model in the down position.
		 */
		private final Spatial downBar;
		
		/**
		 * Contains the entire note geometry.
		 */
		@NotNull
		private final Node noteNode = new Node();
		
		/**
		 * Contains the mallet.
		 */
		@NotNull
		private final Node malletNode = new Node();
		
		/**
		 * The small, circular shadow that appears as the mallet is striking.
		 */
		@NotNull
		private final Spatial shadow;
		
		/**
		 * True if the bar is recoiling, false otherwise.
		 */
		private boolean barIsRecoiling;
		
		/**
		 * True if the bar should begin recoiling.
		 */
		private boolean recoilNow;
		
		public MalletBar(int midiNote, int startPos) {
			Spatial mallet = Mallets.this.context.loadModel("XylophoneMalletWhite.obj", Mallets.this.type.textureFile);
			malletNode.attachChild(mallet);
			malletNode.setLocalScale(MALLET_CASE_SCALE);
			malletNode.setLocalRotation(new Quaternion().fromAngles(rad(50), 0, 0));
			mallet.setLocalTranslation(0, 0, -2);
			malletNode.move(0, 0, 2);
			
			shadow = Mallets.this.context.loadModel("MalletHitShadow.obj", "Black.bmp");
			
			var barNode = new Node();
			if (midiValueToColor(midiNote) == WHITE) {
				upBar = Mallets.this.context.loadModel("XylophoneWhiteBar.obj", Mallets.this.type.textureFile);
				downBar = Mallets.this.context.loadModel("XylophoneWhiteBarDown.obj", Mallets.this.type.textureFile);
				
				barNode.attachChild(upBar);
				barNode.attachChild(downBar);
				
				float scaleFactor = (RANGE_HIGH - midiNote + 20) / 50F;
				barNode.setLocalScale(0.55F, 1, 0.5F * scaleFactor);
				noteNode.move(1.333F * (startPos - 26), 0, 0);
				
				malletNode.setLocalTranslation(0, 1.35F, (-midiNote / 11.5F) + 19);
				shadow.setLocalTranslation(0, 0.75F, (-midiNote / 11.5F) + 11);
			} else {
				upBar = Mallets.this.context.loadModel("XylophoneBlackBar.obj", Mallets.this.type.textureFile);
				downBar = Mallets.this.context.loadModel("XylophoneBlackBarDown.obj", Mallets.this.type.textureFile);
				
				barNode.attachChild(upBar);
				barNode.attachChild(downBar);
				
				float scaleFactor = (RANGE_HIGH - midiNote + 20) / 50F;
				barNode.setLocalScale(0.6F, 0.7F, 0.5F * scaleFactor);
				noteNode.move(1.333F * (midiNote * (0.583F) - 38.2F), 0, (-midiNote / 50F) + 2.667F);
				
				malletNode.setLocalTranslation(0, 2.6F, (midiNote / 12.5F) - 2);
				shadow.setLocalTranslation(0, 2, (midiNote / 12.5F) - 10);
			}
			downBar.setCullHint(Spatial.CullHint.Always);
			barNode.attachChild(upBar);
			noteNode.attachChild(barNode);
			noteNode.attachChild(malletNode);
			noteNode.attachChild(shadow);
			shadow.setLocalScale(0);
		}
		
		/**
		 * Begins recoiling the bar.
		 */
		public void recoilBar() {
			barIsRecoiling = true;
			recoilNow = true;
		}
		
		/**
		 * Animates the mallet and the bar.
		 *
		 * @param delta the amount of time since the last frame update
		 */
		public void tick(float delta) {
			if (barIsRecoiling) {
				upBar.setCullHint(Spatial.CullHint.Always);
				downBar.setCullHint(Spatial.CullHint.Dynamic);
				Vector3f barRecoil = downBar.getLocalTranslation();
				if (recoilNow) {
					// The bar needs to go all the way down
					downBar.setLocalTranslation(0, -0.5F, 0);
				} else {
					if (barRecoil.y < -0.0001) {
						downBar.move(0, 5 * delta, 0);
						Vector3f localTranslation = downBar.getLocalTranslation();
						downBar.setLocalTranslation(new Vector3f(
								localTranslation.x,
								Math.min(0, localTranslation.y),
								localTranslation.z
						));
					} else {
						
						upBar.setCullHint(Spatial.CullHint.Dynamic);
						downBar.setCullHint(Spatial.CullHint.Always);
						downBar.setLocalTranslation(0, 0, 0);
					}
				}
				recoilNow = false;
			} else {
				upBar.setCullHint(Spatial.CullHint.Dynamic);
				downBar.setCullHint(Spatial.CullHint.Always);
			}
		}
	}
}
