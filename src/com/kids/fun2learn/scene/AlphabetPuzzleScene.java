package com.kids.fun2learn.scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.content.Context;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.os.AsyncTask;

import com.appspartan.custom.CustomDashSprite;
import com.appspartan.custom.CustomParticle;
import com.appspartan.custom.CustomSprite;
import com.appspartan.custom.CustomSprite.ImagePuzzleCompleteListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.SharedPrefUtils;

public class AlphabetPuzzleScene implements ImagePuzzleCompleteListener {
	Random random;
	private boolean isAlpahabetMissingPuzzleMode;
	private SimpleBaseGameActivity context;
	private Scene scene;
	private Body body;
	private PhysicsConnector physicsConnector;
	private PhysicsHandler physicsHandler_mittiBgSprite;
	private PhysicsHandler physicsHandler_alphabetsprite;
	private PhysicsWorld mPhysicsWorld;
	private Body groundBody, groundBodyUpper;
	IAreaShape ground, groundUpper;
	private AnimatedSprite alphabetAnimatedSpriteMiddle;
	private ITexture bgTexture, particleTexture;
	private ITexture dashTexture;
	private ITexture belowStandBgTexture;
	private ITexture mittiBgTexture;
	private ITexture screen_shot_fun2learn_bottom_bg_Texture;
	private ITexture colorBitmapTexture;
	private ITexture innerBitmapTextureAtlas;
	private ITexture outerBitmapTextureAtlas;
	private ITexture rubberTexture;
	private ITexture screen_shot_downBlackBgTextureAtlas;
	private ITexture downBlackBgTextureAtlas;
	// for animated sprite
	private BitmapTextureAtlas alphabetBitmapTextureAtlas;
	private BitmapTextureAtlas cheeringCartoonBitmapTextureAtlas;
	private BitmapTextureAtlas pipeTextureAtlas;
	private ITextureRegion dashTextureRegion, belowStandBgTextureRegion;
	private ITextureRegion innerTextureRegion;
	private ITextureRegion outerTextureRegion;
	private ITextureRegion bgTextureRegion, particleTextureRegion;
	private ITextureRegion rubberTextureRegion;
	private ITextureRegion mittiBgTextureRegion;
	private ITextureRegion screen_shot_fun2learn_bottom_bg_TextureRegion;
	private ITextureRegion colorTextureRegion;
	private ITextureRegion downBlackBgTextureRegion;
	private ITextureRegion screen_shot_downBlackBgTextureRegions;
	// for animated sptite
	private TiledTextureRegion cheeringCartoonTiledTextureRegion;
	private TiledTextureRegion pipeTiledTextureRegion;
	private TiledTextureRegion alphabetTextureRegion;
	private Sprite bgSprite, belowBgPlayStandSprite, screen_shot_fun2learn_bottom_bg_Sprite;
	private Sprite part1Sprite, part2Sprite, part3Sprite, part4Sprite;
	private Sprite draggerSprite1, draggerSprite2, draggerSprite3, draggerSprite4;
	private Sprite dashSprite;
	private Sprite slingShotSprite;
	private Sprite mittiBgSprite;
	private Sprite rubberSprite;
	private Sprite innerSprite;
	private Sprite outlineSprite, fullCharacterSprite;
	private Sprite outerSprite;
	private Sprite colorSprite;
	private Sprite draggerBoxSprite;
	private AnimatedSprite characterAnimatingAnimatedSprite;
	private Sprite screen_shot_downBlackBgSprite, downBlackBgSprite;
	private boolean isErase = false;
	private AnimatedSprite pipeSprite;
	private AnimatedSprite alphabetsprite;
	private AnimatedSprite cheeringCartoonAnimatedSprite;
	private int clickedSpriteNumber;
	public Rectangle charac;
	private Entity sceneLayer1, sceneLayer0, sceneLayer2, sceneLayer3;
	private String alphabets[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private Sound alphabetSoundData[];
	private Music backgroundMusic;
	private ArrayList<Sprite> draggingBoxBgSpriteList = new ArrayList<Sprite>();
	private ArrayList<Sprite> partSpriteList = new ArrayList<Sprite>();
	private ArrayList<CustomSprite> draggingPartSpriteList = new ArrayList<CustomSprite>();
	private ArrayList<Sprite> colorPaintList = new ArrayList<Sprite>();
	private ArrayList<ITextureRegion> partTextureRegionList = new ArrayList<ITextureRegion>();
	List<String> alphabetList;
	private Entity scneLayerLast;
	private Rectangle rectangle, rectShapes;
	private Entity sceneLayerTop;

	public AlphabetPuzzleScene(Context context) {
		random = new Random();
		alphabetList = Arrays.asList(alphabets);
		this.context = (SimpleBaseGameActivity) context;
		loadResources();
		loadScene();
		isErase = false;
	}

	private void loadResources() {
		try {
			this.bgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_puzzle/alphabet_puzzle_bg.png");
			this.bgTextureRegion = TextureRegionFactory.extractFromTexture(this.bgTexture);
			this.bgTexture.load();
			this.belowStandBgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_puzzle/stand_play.png");
			this.belowStandBgTextureRegion = TextureRegionFactory.extractFromTexture(this.belowStandBgTexture);
			this.belowStandBgTexture.load();
			this.particleTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/particle.png");
			this.particleTextureRegion = TextureRegionFactory.extractFromTexture(this.particleTexture);
			this.particleTexture.load();
			this.dashTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_puzzle/blank_fill.png");
			this.dashTextureRegion = TextureRegionFactory.extractFromTexture(this.dashTexture);
			this.dashTexture.load();
			this.cheeringCartoonBitmapTextureAtlas = null;
			this.cheeringCartoonBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.cheeringCartoonTiledTextureRegion = null;
			this.cheeringCartoonTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cheeringCartoonBitmapTextureAtlas, context.getAssets(), "gfx/alphabet_puzzle/squarle.png", 0, 0, 4, 1);
			cheeringCartoonBitmapTextureAtlas.load();
		} catch (Exception exception) {}
		loadAlphabetSoundsInAdvance();
	}

