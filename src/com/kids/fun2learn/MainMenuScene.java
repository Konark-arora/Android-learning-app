package com.kids.fun2learn;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;

public class MainMenuScene {

	private Texture homeBgDownTexture;
	private Texture homeBgUpTexture;
	private Texture doorTexture;
	private Texture PlayBtnTexture;
	private Texture fun2LearnTexture;
	private Texture alphabetPaintTexture;
	private Texture birdTexture;

	private TextureRegion homeBgDownTextureRegion;
	private TextureRegion homeBgUpTextureRegion;
	private TextureRegion fun2LearnTextureRegion;
	private TiledTextureRegion alphabetPaintTextureRegion;

	private TiledTextureRegion doorTextureRegion;
	private TiledTextureRegion playBtnTextureRegion;
	private TiledTextureRegion birdTextureRegion;

	private Sprite homeBgDownSprite;
	private Sprite homeBgUpSprite;
	private Sprite fun2LearnSprite;
	private AnimatedSprite alphabetPlayBtnPaintSprite;

	private AnimatedSprite doorAnimatedSprite;
	private AnimatedSprite playBtnAnimatedSprite;
	private AnimatedSprite birdAnimatedSprite;

	private Scene scene;
	private Context context;
	private BaseGameActivity contextBaseGameActivity;

	private static MainMenuScene mainMenuScene;

	Music backgroundMusic;
	public Music birdSound;
	private Music doorSound;

	/**
	 * Load the scene and any assets we need.
	 */

	public MainMenuScene(Context context) {
		this.context = context;
		this.contextBaseGameActivity = (BaseGameActivity) (context);

	}

