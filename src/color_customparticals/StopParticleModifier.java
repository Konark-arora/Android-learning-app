package color_customparticals;

import java.util.Date;

import org.anddev.andengine.entity.particle.Particle;
import org.anddev.andengine.entity.particle.ParticleSystem;
import org.anddev.andengine.entity.particle.modifier.IParticleModifier;
import org.anddev.andengine.entity.scene.Scene;

import android.util.Log;

public class StopParticleModifier implements IParticleModifier {
	private long stoptime = 0;
	private float interval = 0;
	private ParticleSystem particlesystem = null;
	private Scene scene=null;

	public StopParticleModifier(ParticleSystem particlesystem, float interval,Scene scene) {
		this.particlesystem = particlesystem;
		this.scene=scene;
		this.interval = interval; // How many seconds this particlesystem will
									// be alive before stop spawning
	}

	public void onInitializeParticle(Particle particle) {
		stoptime = new Date().getTime() + (long) (interval * 1000);
	}

	public void onUpdateParticle(Particle particle) {
		if (new Date().getTime() > stoptime) {
			particlesystem.setParticlesSpawnEnabled(false);
//			scene.detachChild(particlesystem.getLastChild());
			Log.v("check", "DetachChile Called");
		}
	}
}