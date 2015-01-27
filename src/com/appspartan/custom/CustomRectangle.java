package com.appspartan.custom;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class CustomRectangle extends Rectangle {
	private int tag;

	public CustomRectangle(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager vertexBufferObjectManager, int tag) {
		super(pX, pY, pWidth, pHeight, vertexBufferObjectManager);
		this.tag = tag;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
