package com.kids.fun2learn;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Context;

public class SceneManager {

	private static BaseGameActivity core;

	public static void init(Context base) {
		core = (BaseGameActivity) (base);
		// TextureRegionFactory.setAssetBasePath("gfx/");
	}

	/**
	 * setScene() is the function we'll be using to switch from one screen to
	 * another.
	 */
	public static void setScene(Scene scene) {
		core.getEngine().setScene(scene);
	}

	public static Scene getScene() {
		return core.getEngine().getScene();
	}
	
	public static void loadTexture(Texture texture) {
		core.getEngine().getTextureManager().loadTexture(texture);
	}

	public static void loadFont(Font font) {
		core.getEngine().getFontManager().loadFont(font);
	}

}
