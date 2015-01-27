package com.kids.fun2learn.scene;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
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
import android.widget.Toast;

import com.appspartan.custom.CustomParticle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kids.fun2learn.AppsPartanBaseGameActivity;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.RandomNumberGenerator;
import com.kids.fun2learn.utils.SharedPrefUtils;

public class CountingNumberPaintingScene {
	private String countingNumbers[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	private SimpleBaseGameActivity context;
	private Scene scene;
	private ITexture bgTexture, particleTexture;
	private ITexture bagTexture;
	private ITexture bgSkycutTexture;
	private ITexture slingshotBgTexture;
	private ITexture slingshotTexture;
	private ITexture poleTexture;
	private ITexture numberBulletTexture;
	private ITexture SlingShot;
	private ITexture cloudITexture;
	private ITexture colorBitmapTexture;
	private ITexture innerPaintCountingNumberTexture;
	private ITexture outerPaintCountingNumberTexture;
	private ITexture rubberTexture;
	private ITexture downBlackBgTextureAtlas;
	private ITexture screen_shot_downBlackBgTextureAtlas;
	private ITextureRegion colorTextureRegion, particleTextureRegion;
	private ITextureRegion bgTextureRegion;
	private ITextureRegion bagTextureRegion;
	private ITextureRegion bgSkycutTextureRegion;
	private ITextureRegion slingshotBgTextureRegion;
	private ITextureRegion slingshotTextureRegion;
	private ITextureRegion poleTextureRegion;
	private ITextureRegion numberBulletTextureRegion;
	private ITextureRegion SlingShotTextureRegion;
	private ITextureRegion cloudITextureRegion;
	private ITextureRegion innerPaintCountingNumberTextureRegion;
	private ITextureRegion outerPaintCountingNumberTextureRegion;
	private ITextureRegion rubberTextureRegion;
	private ITextureRegion downBlackBgTextureRegion;
	private ITextureRegion screen_shot_downBlackBgTextureRegions;
	private Sprite bgSprite, bagSprite, bgSlingShotSprite, bgSkyCutSprite;
	private Sprite slingShotSprite;
	private Sprite leftPoleSprite;
	private Sprite rightPoleSprite;
	private Sprite cloudSprite;
	private Sprite outerPaintCountingNumberSprite;
	private Sprite innerPaintCountingNumberSprite;
	private Sprite colorSprite;
	private Sprite rubberSprite;
	private Sprite screen_shot_downBlackBgSprite, downBlackBgSprite;
	private Line leftCord, rightCord;
	// for animated sprite
	private BitmapTextureAtlas birdBitmapTextureAtlas;
	private BitmapTextureAtlas blastBitmapTextureAtlas;
	private BitmapTextureAtlas countingNumnberBitmapTextureAtlas;
	private TiledTextureRegion birTiledTextureRegionn;
	private TiledTextureRegion blastTiledTextureRegionn;
	private TiledTextureRegion countingNumnberTiledTextureRegion;
	private AnimatedSprite birdAnimatedSprite;
	private AnimatedSprite blastAnimatedSprite;
	private AnimatedSprite countingNumberAnimatedSprite;
	// =========
	PhysicsHandler countingNumberPhysicsHandler;
	static float vx, vy, spritePosX, spritePosY;
	float pressedX, pressedY, realseX, realseY;
	float moveX, moveY;
	private Rectangle sligshotRectangle;
	private Sprite slingShot, numberBulletSprite;
	private PhysicsHandler physicsHandler;
	private PhysicsWorld mPhysicsWorld;
	private PhysicsConnector physicsConnector;
	private FixtureDef equibFixtureDef;
	private Body numberBulletBody;
	private boolean isMove;
	public boolean isFirstTime = true;
	private PhysicsHandler birdPhysicsHandler, blastPhysicsHandler, baghysicsHandler;
	private Entity sceneLayer1, sceneLayer0, sceneLayer2, sceneLayer3, sceneLayer4;
	private int clickedPianoItemPosition;
	private ArrayList<Sprite> colorPaintList = new ArrayList<Sprite>();
	private boolean isErase = false;
	private Sound countingNumberSoundData[];
	private Music backgroundMusic;
	private Sound blastSound, releaseSligShotSound;

	public CountingNumberPaintingScene(Context context) {
		this.context = (SimpleBaseGameActivity) context;
		loadResources();
		loadScene();
	}

	private void loadResources() {
		try {
			// Background
			this.bgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/sky.png");
			this.bgTextureRegion = TextureRegionFactory.extractFromTexture(this.bgTexture);
			this.bgTexture.load();
			this.bgSkycutTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/sky_cut.png");
			this.bgSkycutTextureRegion = TextureRegionFactory.extractFromTexture(this.bgSkycutTexture);
			this.bgSkycutTexture.load();
			this.slingshotBgTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/sling_shot_bg.png");
			this.slingshotBgTextureRegion = TextureRegionFactory.extractFromTexture(this.slingshotBgTexture);
			this.slingshotBgTexture.load();
			this.slingshotTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/slingshot.png");
			this.slingshotTextureRegion = TextureRegionFactory.extractFromTexture(this.slingshotTexture);
			this.slingshotTexture.load();
			this.poleTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/electricpole.png");
			this.poleTextureRegion = TextureRegionFactory.extractFromTexture(this.poleTexture);
			this.poleTexture.load();
			this.SlingShot = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/slingshot.png");
			this.SlingShotTextureRegion = TextureRegionFactory.extractFromTexture(this.SlingShot);
			this.SlingShot.load();
			birdBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			birTiledTextureRegionn = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(birdBitmapTextureAtlas, context.getAssets(), "gfx/counting_numbers_painting/bird_animation.png", 0, 0, 5, 1);
			birdBitmapTextureAtlas.load();
			blastBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			blastTiledTextureRegionn = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(blastBitmapTextureAtlas, context.getAssets(), "gfx/counting_numbers_painting/blast_sprite.png", 0, 0, 2, 5);
			blastBitmapTextureAtlas.load();
			this.cloudITexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/cloud.png");
			this.cloudITextureRegion = TextureRegionFactory.extractFromTexture(this.cloudITexture);
			this.cloudITexture.load();
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
			loadCountingNumberSoundsInAdvance();
		} catch (Exception exception) {}
	}

	private void loadScene() {
		scene = new Scene();
		sceneLayer0 = new Entity();
		sceneLayer1 = new Entity();
		sceneLayer2 = new Entity();
		sceneLayer3 = new Entity();
		sceneLayer4 = new Entity();
		scene.attachChild(sceneLayer0);// layer 0
		scene.attachChild(sceneLayer1);// layer
		scene.attachChild(sceneLayer2);// layer2
		scene.attachChild(sceneLayer3);// layer3
		scene.attachChild(sceneLayer4);// layer3
		// default color is of 0 postion & type i color plate not texture plate
		createColorTexuturePaint(0, CommonConstants.COLOR_PLATE);
		bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager());
		bgSkyCutSprite = new Sprite(0, 0, bgSkycutTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer0.attachChild(bgSprite);
		downBlackBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 + downBlackBgTextureRegion.getHeight() + 43, downBlackBgTextureRegion, context.getVertexBufferObjectManager());
		screen_shot_downBlackBgSprite = new Sprite(0, CommonConstants.CAMERA_HEIGHT / 2 + downBlackBgTextureRegion.getHeight() + 43, screen_shot_downBlackBgTextureRegions, context.getVertexBufferObjectManager());
		scene.attachChild(downBlackBgSprite);
		scene.attachChild(screen_shot_downBlackBgSprite);
		screen_shot_downBlackBgSprite.setVisible(false);
		birdAnimatedSprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH, 50, birTiledTextureRegionn.deepCopy(), context.getVertexBufferObjectManager());
		birdAnimatedSprite.setScale(.7f);
		sceneLayer2.attachChild(birdAnimatedSprite);
		birdPhysicsHandler = new PhysicsHandler(birdAnimatedSprite);
		birdAnimatedSprite.registerUpdateHandler(birdPhysicsHandler);
		birdAnimatedSprite.animate(100);
		blastAnimatedSprite = new AnimatedSprite(birdAnimatedSprite.getX(), birdAnimatedSprite.getY(), blastTiledTextureRegionn.deepCopy(), context.getVertexBufferObjectManager());
		blastAnimatedSprite.setScale(.6f);
		blastAnimatedSprite.setVisible(false);
		sceneLayer2.attachChild(blastAnimatedSprite);
		blastPhysicsHandler = new PhysicsHandler(blastAnimatedSprite);
		blastAnimatedSprite.registerUpdateHandler(blastPhysicsHandler);
		birdAnimatedSprite.animate(100);
		blastAnimatedSprite.animate(150);
		bgSlingShotSprite = new Sprite(0, 370, slingshotBgTextureRegion, context.getVertexBufferObjectManager());
		sceneLayer4.attachChild(bgSlingShotSprite);
		cloudSprite = new Sprite(CommonConstants.CAMERA_WIDTH / 2 - cloudITextureRegion.getWidth() / 2, 60, cloudITextureRegion, context.getVertexBufferObjectManager());
		cloudSprite.setScale(0f);
		cloudSprite.setVisible(false);
		sceneLayer1.attachChild(cloudSprite);
		rubberSprite = new Sprite(100, 100, rubberTextureRegion, context.getVertexBufferObjectManager());
		rubberSprite.setScale(.1f);
		scene.attachChild(rubberSprite);
		rubberSprite.setVisible(false);
		spritePosX = CommonConstants.CAMERA_WIDTH / 2 - (10);
		spritePosY = CommonConstants.CAMERA_HEIGHT / 2;
		slingShot = new Sprite(0, spritePosY + 30, SlingShotTextureRegion, context.getVertexBufferObjectManager());
		leftCord = new Line(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 30, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 30, context.getVertexBufferObjectManager());
		rightCord = new Line(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 30, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 30, context.getVertexBufferObjectManager());
		leftCord.setLineWidth(3);
		rightCord.setLineWidth(3);
		leftCord.setColor(0, 0, 0);
		rightCord.setColor(0, 0, 0);
		sceneLayer4.attachChild(slingShot);
		slingShot.setVisible(false);
		sceneLayer4.attachChild(leftCord);
		sceneLayer4.attachChild(rightCord);
		sligshotRectangle = new Rectangle(spritePosX - 50, spritePosY - 30, 120, 120, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						if (numberBulletSprite != null && numberBulletBody != null) {
							pressedX = numberBulletSprite.getWidth() / 2 + spritePosX;
							pressedY = numberBulletSprite.getHeight() / 2 + spritePosY;
							isMove = true;
						}
						break;
					case TouchEvent.ACTION_MOVE:
						moveX = pSceneTouchEvent.getX();
						moveY = pSceneTouchEvent.getY();
						float tempX = moveX - pressedX;
						float tempY = moveY - pressedY;
						// && moveY > 400 && moveY <580
						if (numberBulletSprite != null && numberBulletBody != null && moveY > 400 && moveY < 595) {
							if ((moveX > spritePosX - 100 && moveX < CommonConstants.CAMERA_WIDTH - numberBulletSprite.getWidth() && moveY > spritePosY && moveY < CommonConstants.CAMERA_HEIGHT - numberBulletSprite.getHeight())) {
								if (isMove) { // +mummySprite.getHeight()
									System.out.println("-----Enter in Case 1----");
									numberBulletBody.setTransform((moveX + 15) / 32, (moveY + 30) / 32, 0);
									this.setPosition(moveX - 100, moveY - 100);
									if (SharedPrefUtils.getIsPurchased(context)) {
										if (CommonUtils.isTablet(context)) {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45 + tempY + numberBulletSprite.getHeight()
													/ 2);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 45, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 45);
										} else {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2
													+ 50);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 50, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 50);
										}
									} else {
										if (CommonUtils.isTablet(context)) {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20 + tempY + numberBulletSprite.getHeight()
													/ 2);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 20, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 20);
										} else {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2);
										}
									}
								}
							} else if ((moveX < spritePosX && moveX > numberBulletSprite.getWidth() && moveY > spritePosY && moveY < CommonConstants.CAMERA_HEIGHT - numberBulletSprite.getHeight())) {
								if (isMove) {
									System.out.println("-----Enter in Case 2----");
									numberBulletBody.setTransform((moveX + 15) / 32, (moveY + 30) / 32, 0);
									this.setPosition(moveX - 100, moveY - 100);
									if (SharedPrefUtils.getIsPurchased(context)) {
										if (CommonUtils.isTablet(context)) {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2
													+ 45);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 45, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 45);
										} else {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2
													+ 50);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 50, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 50);
										}
									} else {
										if (CommonUtils.isTablet(context)) {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2
													+ 20);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 20, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2
													+ 20);
										} else {
											leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2);
											rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2);
										}
									}
								}
							} else if (moveY > CommonConstants.CAMERA_HEIGHT - numberBulletSprite.getHeight() || (moveX < numberBulletSprite.getWidth() || moveX > CommonConstants.CAMERA_WIDTH - numberBulletSprite.getWidth() && moveY > spritePosY + 65)) {
								if (isMove) {
									System.out.println("-----Enter in Case 4----");
									isMove = false;
									// if(gamePattern.isVisible())
									// gamePattern.setVisible(false);
									realseX = (int) numberBulletSprite.getX();
									realseY = (int) numberBulletSprite.getY();
									vx = pressedX - realseX;
									vy = pressedY - realseY;
									if (vy > 0) {
										vy = -vy;
									}
									realseX = realseX - vx;
									realseY = realseY - vy;
									vx = pressedX - realseX;
									vy = pressedY - realseY;
									realseX = realseX - vx;
									realseY = realseY - vy;
									vx = pressedX - realseX;
									vy = pressedY - realseY;
									System.out.println("-------final-velocity--V X -------->" + vx);
									System.out.println("-------final velocity--V Y -------->" + vy);
									if (realseY > pressedY) {
										if (SharedPrefUtils.getIsPurchased(context)) {
											if (CommonUtils.isTablet(context)) {
												leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45);
												rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 45);
											} else {
												leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2
														+ 50);
												rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 50, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight()
														/ 2 + 50);
											}
										} else {
											if (CommonUtils.isTablet(context)) {
												leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20);
												rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 20);
											} else {
												leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2);
												rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2);
											}
										}
										shootBird(vx - 50, vy);
										try {
											releaseSligShotSound.play();
										} catch (Exception exception) {}
										scene.unregisterTouchArea(this);
									}
									//
								}
							} else {
								System.out.println("---------Enter in fourth part--------");
								if (moveX >= spritePosX - 100 && moveX <= spritePosX + numberBulletSprite.getWidth() + 150 && moveY >= spritePosY - 150 && moveY <= spritePosY + numberBulletSprite.getHeight() + 50) {
									System.out.println("-----Enter in Case 5----");
								} else {
									System.out.println("-----Enter in Case 6----");
									isMove = false;
								}
								this.setPosition(spritePosX - 100, spritePosY - 100);
								numberBulletSprite.setPosition(spritePosX, spritePosY);
								if (SharedPrefUtils.getIsPurchased(context)) {
									if (CommonUtils.isTablet(context)) {
										leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45);
										rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 45);
									} else {
										leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 50);
										rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2 + 50, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 50);
									}
								} else {
									if (CommonUtils.isTablet(context)) {
										leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20);
										rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 20);
									} else {
										leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2);
										rightCord.setPosition(slingShot.getWidth() / 2 + tempX + numberBulletSprite.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + tempY + numberBulletSprite.getHeight() / 2, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2);
									}
								}
							}
						}
						break;
					case TouchEvent.ACTION_UP:
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						if (numberBulletSprite != null && numberBulletBody != null) {
							if (isMove) {
								isMove = false;
								realseX = (int) numberBulletSprite.getX();
								realseY = (int) numberBulletSprite.getY();
								vx = pressedX - realseX;
								vy = pressedY - realseY;
								if (vy > 0) {
									vy = -vy;
								}
								realseX = realseX - vx;
								realseY = realseY - vy;
								vx = pressedX - realseX;
								vy = pressedY - realseY;
								realseX = realseX - vx;
								realseY = realseY - vy;
								vx = pressedX - realseX;
								vy = pressedY - realseY;
								System.out.println("-------final-velocity--V X -------->" + vx);
								System.out.println("-------final velocity--V Y -------->" + vy);
								if (realseY > pressedY) {
									setScenePosition();
									shootBird(vx - 50, vy);
									try {
										releaseSligShotSound.play();
									} catch (Exception exception) {}
									scene.unregisterTouchArea(this);
								}
								try {
									this.setPosition(CommonConstants.CAMERA_WIDTH / 2 - 50, CommonConstants.CAMERA_HEIGHT / 2);
								} catch (Exception exception) {}
							}
						}
						break;
				}
				return true;
			}
		};
		scene.registerTouchArea(sligshotRectangle);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		sceneLayer4.attachChild(sligshotRectangle);
		sligshotRectangle.setVisible(false);
		equibFixtureDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH * 6), false, 8, 8);
		scene.registerUpdateHandler(this.mPhysicsWorld);
		handleSceneTouch();
		registerSceneUpdateHandler();
	}

	private void handleSceneTouch() {
		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).texture_PlateLinearLayout);
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).colorPlateLinearLayout);
						CommonUtils.setVisibiltyGone(((AppsPartanBaseGameActivity) (context)).eraser_popup_ll);
					}
				});
				if (isErase == true) {
					rubberSprite.setPosition(pSceneTouchEvent.getX() - rubberSprite.getWidth() / 2, pSceneTouchEvent.getY() - rubberSprite.getHeight() / 2);
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
					colorSprite = new Sprite(pSceneTouchEvent.getX() - colorTextureRegion.getWidth(), pSceneTouchEvent.getY() - colorTextureRegion.getHeight(), colorTextureRegion.deepCopy(), context.getVertexBufferObjectManager());
					if (innerPaintCountingNumberSprite != null)
						innerPaintCountingNumberSprite.attachChild(colorSprite);
					colorPaintList.add(colorSprite);
				}
				createParticalSystem(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), 10);
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

	private void registerSceneUpdateHandler() {
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// logic for if body is outside of camera
				if (numberBulletBody != null) {
					if (numberBulletBody.getLinearVelocity().x < -CommonConstants.CAMERA_WIDTH / 4 || numberBulletBody.getLinearVelocity().x > CommonConstants.CAMERA_WIDTH + 20 || numberBulletBody.getLinearVelocity().y < -CommonConstants.CAMERA_HEIGHT / 4
							|| numberBulletBody.getLinearVelocity().y > CommonConstants.CAMERA_HEIGHT + 100) {
						removeBodyFromPhysicsWorld();
					}
				}
				// collision detection login is based on rectangle over brid
				if (numberBulletSprite != null && birdAnimatedSprite != null) {
					if (numberBulletSprite.collidesWith(birdAnimatedSprite) && !blastAnimatedSprite.isVisible()) {
						try {
							blastSound.play();
						} catch (Exception exception) {
							exception.printStackTrace();
						}
						blastAnimatedSprite.setVisible(true);
						blastAnimatedSprite.animate(100, 0, new IAnimationListener() {
							@Override
							public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
							}

							@Override
							public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
								if (pNewFrameIndex == 5) {
									birdAnimatedSprite.setVisible(false);
									bagSprite.setVisible(false);
								}
								if (pNewFrameIndex == 6) {
									prepareClouding();
								}
							}

							@Override
							public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
								blastAnimatedSprite.setVisible(false);
							}
						});
					}
				}
			}
		});
	}

	private void prepareClouding() {
		// perepare number painting
		CloudScaleAtModifier = new ScaleAtModifier(.4f /* Durarion */, .0f/* from */, 1f/* to */, .0f, 1f, cloudSprite.getWidth() / 2/*
																																	 * scale
																																	 * center
																																	 * X
																																	 */, cloudSprite.getHeight() / 2 /*
																																									 * scale
																																									 * center
																																									 * Y
																																									 */);
		cloudSprite.registerEntityModifier(CloudScaleAtModifier);
		cloudSprite.setVisible(true);
		makeCountingNumberSprite();
	}

	private void makeCountingNumberSprite() {
		this.countingNumnberBitmapTextureAtlas = null;
		this.countingNumnberBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.countingNumnberTiledTextureRegion = null;
		this.countingNumnberTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(countingNumnberBitmapTextureAtlas, context.getAssets(), "gfx/counting_numbers_painting/counting_numnber/" + clickedPianoItemPosition + ".png", 0, 0, 2, 1);
		this.countingNumnberBitmapTextureAtlas.load();
		this.countingNumberAnimatedSprite = null;
		this.countingNumberAnimatedSprite = new AnimatedSprite(cloudSprite.getX() + cloudSprite.getWidth() / 2 - countingNumnberTiledTextureRegion.getWidth() / 2, cloudSprite.getY() + cloudSprite.getHeight() / 2 - countingNumnberTiledTextureRegion.getHeight() / 2,
				countingNumnberTiledTextureRegion.deepCopy(), context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
						this.setCurrentTileIndex(1);
						break;
					case TouchEvent.ACTION_UP:
						this.setCurrentTileIndex(0);
						onCountingNumberSpriteClick();
						break;
				}
				return true;
			}
		};
		try {
			sceneLayer4.detachChild(countingNumberAnimatedSprite);
		} catch (Exception exception) {
			try {
				countingNumberAnimatedSprite.setPosition(-1000, -1000);
			} catch (Exception e) {}
		}
		sceneLayer4.attachChild(countingNumberAnimatedSprite);
		scene.registerTouchArea(countingNumberAnimatedSprite);
		countingNumberAnimatedSprite.setCurrentTileIndex(0);
		countingNumberAnimatedSprite.setScale(.0f);
		this.numberScaleAtModifier = null;
		this.numberScaleAtModifier = new ScaleAtModifier(.4f /* Durarion */, .0f/* from */, .9f/* to */, .0f, .9f, countingNumberAnimatedSprite.getWidth() / 2/*
																																								 * scale
																																								 * center
																																								 * X
																																								 */, countingNumberAnimatedSprite.getHeight() / 2 /*
																																																				 * scale
																																																				 * center
																																																				 * Y
																																																				 */);
		countingNumberAnimatedSprite.registerEntityModifier(numberScaleAtModifier);
	}

	ScaleAtModifier numberScaleAtModifier;

	private void onCountingNumberSpriteClick() {
		this.countingNumberPhysicsHandler = new PhysicsHandler(countingNumberAnimatedSprite);
		countingNumberAnimatedSprite.registerUpdateHandler(countingNumberPhysicsHandler);
		// birdPhysicsHandler.setVelocityX(-50);
		countingNumberPhysicsHandler.setVelocityY(-500);
		cloudSprite.setVisible(false);
		// load counting number for painting
		try {
			this.innerPaintCountingNumberTexture = null;
			this.innerPaintCountingNumberTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/counting_numnber_inner/" + clickedPianoItemPosition + ".png");
			this.innerPaintCountingNumberTextureRegion = null;
			this.innerPaintCountingNumberTextureRegion = TextureRegionFactory.extractFromTexture(this.innerPaintCountingNumberTexture);
			this.innerPaintCountingNumberTexture.load();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		// oute paiting number
		try {
			this.outerPaintCountingNumberTexture = null;
			this.outerPaintCountingNumberTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/counting_numnber_outer/" + clickedPianoItemPosition + ".png");
			this.outerPaintCountingNumberTextureRegion = null;
			this.outerPaintCountingNumberTextureRegion = TextureRegionFactory.extractFromTexture(this.outerPaintCountingNumberTexture);
			this.outerPaintCountingNumberTexture.load();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		this.innerPaintCountingNumberSprite = null;
		this.innerPaintCountingNumberSprite = new Sprite(cloudSprite.getX(), cloudSprite.getY(), innerPaintCountingNumberTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				return true;
			}
		};
		this.outerPaintCountingNumberSprite = null;
		this.outerPaintCountingNumberSprite = new Sprite(cloudSprite.getX(), cloudSprite.getY(), outerPaintCountingNumberTextureRegion, context.getVertexBufferObjectManager());
		try {
			sceneLayer3.detachChild(innerPaintCountingNumberSprite);
		} catch (Exception exception) {
			try {
				innerPaintCountingNumberSprite.setPosition(-1000, -1000);
			} catch (Exception e) {}
		}
		sceneLayer3.attachChild(innerPaintCountingNumberSprite);
		try {
			sceneLayer3.detachChild(outerPaintCountingNumberSprite);
		} catch (Exception exception) {
			try {
				outerPaintCountingNumberSprite.setPosition(-1000, -1000);
			} catch (Exception e) {}
		}
		sceneLayer3.attachChild(outerPaintCountingNumberSprite);
		try {
			sceneLayer3.detachChild(bgSkyCutSprite);
		} catch (Exception exception) {
			try {
				bgSkyCutSprite.setPosition(-1000, -1000);
			} catch (Exception e) {}
		}
		sceneLayer3.attachChild(bgSkyCutSprite);
	}

	private void removeBodyFromPhysicsWorld() {
		numberBulletBody.setActive(false);
		if (numberBulletSprite != null) {
			numberBulletSprite.setPosition(-1000, -1000);
		}
		if (numberBulletBody != null) {
			numberBulletBody.setTransform(-1000 / 32, -1000 / 32, 0);
		}
		mPhysicsWorld.clearPhysicsConnectors();
	}

	public void clearPaintScreen() {
		try {
			cloudSprite.setVisible(false);
			try {
				bagSprite.setPosition(-1000, -1000);
				sceneLayer1.detachChild(bagSprite);
				bagSprite.unregisterUpdateHandler(baghysicsHandler);
				bagSprite = null;
			} catch (Exception exception) {}
			try {
				sceneLayer4.detachChild(numberBulletSprite);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			if (numberBulletBody != null) {
				numberBulletBody.setTransform(-1000 / 32, -1000 / 32, 0);
			}
			if (numberBulletSprite != null) {
				numberBulletSprite.setPosition(-1000, -1000);
			}
			try {
				if (countingNumberAnimatedSprite != null) {
					countingNumberAnimatedSprite.unregisterEntityModifier(numberScaleAtModifier);
					countingNumberAnimatedSprite.setPosition(-5000, -5000);
					sceneLayer4.detachChild(countingNumberAnimatedSprite);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			try {
				sceneLayer3.detachChild(bgSkyCutSprite);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			try {
				mPhysicsWorld.unregisterPhysicsConnector(physicsConnector);
				numberBulletBody.setActive(false);
				numberBulletBody = null;
				numberBulletBody.setTransform(-1000 / CommonConstants.PIXEL_TO_METER_RATIO, -1000 / CommonConstants.PIXEL_TO_METER_RATIO, 0);
			} catch (Exception exception) {}
			// ***********clear old screen
			if (colorPaintList != null) {
				for (int i = 0; i < colorPaintList.size(); i++) {
					Sprite sprite = colorPaintList.get(i);
					sprite.setPosition(-1000, -1000);
					sprite = null;
				}
			}
			if (colorPaintList != null)
				colorPaintList.clear();
			if (innerPaintCountingNumberSprite != null) {
				innerPaintCountingNumberSprite.setPosition(-1000, -1000);
				innerPaintCountingNumberSprite = null;
			}
			if (outerPaintCountingNumberSprite != null) {
				outerPaintCountingNumberSprite.setPosition(-1000, -1000);
				outerPaintCountingNumberSprite = null;
			}
			System.gc();
		} catch (Exception exception) {}
	}

	private void loadBullet(int number) {
		// rese old screen
		clearPaintScreen();
		spritePosX = CommonConstants.CAMERA_WIDTH / 2 - (10);
		spritePosY = CommonConstants.CAMERA_HEIGHT / 2;
		scene.registerTouchArea(sligshotRectangle);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		try {
			this.numberBulletTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/numnber_bullet/" + number + ".png");
			this.numberBulletTextureRegion = null;
			this.numberBulletTextureRegion = TextureRegionFactory.extractFromTexture(this.numberBulletTexture);
			this.numberBulletTexture.load();
			try {
				numberBulletSprite.clearEntityModifiers();
				// numberBulletSprite.unregisterEntityModifier(rotationModifier);
				numberBulletSprite.unregisterEntityModifier(scaleAtModifier);
			} catch (Exception exception) {}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		this.numberBulletSprite = null;
		this.numberBulletSprite = new Sprite(CommonConstants.CAMERA_WIDTH / 2 - numberBulletTextureRegion.getWidth() / 2, leftCord.getY() - numberBulletTextureRegion.getHeight() / 2, numberBulletTextureRegion, context.getVertexBufferObjectManager());
		// adding number bullet body
		this.numberBulletBody = null;
		this.numberBulletBody = PhysicsFactory.createBoxBody(mPhysicsWorld, numberBulletSprite, BodyType.DynamicBody, equibFixtureDef);
		//
		this.numberBulletBody.setActive(false);
		this.physicsConnector = null;
		this.physicsConnector = new PhysicsConnector(numberBulletSprite, numberBulletBody, true, false);
		mPhysicsWorld.registerPhysicsConnector(physicsConnector);
		this.mPhysicsWorld.registerPhysicsConnector(physicsConnector);
		//
		sceneLayer4.attachChild(numberBulletSprite);
		this.numberBulletSprite.setScale(.8f);
		sligshotRectangle.setPosition(numberBulletSprite.getX() - sligshotRectangle.getWidth() / 4, numberBulletSprite.getY() - sligshotRectangle.getHeight() / 4);
		try {
			this.bagTexture = null;
			this.bagTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/counting_numbers_painting/Bag_number/" + number + ".png");
			this.bagTextureRegion = null;
			this.bagTextureRegion = TextureRegionFactory.extractFromTexture(this.bagTexture);
			this.bagTexture.load();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		this.bagSprite = null;
		this.bagSprite = new Sprite(birdAnimatedSprite.getX() + bagTextureRegion.getWidth() / 2 - 20, birdAnimatedSprite.getY() + bagTextureRegion.getHeight(), bagTextureRegion, context.getVertexBufferObjectManager());
		bagSprite.setScale(.6f);
		sceneLayer1.attachChild(bagSprite);
		baghysicsHandler = new PhysicsHandler(bagSprite);
		bagSprite.registerUpdateHandler(baghysicsHandler);
		bagSprite.setVisible(true);
		cloudSprite.setScale(0f);
		cloudSprite.setVisible(false);
		resetbirdPosition();
	}

	public void onPianoButtonActiondown() {
		isErase = false;
	}

	public void onPianoButtonActionUp(int position) {
		clickedPianoItemPosition = position;
		playCountingNumberSound(clickedPianoItemPosition);
		loadBullet(position);
	}

	private void resetbirdPosition() {
		birdAnimatedSprite.setVisible(true);
		birdAnimatedSprite.setPosition(CommonConstants.CAMERA_WIDTH, CommonConstants.birdRandomPosition[RandomNumberGenerator.genarateRandomNumber(CommonConstants.birdRandomPosition.length)]);
		birdAnimatedSprite.animate(100);
		blastAnimatedSprite.setPosition(birdAnimatedSprite.getX() - 120, birdAnimatedSprite.getY() - 80);
		int randomSpeed = RandomNumberGenerator.genarateRandomNumber(CommonConstants.birdRandomSpeed.length);
		randomSpeed = CommonConstants.birdRandomSpeed[randomSpeed];
		birdPhysicsHandler.setVelocityX(randomSpeed);
		blastPhysicsHandler.setVelocityX(randomSpeed);
		bagSprite.setPosition(birdAnimatedSprite.getX() + bagTextureRegion.getWidth() / 2 - 20, birdAnimatedSprite.getY() + bagTextureRegion.getHeight());
		baghysicsHandler.setVelocityX(randomSpeed);
	}

	public void unloadScene() {
		this.bgTexture = null;
		this.bgSkycutTexture = null;
		this.slingshotBgTexture = null;
		this.slingshotTexture = null;
		this.poleTexture = null;
		this.SlingShot = null;
		this.SlingShotTextureRegion = null;
		birdBitmapTextureAtlas = null;
		blastBitmapTextureAtlas = null;
		this.cloudITexture = null;
		this.rubberTexture = null;
		this.downBlackBgTextureAtlas = null;
		this.screen_shot_downBlackBgTextureAtlas = null;
		this.screen_shot_downBlackBgTextureRegions = null;
		this.particleTexture = null;
		this.particleTextureRegion = null;
		scene = null;
		sceneLayer0 = null;
		sceneLayer1 = null;
		sceneLayer2 = null;
		sceneLayer3 = null;
		sceneLayer4 = null;
		this.colorBitmapTexture = null;
		this.colorTextureRegion = null;
		bgSprite = null;
		bgSkyCutSprite = null;
		downBlackBgSprite = null;
		screen_shot_downBlackBgSprite = null;
		birdAnimatedSprite = null;
		birdPhysicsHandler = null;
		blastAnimatedSprite = null;
		blastPhysicsHandler = null;
		bgSlingShotSprite = null;
		cloudSprite = null;
		rubberSprite = null;
		leftCord = null;
		rightCord = null;
		sligshotRectangle = null;
		equibFixtureDef = null;
		mPhysicsWorld = null;
		colorPaintList.clear();
		colorPaintList = null;
		numberBulletSprite = null;
		numberBulletBody = null;
		mPhysicsWorld = null;
		System.gc();
	}

	// RotationModifier rotationModifier;
	ScaleAtModifier scaleAtModifier, CloudScaleAtModifier;

	private void shootBird(float vx, float vy) {
		scaleAtModifier = new ScaleAtModifier(1f /* Durarion */, .7f/* from */, 1f/* to */, .7f, 1f, numberBulletSprite.getWidth() / 2/*
																																	 * scale
																																	 * center
																																	 * X
																																	 */, numberBulletSprite.getHeight() / 2 /*
																																											 * scale
																																											 * center
																																											 * Y
																																											 */);
		numberBulletSprite.registerEntityModifier(scaleAtModifier);
		numberBulletBody.setActive(true);
		numberBulletBody.setLinearVelocity(vx / 11, vy / 11);
	}

	public Scene getScene() {
		return scene;
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

	public void setScenePosition() {
		if (SharedPrefUtils.getIsPurchased(context)) {
			makeAdsFreeScene();
		} else {
			makeSceneWithAds();
		}
	}

	private void makeSceneWithAds() {
		if (CommonUtils.isTablet(context)) {
			bgSlingShotSprite.setPosition(0, CommonConstants.CAMERA_HEIGHT / 2 - bgSlingShotSprite.getHeight() / 6);
			leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20);
			rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 20, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 20);
		} else {
			bgSlingShotSprite.setPosition(0, CommonConstants.CAMERA_HEIGHT / 2 - bgSlingShotSprite.getHeight() / 4);
			leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2);
			rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2);
		}
	}

	private void makeAdsFreeScene() {
		if (CommonUtils.isTablet(context)) {
			bgSlingShotSprite.setPosition(0, 385);
			leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45);
			rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 45, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 45);
		} else {
			bgSlingShotSprite.setPosition(0, 390);
			leftCord.setPosition(slingShot.getWidth() / 2 - 150, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 50);
			rightCord.setPosition(slingShot.getWidth() / 2, spritePosY + slingShot.getHeight() / 2 + 50, slingShot.getWidth() / 2 + 150, spritePosY + slingShot.getHeight() / 2 + 50);
		}
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
		return countingNumbers[clickedPianoItemPosition];
	}

	public void playCountingNumberSound(int position) {
		try {
			countingNumberSoundData[position].play();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void loadCountingNumberSoundsInAdvance() {
		countingNumberSoundData = new Sound[10];
		for (int i = 0; i < countingNumberSoundData.length; i++) {
			try {
				countingNumberSoundData[i] = SoundFactory.createSoundFromAsset(context.getSoundManager(), context, "sfx/counting_numbers_painting_scene/" + countingNumbers[i] + ".mp3");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			blastSound = SoundFactory.createSoundFromAsset(context.getSoundManager(), context, "sfx/counting_numbers_painting_scene/" + "blasts.mp3");
			blastSound.setVolume(.5f);
		} catch (Exception e) {}
		try {
			releaseSligShotSound = SoundFactory.createSoundFromAsset(context.getSoundManager(), context, "sfx/counting_numbers_painting_scene/" + "release.mp3");
			releaseSligShotSound.setVolume(.5f);
		} catch (Exception e) {}
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
		if (SharedPrefUtils.getCountingScreenSoundSettings(context)) {
			try {
				if (backgroundMusic != null) {
					backgroundMusic.stop();
					backgroundMusic = null;
				}
				backgroundMusic = MusicFactory.createMusicFromAsset(context.getMusicManager(), context, "sfx/counting_numbers_painting_scene/" + "counting_num,ber_bg_music.mp3");
				backgroundMusic.play();
				backgroundMusic.setVolume(.8f);
				backgroundMusic.setLooping(true);
			} catch (Exception e) {
			}
		}
	}
}
