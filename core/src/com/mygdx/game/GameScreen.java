package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Map map;
    private Hero hero;
    private BitmapFont font;
    private Trash[] trashes;
    private PowerUpsEmitter powerUpsEmitter;
    private int counter;
    private Monster monster;


    public GameScreen(SpriteBatch batch){
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas();
        atlas = new TextureAtlas(Gdx.files.internal("123.pack"));
        map = new Map(atlas.findRegion("star16"), atlas.findRegion("ground"));
        map.generateMap();
        hero = new Hero(map, atlas.findRegion("runner"), 300, 300);
        monster = new Monster(map, atlas.findRegion("runner"), 600, 600, hero);
        generateFonts();
        TextureRegion asteroidTexture = atlas.findRegion("asteroid64");
        trashes = new Trash[30];
        for (int i = 0; i < trashes.length ; i++) {
            trashes[i] = new Trash(asteroidTexture);
            trashes[i].prepare();
        }
        powerUpsEmitter = new PowerUpsEmitter(atlas.findRegion("money"));
    }

    public void generateFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameters.size = 24;
        parameters.color = Color.WHITE;
        parameters.borderWidth = 2;
        parameters.borderColor = Color.BLACK;
        parameters.shadowOffsetX = 2;
        parameters.shadowOffsetY = 2;
        parameters.shadowColor = Color.BLACK;
        font = generator.generateFont(parameters);
        generator.dispose();
    }


    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.9f, 0.9f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        map.render(batch);
        for (int i = 0; i <trashes.length ; i++) {
            trashes[i].render(batch);
        }
        hero.render(batch);
        monster.render(batch);
        powerUpsEmitter.render(batch);
        hero.renderGUI(batch, font);;
        batch.end();

    }

    public void update(float dt) {
        counter++;
        if (counter % 50 == 0){
            powerUpsEmitter.tryToCreatePowerUP(MathUtils.random(0, 1280), MathUtils.random(200, 250), 1);
        }
        map.update(dt);
        hero.update(dt);
        monster.update(dt);
        powerUpsEmitter.update(dt);
        for (int i = 0; i < trashes.length; i++) {
            trashes[i].update(dt);
            if (hero.getHitArea().overlaps(trashes[i].getHitArea())) {
                trashes[i].prepare();
                hero.takeDamage(5);
            }
        }
        for (int i = 0; i < powerUpsEmitter.getPowerUps().length ; i++) {
            PowerUp p = powerUpsEmitter.getPowerUps()[i];
            if (p.isActive() && hero.getHitArea().contains(p.getPosition())){
                p.use(hero);
                p.deactivate();
            }
        }

    }

    @Override
    public void resize(int width, int height) {

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
        atlas.dispose();
    }
}
