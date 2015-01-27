package com.appspartan.custom;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kids.fun2learn.scene.AlphabetPuzzleScene;

public class CustomSprite extends Sprite {
	private Scene scene;
	private boolean actionMove;
	private ArrayList<Sprite> arrayList;
	private int tag;
	private IEntity sceneLayerTop, sceneLayer;
	private int tileNumber;
	private static int isImageComplete;
	private ImagePuzzleCompleteListener imagePuzzleCompleteListener;

	public CustomSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, Scene scene, IEntity sceneLayer, ArrayList<Sprite> arrayList, int tag, ImagePuzzleCompleteListener imagePuzzleCompleteListener, Entity sceneLayerTop) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.scene = scene;
		tileNumber = tag;
		this.sceneLayerTop = sceneLayerTop;
		this.sceneLayer = sceneLayer;
		this.arrayList = arrayList;
		this.imagePuzzleCompleteListener = imagePuzzleCompleteListener;
		isImageComplete = 0;
		this.tag = tag;
		scene.registerTouchArea(this);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.setScale(.6f);
		sceneLayer.attachChild(this);
	}

	public int getTileNumber() {
		return tileNumber;
	}

	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public interface ImagePuzzleCompleteListener {
		void isImageComplete();
	}

	public void isImageComplete() {
		if (isImageComplete == arrayList.size()) {
			isImageComplete = 0;
			imagePuzzleCompleteListener.isImageComplete();
		}
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (actionMove) {
			System.out.println("!!action__moveeee");
			for (int i = 0; i < arrayList.size(); i++) {
				Sprite sprite = arrayList.get(i);
				if ((sprite.collidesWith(this)) && (this.getTextureRegion().getTexture() == sprite.getTextureRegion().getTexture())) {
					isImageComplete++;
					sprite.setVisible(true);
					this.setVisible(false);
					scene.unregisterTouchArea(this);
					actionMove = false;
					break;
				}
				if (i == arrayList.size() - 1) {
					final ScaleAtModifier entityModifier = new ScaleAtModifier(.5f, .8f, .5f, .8f, .5f, this.getWidth() / 2, this.getHeight() / 2);
					MoveModifier moveModifier = new MoveModifier(.5f, this.getX(), AlphabetPuzzleScene.dragCordsss[tag][0], this.getY(), AlphabetPuzzleScene.dragCordsss[tag][1]);
					this.registerEntityModifier(new ParallelEntityModifier(moveModifier, entityModifier));
					actionMove = false;
					break;
				}
			}
			isImageComplete();
		}
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
			final ScaleAtModifier entityModifier = new ScaleAtModifier(.1f, .5f, .8f, .5f, .8f, this.getWidth() / 2, this.getHeight() / 2);
			this.registerEntityModifier(entityModifier);
			this.getParent().detachChild(this);
			sceneLayerTop.attachChild(this);
		} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			actionMove = true;
			this.getParent().detachChild(this);
			sceneLayer.attachChild(this);
		} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
			this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
		}
		return true;
	}
}
