package com.appspartan.custom;

import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;

public class CustomParticle extends SpriteParticleSystem {
	private Context context;

	public CustomParticle(IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum, pTextureRegion, pVertexBufferObjectManager);
		startTime = System.currentTimeMillis();
	}

	public CustomParticle(Context context, IParticleEmitter pParticleEmitter, float pRateMinimum, float pRateMaximum, int pParticlesMaximum, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pParticleEmitter, pRateMinimum, pRateMaximum, pParticlesMaximum, pTextureRegion, pVertexBufferObjectManager);
		this.context = context;
		startTime = System.currentTimeMillis();
	}

	long startTime, endTime;
	boolean bbb = false;

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		endTime = System.currentTimeMillis();
		try {
			if (context != null) {
				if ((endTime - startTime) > 3000) {
					if (!bbb) {
						bbb = true;
						System.out.println("methodd calleddd");
						setParticlesSpawnEnabled(false);
						if (this != null) {
							this.setVisible(false);
						}
					}
				}
			} else {
				if ((endTime - startTime) > 1500) {
					if (!bbb) {
						bbb = true;
						System.out.println("methodd calleddd");
						setParticlesSpawnEnabled(false);
						if (this != null) {
							this.setVisible(false);
						}
					}
				}
			}
		} catch (Exception e) {}
	}
}
