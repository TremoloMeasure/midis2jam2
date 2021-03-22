package org.wysko.midis2jam2.instrument.percussion.drumset;

import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import org.wysko.midis2jam2.Midis2jam2;
import org.wysko.midis2jam2.midi.MidiNoteOnEvent;

import java.util.List;

import static org.wysko.midis2jam2.Midis2jam2.rad;

/**
 * Anything that is hit with a stick.
 */
public abstract class SingleStickInstrument extends PercussionInstrument {
	
	protected final static double STRIKE_SPEED = 4;
	
	final static double MAX_ANGLE = 50;
	
	protected final Spatial stick;
	
	final Node stickNode = new Node();
	
	protected SingleStickInstrument(Midis2jam2 context, List<MidiNoteOnEvent> hits) {
		super(context, hits);
		stick = context.loadModel("DrumSet_Stick.obj", "StickSkin.bmp", Midis2jam2.MatType.UNSHADED, 0.9f);
		stickNode.attachChild(stick);
		stick.setLocalTranslation(0, 0, 0); // Offset set the stick so the pivot is at the base of the stick
		highLevelNode.attachChild(stickNode);
		
		stick.setLocalRotation(new Quaternion().fromAngles(rad(MAX_ANGLE), 0, 0));
	}
	
	void handleStick(double time, float delta, List<MidiNoteOnEvent> hits) {
		MidiNoteOnEvent nextHit = null;
		if (!hits.isEmpty())
			nextHit = hits.get(0);
		
		while (!hits.isEmpty() && context.file.eventInSeconds(hits.get(0)) <= time)
			nextHit = hits.remove(0);
		
		double proposedRotation = nextHit == null ? SingleStickInstrument.MAX_ANGLE :
				-1000 * ((6E7 / context.file.tempoBefore(nextHit).number) / (1000f / STRIKE_SPEED)) * (time - context.file.eventInSeconds(nextHit));
		
		float[] floats = stick.getLocalRotation().toAngles(new float[3]);
		
		if (proposedRotation > SingleStickInstrument.MAX_ANGLE) {
			// Not yet ready to strike
			if (floats[0] < SingleStickInstrument.MAX_ANGLE) {
				// We have come down, need to recoil
				float xAngle = floats[0] + 5f * delta;
				xAngle = Math.min(rad((float) SingleStickInstrument.MAX_ANGLE), xAngle);
				stick.setLocalRotation(new Quaternion().fromAngles(
						xAngle, 0, 0
				));
			}
		} else {
			// Striking
			stick.setLocalRotation(new Quaternion().fromAngles(rad((float) (
					Math.min(SingleStickInstrument.MAX_ANGLE, proposedRotation)
			)), 0, 0));
		}
		
		float[] finalAngles = stick.getLocalRotation().toAngles(new float[3]);
		
		if (finalAngles[0] > rad((float) SingleStickInstrument.MAX_ANGLE)) {
			// Not yet ready to strike
			stick.setCullHint(Spatial.CullHint.Always);
		} else {
			// Striking or recoiling
			stick.setCullHint(Spatial.CullHint.Dynamic);
		}
	}
}
