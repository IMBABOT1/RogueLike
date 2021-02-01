package com.rogue.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Monster extends BaseUnit {
    public Monster(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 120.0f, 1.0f, 35, x, y, 100, 100);
    }

    @Override
    public void update(float dt) {
        fire(dt, false);
        if (Math.abs(gameScreen.getHero().getCenterX() - getCenterX()) > 100.0f) {
            if (gameScreen.getHero().getCenterX() < getCenterX()) {
                moveLeft();
            }
            if (gameScreen.getHero().getCenterX() > getCenterX()) {
                moveRight();
            }
        }
        super.update(dt);
        if (Math.abs(gameScreen.getHero().getCenterX() - getCenterX()) > 100.0f) {
            if (Math.abs(velocity.x) < 0.1f) {
                jump();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.RED);
        super.render(batch);
        batch.setColor(Color.WHITE);
    }
}
