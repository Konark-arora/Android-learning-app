package color_customparticals;


import org.anddev.andengine.entity.particle.ParticleSystem;
import org.anddev.andengine.entity.particle.emitter.IParticleEmitter;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class CustomParticle extends ParticleSystem {
	long startTime ,endTime;
	boolean bbb = false;

	public CustomParticle(IParticleEmitter pParticleEmitter,
			float pRateMinimum, float pRateMaximum, int pParticlesMaximum,
			TextureRegion pTextureRegion) {
		super(pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum,
				pTextureRegion);
		
		startTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {		
		super.onManagedUpdate(pSecondsElapsed);
		endTime = System.currentTimeMillis();
		if((endTime-startTime)>1000)
		{
			if(!bbb)
			{
				bbb = true;
			System.out.println("methodd calleddd");
			setParticlesSpawnEnabled(false);			
			//SceneUtil.scene.detachChild(this.getLastChild());
			}
		}
		
	}

	

}
