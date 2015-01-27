package com.kids.fun2learn.scene;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;

import com.kids.fun2learn.AppsPartanBaseGameActivity;
import com.kids.fun2learn.MainActivity;
import com.kids.fun2learn.SceneManager;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;

public class MainMenuScene {
	private SimpleBaseGameActivity context;
	private Scene scene;
	private ITexture homeBgDownTexture;
	private ITexture homeBgUpTexture;
	private ITexture fun2LearnTexture;
	private ITextureRegion homeBgDownTextureRegion;
	private TextureRegion homeBgUpTextureRegion;
	private TextureRegion fun2LearnTextureRegion;
	private TiledTextureRegion doorTextureRegion;
	private TiledTextureRegion playBtnTextureRegion;
	private TiledTextureRegion birdTextureRegion;
	private TiledTextureRegion play_alphabet_PaintTextureRegion;
	private TiledTextureRegion play_counting_PaintTextureRegion;
	// for animated sprite
	private BitmapTextureAtlas PlayBtnTexture;
	private BitmapTextureAtlas play_alphabet_PaintTexture;
	private BitmapTextureAtlas play_counting_PaintTexture;
	private BitmapTextureAtlas doorTexture;
	private BitmapTextureAtlas birdTexture;
	private Sprite homeBgDownSprite;
	private Sprite homeBgUpSprite;
	private Sprite fun2LearnSprite;
	private AnimatedSprite play_alphabet_BtnPaintSprite;
	private AnimatedSprite play_counting_BtnPaintSprite;
	private AnimatedSprite nextBtn;
	private AnimatedSprite prevBtn;
	private AnimatedSprite doorAnimatedSprite;
	private AnimatedSprite playBtnAnimatedSprite;
	public AnimatedSprite birdAnimatedSprite;
	public Rectangle charac;
	private Music backgroundMusic;
	public Music birdSound;
	private Music doorSound;

	public MainMenuScene(Context context) {
		this.context = (SimpleBaseGameActivity) context;
		loadResources();
		loadScene();
	}