	public void loadResources() {

		homeBgDownTexture = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		homeBgDownTextureRegion = TextureRegionFactory.createFromAsset(
				homeBgDownTexture, context, "gfx/main_menu/home_bg_up.png", 0,
				0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(homeBgDownTexture);

		homeBgUpTexture = new Texture(512, 1024,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		homeBgUpTextureRegion = TextureRegionFactory.createFromAsset(
				homeBgUpTexture, context, "gfx/main_menu/home_bg_down.png", 0,
				0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(homeBgUpTexture);

		doorTexture = new Texture(512, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		doorTextureRegion = TextureRegionFactory
				.createTiledFromAsset(doorTexture, context,
						"gfx/main_menu/door_open.png", 0, 0, 4, 1);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(doorTexture);

		PlayBtnTexture = new Texture(128, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		playBtnTextureRegion = TextureRegionFactory.createTiledFromAsset(
				PlayBtnTexture, context, "gfx/main_menu/play_btn.png", 0, 0, 2,
				1);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(PlayBtnTexture);

		fun2LearnTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		fun2LearnTextureRegion = TextureRegionFactory.createFromAsset(
				fun2LearnTexture, context, "gfx/main_menu/fun2learn.png", 0, 0);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(fun2LearnTexture);

		alphabetPaintTexture = new Texture(512, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		alphabetPaintTextureRegion = TextureRegionFactory.createTiledFromAsset(
				alphabetPaintTexture, context,
				"gfx/main_menu/alphabet_sprite.png", 0, 0, 2, 1);

		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(alphabetPaintTexture);

		birdTexture = new Texture(128, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		birdTextureRegion = TextureRegionFactory
				.createTiledFromAsset(birdTexture, context,
						"gfx/main_menu/bird_tell.png", 0, 0, 2, 1);
		contextBaseGameActivity.getEngine().getTextureManager()
				.loadTexture(birdTexture);

	}

	/**
	 * Return the scene for when the scene is called to become active.
	 */
	public void loadScene() {

		scene = new Scene(4);

		homeBgDownSprite = new Sprite(0, 0, homeBgDownTextureRegion);
		scene.getTopLayer().addEntity(homeBgDownSprite);

		// final AutoParallaxBackground autoParallaxBackground = new
		// AutoParallaxBackground(0, 0, 0, 5);
		// autoParallaxBackground.addParallaxEntity(new ParallaxEntity(-5f,
		// homeBgDownSprite ));
		// scene.setBackground(autoParallaxBackground);

		doorAnimatedSprite = new AnimatedSprite(142, 342, doorTextureRegion);
		scene.getTopLayer().addEntity(doorAnimatedSprite);

		homeBgUpSprite = new Sprite(0, 0, homeBgUpTextureRegion);
		scene.getTopLayer().addEntity(homeBgUpSprite);

		playBtnAnimatedSprite = new AnimatedSprite(
				(doorAnimatedSprite.getX() + doorAnimatedSprite.getWidth()) / 2
						+ playBtnTextureRegion.getWidth() / 2,
				(doorAnimatedSprite.getY()) + doorAnimatedSprite.getHeight()
						/ 2 - playBtnTextureRegion.getHeight() / 2,
				playBtnTextureRegion) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					this.setCurrentTileIndex(1);

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					this.setCurrentTileIndex(0);

					alphabetPlayBtnPaintSprite.setVisible(true);
					fun2LearnSprite.setVisible(false);
					this.setVisible(false);

					birdAnimatedSprite.animate(200);

					playBirdSound();
				}
				return true;
			}
		};

		scene.getTopLayer().addEntity(playBtnAnimatedSprite);

		playBtnAnimatedSprite.setScale(1.5f);

		fun2LearnSprite = new Sprite(83, 88, fun2LearnTextureRegion);
		scene.getTopLayer().addEntity(fun2LearnSprite);

		alphabetPlayBtnPaintSprite = new AnimatedSprite(90, 98,
				alphabetPaintTextureRegion) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					if (!playBtnAnimatedSprite.isVisible()) {

						stopBirdSound();

						playdoorSound();

						doorAnimatedSprite.animate(300, 0,
								new IAnimationListener() {

									@Override
									public void onAnimationEnd(
											AnimatedSprite pAnimatedSprite) {

										// CommonUtils.launchActivity(context,
										// AlphabetPaintingActivity.class);

										// ((MainActivity)(context)).showAlphabetPaintLL();

										try {
											stopBackgroundMusic();
										} catch (Exception exception) {

										}

										try {
											SceneManager
													.setScene(((MainActivity) (context))
															.getAlphabetPaintingScene()
															.getScene());
										} catch (Exception exception) {

										}

										try {
											((MainActivity) (context))
													.getAlphabetPaintingScene()
													.playBackgroundMusic();
										} catch (Exception exception) {

										}
										try {
											((MainActivity) (context))
													.showAplhabetScene();
										} catch (Exception exception) {

										}
										// ((MainActivity)
										// (context)).alphabetPaintingScene.handleScreenShotPre();

									}
								});
					}
				}

				return true;
			}
		};

		// alphabetPlayBtnPaintSprite = new Sprite(83, 88,
		// alphabetPaintTextureRegion) {
		//
		//
		// };
		scene.getTopLayer().addEntity(alphabetPlayBtnPaintSprite);
		alphabetPlayBtnPaintSprite.setVisible(false);
		alphabetPlayBtnPaintSprite.animate(500);

		birdAnimatedSprite = new AnimatedSprite(150, 15, birdTextureRegion);
		scene.getTopLayer().addEntity(birdAnimatedSprite);
		// birdAnimatedSprite.setScale(1.2f);

		scene.setTouchAreaBindingEnabled(true);

		scene.registerTouchArea(playBtnAnimatedSprite);
		scene.registerTouchArea(alphabetPlayBtnPaintSprite);

		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
			}
		});

		// scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
		// @Override
		// public boolean onSceneTouchEvent(final Scene scene,
		// final TouchEvent touchEvent) {
		//
		// retrun true;
		// }
		// });

	}

	public void resetDoorSprite() {

		doorAnimatedSprite.setCurrentTileIndex(0);

		playBirdSound();
		playBackgroundMusic();
	}

	public void playdoorSound() {

		stopDoorSound();

		doorSound = null;

		try {
			doorSound = MusicFactory.createMusicFromAsset(
					contextBaseGameActivity.getMusicManager(),
					contextBaseGameActivity, "sfx/alphabet_painting_scene/"
							+ "Dresser Drawer Open 03.mp3");

			doorSound.play();
			// doorSound.setLooping(true);

			// birdSound.setVolume(1f);
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

	public void stopBackgroundMusic() {

		try {

			if (backgroundMusic != null) {

				backgroundMusic.stop();
				backgroundMusic = null;
			}
		} catch (Exception e) {
			// TODO: handle exception

		}
	}

	public void playBackgroundMusic() {

		stopBackgroundMusic();

		try {
			backgroundMusic = MusicFactory.createMusicFromAsset(
					contextBaseGameActivity.getMusicManager(),
					contextBaseGameActivity, "sfx/alphabet_painting_scene/"
							+ "main_menu_scene_bg_sound.mp3");

			backgroundMusic.play();

			backgroundMusic.setVolume(.7f);

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

	public void stopBirdSound() {

		try {

			if (birdSound != null) {

				birdSound.stop();
				birdSound = null;

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void pauseBirdSound() {

		try {

			if (birdSound != null) {

				birdSound.pause();
				// birdSound = null;

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void stopDoorSound() {

		try {

			if (doorSound != null) {

				doorSound.stop();
				doorSound = null;

			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void playBirdSound() {

		stopBirdSound();

		birdSound = null;

		try {
			birdSound = MusicFactory.createMusicFromAsset(
					contextBaseGameActivity.getMusicManager(),
					contextBaseGameActivity, "sfx/alphabet_painting_scene/"
							+ "blingsley_bird_1.mp3");

			birdSound.setVolume(.2f);
			birdSound.play();
			birdSound.setLooping(true);

			// birdSound.setVolume(1f);
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

	public void replayBirdSound() {

		if (birdSound != null) {

			birdSound.play();
		}

	}

	/**
	 * Unload any assets here - to be called later.
	 * 
	 * It se
	 */
	public static void unloadScene() {

		mainMenuScene = null;

	}

	public void freemMenory() {

		homeBgDownTexture = null;
		homeBgUpTexture = null;
		doorTexture = null;
		PlayBtnTexture = null;
		fun2LearnTexture = null;
		alphabetPaintTexture = null;
		birdTexture = null;

		homeBgDownTextureRegion = null;
		homeBgUpTextureRegion = null;
		fun2LearnTextureRegion = null;
		alphabetPaintTextureRegion = null;

		doorTextureRegion = null;
		playBtnTextureRegion = null;
		birdTextureRegion = null;

		homeBgDownSprite = null;
		homeBgUpSprite = null;
		fun2LearnSprite = null;
		alphabetPlayBtnPaintSprite = null;

		doorAnimatedSprite = null;
		playBtnAnimatedSprite = null;
		birdAnimatedSprite = null;

		scene = null;
		context = null;
		contextBaseGameActivity = null;

		mainMenuScene = null;

		backgroundMusic = null;
		birdSound = null;
		doorSound = null;

	}

	/**
	 * It reset the scene and unloads
	 */
	public void resetScene() {

		resetDoorSprite();
		// mainMenuScene = null;
	}

	public static MainMenuScene getMainMenuScene(Context context) {

//		if (mainMenuScene == null) {

			mainMenuScene = new MainMenuScene(context);
			mainMenuScene.loadResources();
			mainMenuScene.loadScene();
//		}

		return mainMenuScene;
	}

	public Scene getScene() {

		return scene;
	}

}
