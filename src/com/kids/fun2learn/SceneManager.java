package com.kids.fun2learn;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.Texture;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;

public class SceneManager {
	private static SimpleBaseGameActivity core;

	public static void init(Context base) {
		core = (SimpleBaseGameActivity) (base);
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
