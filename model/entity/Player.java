package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.Tiles;

/**
 * Created by jorge on 23/05/17.
 */

public class Player extends Entity {
    private int x = 0;
    private int y = 0;

    public Player(){
        super(Tiles.getPlayerTexture());
    }

    public void move(Direction direction){
        switch (direction){
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
    }

    public int getMapX() {
        return x;
    }

    public int getMapY() {
        return y;
    }
}
