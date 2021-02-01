package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Created by FlameXander on 09.01.2018.
 */

public class Assets {
    private static final Assets ourInstance = new Assets();

    private AssetManager assetManager;
    private TextureAtlas atlas;

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public static Assets getInstance() {
        return ourInstance;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private Assets() {
        assetManager = new AssetManager();
    }

    public void loadAssets(ScreenManager.ScreenType type) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        switch (type) {
            case MENU:
                FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter48 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
                fontParameter48.fontFileName = "zorque.ttf";
                fontParameter48.fontParameters.size = 48;
                fontParameter48.fontParameters.color = Color.WHITE;
                fontParameter48.fontParameters.borderWidth = 1;
                fontParameter48.fontParameters.borderColor = Color.BLACK;
                fontParameter48.fontParameters.shadowOffsetX = 1;
                fontParameter48.fontParameters.shadowOffsetY = 1;
                fontParameter48.fontParameters.shadowColor = Color.BLACK;
                assetManager.load("zorque48.ttf", BitmapFont.class, fontParameter48);
                assetManager.load("background.png", Texture.class);
                assetManager.load("mainPack.pack", TextureAtlas.class);
//                assetManager.load("menuBtn.png", Texture.class);
                break;
            case GAME:
                FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter24 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
                fontParameter24.fontFileName = "zorque.ttf";
                fontParameter24.fontParameters.size = 24;
                fontParameter24.fontParameters.color = Color.WHITE;
                fontParameter24.fontParameters.borderWidth = 1;
                fontParameter24.fontParameters.borderColor = Color.BLACK;
                fontParameter24.fontParameters.shadowOffsetX = 3;
                fontParameter24.fontParameters.shadowOffsetY = 3;
                fontParameter24.fontParameters.shadowColor = Color.BLACK;
                assetManager.load("zorque24.ttf", BitmapFont.class, fontParameter24);
                assetManager.load("mainPack.pack", TextureAtlas.class);
                break;
        }
        assetManager.finishLoading();
        atlas = assetManager.get("mainPack.pack", TextureAtlas.class);
    }

    public void clear() {
        assetManager.clear();
    }

    public void dispose() {
        assetManager.dispose();
    }
}
