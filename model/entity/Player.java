package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Textures;

import java.util.ArrayList;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Entity {

    public Player(){
        health = 3;
        color = GameVariables.PLAYER_SKIN_MOUNTAINS;
    }

    @Override
    public void update() {

        if(velocity.x>0){
            setOrientation(Orientation.RIGHT);
        } else if(velocity.x<0) {
            setOrientation(Orientation.LEFT);
        } else {
            setOrientation(Orientation.STILL);
        }

        position.x += velocity.x;
        position.y += velocity.y;

        updateGameVariables();
    }

    void updateGameVariables(){

        GameVariables.PLAYER_POSITION.x = getX();
        GameVariables.PLAYER_POSITION.y = getY();

        GameVariables.PLAYER_HEALTH = health;
    }
}