	public void reInitializeResources() {
		x = 0;
		partTextureRegionList.clear();
		dashSpriteList.clear();
		alphabetAnimatedList.clear();
		count = -1;
		isAlpahabetMissingPuzzleMode = false;
		partSpriteList.clear();
		draggingPartSpriteList.clear();
	}

	public void clearScene() {
		if (characterAnimatingAnimatedSprite != null) {
			characterAnimatingAnimatedSprite.setPosition(-1000, -1000);
			characterAnimatingAnimatedSprite.setVisible(false);
			sceneLayer1.detachChild(characterAnimatingAnimatedSprite);
			characterAnimatingAnimatedSprite = null;
		}
		if (rectangle != null) {
			rectangle.setPosition(-1000, -1000);
		}
		if (rectShapes != null) {
			rectShapes.setPosition(-1000, -1000);
		}
		if (draggingBoxBgSpriteList != null && draggingBoxBgSpriteList.size() > 0) {
			for (Sprite sprite : draggingBoxBgSpriteList) {
				sprite.setPosition(-1000, -1000);
			}
		}
		if (outlineSprite != null)
			clearSprite(outlineSprite);
		if (alphabetAnimatedSpriteMiddle != null) {
			alphabetAnimatedSpriteMiddle.setPosition(-1000, -1000);
			alphabetAnimatedSpriteMiddle.setVisible(false);
			sceneLayerTop.detachChild(alphabetAnimatedSpriteMiddle);
			alphabetAnimatedSpriteMiddle = null;
		}
		if (fullCharacterSprite != null)
			clearSprite(fullCharacterSprite);
		if (partSpriteList != null && partSpriteList.size() > 0) {
			for (int i = 0; i < partSpriteList.size(); i++) {
				Sprite sprite = partSpriteList.get(i);
				clearSprite(sprite);
				if (draggingPartSpriteList != null && draggingPartSpriteList.size() > 0) {
					if (i < draggingPartSpriteList.size()) {
						clearSprite(draggingPartSpriteList.get(i));
					}
				}
			}
		}
		clearDashSpriteList();
		clearAlphabetAnimatedPuzzleSpriteList();
	}

	public void clearDashSpriteList() {
		if (dashSpriteList != null && dashSpriteList.size() > 0) {
			for (int i = 0; i < dashSpriteList.size(); i++) {
				clearSprite(dashSpriteList.get(i));
			}
		}
	}

	public void clearAlphabetAnimatedPuzzleSpriteList() {
		if (alphabetAnimatedList != null && alphabetAnimatedList.size() > 0) {
			for (int i = 0; i < alphabetAnimatedList.size(); i++) {
				clearSprite(alphabetAnimatedList.get(i));
			}
		}
	}

	public void clearSprite(Sprite sprite) {
		sprite.setPosition(-1000, -1000);
		sprite.setVisible(false);
		sceneLayer1.detachChild(sprite);
		sprite = null;
	}

	public void changeLevel(int position) {
		clearScene();
		System.gc();
		reInitializeResources();
		clickedSpriteNumber = position;
		loadShapeTexture(position);
		loadShapeSprite(position);
	}

	public void loadShapeTexture(int position) {
		String folderName = alphabetList.get(position);
		try {
			for (int i = 0; i < 6; i++) {
				ITexture draggerBoxTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_puzzle/" + folderName + "/" + i + ".png");
				ITextureRegion draggerBoxTextureRegion = TextureRegionFactory.extractFromTexture(draggerBoxTexture);
				this.partTextureRegionList.add(draggerBoxTextureRegion);
				draggerBoxTexture.load();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadShapeSprite(int position) {
		outlineSprite = new Sprite(CommonConstants.CAMERA_WIDTH / 2 - partTextureRegionList.get(0).getWidth() / 2, 0, partTextureRegionList.get(0).deepCopy(), context.getVertexBufferObjectManager());
		sceneLayer1.attachChild(outlineSprite);
		makePartSprites();// default
		makeDraggingPartSprites();
	}

	private void loadScene() {
		scene = new Scene();
		sceneLayer0 = new Entity();
		scene.attachChild(sceneLayer0);
		rectangle = new Rectangle(100, 100, 100, 100, context.getVertexBufferObjectManager());
		rectangle.setVisible(true);
		rectangle.setColor(Color.TRANSPARENT);
		rectShapes = new Rectangle(100, 100, 100, 100, context.getVertexBufferObjectManager());
		rectShapes.setVisible(false);
		sceneLayer1 = new Entity();
		scene.attachChild(sceneLayer1);
		scneLayerLast = new Entity();
		scene.attachChild(scneLayerLast);
		bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer0.attachChild(bgSprite);
		sceneLayerTop = new Entity();
		scene.attachChild(sceneLayerTop);
		this.cheeringCartoonAnimatedSprite = null;
		this.cheeringCartoonAnimatedSprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 - cheeringCartoonTiledTextureRegion.getWidth() / 2, 80, cheeringCartoonTiledTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer0.attachChild(cheeringCartoonAnimatedSprite);
		cheeringCartoonAnimatedSprite.setScale(.2f);
		belowBgPlayStandSprite = new Sprite(0, bgSprite.getX() + bgSprite.getHeight() - 100, belowStandBgTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer0.attachChild(belowBgPlayStandSprite);
		try {
			ITexture draggingBoxTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_puzzle/puzzle_dragger_box_bg.png");
			ITextureRegion draggingBoxTextureRegion = TextureRegionFactory.extractFromTexture(draggingBoxTexture);
			draggingBoxTexture.load();
			draggingBoxBgSpriteList.clear();
			for (int i = 0; i < 4; i++) {
				Sprite draggingBoxSprite = new Sprite(-1000, -1000, draggingBoxTextureRegion.deepCopy(), context.getVertexBufferObjectManager());
				draggingBoxSprite.setScale(.6f);
				sceneLayer1.attachChild(draggingBoxSprite);
				draggingBoxBgSpriteList.add(draggingBoxSprite);
			}
		} catch (Exception exception) {}
		changeLevel(0);
		showMiddleLetter();
		scene.attachChild(rectShapes);
		scene.attachChild(rectangle);
		setPhysicsWorld();
		setBodyToPhysicsWorld();
	}

	private void makeCharacterAnimating() {
		BitmapTextureAtlas characterAnimatingTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion characterAnimatingTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(characterAnimatingTextureAtlas, context.getAssets(), "gfx/alphabet_puzzle/animating_character/" + 0 + ".png", 0, 0, 2, 1);
		characterAnimatingTextureAtlas.load();
		characterAnimatingAnimatedSprite = new AnimatedSprite(outlineSprite.getX(), outlineSprite.getY(), characterAnimatingTiledTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					this.setCurrentTileIndex(0);
					if (!characterAnimatingAnimatedSprite.isAnimationRunning())
						animateSprite(characterAnimatingAnimatedSprite, false);
				}
				return true;
			}
		};
		animateSprite(characterAnimatingAnimatedSprite, true);
		scene.registerTouchArea(characterAnimatingAnimatedSprite);
		sceneLayer1.attachChild(characterAnimatingAnimatedSprite);
	}

	public void animateSprite(AnimatedSprite animatedSprite, final boolean isShowAlpahbetPuzzle) {
		animatedSprite.animate(200, 2, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				if (isShowAlpahbetPuzzle)
					showAlphabetPuzzle();
			}
		});
	}

