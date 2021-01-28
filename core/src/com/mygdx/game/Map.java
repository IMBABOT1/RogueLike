package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Map{
    private static final char SYMB_GRASS = 'g';
    private char[][] map;
    private Texture groundTexture;


    public Map(){
        groundTexture = new Texture("ground.png");
        map = new char[32][18];
    }


    public void fillGroundPart(int x1, int x2, int height){
        if (x2 > 31) x2 = 31;
        for (int i = x1; i <=x2 ; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = SYMB_GRASS;
            }
        }
    }

    public void generateMap(){
        int height = 4;
        int position = 0;
        fillGroundPart(0, 3, height);
        position = 4;
        while (position < 32){
            int len = MathUtils.random(3, 6);
            height += MathUtils.random(-2, 2);
            if (height < 1) height = 1;
            if (height > 5) height = 5;
            fillGroundPart(position, position + len-1, height);
            position +=  len;
        }
    }

    public void render(SpriteBatch batch){
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 18; j++) {
                if (map[i][j] == SYMB_GRASS){
                    batch.draw(groundTexture, i * 40, j * 40);
                }
            }
        }
    }

    public void update(float dt){

    }
}
