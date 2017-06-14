package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.jiedro.canels.GameVariables;

/**
 * Created by jorge on 23/05/17.
 */

public class Player extends Entity {
    private int x = 0;
    private int y = 0;

    public Player(){
        super(new Texture(GameVariables.PLAYER_BASE));
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