	private void makePartSprites() {
		for (int i = 0; i < 4; i++) {
			Sprite partSprite = new Sprite(-1000, -1000, partTextureRegionList.get(i + 1).deepCopy(), context.getVertexBufferObjectManager());
			partSprite.setVisible(false);
			sceneLayer1.attachChild(partSprite);
			partSpriteList.add(partSprite);
			switch (i) {
				case 0:
					partSpriteList.get(i).setPosition(outlineSprite.getX(), outlineSprite.getY());
					break;
				case 1:
					partSpriteList.get(i).setPosition(partSpriteList.get(0).getX() + partSpriteList.get(0).getWidth(), outlineSprite.getY());
					break;
				case 2:
					partSpriteList.get(i).setPosition(partSpriteList.get(0).getX(), partSpriteList.get(1).getY() + partSpriteList.get(1).getHeight());
					break;
				case 3:
					partSpriteList.get(i).setPosition(partSpriteList.get(2).getX() + partSpriteList.get(2).getWidth(), partSpriteList.get(1).getY() + partSpriteList.get(1).getHeight());
					break;
			}
		}
	}

	public void blinkSprite(final Sprite sprite, final boolean value) {
		final LoopEntityModifier blinkModifier = new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(0.35f), new FadeInModifier(0.35f)), 1, new IEntityModifier.IEntityModifierListener() {
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				sprite.setVisible(true);
			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				if (value)
					sprite.setVisible(false);
			}
		});
		sprite.registerEntityModifier(blinkModifier);
	}

	public void hintShapeImages() {
		outer: for (int i = 0; i < partSpriteList.size(); i++) {
			Sprite sprite = partSpriteList.get(i);
			if (!sprite.isVisible()) {
				System.out.println("!!!iii==" + i);
				blinkSprite(sprite, true);
				for (int j = 0; j < draggingPartSpriteList.size(); j++) {
					if (draggingPartSpriteList.get(j).getTileNumber() == i) {
						System.out.println("!!!jjj==" + j + "i==" + i);
						blinkSprite(draggingPartSpriteList.get(j), false);
						break outer;
					}
				}
			}
		}
	}

	public static float dragCordsss[][] = new float[4][2];

	private void makeDraggingPartSprites() {
		float x_initialDragSprite = 8;
		float y = (belowBgPlayStandSprite.getY() + belowBgPlayStandSprite.getHeight()) / 2 + draggingBoxBgSpriteList.get(0).getHeight() + 3;
		for (int i = 0; i < 4; i++) {
			if (i != 0) {
				x_initialDragSprite = x_initialDragSprite + draggingPartSpriteList.get(0).getWidth() / 2 + gapForDraggingSprite;
			}
			CustomSprite partSprite = new CustomSprite(x_initialDragSprite, y, partTextureRegionList.get(i + 1).deepCopy(), context.getVertexBufferObjectManager(), scene, sceneLayer1, partSpriteList, i, this, sceneLayerTop);
			dragCordsss[i][0] = partSprite.getX();
			dragCordsss[i][1] = partSprite.getY();
			draggingPartSpriteList.add(partSprite);
		}
		Collections.shuffle(draggingPartSpriteList);
		for (int j = 0; j < 4; j++) {
			draggingPartSpriteList.get(j).setTag(j);
			draggingPartSpriteList.get(j).setPosition(dragCordsss[j][0], dragCordsss[j][1]);
			draggingBoxBgSpriteList.get(j).setWidth(draggingPartSpriteList.get(j).getWidth());
			draggingBoxBgSpriteList.get(j).setHeight(draggingPartSpriteList.get(j).getHeight());
			draggingBoxBgSpriteList.get(j).setPosition(dragCordsss[j][0], dragCordsss[j][1]);
		}
	}

	void createParticalSystem(float x, float y, int parNo1) {
		final PointParticleEmitter particleEmitter = new PointParticleEmitter(x, y);
		final CustomParticle particleSystem = new CustomParticle(particleEmitter, 30, 30, 5, particleTextureRegion, context.getVertexBufferObjectManager());
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 1));
		particleSystem.addParticleInitializer(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-50, 50, 50, -50));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(50.0f, 360.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1, 1, 1, 1));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2.0f));
		scene.attachChild(particleSystem);
	}

	String colorCode[] = { "128,0,128", "255,255,0" };

	public void chooseColorTexture(int position, String color_texture_plate) {
		isErase = false;
		String colorCodeRGB = colorCode[0];
		String colorCodeRGBArray[] = colorCodeRGB.split(",");
		for (int i = 0; i < partSpriteList.size(); i++) {
			partSpriteList.get(i).setColor(Float.parseFloat(colorCodeRGBArray[0]), Float.parseFloat(colorCodeRGBArray[1]), Float.parseFloat(colorCodeRGBArray[2]));
		}
		for (int i = 0; i < draggingPartSpriteList.size(); i++) {
			draggingPartSpriteList.get(i).setColor(Float.parseFloat(colorCodeRGBArray[0]), Float.parseFloat(colorCodeRGBArray[1]), Float.parseFloat(colorCodeRGBArray[2]));
		}
		hintShapeImages();
	}

	public void onPianoButtonActiondown() {
		if (!SharedPrefUtils.getIsPurchased(context)) {
			mittiBgSprite.setPosition(0, 290);
		} else {
			mittiBgSprite.setPosition(0, 338);
		}
		physicsHandler_mittiBgSprite.setVelocity(0);
		isErase = false;
	}

	public void onPianoButtonActionUp(int position) {
		if (!isAlpahabetMissingPuzzleMode) {
			clickedSpriteNumber = position;
			showMiddleLetter();
			playAlphabetSound(position);
			changeLevel(position);
		} else if (count < dashSpriteList.size()) { // code to drag element
			checkUserValue(position);
		}
	}

	class DelaAsyncTask1 extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			groundBodyUpper.setActive(true);
		}
	}

	class DelaAsyncTask3 extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(280);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			alphabetsprite.setVisible(true);
		}
	}

	private void onPipespriteJump() {
		removeBodyFromPhysicsWorld();
		makeAlphabetsprite(clickedSpriteNumber);
		setBodyToPhysicsWorld();
	}

	long a[] = { 300, 300 };

	private void JumpChar(int x, int fromindexAnimate, int toindexAnimate) {
		groundBodyUpper.setActive(false);
		body.setTransform(x / CommonConstants.PIXEL_TO_METER_RATIO, (belowBgPlayStandSprite.getY() + 150) / 32, 0);
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, mPhysicsWorld.getGravity().y * -0.5f));
		cheeringCartoonAnimatedSprite.animate(a, fromindexAnimate, toindexAnimate, 5, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
				new DelaAsyncTask1().execute();
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				groundBodyUpper.setActive(false);
			}
		});
		final ScaleAtModifier entityModifier = new ScaleAtModifier(.5f /* Durarion */, .1f/* from */, .2f/* to */, .1f, .2f, cheeringCartoonAnimatedSprite.getWidth() / 2/*
																																											 * scale
																																											 * center
																																											 * X
																																											 */, cheeringCartoonAnimatedSprite.getHeight() / 2 /*
																																																								 * scale
																																																								 * center
																																																								 * Y
																																																								 */);
	}

	private void setBodyToPhysicsWorld() {
		final FixtureDef apphabet_FIX = PhysicsFactory.createFixtureDef(0f, 0f, 0f);
		body = PhysicsFactory.createBoxBody(mPhysicsWorld, cheeringCartoonAnimatedSprite, BodyType.DynamicBody, apphabet_FIX);
		physicsConnector = new PhysicsConnector(cheeringCartoonAnimatedSprite, body, true, false);
		mPhysicsWorld.registerPhysicsConnector(physicsConnector);
		// body.setActive(false);
		physicsConnector = new PhysicsConnector(cheeringCartoonAnimatedSprite, body, true, false);
		mPhysicsWorld.registerPhysicsConnector(physicsConnector);
		//
		groundBody.setTransform(groundBody.getLinearVelocity().x, (belowBgPlayStandSprite.getY() + 200) / 32, 0);
		body.setTransform(70 / CommonConstants.PIXEL_TO_METER_RATIO, groundBody.getTransform().getPosition().y - 100, 0);
	}

	public void clearPaintScreen() {
		for (int i = 0; i < colorPaintList.size(); i++) {
			Sprite sprite = colorPaintList.get(i);
			sprite.setPosition(-1000, -1000);
			sprite = null;
		}
		if (colorPaintList != null)
			colorPaintList.clear();
		if (innerSprite != null) {
			innerSprite.setPosition(-1000, -1000);
			innerSprite = null;
		}
		if (outerSprite != null) {
			outerSprite.setPosition(-1000, -1000);
			outerSprite = null;
		}
		try {
			mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
			body.setActive(false);
			body = null;
			alphabetsprite.setPosition(-1000, -1000);
			mittiBgSprite.setPosition(-1000, -1000);
		} catch (Exception exception) {}
		try {
			if (physicsHandler_mittiBgSprite != null)
				physicsHandler_mittiBgSprite.setVelocityY(0);
			if (mittiBgSprite != null) {
				if (SharedPrefUtils.getIsPurchased(context)) {
					mittiBgSprite.setPosition(0, 338);
				} else {
					mittiBgSprite.setPosition(0, 290);
				}
			}
		} catch (Exception exception) {}
	}

	private void makeAlphabetsprite(int spriteClickedSpriteNo) {
		clearPaintScreen();
		try {
			this.innerBitmapTextureAtlas = null;
			this.innerBitmapTextureAtlas = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_painting/alphabet_inner/" + spriteClickedSpriteNo + ".png");
			this.innerTextureRegion = null;
			this.innerTextureRegion = TextureRegionFactory.extractFromTexture(this.innerBitmapTextureAtlas);
			this.innerBitmapTextureAtlas.load();
			this.outerBitmapTextureAtlas = null;
			this.outerBitmapTextureAtlas = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_painting/alphabet_outer/" + spriteClickedSpriteNo + ".png");
			this.outerTextureRegion = null;
			this.outerTextureRegion = TextureRegionFactory.extractFromTexture(this.outerBitmapTextureAtlas);
			this.outerBitmapTextureAtlas.load();
			this.innerSprite = null;
			this.innerSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 - innerTextureRegion.getHeight() / 2 + 100, innerTextureRegion.deepCopy(), context.getVertexBufferObjectManager());
			sceneLayer0.attachChild(innerSprite);
			scene.registerTouchArea(innerSprite);
			if (!SharedPrefUtils.getIsPurchased(context)) {
				this.innerSprite.setPosition(0, CommonConstants.CAMERA_HEIGHT / 2 - innerTextureRegion.getHeight() / 2 + 50);
			}
			this.outerSprite = null;
			this.outerSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 - outerTextureRegion.getHeight() / 2 + 100, outerTextureRegion.deepCopy(), context.getVertexBufferObjectManager());
			if (!SharedPrefUtils.getIsPurchased(context)) {
				this.outerSprite.setPosition(0, CommonConstants.CAMERA_HEIGHT / 2 - innerTextureRegion.getHeight() / 2 + 50);
			}
			sceneLayer1.attachChild(outerSprite);
			this.alphabetBitmapTextureAtlas = null;
			this.alphabetBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.alphabetTextureRegion = null;
			this.alphabetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(alphabetBitmapTextureAtlas, context.getAssets(), "gfx/alphabet_painting/alphabet_letter/" + (spriteClickedSpriteNo + 1) + ".png", 0, 0, 2, 1);
			alphabetBitmapTextureAtlas.load();
			this.alphabetsprite = null;
			this.alphabetsprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 - alphabetTextureRegion.getWidth() / 2, CommonConstants.CAMERA_HEIGHT / 2 - 20, alphabetTextureRegion, context.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					switch (pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							alphabetsprite.setCurrentTileIndex(1);
							break;
						case TouchEvent.ACTION_UP:
							alphabetsprite.setCurrentTileIndex(0);
							onAlphabetItemClick();
							break;
					}
					return true;
				}
			};
			physicsHandler_alphabetsprite = new PhysicsHandler(alphabetsprite);
			alphabetsprite.registerUpdateHandler(physicsHandler_alphabetsprite);
			sceneLayer3.attachChild(alphabetsprite);
			alphabetsprite.setScale(0f);
			scene.registerTouchArea(alphabetsprite);
			alphabetsprite.setCurrentTileIndex(0);
			alphabetsprite.setVisible(false);
			if (SharedPrefUtils.getIsPurchased(context)) {
				alphabetsprite.setPosition(CommonConstants.CAMERA_WIDTH / 2 - alphabetsprite.getWidth() / 2, CommonConstants.CAMERA_HEIGHT / 2 - alphabetsprite.getHeight() / 2);
			} else {
				alphabetsprite.setPosition(CommonConstants.CAMERA_WIDTH / 2 - alphabetsprite.getWidth() / 2, pipeSprite.getY() + pipeSprite.getHeight() + 50);
			}
			charac = new Rectangle(alphabetsprite.getX(), alphabetsprite.getY(), alphabetsprite.getWidth() + 60, alphabetsprite.getHeight() + 1, context.getVertexBufferObjectManager());
			charac.setPosition(CommonConstants.CAMERA_WIDTH / 2 - charac.getWidth() / 2, CommonConstants.CAMERA_HEIGHT / 2 - charac.getHeight() / 2);
			scene.attachChild(charac);
			charac.setVisible(false);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void removeBodyFromPhysicsWorld() {
		if (body != null) {
			mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
			body.setActive(false);
			body = null;
			alphabetBitmapTextureAtlas = null;
			alphabetTextureRegion = null;
			alphabetsprite.setPosition(-10000, -10000);
			alphabetsprite = null;
		}
	}

	/**
	 * Called when body is clicked after jump
	 */
	public void onAlphabetItemClick() {
		try {
			mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
			body.setActive(false);
			body = null;
			physicsHandler_alphabetsprite.setVelocityY(-1000);
			physicsHandler_mittiBgSprite.setVelocityY(-1000);
		} catch (Exception exception) {}
	}

	/**
	 * creates painting color or texture
	 * 
	 * @param position
	 */
	private void createColorTexuturePaint(int position, String color_texture_plate) {
		try {
			if (color_texture_plate.equalsIgnoreCase(CommonConstants.COLOR_PLATE)) {
				this.colorBitmapTexture = null;
				this.colorBitmapTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/painting_color/" + position + ".png");
				this.colorTextureRegion = null;
				this.colorTextureRegion = TextureRegionFactory.extractFromTexture(this.colorBitmapTexture);
				this.colorBitmapTexture.load();
			} else if (color_texture_plate.equalsIgnoreCase(CommonConstants.TEXTURE_PLATE)) {
				this.colorBitmapTexture = null;
				this.colorBitmapTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/painting_texture/" + position + ".png");
				this.colorTextureRegion = null;
				this.colorTextureRegion = TextureRegionFactory.extractFromTexture(this.colorBitmapTexture);
				this.colorBitmapTexture.load();
			}
		} catch (Exception exception) {}
	}

	public void setScenePosition() {
		if (SharedPrefUtils.getIsPurchased(context)) {
			makeAdsFreeScene();
		} else {
			makeSceneWithAds();
		}
	}

	private void makeSceneWithAds() {
		pipeSprite.setPosition(CommonConstants.CAMERA_WIDTH / 2 - 50, 236.146f - pipeTiledTextureRegion.getHeight() / 2 - 65);
		groundBody.setTransform(-100000, -100000, 0);
		ground.setPosition(-100000, -1000000);
		ground = null;
		groundBody = null;
		ground = new Rectangle(-CommonConstants.CAMERA_WIDTH, CommonConstants.CAMERA_HEIGHT / 2 + 30, 2 * CommonConstants.CAMERA_WIDTH, 2, context.getVertexBufferObjectManager());
		ground.setVisible(false);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
		groundBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		scene.attachChild(ground);
		bgSprite.setPosition(0, -50);
		mittiBgSprite.setPosition(0, 290);
	}

	private void makeAdsFreeScene() {
		pipeSprite.setPosition(CommonConstants.CAMERA_WIDTH / 2 - 50, 236.146f - pipeTiledTextureRegion.getHeight() / 2 - 17);
		groundBody.setTransform(-100000, -100000, 0);
		ground.setPosition(-100000, -1000000);
		ground = null;
		groundBody = null;
		ground = new Rectangle(-CommonConstants.CAMERA_WIDTH, CommonConstants.CAMERA_HEIGHT / 2 + mittiBgTextureRegion.getHeight() / 6 + 15, 2 * CommonConstants.CAMERA_WIDTH, 2, context.getVertexBufferObjectManager());
		ground.setVisible(false);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
		groundBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) ground, BodyType.StaticBody, wallFixtureDef);
		scene.attachChild(ground);
		bgSprite.setPosition(0, 0);
		mittiBgSprite.setPosition(0, 338);
	}

	public void unloadScene() {
		alphabets = null;
		this.bgTexture = null;
		this.mittiBgTexture = null;
		this.screen_shot_fun2learn_bottom_bg_Texture = null;
		pipeTextureAtlas = null;
		this.rubberTexture = null;
		this.downBlackBgTextureAtlas = null;
		this.screen_shot_downBlackBgTextureAtlas = null;
		this.particleTexture = null;
		scene = null;
		sceneLayer0 = null;
		sceneLayer1 = null;
		sceneLayer2 = null;
		sceneLayer3 = null;
		downBlackBgSprite = null;
		screen_shot_downBlackBgSprite = null;
		mittiBgSprite = null;
		bgSprite = null;
		physicsHandler_mittiBgSprite = null;
		pipeSprite = null;
		rubberSprite = null;
		mPhysicsWorld = null;
		this.colorBitmapTexture = null;
		this.colorBitmapTexture = null;
		this.colorTextureRegion = null;
		System.gc();
	}

	public Scene getScene() {
		return scene;
	}

	private void setPhysicsWorld() {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH * 6), false, 8, 8);
		ground = new Rectangle(-CommonConstants.CAMERA_WIDTH, belowBgPlayStandSprite.getY() + 140, 2 * CommonConstants.CAMERA_WIDTH, 1f, context.getVertexBufferObjectManager());
		groundUpper = new Rectangle(-CommonConstants.CAMERA_WIDTH, belowBgPlayStandSprite.getY() + 3, 2 * CommonConstants.CAMERA_WIDTH, 1f, context.getVertexBufferObjectManager());
		ground.setVisible(false);
		groundUpper.setVisible(false);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
		groundBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) ground, BodyType.StaticBody, wallFixtureDef);
		groundBodyUpper = PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) groundUpper, BodyType.StaticBody, wallFixtureDef);
		scene.attachChild(ground);
		scene.attachChild(groundUpper);
		this.scene.registerUpdateHandler(this.mPhysicsWorld);
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ground, groundBody, true, false));
		mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(groundUpper, groundBodyUpper, true, false));
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		groundBodyUpper.setActive(false);
	}

	/**
	 * It resets the whole painting screen
	 */
	public void eraseWholePainting() {
		// ***********clear old screen
		for (int i = 0; i < colorPaintList.size(); i++) {
			Sprite sprite = colorPaintList.get(i);
			sprite.setPosition(-1000, -1000);
			sprite = null;
		}
		colorPaintList.clear();
		// isErase = true;
		isErase = false;
	}

	/**
	 * It resets the painting ..gives rubber functionlaity
	 */
	public void erasePainting() {
		isErase = true;
	}

	public void handleScreenShotPre() {
		screen_shot_downBlackBgSprite.setVisible(true);
		downBlackBgSprite.setVisible(false);
	}

	public void handleScreenShotPost() {
		screen_shot_downBlackBgSprite.setVisible(false);
		downBlackBgSprite.setVisible(true);
	}

	public String getSelectedLetterName() {
		return alphabets[clickedSpriteNumber];
	}

	public void playAlphabetSound(int position) {
		try {
			alphabetSoundData[position].play();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void stopBackgroundMusic() {
		try {
			if (backgroundMusic != null) {
				backgroundMusic.stop();
				backgroundMusic = null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void playBackgroundMusic() {
		if (SharedPrefUtils.getSoundSettings(context)) {
			try {
				if (backgroundMusic != null) {
					backgroundMusic.stop();
					backgroundMusic = null;
				}
				backgroundMusic = MusicFactory.createMusicFromAsset(context.getMusicManager(), context, "sfx/alphabet_painting_scene/" + "bg_music.mp3");
				backgroundMusic.play();
				backgroundMusic.setVolume(.5f);
				backgroundMusic.setLooping(true);
			} catch (Exception e) {
			}
		}
	}

	private void loadAlphabetSoundsInAdvance() {
		alphabetSoundData = new Sound[26];
		for (int i = 0; i < alphabetSoundData.length; i++) {
			try {
				alphabetSoundData[i] = SoundFactory.createSoundFromAsset(context.getSoundManager(), context, "sfx/alphabet_painting_scene/" + alphabets[i] + ".mp3");
			} catch (IOException e) {
				// catch block
				e.printStackTrace();
			}
		}
	}

	private void showMiddleLetter() {
		BitmapTextureAtlas alphabetBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion alphabetTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(alphabetBitmapTextureAtlas, context.getAssets(), "gfx/alphabet_painting/alphabet_letter/" + (clickedSpriteNumber + 1) + ".png", 0, 0, 2, 1);
		alphabetBitmapTextureAtlas.load();
		alphabetAnimatedSpriteMiddle = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 - alphabetTiledTextureRegion.getWidth() / 2, belowBgPlayStandSprite.getY() - alphabetTiledTextureRegion.getHeight() / 3 - 4, alphabetTiledTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					this.setCurrentTileIndex(0);
					playAlphabetSound(clickedSpriteNumber);
				}
				return true;
			}
		};
		alphabetAnimatedSpriteMiddle.setScale(.0f);
		sceneLayerTop.attachChild(alphabetAnimatedSpriteMiddle);
		scene.registerTouchArea(alphabetAnimatedSpriteMiddle);
		final ScaleAtModifier entityModifier = new ScaleAtModifier(1.0f, .0f, .3f, .0f, .3f, alphabetAnimatedSpriteMiddle.getWidth() / 2, alphabetAnimatedSpriteMiddle.getHeight() / 2);
		RotationModifier rotationModifier = new RotationModifier(.6f, 0, 360);
		alphabetAnimatedSpriteMiddle.registerEntityModifier(new ParallelEntityModifier(rotationModifier, entityModifier));
	}

	public void showParticleEffect(float x, float y) {
		ITextureRegion textureRegion = CommonUtils.createParticleTextureRegion(context);
		CommonUtils.createParticalSystem(context, x, y, 10000, textureRegion, scene);
	}

	public void showAlphabetPuzzle() {
		System.out.println("spriteeeeeee==" + outlineSprite.getX());
		isAlpahabetMissingPuzzleMode = true;
		String string = CommonConstants.puzzleArray[clickedSpriteNumber];
		int randomBlanks = string.length() / 2; // 2
		int remainder = string.length() % 2;
		int randomNumber = random.nextInt(randomBlanks + remainder) + 1;
		System.out.println("!!!!randomBlankkk==" + randomBlanks + "randomNumberr==" + randomNumber);
		ArrayList<Integer> arrayList = new ArrayList<>();
		for (int i = 0; i < string.length(); i++) {
			arrayList.add(i);
		}
		Collections.shuffle(arrayList);
		int pos_blanks[] = new int[randomNumber];
		for (int x = 0; x < pos_blanks.length; x++) {
			pos_blanks[x] = arrayList.get(x);
		}
		Arrays.sort(pos_blanks);
		int alphabetcord[][] = { { 0, 450 }, { 80, 450 }, { 150, 450 }, { 250, 450 }, { 300, 450 }, { 380, 450 }, { 400, 450, }, { 430, 450, }, { 500, 450 }, { 500, 450 }, { 480, 450 } };
		for (int z = 0; z < string.length(); z++) {
			int index = alphabetList.indexOf(String.valueOf(string.charAt(z))); // 11
			BitmapTextureAtlas alphabetBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			TiledTextureRegion alphabetTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(alphabetBitmapTextureAtlas, context.getAssets(), "gfx/alphabet_painting/alphabet_letter/" + (index + 1) + ".png", 0, 0, 2, 1);
			alphabetBitmapTextureAtlas.load();
			if (z != 0) {
				x = (int) (x + gap + alphabetAnimatedList.get(0).getWidth() / 2);
			}
			AnimatedSprite alphabetAnimatedSprite = new AnimatedSprite(x, belowBgPlayStandSprite.getY() + 100, alphabetTiledTextureRegion, context.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
						this.setCurrentTileIndex(1);
					} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
						this.setCurrentTileIndex(0);
					}
					return true;
				}
			};
			alphabetAnimatedSprite.setAlpha(0.1f);
			alphabetAnimatedSprite.setScale(.0f);
			alphabetAnimatedSprite.setCurrentTileIndex(0);
			alphabetAnimatedList.add(alphabetAnimatedSprite);
			if (z == 0) {
				rectangle.setX(alphabetAnimatedSprite.getX());
			} else {
				rectangle.setWidth(alphabetAnimatedSprite.getX() + (alphabetAnimatedSprite.getWidth() / 2) + 20);
			}
			scene.registerTouchArea(alphabetAnimatedSprite);
			rectangle.attachChild(alphabetAnimatedSprite);
		}
		float x_initialDragSprite = 8;
		float y = (belowBgPlayStandSprite.getY() + belowBgPlayStandSprite.getHeight()) / 2 + draggingBoxBgSpriteList.get(0).getHeight() + 3;
		rectangle.setY(690);
		float totalWidth = alphabetAnimatedList.get(0).getX() + alphabetAnimatedList.get(alphabetAnimatedList.size() - 1).getX() + (alphabetAnimatedList.get(alphabetAnimatedList.size() - 1).getWidth() / 2);
		scneLayerLast.setPosition(100, 100);
		rectangle.setPosition(((CommonConstants.CAMERA_WIDTH / 2) - (rectangle.getWidth() / 2)), belowBgPlayStandSprite.getY() + 98);
		rectangle.setWidth(rectangle.getWidth());
		rectShapes.setPosition(rectangle.getX() - 10, rectangle.getY() - 10);
		rectShapes.setWidth(rectangle.getWidth() + 10);
		rectShapes.setHeight(rectangle.getHeight());
		rectShapes.setColor(Color.BLACK);
		rectShapes.setVisible(true);
		float cordssss[] = rectangle.convertSceneToLocalCoordinates(rectangle.getX(), rectangle.getY());
		System.out.println("corddd__xxx==" + cordssss[0] + "corrdss__yy==" + cordssss[1]);
		for (int v = 0; v < alphabetAnimatedList.size(); v++) {
			AnimatedSprite animatedSprite = alphabetAnimatedList.get(v);
			if (v == 0) {
				animatedSprite.setPosition(0 - animatedSprite.getWidth() / 4, 0 - animatedSprite.getHeight() / 4);
			} else {
				animatedSprite.setPosition(alphabetAnimatedList.get(v - 1).getX() + alphabetAnimatedList.get(0).getWidth() / 2 + gap, alphabetAnimatedList.get(v - 1).getY());
				System.out.println("vvvv==" + v + "__x==" + animatedSprite.getX() + "__yy" + animatedSprite.getY());
			}
			final ScaleAtModifier entityModifier = new ScaleAtModifier(1.0f, .0f, .5f, .0f, .5f, animatedSprite.getWidth() / 2, animatedSprite.getHeight() / 2);
			RotationModifier rotationModifier = new RotationModifier(.6f, 0, 360);
			animatedSprite.registerEntityModifier(new ParallelEntityModifier(rotationModifier, entityModifier));
		}
		for (int j = 0; j < randomNumber; j++) {
			System.out.println("!!!!jjjj==" + j + "valuee==" + pos_blanks[j]);
			alphabetAnimatedList.get(pos_blanks[j]).setVisible(false);
			CustomDashSprite dashSprite = new CustomDashSprite(alphabetAnimatedList.get(pos_blanks[j]).getX(), alphabetAnimatedList.get(pos_blanks[j]).getY() + alphabetAnimatedList.get(pos_blanks[j]).getHeight() / 2, dashTextureRegion.deepCopy(), context.getVertexBufferObjectManager(),
					pos_blanks[j]);
			dashSprite.setScale(.0f);
			final ScaleAtModifier entityModifier = new ScaleAtModifier(1.0f, .0f, .5f, .0f, .5f, dashSprite.getWidth() / 2, dashSprite.getHeight() / 2);
			dashSprite.registerEntityModifier(entityModifier);
			// sceneLayer1.attachChild(dashSprite);
			rectangle.attachChild(dashSprite);
			dashSpriteList.add(dashSprite);
		}
		setBlinkDashSprite();
	}

	private ArrayList<Sprite> dashSpriteList = new ArrayList<>();
	ArrayList<AnimatedSprite> alphabetAnimatedList = new ArrayList<AnimatedSprite>();
	int count = -1;
	int gap = 10;
	int gapForDraggingSprite = 30;
	int x = 0;

	@Override
	public void isImageComplete() {
		if (draggingBoxBgSpriteList != null && draggingBoxBgSpriteList.size() > 0) {
			for (Sprite sprite : draggingBoxBgSpriteList) {
				sprite.setPosition(-1000, -1000);
			}
		}
		fullCharacterSprite = new Sprite(CommonConstants.CAMERA_WIDTH / 2 - partTextureRegionList.get(0).getWidth() / 2, 0, partTextureRegionList.get(5).deepCopy(), context.getVertexBufferObjectManager());
		sceneLayer1.attachChild(fullCharacterSprite);
		showParticleEffect(outlineSprite.getX() + outlineSprite.getWidth() / 2, outlineSprite.getY() + outlineSprite.getHeight() / 2);
		showAlphabetPuzzle();
	}

	public void checkUserValue(int position) {
		String userClickedAlphabet = alphabetList.get(position);
		String alphabetString = CommonConstants.puzzleArray[clickedSpriteNumber];
		String matchAlphabet = String.valueOf(alphabetString.charAt(dashSpriteList.get(count).getTag()));
		System.out.println("userclickedAlphabettt==" + userClickedAlphabet + "matchAlphabet==" + matchAlphabet);
		boolean isCorrectValue = false;
		if (userClickedAlphabet.equalsIgnoreCase(matchAlphabet))
			isCorrectValue = true;
		if (isCorrectValue) {
			dashSpriteList.get(count).setVisible(false);
			dashSpriteList.get(count).clearEntityModifiers();
			sceneLayer1.detachChild(dashSpriteList.get(count));
			alphabetAnimatedList.get(dashSpriteList.get(count).getTag()).setVisible(true);
			setBlinkDashSprite();
		} else {
			JumpChar(70, 0, 1);
			// prompt the user try again
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					CommonUtils.showToast(context, "Try Again...");
				}
			});
		}
	}

	private void shoWCheeringCartoon() {
		try {
			long a[] = { 300, 300 };
			cheeringCartoonAnimatedSprite.animate(a);
			MoveModifier moveModifierUp = new MoveModifier(1f, 10, 10, 400, 10);
			MoveModifier moveModifierDown = new MoveModifier(1f, 10, 10, 10, 400);
			if (cheeringCartoonAnimatedSprite != null)
				cheeringCartoonAnimatedSprite.registerEntityModifier(new SequenceEntityModifier(moveModifierUp, moveModifierDown));
		} catch (Exception exception) {}
	}

	public void setBlinkDashSprite() {
		count++;
		if (count < dashSpriteList.size()) {
			Sprite sprite = dashSpriteList.get(count);
			final LoopEntityModifier blinkModifier = new LoopEntityModifier(new SequenceEntityModifier(new FadeOutModifier(0.35f), new FadeInModifier(0.35f)));
			sprite.registerEntityModifier(blinkModifier);
		} else {
			JumpChar(CommonConstants.CAMERA_WIDTH - 38, 2, 3);
			showParticleEffect(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
			isAlpahabetMissingPuzzleMode = false;
		}
	}
}
