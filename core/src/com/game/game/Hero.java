package com.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hero extends BaseUnit {
    private int coins;

    public void addCoins(int amount) {
        coins += amount;
    }

    public Hero(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 360.0f, 0.4f, 35, x, y, 100, 100);
        this.coins = 0;
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            jump();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            fire(dt, true);
        }
        super.update(dt);
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "HP: " + hp + " / " + maxHp + "\nCoins: " + coins, 20, 700);
    }
}
