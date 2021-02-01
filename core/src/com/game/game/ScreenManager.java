package com.rogue.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by FlameXander on 09.01.2018.
 */

public class ScreenManager {
    public enum ScreenType {
        MENU, GAME;
    }

    private RpgGame rpgGame;
    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    public Viewport getViewport() {
        return viewport;
    }

    public void init(RpgGame rpgGame, SpriteBatch batch) {
        this.rpgGame = rpgGame;
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen(batch);
        this.viewport = new FitViewport(1280, 720);
        this.viewport.apply();
    }

    private static final ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    private ScreenManager() {
    }

    public void onResize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply();
    }

    public void switchScreen(ScreenType type) {
        Screen currentScreen = rpgGame.getScreen();
        Assets.getInstance().clear();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        switch (type) {
            case MENU:
                Assets.getInstance().loadAssets(ScreenType.MENU);
                rpgGame.setScreen(menuScreen);
                break;
            case GAME:
                Assets.getInstance().loadAssets(ScreenType.GAME);
                rpgGame.setScreen(gameScreen);
                break;
        }
    }

    public void dispose() {
        Assets.getInstance().dispose();
        rpgGame.getScreen().dispose();
    }
}
