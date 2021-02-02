package com.game.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Map {
    private class Snow {
        private Vector2 position;
        private Vector2 velocity;

        public Snow() {
            this.position = new Vector2(0, 0);
            this.velocity = new Vector2(0, 0);
        }

        public void recreate() {
            this.position.set(MathUtils.random(0, 1280), MathUtils.random(720, 1440));
            this.velocity.set(MathUtils.random(-50, 50), MathUtils.random(-300, -100));
        }

        public void update(float dt) {
            position.mulAdd(velocity, dt);
            if (position.x > 1280) {
                position.x = 0;
            }
            if (position.x < 0) {
                position.x = 1280;
            }
            if (position.y < 0) {
                recreate();
            }
        }
    }

    private static final char SYMB_GRASS = 'g';

    private static final boolean SNOW_ENABLED = false;
    private static final int SNOW_FLAKES_COUNT = 100;

    private TextureRegion textureSnow;
    private TextureRegion groundTexture;
    private char[][] data;
    private Snow[] snow;
    private GameScreen gameScreen;

    public Map(TextureRegion textureSnow, TextureRegion groundTexture) {
        this.groundTexture = groundTexture;
        this.textureSnow = textureSnow;
        data = new char[1000][18];
        snow = new Snow[SNOW_FLAKES_COUNT];
        for (int i = 0; i < snow.length; i++) {
            snow[i] = new Snow();
            snow[i].recreate();
        }
        if(!SNOW_ENABLED) {
            snow = new Snow[0];
        }
    }

    public void fillGroundPart(int x1, int x2, int height) {
        if (x2 > 999) x2 = 999;
        for (int i = x1; i <= x2; i++) {
            for (int j = 0; j < height; j++) {
                data[i][j] = SYMB_GRASS;
            }
        }
    }

    public void generateMap() {
        int height = 4;
        int position = 0;
        fillGroundPart(0, 3, height);
        position = 4;
        while (position < 32) {
            int len = MathUtils.random(3, 6);
            height += MathUtils.random(-3, 3);
            if (height < 1) height = 1;
            if (height > 6) height = 6;
            fillGroundPart(position, position + len - 1, height);
            position += len;
        }
        while (position >= 32){
            int len = 1;
            height = 1;
            fillGroundPart(position, position + len -1, 1);{
                position += len;
            }
        }
        data[5][7] = SYMB_GRASS;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < 999; i++) {
            for (int j = 0; j < 18; j++) {
                if (data[i][j] == SYMB_GRASS) {
                    batch.draw(groundTexture, i * 40, j * 40);
                }
            }
        }
        for (int i = 0; i < snow.length; i++) {
            batch.draw(textureSnow, snow[i].position.x - 8, snow[i].position.y - 8);
            batch.draw(textureSnow, snow[i].position.x - 8, snow[i].position.y - 8);
        }
    }

    private boolean isCellEmpty(int cellX, int cellY) {
        if (data[cellX][cellY] == SYMB_GRASS) {
            return false;
        }
        return true;
    }

    public boolean checkSpaceIsEmpty(float x, float y) {
        if (x < 0 || x > 100000) return false;
        int cellX = (int) (x / 40);
        int cellY = (int) (y / 40);
        return isCellEmpty(cellX, cellY);
    }

    public void update(float dt) {
        for (int i = 0; i < snow.length; i++) {
            snow[i].update(dt);
        }
    }
}
