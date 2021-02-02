package com.game.game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class ScreenManager {
    public enum ScreenType {
        MENU, GAME;
    }

    private RpgGame rpgGame;
    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private LoadingScreen loadingScreen;
    private Screen targetScreen;

    public static final int VIEW_WIDTH = 1280;
    public static final int VIEW_HEIGHT = 720;

    public Viewport getViewport() {
        return viewport;
    }

    public void init(RpgGame rpgGame, SpriteBatch batch) {
        this.rpgGame = rpgGame;
        this.gameScreen = new GameScreen(batch);
        this.menuScreen = new MenuScreen(batch);
        this.loadingScreen = new LoadingScreen(batch);
        this.viewport = new FitViewport(VIEW_WIDTH, VIEW_HEIGHT);
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
                rpgGame.setScreen(loadingScreen);
                targetScreen = menuScreen;
                Assets.getInstance().loadAssets(ScreenType.MENU);
                break;
            case GAME:
                rpgGame.setScreen(loadingScreen);
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
        }
    }

    public void goToTarget() {
        rpgGame.setScreen(targetScreen);
    }

    public void dispose() {
        Assets.getInstance().dispose();
        rpgGame.getScreen().dispose();
    }
}
