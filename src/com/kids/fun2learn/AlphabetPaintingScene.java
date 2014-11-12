package com.kids.fun2learn;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.particle.emitter.PointParticleEmitter;
import org.anddev.andengine.entity.particle.modifier.ColorInitializer;
import org.anddev.andengine.entity.particle.modifier.ExpireModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.entity.shape.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.view.View;
import color_customparticals.CustomParticle;
import color_customparticals.RandomColorInitializer;
import color_customparticals.RandomDirectionModifier;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;

public class AlphabetPaintingScene {

	private Texture textureAtlas, colisionBitmapTextureAtlas,
			backBitmapTextureAtlas, mittiBgTextureAtlas,
			downBlackBgTextureAtlas, buttonPlayTextureAtlas,
			adBgBitmapTextureAtlas, pipeTextureAtlas,
			alphabetBitmapTextureAtlas, innerBitmapTextureAtlas,
			color_texture_bg, outerBitmapTextureAtlas, colorBitmapTextureAtlas,
			rubberBitmapTextureAtlas, particleTexture,
			screen_shot_downBlackBgTextureAtlas;
	private TextureRegion texture_region, colisionTextureRegion,
			backTextureRegion, downBlackBgTextureRegion,
			buttonPlayTextureRegion, adBgTextureRegion, innerTextureRegion,
			outerTextureRegion, rubberTextureRegion, mittiBgTextureRegion,
			colorTextureRegion, color_texture_bgTextureRegion,
			particleTextureRegion, screen_shot_downBlackBgTextureRegions;

	private TiledTextureRegion pipeTiledTextureRegion;
	private AnimatedSprite pipeSprite;
	private ArrayList<TextureRegion> texture_region_list;
	private ArrayList<Sprite> spritesList;
	private ArrayList<Texture> BitmapTextureAtlasList = new ArrayList<Texture>();
	private Sprite mittiBgSprite, downBlackBgSprite, buttonPlaySprite,
			adBgSprite, innerSprite, outerSprite, colorSprite,
			color_texture_bgSprite, rubberSprite,
			screen_shot_downBlackBgSprite;
	private PhysicsWorld mPhysicsWorld;
	private TiledTextureRegion alphabetTextureRegion;
	private AnimatedSprite alphabetsprite;
	private Camera mCamera;

	private ArrayList<Sprite> colorPaintList = new ArrayList<Sprite>();

	public static Scene scene;
	private Context context;
	private BaseGameActivity contextBaseGameActivity;
	private static AlphabetPaintingScene alphabetPaintingScene;

