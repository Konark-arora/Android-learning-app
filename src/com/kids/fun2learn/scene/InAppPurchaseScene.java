package com.kids.fun2learn.scene;

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
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.kids.fun2learn.AppsPartanBaseGameActivity;
import com.kids.fun2learn.SceneManager;
import com.kids.fun2learn.utils.CommonConstants;

public class InAppPurchaseScene {

	private AppsPartanBaseGameActivity context;
	private Scene scene;

	
	private ITexture inAppBgTexture;
	private ITexture inAppBodyBgTexture;

	private ITextureRegion inAppBgTextureRegion;
	private ITextureRegion inAppBodyBgTextureRegion;

	private TiledTextureRegion buyBtnTiledTextureRegion;
	private TiledTextureRegion restoreBtnTiledTextureRegion;
	private TiledTextureRegion closeInAppTiledTextureRegion;
	private TiledTextureRegion exitGameTiledTextureRegion;
	
	

	// for animated sprite
	private BitmapTextureAtlas buyBtnBitmapTextureAtlas;
	private BitmapTextureAtlas restoreBtnBitmapTextureAtlas;
	private BitmapTextureAtlas closeInAppBitmapTextureAtlass;
	private BitmapTextureAtlas exitGameBitmapTextureAtlass;
	

	private Sprite inAppBgSprite;
	private Sprite inAppBdyBgSprite;

	private Sprite fun2LearnSprite;

	private AnimatedSprite play_alphabet_BtnPaintSprite;
	private AnimatedSprite play_counting_BtnPaintSprite;
	private AnimatedSprite nextBtn;
	private AnimatedSprite prevBtn;
	private AnimatedSprite closeInAppAnimatedSprite;

	private AnimatedSprite doorAnimatedSprite;
	private AnimatedSprite buyBtnAnimatedSprite;
	private AnimatedSprite restoreBtnAnimatedSprite;
	private AnimatedSprite exitGameBtnAnimatedSprite;

	public Rectangle charac;

	public InAppPurchaseScene(Context context) {

		this.context = (AppsPartanBaseGameActivity) context;
		loadResources();
		loadScene();

	}

