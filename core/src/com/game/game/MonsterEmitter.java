package com.game.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MonsterEmitter {
    private GameScreen gameScreen;
    private Monster[] monsters;
    private TextureRegion[] textureRegions;
    private float factoryCurrentTimer;
    private float factoryRate;


    public Monster[] getMonsters() {
        return monsters;
    }



    public MonsterEmitter(GameScreen gameScreen, TextureRegion texture, int poolSize, float factoryRate) {
        this.gameScreen = gameScreen;
        this.monsters = new Monster[poolSize];
        this.factoryRate = factoryRate;
        for (int i = 0; i < monsters.length; i++) {
            this.monsters[i] = new Monster(gameScreen, gameScreen.getMap(), texture, 0, 0);
        }
    }

    public void createMonster(int x, int y) {
        for (int i = 0; i < monsters.length; i++) {
            if (!monsters[i].isActive()) {
                monsters[i].activate(x, y);
                break;
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i].isActive()) {
                monsters[i].render(batch);
            }
        }
    }

    public void update(float dt) {
        factoryCurrentTimer += dt;
        if (factoryCurrentTimer > factoryRate){
            if (factoryCurrentTimer > factoryRate){
                factoryCurrentTimer -= factoryRate;
                createMonster(MathUtils.random(0, gameScreen.getMap().getEndOfWorldX()), 500);
            }
        }
        for (int i = 0; i < monsters.length; i++) {
            if (monsters[i].isActive()) {
                monsters[i].update(dt);
            }
        }
    }
}

