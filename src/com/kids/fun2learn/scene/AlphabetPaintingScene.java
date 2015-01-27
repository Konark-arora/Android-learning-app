package com.kids.fun2learn.scene;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
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

import android.content.Context;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.os.AsyncTask;

import com.appspartan.custom.CustomParticle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kids.fun2learn.AppsPartanBaseGameActivity;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.SharedPrefUtils;

public class AlphabetPaintingScene {
	private SimpleBaseGameActivity context;
	private Scene scene;
	private Body body;
	private PhysicsConnector physicsConnector;
	private PhysicsHandler physicsHandler_mittiBgSprite;
	private PhysicsHandler physicsHandler_alphabetsprite;
	private PhysicsWorld mPhysicsWorld;
	private Body groundBody;
	IAreaShape ground;
	private ITexture bgTexture, particleTexture;
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
	private BitmapTextureAtlas pipeTextureAtlas;
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
	private TiledTextureRegion pipeTiledTextureRegion;
	private TiledTextureRegion alphabetTextureRegion;
	private Sprite bgSprite, screen_shot_fun2learn_bottom_bg_Sprite;
	private Sprite slingShotSprite;
	private Sprite mittiBgSprite;
	private Sprite rubberSprite;
	private Sprite innerSprite;
	private Sprite outerSprite;
	private Sprite colorSprite;
	private Sprite screen_shot_downBlackBgSprite, downBlackBgSprite;
	private boolean isErase = false;
	private AnimatedSprite pipeSprite;
	private AnimatedSprite alphabetsprite;
	private int clickedSpriteNumber;
	public Rectangle charac;
	private Entity sceneLayer1, sceneLayer0, sceneLayer2, sceneLayer3;
	private ArrayList<Sprite> colorPaintList = new ArrayList<Sprite>();
	private String alphabets[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private Sound alphabetSoundData[];
	private Music backgroundMusic;

	public AlphabetPaintingScene(Context context) {
		this.context = (SimpleBaseGameActivity) context;
		loadResources();
		loadScene();
		isErase = false;
	}

	private void loadResources() {
		try {
			this.bgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_painting/Play-Bg.png");
			this.bgTextureRegion = TextureRegionFactory.extractFromTexture(this.bgTexture);
			this.bgTexture.load();
			this.mittiBgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/alphabet_painting/mittib_bg.png");
			this.mittiBgTextureRegion = TextureRegionFactory.extractFromTexture(this.mittiBgTexture);
			this.mittiBgTexture.load();
			this.screen_shot_fun2learn_bottom_bg_Texture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/fun-2-run_under.png");
			this.screen_shot_fun2learn_bottom_bg_TextureRegion = TextureRegionFactory.extractFromTexture(this.screen_shot_fun2learn_bottom_bg_Texture);
			this.screen_shot_fun2learn_bottom_bg_Texture.load();
			pipeTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			pipeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(pipeTextureAtlas, context.getAssets(), "gfx/alphabet_painting/Pipe_Sprite.png", 0, 0, 6, 1);
			pipeTextureAtlas.load();
			this.rubberTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/rubber_dummy.png");
			this.rubberTextureRegion = TextureRegionFactory.extractFromTexture(this.rubberTexture);
			this.rubberTexture.load();
			this.downBlackBgTextureAtlas = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/Down_black_BG.png");
			this.downBlackBgTextureRegion = TextureRegionFactory.extractFromTexture(this.downBlackBgTextureAtlas);
			this.downBlackBgTextureAtlas.load();
			this.screen_shot_downBlackBgTextureAtlas = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/fun-2-run_under.png");
			this.screen_shot_downBlackBgTextureRegions = TextureRegionFactory.extractFromTexture(this.screen_shot_downBlackBgTextureAtlas);
			this.screen_shot_downBlackBgTextureAtlas.load();
			this.particleTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/common/particle.png");
			this.particleTextureRegion = TextureRegionFactory.extractFromTexture(this.particleTexture);
			this.particleTexture.load();
		} catch (Exception exception) {}
		loadAlphabetSoundsInAdvance();
	}

	private void loadScene() {
		scene = new Scene();
		// layer 0
		sceneLayer0 = new Entity();
		scene.attachChild(sceneLayer0);
		// layer
		sceneLayer1 = new Entity();
		scene.attachChild(sceneLayer1);
		// layer2
		sceneLayer2 = new Entity();
		scene.attachChild(sceneLayer2);
		// layer3
		sceneLayer3 = new Entity();
		scene.attachChild(sceneLayer3);
		downBlackBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 + downBlackBgTextureRegion.getHeight() + 43, downBlackBgTextureRegion, context.getVertexBufferObjectManager());
		screen_shot_downBlackBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 + downBlackBgTextureRegion.getHeight() + 43, screen_shot_downBlackBgTextureRegions, context.getVertexBufferObjectManager());
		scene.attachChild(downBlackBgSprite);
		scene.attachChild(screen_shot_downBlackBgSprite);
		screen_shot_downBlackBgSprite.setVisible(false);
		mittiBgSprite = new Sprite(0, 338, mittiBgTextureRegion, context.getVertexBufferObjectManager());
		if (!SharedPrefUtils.getIsPurchased(context)) {
			mittiBgSprite.setPosition(0, 290);
		}
		sceneLayer2.attachChild(mittiBgSprite);
		// scene layer 1
		bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer3.attachChild(bgSprite);
		physicsHandler_mittiBgSprite = new PhysicsHandler(mittiBgSprite);
		mittiBgSprite.registerUpdateHandler(physicsHandler_mittiBgSprite);
		pipeSprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 - 50, 236.146f - pipeTiledTextureRegion.getHeight() / 2 - 17, pipeTiledTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer3.attachChild(pipeSprite);
		rubberSprite = new Sprite(100, 100, rubberTextureRegion, context.getVertexBufferObjectManager());
		rubberSprite.setScale(.1f);
		scene.attachChild(rubberSprite);
		rubberSprite.setVisible(false);
		setPhysicsWorld();
		createColorTexuturePaint(0, CommonConstants.COLOR_PLATE);
		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene scene, final TouchEvent touchEvent) {
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).texture_PlateLinearLayout);
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).colorPlateLinearLayout);
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).eraser_popup_ll);
					}
				});
				if (isErase == true) {
					//
					// ==setting rubber sprite position
					rubberSprite.setPosition(touchEvent.getX() - rubberSprite.getWidth() / 2, touchEvent.getY() - rubberSprite.getHeight() / 2);
					// erasing screen by colliding logic
					if (!colorPaintList.isEmpty() && colorPaintList != null) {
						for (int i = 0; i < colorPaintList.size(); i++) {
							if (rubberSprite.collidesWith(colorPaintList.get(i))) {
								Sprite sprite = colorPaintList.get(i);
								sprite.setPosition(-1000, -1000);
								sprite = null;
							}
						}
					}
				} else {
					colorSprite = new Sprite(touchEvent.getX(), touchEvent.getY(), colorTextureRegion.deepCopy(), context.getVertexBufferObjectManager());
					// colorSprite.setScale(.5f);
					sceneLayer0.attachChild(colorSprite);
					colorPaintList.add(colorSprite);
				}
				createParticalSystem(touchEvent.getX(), touchEvent.getY(), 10);
				return true;
			}
		});
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

	public void chooseColorTexture(int position, String color_texture_plate) {
		isErase = false;
		if (color_texture_plate.equalsIgnoreCase(CommonConstants.COLOR_PLATE)) {
			createColorTexuturePaint(position, color_texture_plate);
			CommonUtils.setVisibilty(((AppsPartanBaseGameActivity) context).colorPlateLinearLayout);
		} else if (color_texture_plate.equalsIgnoreCase(CommonConstants.TEXTURE_PLATE)) {
			createColorTexuturePaint(position, color_texture_plate);
			CommonUtils.setVisibilty(((AppsPartanBaseGameActivity) context).texture_PlateLinearLayout);
		}
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
		clickedSpriteNumber = position;
		playAlphabetSound(position);
		pipeSprite.animate(270, 0, new IAnimationListener() {
			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
				if (pNewFrameIndex == 2) {
					onPipespriteJump();
				}
				if (pNewFrameIndex == 3) {
					alphabetsprite.setVisible(true);
				}
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {}
		});
	}

	class DelaAsyncTask1 extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new DelaAsyncTask3().execute();
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
		JumpChar();
	}

	public void JumpChar() {
		body.setActive(true);
		final ScaleAtModifier entityModifier = new ScaleAtModifier(1.5f /* Durarion */, 0f/* from */, 1.5f/* to */, 0f, 1.5f, alphabetsprite.getWidth() / 2/*
																																														 */);
		// entityModifier =new ScaleAtModifier(1.5f, 0f, 1.5f, 0f, 1.5f, 0, 0);
		// register modifier
		alphabetsprite.registerEntityModifier(entityModifier);
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, mPhysicsWorld.getGravity().y * -0.6f));
	}

	private void setBodyToPhysicsWorld() {
		final FixtureDef apphabet_FIX = PhysicsFactory.createFixtureDef(0f, .5f, 0f);
		body = PhysicsFactory.createBoxBody(mPhysicsWorld, alphabetsprite, BodyType.DynamicBody, apphabet_FIX);
		physicsConnector = new PhysicsConnector(alphabetsprite, body, true, false);
		mPhysicsWorld.registerPhysicsConnector(physicsConnector);
		body.setActive(false);
	}

	public void clearPaintScreen() {
		// try{
		// ***********clear old screen
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
		// eraseWholePainting();
		return scene;
	}

	private void setPhysicsWorld() {
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH * 6), false, 8, 8);
		ground = new Rectangle(-CommonConstants.CAMERA_WIDTH, CommonConstants.CAMERA_HEIGHT / 2 + mittiBgTextureRegion.getHeight() / 6 + 15, 2 * CommonConstants.CAMERA_WIDTH, 2, context.getVertexBufferObjectManager());
		ground.setVisible(false);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
		groundBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, (IAreaShape) ground, BodyType.StaticBody, wallFixtureDef);
		scene.attachChild(ground);
		this.scene.registerUpdateHandler(this.mPhysicsWorld);
		scene.registerTouchArea(mittiBgSprite);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
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
		// if (!SharedPrefUtils.getSoundSettings(context)) {
		try {
			if (backgroundMusic != null) {
				backgroundMusic.stop();
				backgroundMusic = null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// }
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
				System.out.println("exception is " + e);
			}
		}
	}

	private void loadAlphabetSoundsInAdvance() {
		alphabetSoundData = new Sound[26];
		for (int i = 0; i < alphabetSoundData.length; i++) {
			try {
				alphabetSoundData[i] = SoundFactory.createSoundFromAsset(context.getSoundManager(), context, "sfx/alphabet_painting_scene/" + alphabets[i] + ".mp3");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
