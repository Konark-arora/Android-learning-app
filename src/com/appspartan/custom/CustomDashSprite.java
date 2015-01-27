package com.appspartan.custom;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class CustomDashSprite extends Sprite {
	private int tag;

	public CustomDashSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, int tag) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.tag = tag;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