	private void loadResources() {

		try {

			this.inAppBgTexture = new AssetBitmapTexture(
					context.getTextureManager(), context.getAssets(),
					"gfx/in_app_purchase/transparentsheet.png");
			this.inAppBgTextureRegion = TextureRegionFactory
					.extractFromTexture(this.inAppBgTexture);
			this.inAppBgTexture.load();

			this.inAppBodyBgTexture = new AssetBitmapTexture(
					context.getTextureManager(), context.getAssets(),
					"gfx/in_app_purchase/in-app-purchase_dollar.png");
			this.inAppBodyBgTextureRegion = TextureRegionFactory
					.extractFromTexture(this.inAppBodyBgTexture);
			this.inAppBodyBgTexture.load();

			this.buyBtnBitmapTextureAtlas = new BitmapTextureAtlas(
					context.getTextureManager(), 256, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.buyBtnTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(buyBtnBitmapTextureAtlas,
							context.getAssets(),
							"gfx/in_app_purchase/remove_sprite.png", 0, 0, 2, 1);
			buyBtnBitmapTextureAtlas.load();

			this.restoreBtnBitmapTextureAtlas = new BitmapTextureAtlas(
					context.getTextureManager(), 256, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.restoreBtnTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(restoreBtnBitmapTextureAtlas,
							context.getAssets(),
							"gfx/in_app_purchase/restore_sprite.png", 0, 0, 2,
							1);
			restoreBtnBitmapTextureAtlas.load();
			
			
			
			this.closeInAppBitmapTextureAtlass = new BitmapTextureAtlas(
					context.getTextureManager(), 256, 128,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.closeInAppTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(closeInAppBitmapTextureAtlass,
							context.getAssets(),
							"gfx/in_app_purchase/close_sprite.png", 0, 0, 2, 1);
			closeInAppBitmapTextureAtlass.load();

			
			this.exitGameBitmapTextureAtlass = new BitmapTextureAtlas(
					context.getTextureManager(), 512, 64,
					TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			this.exitGameTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(exitGameBitmapTextureAtlass,
							context.getAssets(),
							"gfx/in_app_purchase/exit-game.png", 0, 0, 2, 1);
			exitGameBitmapTextureAtlass.load();
			
			

		} catch (Exception exception) {

		}

	}

	private void loadScene() {

		scene = new Scene();

		scene.setBackgroundEnabled(false);
		
		inAppBgSprite = new Sprite(0, 0, inAppBgTextureRegion,
				context.getVertexBufferObjectManager());
		scene.attachChild(inAppBgSprite);

		
		inAppBdyBgSprite = new Sprite(CommonConstants.CAMERA_WIDTH/2 - inAppBodyBgTextureRegion.getWidth()/2, CommonConstants.CAMERA_HEIGHT/2 - inAppBodyBgTextureRegion.getHeight(), inAppBodyBgTextureRegion,context.getVertexBufferObjectManager());
		
		inAppBdyBgSprite.setScale(.8f);

		
		closeInAppAnimatedSprite = new AnimatedSprite(inAppBdyBgSprite.getX()+closeInAppTiledTextureRegion.getWidth()/2 , inAppBdyBgSprite.getY()-closeInAppTiledTextureRegion.getHeight()/3+4,
				closeInAppTiledTextureRegion.deepCopy(),
				context.getVertexBufferObjectManager()){
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					this.setCurrentTileIndex(1);

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					this.setCurrentTileIndex(0);
					
					
					context.getMainMenuScene().getScene().clearChildScene();
					
					context.onShowMainMenuScene();
					
					
					
//				        this.mEngine.start();
				        
				        
//					CommonUtils.showToast(context, "clicked...");
//					try{
//					context.getEngine().setScene(context.getMainMenuScene().getScene());
//					}catch(Exception exception){)
//					SceneManager.setScene(context.getMainMenuScene().getScene());
//					}catch(Exception exception){
//						
//						exception.printStackTrace();
//					}

				}
				return true;
			}
		};

		
		scene.attachChild(closeInAppAnimatedSprite);
		scene.attachChild(inAppBdyBgSprite);
		
			
		
		buyBtnAnimatedSprite = new AnimatedSprite(inAppBdyBgSprite.getX()+inAppBdyBgSprite.getWidth()/2 -buyBtnTiledTextureRegion.getWidth()+20 , inAppBdyBgSprite.getY()+inAppBdyBgSprite.getHeight()-buyBtnTiledTextureRegion.getHeight()/2,
				buyBtnTiledTextureRegion.deepCopy(),
				context.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					this.setCurrentTileIndex(1);

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					this.setCurrentTileIndex(0);

					context.startInAppBillibg();
				}
				return true;
			}
		};

		scene.attachChild(buyBtnAnimatedSprite);
		buyBtnAnimatedSprite.setScale(.8f);

		restoreBtnAnimatedSprite = new AnimatedSprite(inAppBdyBgSprite.getX()+inAppBdyBgSprite.getWidth()/2 +restoreBtnTiledTextureRegion.getWidth()/2 -40 , inAppBdyBgSprite.getY()+inAppBdyBgSprite.getHeight()-buyBtnTiledTextureRegion.getHeight()/2,
				restoreBtnTiledTextureRegion.deepCopy(),
				context.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					this.setCurrentTileIndex(1);

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					this.setCurrentTileIndex(0);

					context.startRestoringTransaction();

				}
				return true;
			}
		};

		
		exitGameBtnAnimatedSprite = new AnimatedSprite(CommonConstants.CAMERA_WIDTH/2 -exitGameTiledTextureRegion.getWidth()/2 , buyBtnAnimatedSprite.getY()+buyBtnAnimatedSprite.getHeight(),
				exitGameTiledTextureRegion.deepCopy(),
				context.getVertexBufferObjectManager()){
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {

					this.setCurrentTileIndex(1);

				} else if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {

					this.setCurrentTileIndex(0);
					
					context.finish();

				}
				return true;
			}
		};

		
		scene.attachChild(exitGameBtnAnimatedSprite);
		
		
		restoreBtnAnimatedSprite.setScale(.8f);
		scene.attachChild(restoreBtnAnimatedSprite);
		scene.registerTouchArea(buyBtnAnimatedSprite);
		scene.registerTouchArea(restoreBtnAnimatedSprite);
		scene.registerTouchArea(closeInAppAnimatedSprite);
		scene.registerTouchArea(exitGameBtnAnimatedSprite);

	}

	private void unloadScene() {

		this.inAppBgTexture = null;
		this.inAppBodyBgTexture = null;
		this.buyBtnBitmapTextureAtlas = null;
		this.restoreBtnBitmapTextureAtlas = null;
		this.scene = null;
		this.inAppBgSprite = null;
		this.buyBtnAnimatedSprite = null;
		this.restoreBtnAnimatedSprite = null;

		System.gc();

	}

	public Scene getScene() {

		return scene;
	}

}
