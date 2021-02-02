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
    private int lifes;
    private int score;

    public void addCoins(int amount) {
        coins += amount;
    }

    public Hero(GameScreen gameScreen, Map map, TextureRegion original, float x, float y) {
        super(gameScreen, map, original, 100, 360.0f, 0.4f, x, y, 100, 100);
        this.coins = 0;
        this.lifes = 5;
        this.score = 0;
    }

    public void addScore(int amount){
        score += amount;
    }

    @Override
    public void destroy() {
        lifes--;
        hp = maxHp;
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
        font.draw(batch, "Score " + score +  "\nHP: " + hp + " / " + maxHp + " x" + lifes + "\nCoins: " + coins, 20, 700);
    }
}
