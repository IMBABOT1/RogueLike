package com.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameScreen  implements Screen {
    private SpriteBatch batch;

    public Map getMap() {
        return map;
    }

    private Map map;
    private Hero hero;
    private BitmapFont font;
    private Trash[] trashes;
    private PowerUpsEmitter powerUpsEmitter;
    private BulletEmitter bulletEmitter;
    private Sound soundTakeMoney;
    private Music mainTheme;
    private ShapeRenderer shapeRenderer;
    private MonsterEmitter monsterEmitter;
    private final static boolean DEBUG_MODE = true;
    private Camera camera;
    private Camera screenCamera;
    private TrashEmitter trashEmitter;
    private Stage stage;
    private Skin skin;
    private BitmapFont font24;



    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public Hero getHero() {
        return hero;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    @Override
    public void show() {
        font24 = Assets.getInstance().getAssetManager().get("zorque24.ttf", BitmapFont.class);
        createGameGUI();

        Gdx.input.setInputProcessor(stage);
        TextureAtlas atlas = Assets.getInstance().getAtlas();
        camera = new OrthographicCamera(ScreenManager.VIEW_WIDTH, ScreenManager.VIEW_HEIGHT);
        screenCamera = new OrthographicCamera(ScreenManager.VIEW_WIDTH, ScreenManager.VIEW_HEIGHT);
        screenCamera.position.set(ScreenManager.VIEW_WIDTH / 2, ScreenManager.VIEW_HEIGHT / 2, 0);
        screenCamera.update();
        map = new Map(atlas.findRegion("star16"), atlas.findRegion("ground"), 320);
        hero = new Hero(this, map, atlas.findRegion("runner"), 300, 300);
        map.generateMap();
        monsterEmitter = new MonsterEmitter(this, atlas.findRegion("runner"), 20, 10);
        for (int i = 0; i < 15; i++) {
            monsterEmitter.createMonster(MathUtils.random(0, map.getEndOfWorldX()), 500);
        }
        trashEmitter = new TrashEmitter(this, atlas.findRegion("asteroid64"), 20);
        trashes = new Trash[20];
        TextureRegion asteroidTexture = atlas.findRegion("asteroid64");
        for (int i = 0; i < trashes.length; i++) {
            trashes[i] = new Trash(asteroidTexture);
            trashes[i].prepare(i);
        }
        // mainTheme = Gdx.audio.newMusic(Gdx.files.internal("Jumping bat.wav"));
        // mainTheme.setLooping(true);
        // mainTheme.play();
//        soundTakeMoney = Gdx.audio.newSound(Gdx.files.internal("takeMoney.wav"));
        powerUpsEmitter = new PowerUpsEmitter(atlas.findRegion("money"));
        bulletEmitter = new BulletEmitter(atlas.findRegion("bullet48"), 0);
        if (DEBUG_MODE) {
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setAutoShapeType(true);
        }
        font = Assets.getInstance().getAssetManager().get("zorque24.ttf", BitmapFont.class);
    }

    public void createGameGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());
        skin.add("font24", font24);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.font = font24;
        skin.add("simpleBtn", textButtonStyle);




    }






    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.render(batch);
        hero.render(batch);
        monsterEmitter.render(batch);
        for (int i = 0; i < trashes.length; i++) {
            trashes[i].render(batch);
        }
        powerUpsEmitter.render(batch);
        bulletEmitter.render(batch);
        trashEmitter.render(batch);
        batch.setProjectionMatrix(screenCamera.combined);
        hero.renderGUI(batch, font);
        batch.end();
        stage.draw();
        if (DEBUG_MODE) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin();
            shapeRenderer.rect(hero.getHitArea().x, hero.getHitArea().y, hero.getHitArea().width, hero.getHitArea().height);
            shapeRenderer.end();
        }
    }

    public void update(final float dt) {
        map.update(dt);
        hero.update(dt);
        updateHeroCamera();
        camera.update();
        monsterEmitter.update(dt);
        bulletEmitter.update(dt);
        powerUpsEmitter.update(dt);
        trashEmitter.update(dt);
        checkCollisions();
        stage.act(dt);
        bulletEmitter.checkPool();

        Button btnLeft = new TextButton("Left", skin, "simpleBtn");
        Button btnRight = new TextButton("Right", skin, "simpleBtn");
        Button btnFire = new TextButton("Fire", skin, "simpleBtn");
        Button bunJump = new TextButton("Jump", skin, "simpleBtn");


        btnLeft.setPosition(50, 300);
        btnRight.setPosition(1100, 300);
        btnFire.setPosition(1100, 200);
        bunJump.setPosition(1100, 400);


        stage.addActor(btnLeft);
        stage.addActor(btnRight);
        stage.addActor(btnFire);
        stage.addActor(bunJump);

        btnLeft.addListener(new InputListener(){
            public boolean handle (Event e){
                hero.moveLeft();
                return true;
            }
        });
        btnRight.addListener(new InputListener(){
            public boolean handle (Event e){
                hero.moveRight();
                return true;
            }
        });
        bunJump.addListener(new InputListener(){
            public boolean handle (Event e){
                hero.jump();
                return true;
            }
        });
        btnFire.addListener(new InputListener(){
            public boolean handle(Event e){
                hero.fire(dt, true);
                return true;
            }
        });
    }

    private void checkCollisions(){
        for (int i = 0; i < trashEmitter.getTrash().length; i++) {
            if (hero.getHitArea().overlaps(trashEmitter.getTrash()[i].getHitArea())) {
                trashEmitter.recreateTrash(i);
                hero.takeDamage(5);
            }
        }
        for (int i = 0; i < powerUpsEmitter.getPowerUps().length; i++) {
            PowerUp p = powerUpsEmitter.getPowerUps()[i];
            if (p.isActive() && hero.getHitArea().contains(p.getPosition())) {
                p.use(hero);
                p.deactivate();
//                soundTakeMoney.play();
            }
        }
        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            Bullet b = bulletEmitter.getActiveList().get(i);
            if (!map.checkSpaceIsEmpty(b.getPosition().x, b.getPosition().y)) {
                b.deactivate();
                continue;
            }
            if (b.isPlayersBullet()){
                for (int j = 0; j <monsterEmitter.getMonsters().length ; j++) {
                    Monster m = monsterEmitter.getMonsters()[j];
                    if (m.isActive()){
                        if (m.getHitArea().contains(b.getPosition())){
                            b.deactivate();
                            if (m.takeDamage(25)) {
                                powerUpsEmitter.tryToCreatePowerUp(m.getCenterX(), m.getCenterY(), 0.5f);
                                hero.addScore(100);
                            }
                            break;
                        }

                    }
                }
            }
            if (!b.isPlayersBullet()){
                if (hero.getHitArea().contains(b.getPosition())){
                    b.deactivate();
                    hero.takeDamage(10);
                    break;
                }
            }
        }
    }

    public void updateHeroCamera(){
        camera.position.set(hero.getCenterX(), hero.getCenterY(), 0);
        if (camera.position.y < ScreenManager.VIEW_HEIGHT / 2){
            camera.position.y = ScreenManager.VIEW_HEIGHT / 2;
        }
        if (camera.position.x < ScreenManager.VIEW_WIDTH / 2){
            camera.position.x = ScreenManager.VIEW_WIDTH / 2;
        }
        if (camera.position.x > map.getEndOfWorldX() - ScreenManager.VIEW_WIDTH / 2){
            camera.position.x = map.getEndOfWorldX() - ScreenManager.VIEW_WIDTH / 2;
        }
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mainTheme.dispose();
        if (DEBUG_MODE) {
            shapeRenderer.dispose();
        }
    }
}