	private void loadResources() {
		try {
			this.homeBgDownTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/main_menu/home_bg_up.png");
			this.homeBgDownTextureRegion = TextureRegionFactory.extractFromTexture(this.homeBgDownTexture);
			this.homeBgDownTexture.load();
			this.homeBgUpTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/main_menu/home_bg_down.png");
			this.homeBgUpTextureRegion = TextureRegionFactory.extractFromTexture(this.homeBgUpTexture);
			this.homeBgUpTexture.load();
			this.doorTexture = new BitmapTextureAtlas(context.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.doorTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(doorTexture, context.getAssets(), "gfx/main_menu/door_open.png", 0, 0, 4, 1);
			doorTexture.load();
			this.PlayBtnTexture = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.playBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(PlayBtnTexture, context.getAssets(), "gfx/main_menu/play_btn.png", 0, 0, 2, 1);
			PlayBtnTexture.load();
			this.fun2LearnTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/main_menu/fun2learn.png");
			this.fun2LearnTextureRegion = TextureRegionFactory.extractFromTexture(this.fun2LearnTexture);
			this.fun2LearnTexture.load();
			this.play_alphabet_PaintTexture = new BitmapTextureAtlas(context.getTextureManager(), 512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.play_alphabet_PaintTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(play_alphabet_PaintTexture, context.getAssets(), "gfx/main_menu/play_alphabet_sprite.png", 0, 0, 2, 1);
			play_alphabet_PaintTexture.load();
			this.play_counting_PaintTexture = new BitmapTextureAtlas(context.getTextureManager(), 512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.play_counting_PaintTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(play_counting_PaintTexture, context.getAssets(), "gfx/main_menu/play-counting.png", 0, 0, 2, 1);
			play_counting_PaintTexture.load();
			this.birdTexture = new BitmapTextureAtlas(context.getTextureManager(), 128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.birdTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(birdTexture, context.getAssets(), "gfx/main_menu/bird_tell.png", 0, 0, 2, 1);
			birdTexture.load();
		} catch (Exception exception) {}
	}

	private void loadScene() {
		scene = new Scene();
		homeBgDownSprite = new Sprite(0, 0, homeBgDownTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(homeBgDownSprite);
		doorAnimatedSprite = new AnimatedSprite(142, 342, doorTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(doorAnimatedSprite);
		homeBgUpSprite = new Sprite(0, 0, homeBgUpTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(homeBgUpSprite);
		playBtnAnimatedSprite = new AnimatedSprite((doorAnimatedSprite.getX() + doorAnimatedSprite.getWidth()) / 2 + playBtnTextureRegion.getWidth(), (doorAnimatedSprite.getY()) + doorAnimatedSprite.getHeight() / 2 - playBtnTextureRegion.getHeight() / 2, playBtnTextureRegion.deepCopy(),
				context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					this.setCurrentTileIndex(0);
					play_alphabet_BtnPaintSprite.setVisible(true);
					fun2LearnSprite.setVisible(false);
					this.setVisible(false);
					birdAnimatedSprite.animate(200);
					playBirdSound();
				}
				return true;
			}
		};
		scene.attachChild(playBtnAnimatedSprite);
		if (CommonUtils.isTablet(context)) {
			playBtnAnimatedSprite.setScale(1.1f);
		} else {
			playBtnAnimatedSprite.setScale(1.5f);
		}
		fun2LearnSprite = new Sprite(83, 88, fun2LearnTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(fun2LearnSprite);
		if (CommonUtils.isTablet(context)) {
			fun2LearnSprite.setScale(.8f);
		} else {
			fun2LearnSprite.setScale(.9f);
		}
		play_alphabet_BtnPaintSprite = new AnimatedSprite(90, 98, play_alphabet_PaintTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					if (!playBtnAnimatedSprite.isVisible()) {
						stopBirdSound();
						playdoorSound();
						doorAnimatedSprite.animate(300, 0, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
								// TODO Auto-generated method stub
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
								try {
									stopBackgroundMusic();
								} catch (Exception exception) {}
								((AppsPartanBaseGameActivity) (context)).onShowAplhabetScene();
								try {
									AlphabetPaintingScene alphabetPaintingScene = ((AppsPartanBaseGameActivity) (context)).getAlphabetPaintingScene();
									alphabetPaintingScene.clearPaintScreen();
									SceneManager.setScene(alphabetPaintingScene.getScene());
								} catch (Exception exception) {}
								try {
									((MainActivity) (context)).getAlphabetPaintingScene().playBackgroundMusic();
								} catch (Exception exception) {}
							}
						});
					}
				}
				return true;
			}
		};
		scene.attachChild(play_alphabet_BtnPaintSprite);
		play_alphabet_BtnPaintSprite.setVisible(false);
		play_alphabet_BtnPaintSprite.animate(500);
		if (CommonUtils.isTablet(context)) {
			play_alphabet_BtnPaintSprite.setScale(.9f);
		} else {
			play_alphabet_BtnPaintSprite.setScale(1.1f);
		}
		play_counting_BtnPaintSprite = new AnimatedSprite(90, 98, play_counting_PaintTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					stopBirdSound();
					playdoorSound();
					if (!playBtnAnimatedSprite.isVisible()) {
						doorAnimatedSprite.animate(300, 0, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
							}

							@Override
							public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
								try {
									stopBackgroundMusic();
								} catch (Exception exception) {}
								((AppsPartanBaseGameActivity) (context)).onShowCountingNumberScene();
								try {
									CountingNumberPaintingScene countingNumberPaintingScene = ((AppsPartanBaseGameActivity) (context)).getCountingNumberPaintingScene();
									countingNumberPaintingScene.clearPaintScreen();
									SceneManager.setScene(countingNumberPaintingScene.getScene());
								} catch (Exception exception) {}
							}
						});
					}
				}
				return true;
			}
		};
		scene.attachChild(play_counting_BtnPaintSprite);
		play_counting_BtnPaintSprite.setVisible(false);
		play_counting_BtnPaintSprite.animate(500);
		if (CommonUtils.isTablet(context)) {
			play_counting_BtnPaintSprite.setScale(.9f);
		} else {
			play_counting_BtnPaintSprite.setScale(1.1f);
		}
		birdAnimatedSprite = new AnimatedSprite(150, 15, birdTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(birdAnimatedSprite);
		if (CommonUtils.isTablet(context)) {
			birdAnimatedSprite.setScale(.9f);
		} else {
			birdAnimatedSprite.setScale(1.1f);
		}
		// this is for left navigation
		prevBtn = new AnimatedSprite(32, 160, playBtnTextureRegion.deepCopy(), context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					this.setCurrentTileIndex(0);
					if (!playBtnAnimatedSprite.isVisible()) {
						play_counting_BtnPaintSprite.setVisible(false);
						play_alphabet_BtnPaintSprite.setVisible(true);
						fun2LearnSprite.setVisible(false);
						play_counting_BtnPaintSprite.setPosition(-1000, -1000);
						play_alphabet_BtnPaintSprite.setPosition(90, 98);
					}
				}
				return true;
			}
		};
		prevBtn.setRotation((prevBtn.getRotation() + 160)); // to rotate of
		if (CommonUtils.isTablet(context)) {
			prevBtn.setScale(.8f);
		} else {
			prevBtn.setScale(1f);
		}
		scene.attachChild(prevBtn);
		// this is for right next navigation
		nextBtn = new AnimatedSprite(CommonConstants.CAMERA_WIDTH / 2 + 42, 55, playBtnTextureRegion.deepCopy(), context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1);
				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					this.setCurrentTileIndex(0);
					// show play counting sprite
					if (!playBtnAnimatedSprite.isVisible()) {
						play_counting_BtnPaintSprite.setVisible(true);
						play_alphabet_BtnPaintSprite.setVisible(false);
						fun2LearnSprite.setVisible(false);
						play_alphabet_BtnPaintSprite.setPosition(-1000, -1000);
						play_counting_BtnPaintSprite.setPosition(90, 98);
					}
				}
				return true;
			}
		};
		nextBtn.setRotation(-(nextBtn.getRotation() + 140)); // to rotate of
		scene.attachChild(nextBtn);
		if (CommonUtils.isTablet(context)) {
			nextBtn.setScale(.8f);
		} else {
			nextBtn.setScale(1f);
		}
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		scene.registerTouchArea(playBtnAnimatedSprite);
		scene.registerTouchArea(play_alphabet_BtnPaintSprite);
		scene.registerTouchArea(play_counting_BtnPaintSprite);
		scene.registerTouchArea(prevBtn);
		scene.registerTouchArea(nextBtn);
	}

	private void unloadScene() {
		try {
			this.homeBgDownTexture = null;
			this.homeBgDownTextureRegion = null;
			this.homeBgUpTexture = null;
			this.homeBgUpTextureRegion = null;
			this.doorTexture = null;
			this.doorTextureRegion = null;
			this.PlayBtnTexture = null;
			this.playBtnTextureRegion = null;
			this.fun2LearnTexture = null;
			this.fun2LearnTextureRegion = null;
			this.play_alphabet_PaintTexture = null;
			this.play_alphabet_PaintTextureRegion = null;
			this.play_counting_PaintTexture = null;
			this.play_counting_PaintTextureRegion = null;
			this.birdTexture = null;
			this.scene = null;
			this.homeBgDownSprite = null;
			this.doorAnimatedSprite = null;
			this.homeBgUpSprite = null;
			this.playBtnAnimatedSprite = null;
			this.fun2LearnSprite = null;
			this.play_alphabet_BtnPaintSprite = null;
			this.play_counting_BtnPaintSprite = null;
			this.birdAnimatedSprite = null;
			this.prevBtn = null;
			this.nextBtn = null;
			System.gc();
		} catch (Exception exception) {}
	}

	public void resetDoorSprite() {
		doorAnimatedSprite.setCurrentTileIndex(0);
	}

	public Scene getScene() {
		return scene;
	}

	public void stopBackgroundMusic() {
		try {
			if (backgroundMusic != null) {
				backgroundMusic.stop();
				backgroundMusic = null;
			}
		} catch (Exception e) {
		}
	}

	public void playBirdSound() {
		stopBirdSound();
		birdSound = null;
		try {
			birdSound = MusicFactory.createMusicFromAsset(context.getMusicManager(), context, "sfx/alphabet_painting_scene/" + "blingsley_bird_1.mp3");
			birdSound.setVolume(.2f);
			birdSound.play();
			birdSound.setLooping(true);
		} catch (Exception e) {
		}
	}

	public void replayBirdSound() {
		if (birdSound != null) {
			birdSound.play();
		}
	}

	public void playBackgroundMusic() {
		stopBackgroundMusic();
		try {
			backgroundMusic = MusicFactory.createMusicFromAsset(context.getMusicManager(), context, "sfx/alphabet_painting_scene/" + "main_menu_scene_bg_sound.mp3");
			backgroundMusic.play();
			backgroundMusic.setVolume(.6f);
			backgroundMusic.setLooping(true);
		} catch (Exception e) {
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
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void playdoorSound() {
		stopDoorSound();
		doorSound = null;
		try {
			doorSound = MusicFactory.createMusicFromAsset(context.getMusicManager(), context, "sfx/alphabet_painting_scene/" + "Dresser Drawer Open 03.mp3");
			doorSound.play();
		} catch (Exception e) {
			System.out.println("exception is " + e);
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
}