	private String alphabets[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z" };

	private int alphabetSoundsNative[] = new int[26];

	private boolean isErase = false;

	Body body;

	PhysicsConnector physicsConnector;

	public Rectangle charac;

	Music backgroundMusic;
	Music alphabetSound;

	Sound alphabetSoundData[];

	int spriteClickedSpriteNumber;

	/**
	 * Load the scene and any assets we need.
	 */

	public AlphabetPaintingScene(Context context) {
		this.context = context;
		this.contextBaseGameActivity = (BaseGameActivity) (context);

	}

	public void loadResources() {

		backBitmapTextureAtlas = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		backTextureRegion = TextureRegionFactory.createFromAsset(
				backBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/Play-Bg.png", 0, 0);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(backBitmapTextureAtlas);

		mittiBgTextureAtlas = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mittiBgTextureRegion = TextureRegionFactory.createFromAsset(
				mittiBgTextureAtlas, contextBaseGameActivity,
				"gfx/mittib_bg.png", 0, 0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(mittiBgTextureAtlas);

		downBlackBgTextureAtlas = new Texture(512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		downBlackBgTextureRegion = TextureRegionFactory.createFromAsset(
				downBlackBgTextureAtlas, contextBaseGameActivity,
				"gfx/Down_black_BG.png", 0, 0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(downBlackBgTextureAtlas);

		screen_shot_downBlackBgTextureAtlas = new Texture(512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		screen_shot_downBlackBgTextureRegions = TextureRegionFactory
				.createFromAsset(screen_shot_downBlackBgTextureAtlas,
						contextBaseGameActivity, "gfx/fun-2-run_under.png", 0,
						0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(screen_shot_downBlackBgTextureAtlas);

		buttonPlayTextureAtlas = new Texture(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		buttonPlayTextureRegion = TextureRegionFactory.createFromAsset(
				buttonPlayTextureAtlas, contextBaseGameActivity,
				"gfx/Buton_play_bg.png", 0, 0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(buttonPlayTextureAtlas);

		/*
		 * color_texture_bg = new Texture(512, 128,
		 * TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 * color_texture_bgTextureRegion = TextureRegionFactory.createFromAsset(
		 * color_texture_bg, this, "gfx/alphabet_texture/Color_texture_Bar.png",
		 * 0, 0);
		 * this.mEngine.getTextureManager().loadTexture(color_texture_bg);
		 */

		adBgBitmapTextureAtlas = new Texture(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		adBgTextureRegion = TextureRegionFactory.createFromAsset(
				adBgBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/add-image.png", 0, 0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(adBgBitmapTextureAtlas);

		pipeTextureAtlas = new Texture(1024, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		pipeTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(
				pipeTextureAtlas, contextBaseGameActivity,
				"gfx/Pipe_Sprite.png", 0, 0, 6, 1);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(pipeTextureAtlas);

		rubberBitmapTextureAtlas = new Texture(32, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		rubberTextureRegion = TextureRegionFactory.createFromAsset(
				rubberBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/rubber_dummy.png", 0, 0);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(rubberBitmapTextureAtlas);

		particleTexture = new Texture(32, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		rubberTextureRegion = TextureRegionFactory.createFromAsset(
				rubberBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/particle.png", 0, 0);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(rubberBitmapTextureAtlas);

		loadAlphabetSoundsInAdvance();

	}

	/**
	 * Return the scene for when the scene is called to become active.
	 */
	public void loadScene() {

		scene = new Scene(4);

		//
		mittiBgSprite = new Sprite(0, 338, mittiBgTextureRegion);

		scene.getTopLayer().addEntity(mittiBgSprite);

		Sprite backSprite = new Sprite(0, 0, backTextureRegion);
		scene.getTopLayer().addEntity(backSprite);

		downBlackBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2
				+ downBlackBgTextureRegion.getHeight() + 43,
				downBlackBgTextureRegion);

		screen_shot_downBlackBgSprite = new Sprite(0,
				CommonConstants.CAMERA_HEIGHT / 2
						+ downBlackBgTextureRegion.getHeight() + 43,
				screen_shot_downBlackBgTextureRegions);

		buttonPlaySprite = new Sprite(0, downBlackBgSprite.getY(),
				buttonPlayTextureRegion);

		rubberSprite = new Sprite(100, 100, rubberTextureRegion.clone());
		rubberSprite.setScale(.1f);
		scene.getLayer(1).addEntity(rubberSprite);
		rubberSprite.setVisible(false);
		// color_texture_bgSprite = new Sprite(0,
		// downBlackBgSprite.getY()-color_texture_bgTextureRegion.getHeight(),
		// color_texture_bgTextureRegion);

		adBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT
				- adBgTextureRegion.getHeight(), adBgTextureRegion);

		pipeSprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 - 50,
				236.146f - pipeTiledTextureRegion.getHeight() / 2 - 17,
				pipeTiledTextureRegion) {

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub

				// System.out.println("this.getCurrentTileIndex();"
				// + this.getCurrentTileIndex());
				// if (this.getCurrentTileIndex() == 1) {
				// onPipespriteJump();
				// }
				// // to fix layering issue of alphabet
				// if (this.getCurrentTileIndex() == 3) {
				// alphabetsprite.setVisible(true);
				// }

				super.onManagedUpdate(pSecondsElapsed);
			}
		};

		//
		scene.getTopLayer().addEntity(pipeSprite);
		// pipeSprite.setCurrentTileIndex(0);
		// pipeSprite.animate(500, 1);

		// pipeSprite.animate(500, 1, new IAnimationListener() {
		//
		// @Override
		// public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// // public void onAnimationLoopFinished(AnimatedSprite
		// pAnimatedSprite,
		// // int pRemainingLoopCount, int pInitialLoopCount) {
		// // }
		// //
		// // @Override
		// // public void onAnimationFrameChanged(AnimatedSprite
		// pAnimatedSprite,
		// // int pOldFrameIndex, int pNewFrameIndex) {
		// // }
		// //
		// // @Override
		// // public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// // // start your second animation
		// // }
		//
		// });

		applyPhysics();

		scene.registerTouchArea(mittiBgSprite);
		scene.setTouchAreaBindingEnabled(true);
		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUpdate(float pSecondsElapsed) {

				// try {
				// System.out.println("body dump " + body.getLinearDamping()
				// + "lkn" + body.getLinearVelocity().y);
				// } catch (Exception e) {
				// // TODO: handle exception
				// }

				// if (mittiBgSprite.getY() >= 220) {
				// mittiBgSprite.setVelocityY(0);
				//
				// }
				// if (pipeSprite.getCurrentTileIndex() == 2) {
				// System.out.println("pipeSprite  getCurrentTileIndex "+pipeSprite.getCurrentTileIndex()
				// );
				// JumpChar();
				//
				// }
			}
		});

		// for (int i = 0; i < position.length; i++) {
		// spritesList.get(i).setPosition(position[i][0], position[i][1]);
		// }
		//
		//
		// scene.attachChild(backgroundSprite);

		// downBlackBgSprite.setScale(5f);
		scene.getLayer(3).addEntity(downBlackBgSprite);
		scene.getLayer(3).addEntity(screen_shot_downBlackBgSprite);
		screen_shot_downBlackBgSprite.setVisible(false);
		// scene.getLayer(3).addEntity(buttonPlaySprite);
		// scene.getLayer(3).addEntity(color_texture_bgSprite);

		// scene.getLayer(3).addEntity(adBgSprite);
		// this.scene.setOnSceneTouchListener(this);

		// CreateMenuBoxes();

		// default color is of 0 postion & type i color plate not texture plate
		createColorTexuturePaint(0, CommonConstants.COLOR_PLATE);

		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(final Scene scene,
					final TouchEvent touchEvent) {

				// CommonUtils.setVisibiltyGone(getColorTextureBgView());

				if (((MainActivity) (context)).texture_PlateLinearLayout
						.getVisibility() == View.VISIBLE) {

					CommonUtils
							.setVisibiltyGone(((MainActivity) (context)).texture_PlateLinearLayout);
				}

				if (((MainActivity) (context)).colorPlateLinearLayout
						.getVisibility() == View.VISIBLE) {

					CommonUtils
							.setVisibiltyGone(((MainActivity) (context)).colorPlateLinearLayout);
				}
				if (((MainActivity) (context)).eraser_popup_ll.getVisibility() == View.VISIBLE) {

					CommonUtils
							.setVisibiltyGone(((MainActivity) (context)).eraser_popup_ll);
				}

				//
				if (isErase == true) {
					//

					// ==setting rubber sprite position
					rubberSprite.setPosition(
							touchEvent.getX() - rubberSprite.getWidth() / 2,
							touchEvent.getY() - rubberSprite.getHeight() / 2);

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
					colorSprite = new Sprite(touchEvent.getX(), touchEvent
							.getY(), colorTextureRegion.clone());

					colorSprite.setScale(1.2f);
					scene.getLayer(1).addEntity(colorSprite);

					colorPaintList.add(colorSprite);

				}
				createParticals(touchEvent.getX(), touchEvent.getY(), 10);
				return true;
			}
		});

	}

	/**
	 * Unload any assets here - to be called later.
	 */
	public static void unload() {

	}

	/**
	 * Unload any assets here - to be called later.
	 * 
	 * It se
	 */
	public static void unloadScene() {

		alphabetPaintingScene = null;
	}

	/**
	 * It reset the scene and unloads
	 */
	public void resetScene() {

		alphabetPaintingScene = null;
	}

	public static AlphabetPaintingScene getAlphabetPaintingScene(Context context) {

//		if (alphabetPaintingScene == null) {

			alphabetPaintingScene = new AlphabetPaintingScene(context);
			alphabetPaintingScene.loadResources();
			alphabetPaintingScene.loadScene();
//		}

		return alphabetPaintingScene;
	}

	public Scene getScene() {

		clearPaintScreen();

		return scene;
	}

	public void createParticals(float x, float y, int parNo) {
		PointParticleEmitter emitter = new PointParticleEmitter(x, y);
		CustomParticle particalSystem = new CustomParticle(emitter, 25, 25,
				parNo, rubberTextureRegion);
		particalSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		particalSystem.addParticleInitializer(new RandomColorInitializer());
		particalSystem.addParticleInitializer(new ColorInitializer(1, 1, 1));
		// particalSystem.addParticleInitializer(new ScaleModifier(pDuration,
		// pFromScale, pToScale)));

		// particalSystem.addParticleInitializer((IParticleInitializer) new
		// ScaleModifier(1, 0f, 1f));

		particalSystem.addParticleModifier(new RandomDirectionModifier(2));
		particalSystem.addParticleModifier(new ExpireModifier(1f));

		// scene.getTopLayer().addEntity(particalSystem);

		scene.getLayer(3).addEntity(particalSystem);
	}

	private void applyPhysics() {

		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0,
				SensorManager.GRAVITY_EARTH * 6), false, 8, 8);

		final Shape ground = new Rectangle(-CommonConstants.CAMERA_WIDTH,
				CommonConstants.CAMERA_HEIGHT / 2
						+ mittiBgTextureRegion.getHeight() / 6 + 20,
				2 * CommonConstants.CAMERA_WIDTH, 2);
		ground.setVisible(false);
		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
				0.0f, 0.5f);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground,
				BodyType.StaticBody, wallFixtureDef);
		scene.getTopLayer().addEntity(ground);
		this.scene.registerUpdateHandler(this.mPhysicsWorld);

	}

	private void setBodyToPhysicsWorld() {

		final FixtureDef apphabet_FIX = PhysicsFactory.createFixtureDef(0f,
				.5f, 0f);

		body = PhysicsFactory.createBoxBody(mPhysicsWorld, alphabetsprite,
				BodyType.DynamicBody, apphabet_FIX);

		physicsConnector = new PhysicsConnector(alphabetsprite, body, true,
				false);

		mPhysicsWorld.registerPhysicsConnector(physicsConnector);

		body.setActive(false);

		// body.setTransform((CommonConstants.CAMERA_WIDTH/32)/2, 0, 0);

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

	private void clearPaintScreen() {

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
		} catch (Exception exception) {

		}
		if (mittiBgSprite != null) {
			mittiBgSprite.setPosition(0, 338);
			mittiBgSprite.setVelocityY(0);
		}

		// =======================

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

	private void makeAlphabetsprite(int spriteClickedSpriteNo) {

		clearPaintScreen();

		innerBitmapTextureAtlas = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		innerTextureRegion = TextureRegionFactory.createFromAsset(
				innerBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/alphabet_inner/" + spriteClickedSpriteNo + ".png", 0, 0);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(innerBitmapTextureAtlas);

		outerBitmapTextureAtlas = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		outerTextureRegion = TextureRegionFactory.createFromAsset(
				outerBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/alphabet_outer/" + spriteClickedSpriteNo + ".png", 0, 0);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(outerBitmapTextureAtlas);

		innerSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2
				- innerTextureRegion.getHeight() / 2 + 100,
				innerTextureRegion.clone());

		// {

		// @Override
		// public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
		// float pTouchAreaLocalX, float pTouchAreaLocalY);
		// {
		// // TODO Auto-generated method stub
		// //
		// // =======================
		// if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_MOVE) {
		// colorSprite = new Sprite(pSceneTouchEvent.getX(),
		// pSceneTouchEvent.getY(), colorTextureRegion.clone());
		//
		// scene.getLayer(1).addEntity(colorSprite);
		//
		// colorPaintList.add(colorSprite);
		// }
		// return true;
		// }
		// };

		scene.getLayer(0).addEntity(innerSprite);
		scene.registerTouchArea(innerSprite);
		outerSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2
				- outerTextureRegion.getHeight() / 2 + 100,
				outerTextureRegion.clone());
		scene.getLayer(2).addEntity(outerSprite);

		// downBlackBgSprite.setPosition(0,
		// outerSprite.getY() + outerSprite.getHeight());

		// =====================

		alphabetBitmapTextureAtlas = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		alphabetTextureRegion = TextureRegionFactory.createTiledFromAsset(
				alphabetBitmapTextureAtlas, contextBaseGameActivity,
				"gfx/alphabet_letter/" + (spriteClickedSpriteNo + 1) + ".png",
				0, 0, 2, 1);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(alphabetBitmapTextureAtlas);

		alphabetsprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2
				- alphabetTextureRegion.getWidth() / 2,

		CommonConstants.CAMERA_HEIGHT / 2 - 20, alphabetTextureRegion) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

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
		scene.getLayer(3).addEntity(alphabetsprite);
		alphabetsprite.setScale(0f);
		scene.registerTouchArea(alphabetsprite);
		alphabetsprite.setCurrentTileIndex(0);

		alphabetsprite.setVisible(false);

		alphabetsprite.setPosition(CommonConstants.CAMERA_WIDTH / 2
				- alphabetsprite.getWidth() / 2, CommonConstants.CAMERA_HEIGHT
				/ 2 - alphabetsprite.getHeight() / 2);

		charac = new Rectangle(alphabetsprite.getX(), alphabetsprite.getY(),
				alphabetsprite.getWidth() + 60, alphabetsprite.getHeight() + 1);

		charac.setPosition(CommonConstants.CAMERA_WIDTH / 2 - charac.getWidth()
				/ 2, CommonConstants.CAMERA_HEIGHT / 2 - charac.getHeight() / 2);
		scene.getLayer(3).addEntity(charac);

		charac.setVisible(false);
		// scene.setTouchAreaBindingEnabled(true);

		// scene.getTopLayer().addEntity(pipeSprite);
	}

	/**
	 * creates painting color or texture
	 * 
	 * @param position
	 */
	private void createColorTexuturePaint(int position,
			String color_texture_plate) {

		colorBitmapTextureAtlas = new Texture(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		if (color_texture_plate.equalsIgnoreCase(CommonConstants.COLOR_PLATE)) {

			colorTextureRegion = TextureRegionFactory.createFromAsset(
					colorBitmapTextureAtlas, contextBaseGameActivity,
					"gfx/alphabet_painting_color/" + position + ".png", 0, 0);

		} else if (color_texture_plate
				.equalsIgnoreCase(CommonConstants.TEXTURE_PLATE)) {

			colorTextureRegion = TextureRegionFactory.createFromAsset(
					colorBitmapTextureAtlas, contextBaseGameActivity,
					"gfx/alphabet_painting_texture/" + position + ".png", 0, 0);
		}

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(colorBitmapTextureAtlas);

	}

	/**
	 * Called from ColorAndTexturePlateImageAdapter getView()
	 * 
	 * @param position
	 *            : selected color texture
	 */

	public void chooseColorTexture(int position, String color_texture_plate) {

		isErase = false;

		if (color_texture_plate.equalsIgnoreCase(CommonConstants.COLOR_PLATE)) {

			createColorTexuturePaint(position, color_texture_plate);

			CommonUtils
					.setVisibilty(((MainActivity) (context)).colorPlateLinearLayout);

		} else if (color_texture_plate
				.equalsIgnoreCase(CommonConstants.TEXTURE_PLATE)) {

			createColorTexuturePaint(position, color_texture_plate);

			CommonUtils
					.setVisibilty(((MainActivity) (context)).texture_PlateLinearLayout);
		}

	}

	private void onPipespriteJump() {

		removeBodyFromPhysicsWorld();
		makeAlphabetsprite(spriteClickedSpriteNumber);
		setBodyToPhysicsWorld();
		// Toast.makeText(MainActivity.this, "hi.....",
		// Toast.LENGTH_SHORT).show();
		JumpChar();
	}

	/**
	 * Called when body is clicked after jump
	 */
	public void onAlphabetItemClick() {

		if (((MainActivity) (context)).btn_play_ll.getVisibility() == View.GONE) {

			CommonUtils.setVisibiltyOn(((MainActivity) (context)).btn_play_ll);
		}
		// clearPaintScreen();
		// makeAlphabetsprite(spriteClickedSpriteNumber);

		// ***********clear old screen=======
		for (int i = 0; i < colorPaintList.size(); i++) {

			Sprite sprite = colorPaintList.get(i);
			sprite.setPosition(-1000, -1000);
			sprite = null;

		}
		colorPaintList.clear();

		// =====================

		try {
			mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
			body.setActive(false);
			body = null;

			alphabetsprite.setVelocityY(-1000);
			mittiBgSprite.setVelocityY(-1000);
		} catch (Exception exception) {

		}

	}

	public void onAlphabetItemActionDown() {
		mittiBgSprite.setPosition(0, 338);
		mittiBgSprite.setVelocityY(0);
		// mittiBgSprite.setVelocityY(1000);

		isErase = false;

		if (((MainActivity) (context)).texture_PlateLinearLayout
				.getVisibility() == View.VISIBLE) {

			CommonUtils
					.setVisibiltyGone(((MainActivity) (context)).texture_PlateLinearLayout);
		}

		if (((MainActivity) (context)).colorPlateLinearLayout.getVisibility() == View.VISIBLE) {

			CommonUtils
					.setVisibiltyGone(((MainActivity) (context)).colorPlateLinearLayout);
		}

		if (((MainActivity) (context)).eraser_popup_ll.getVisibility() == View.VISIBLE) {

			CommonUtils
					.setVisibiltyGone(((MainActivity) (context)).eraser_popup_ll);
		}

	}

	/**
	 * Called from when piano button is clicked ((MainActivity)(context)).
	 * 
	 * @param position
	 */
	public void onAlphabetItemActionUp(final int position) {

		playAlphabetSound(position);

		spriteClickedSpriteNumber = position;

		//
		long frameDuration[] = { 300, 300, 300, 300, 300 };
		// pipeSprite.animate(frameDuration, 1, 4, 0);

		pipeSprite.animate(300, false);

		new DelaAsyncTask1().execute();

		// System.out.println("this.getCurrentTileIndex();"
		// + this.getCurrentTileIndex());
		// if (this.getCurrentTileIndex() == 1) {
		// onPipespriteJump();
		// }
		// to fix layering issue of alphabet
		// if (this.getCurrentTileIndex() == 3) {
		// alphabetsprite.setVisible(true);
		// }

		// pipeSprite.animate(frameDuration, 1, 2, 0, new IAnimationListener() {
		//
		// @Override
		// public void onAnimationEnd(AnimatedSprite pAnimatedSprite) {
		// removeBodyFromPhysicsWorld();
		// makeAlphabetsprite();
		// setBodyToPhysicsWorld();
		// // Toast.makeText(MainActivity.this, "hi.....",
		// Toast.LENGTH_SHORT).show();
		// JumpChar();
		//
		// }
		//
		//
		// });

		// pipeSprite.animate(frameDuration, 2, 3, 0);

	}

	public void JumpChar() {

		body.setActive(true);
		// body.setCurrentTileIndex(4);
		//
		final ScaleAtModifier entityModifier = new ScaleAtModifier(
				1.5f /* Durarion */, 0f/* from */, 1.5f/* to */, 0f, 1.5f,
				alphabetsprite.getWidth() / 2/*
											 * scale center X
											 */, alphabetsprite.getHeight() / 2 /*
																				 * scale
																				 * center
																				 * Y
																				 */);

		// entityModifier =new ScaleAtModifier(1.5f, 0f, 1.5f, 0f, 1.5f, 0, 0);

		// register modifier
		alphabetsprite.addShapeModifier(entityModifier);

		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,
				mPhysicsWorld.getGravity().y * -0.6f));

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

		try {

			if (backgroundMusic != null) {

				backgroundMusic.stop();
				backgroundMusic = null;

			}

			backgroundMusic = MusicFactory.createMusicFromAsset(
					contextBaseGameActivity.getMusicManager(),
					contextBaseGameActivity, "sfx/alphabet_painting_scene/"
							+ "bg_music.mp3");

			backgroundMusic.play();

			backgroundMusic.setVolume(.5f);
			backgroundMusic.setLooping(true);

			// this.coinSound = SoundFactory.createSoundFromAsset(co
			// this.mEngine.getSoundManager(), this, "coin sound.mp3");
			//
			// this.bonusSound = SoundFactory.createSoundFromAsset(
			// this.mEngine.getSoundManager(), this, "high_score.ogg");
			//
			// this.backgroundMusic = MusicFactory.createMusicFromAsset(
			// this.mEngine.getMusicManager(), this,
			// "gfx/bg_music.oexception isgg");
			//
			// backgroundMusic.setLooping(true);

		} catch (Exception e) {
			System.out.println("sound44444444");
			System.out.println("exception is " + e);
		}

	}

	private void loadAlphabetSoundsInAdvance() {

		alphabetSoundData = new Sound[26];

		for (int i = 0; i < alphabetSoundData.length; i++) {

			try {
				alphabetSoundData[i] = SoundFactory.createSoundFromAsset(
						contextBaseGameActivity.getSoundManager(),
						contextBaseGameActivity, "sfx/alphabet_painting_scene/"
								+ alphabets[i] + ".mp3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void handleScreenShotPre() {

		screen_shot_downBlackBgSprite.setVisible(true);
		downBlackBgSprite.setVisible(false);

	}

	public void handleScreenShotPost() {

		screen_shot_downBlackBgSprite.setVisible(false);
		downBlackBgSprite.setVisible(true);

	}

	public void playAlphabetSound(int position) {

		// try {
		// // sound=
		// SoundFactory.createSoundFromAsset(contextBaseGameActivity.getSoundManager(),
		// contextBaseGameActivity,"a.mp3");
		//
		// // sound.play();
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		alphabetSoundData[position].play();

		// MediaPlayer mp1 = MediaPlayer.create(context, R.raw.r);

		// mp1.start();
		//

		// MediaPlayer p = null;
		//
		// p = new MediaPlayer();
		// try {
		// AssetFileDescriptor afd =
		// context.getAssets().openFd(alphabets[position] +".mp3");
		// p.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
		// afd.getLength());
		// afd.close();
		// p.prepare();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// p.start();
		// }

		// try {
		// //
		// // if (alphabetSound != null) {
		// //
		// // alphabetSound.stop();
		// // alphabetSound = null;
		// //
		// // }
		//
		// alphabetSound = MusicFactory.createMusicFromAsset(
		// contextBaseGameActivity.getMusicManager(),
		// contextBaseGameActivity, "sfx/alphabet_painting_scene/"
		// + alphabets[position] + ".mp3");
		//
		// alphabetSound.play();
		// backgroundMusic.setVolume(1f);
		// // this.coinSound = SoundFactory.createSoundFromAsset(co
		// // this.mEngine.getSoundManager(), this, "coin sound.mp3");
		// //
		// // this.bonusSound = SoundFactory.createSoundFromAsset(
		// // this.mEngine.getSoundManager(), this, "high_score.ogg");
		// //
		// // this.backgroundMusic = MusicFactory.createMusicFromAsset(
		// // this.mEngine.getMusicManager(), this,
		// // "gfx/bg_music.oexception isgg");
		// //
		// // backgroundMusic.setLooping(true);
		//
		// } catch (Exception e) {
		System.out.println("sound44444444");
		System.out.println("exception is ");
		// }
	}

	class DelaAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// mittiBgSprite.setVelocityY(-300);

		}
	}

	class DelaAsyncTask1 extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// if (this.getCurrentTileIndex() == 1) {
			onPipespriteJump();
			// }
			// to fix layering issue of alphabet
			// if (this.getCurrentTileIndex() == 3) {
			// alphabetsprite.setVisible(true);

			new DelaAsyncTask3().execute();

		}
	}

	class DelaAsyncTask3 extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(295);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// if (this.getCurrentTileIndex() == 1) {
			// onPipespriteJump();
			// }
			// to fix layering issue of alphabet
			// if (this.getCurrentTileIndex() == 3) {
			alphabetsprite.setVisible(true);

		}
	}

