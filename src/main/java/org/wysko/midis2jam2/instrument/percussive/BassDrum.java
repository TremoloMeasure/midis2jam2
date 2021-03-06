package org.wysko.midis2jam2.instrument.percussive;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import org.wysko.midis2jam2.Midis2jam2;
import org.wysko.midis2jam2.midi.MidiNoteOnEvent;

import java.util.List;

import static org.wysko.midis2jam2.Midis2jam2.rad;
import static org.wysko.midis2jam2.instrument.percussive.StickDrum.MAX_ANGLE;

public class BassDrum extends Drum {
	
	private final static int PEDAL_MAX_ANGLE = 20;
	
	Spatial bassDrum;
	Spatial bassDrumBeaterArm;
	Spatial bassDrumBeaterHolder;
	Spatial bassDrumPedal;
	
	Node highLevelNode = new Node();
	Node drumNode = new Node();
	Node beaterNode = new Node();
	
	public BassDrum(Midis2jam2 context, List<MidiNoteOnEvent> hits) {
		super(context, hits);
		bassDrum = context.loadModel("DrumSet_BassDrum.obj", "DrumShell.bmp");
		bassDrumBeaterArm = context.loadModel("DrumSet_BassDrumBeaterArm.obj", "MetalTexture.bmp");
		bassDrumBeaterHolder = context.loadModel("DrumSet_BassDrumBeaterHolder.obj", "MetalTexture.bmp");
		bassDrumPedal = context.loadModel("DrumSet_BassDrumPedal.obj", "MetalTexture.bmp");
		
		drumNode.attachChild(bassDrum);
		beaterNode.attachChild(bassDrumBeaterArm);
		beaterNode.attachChild(bassDrumBeaterHolder);
		beaterNode.attachChild(bassDrumPedal);
		highLevelNode.attachChild(drumNode);
		highLevelNode.attachChild(beaterNode);
		
		bassDrumBeaterArm.setLocalTranslation(0, 5.5f, 1.35f);
		bassDrumBeaterArm.setLocalRotation(new Quaternion().fromAngles(rad(MAX_ANGLE),0,0));
		bassDrumPedal.setLocalRotation(new Quaternion().fromAngles(rad(PEDAL_MAX_ANGLE),0,0));
		bassDrumPedal.setLocalTranslation(0,0.5f,7.5f);
		beaterNode.setLocalTranslation(0,0,1.5f);
		
		highLevelNode.setLocalTranslation(0,0,-80);
		context.getRootNode().attachChild(highLevelNode);
	}
	
	@Override
	public void tick(double time, float delta) {
		MidiNoteOnEvent nextHit = null;
		while (!hits.isEmpty() && context.file.eventInSeconds(hits.get(0)) <= time)
			nextHit = hits.remove(0);
		
		if (nextHit != null) {
			// We need to strike
			bassDrumBeaterArm.setLocalRotation(new Quaternion().fromAngles(0, 0, 0));
			bassDrumPedal.setLocalRotation(new Quaternion().fromAngles(0, 0, 0));
			drumNode.setLocalTranslation(0,0, (float) (-3 * velocityRecoilDampening(nextHit.velocity)));
		} else {
			
			// Drum recoil
			Vector3f localTranslation = drumNode.getLocalTranslation();
			if (localTranslation.z < -0.0001) {
				drumNode.setLocalTranslation(0, 0, Math.min(0,
						localTranslation.z + (Drum.DRUM_RECOIL_COMEBACK * delta)));
			} else {
				drumNode.setLocalTranslation(0, 0, 0);
			}
			
			// Beater comeback
			float[] beaterAngles = bassDrumBeaterArm.getLocalRotation().toAngles(new float[3]);
			float beaterAngle = beaterAngles[0] + 8f * delta;
			beaterAngle = Math.min(rad((float) MAX_ANGLE), beaterAngle);
			bassDrumBeaterArm.setLocalRotation(new Quaternion().fromAngles(
					beaterAngle, 0, 0
			));
			
			// Pedal comeback
			float[] pedalAngles = bassDrumPedal.getLocalRotation().toAngles(new float[3]);
			float pedalAngle = (float) (pedalAngles[0] + 8f * delta * (PEDAL_MAX_ANGLE/ MAX_ANGLE));
			pedalAngle = Math.min(rad((float) PEDAL_MAX_ANGLE), pedalAngle);
			bassDrumPedal.setLocalRotation(new Quaternion().fromAngles(
					pedalAngle, 0, 0
			));
		}
		
		
	}
}