	public String getSelectedLetterName() {

		return alphabets[spriteClickedSpriteNumber];

	}

	public void freeMemory() {

		textureAtlas = null;
		colisionBitmapTextureAtlas = null;
		backBitmapTextureAtlas = null;
		mittiBgTextureAtlas = null;
		downBlackBgTextureAtlas = null;
		buttonPlayTextureAtlas = null;
		adBgBitmapTextureAtlas = null;
		pipeTextureAtlas = null;
		alphabetBitmapTextureAtlas = null;
		innerBitmapTextureAtlas = null;
		color_texture_bg = null;
		outerBitmapTextureAtlas = null;
		colorBitmapTextureAtlas = null;
		rubberBitmapTextureAtlas = null;
		particleTexture = null;

		texture_region = null;
		colisionTextureRegion = null;
		backTextureRegion = null;
		downBlackBgTextureRegion = null;
		buttonPlayTextureRegion = null;
		adBgTextureRegion = null;
		innerTextureRegion = null;
		outerTextureRegion = null;
		rubberTextureRegion = null;
		mittiBgTextureRegion = null;
		colorTextureRegion = null;
		color_texture_bgTextureRegion = null;
		particleTextureRegion = null;

		pipeTiledTextureRegion = null;
		pipeSprite = null;
		texture_region_list = null;
		spritesList = null;
		BitmapTextureAtlasList = null;
		mittiBgSprite = null;
		downBlackBgSprite = null;
		buttonPlaySprite = null;
		adBgSprite = null;
		innerSprite = null;
		outerSprite = null;
		colorSprite = null;
		color_texture_bgSprite = null;
		rubberSprite = null;
		mPhysicsWorld = null;
		alphabetTextureRegion = null;
		alphabetsprite = null;
		mCamera = null;

		colorPaintList.clear();

		scene = null;
		context = null;
		contextBaseGameActivity = null;
		alphabetPaintingScene = null;

		alphabets = null;

		alphabetSoundsNative = null;

		isErase = false;

		body = null;

		physicsConnector = null;

		charac = null;

		backgroundMusic = null;
		alphabetSound = null;

		alphabetSoundData = null;

	}
}
