package com.jiedro.canels.model.entity;

import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Living {

    public Player(){
        name = "player";
        health = 3;
        color = GameVariables.PLAYER_SKIN_MOUNTAINS;
    }

    @Override
    public void update(World world) {
        elapsedTime += world.deltaTime;

        if(velocity.x>0){
            setOrientation(Orientation.RIGHT);
        } else if(velocity.x<0) {
            setOrientation(Orientation.LEFT);
        } else {
            setOrientation(Orientation.STILL);
        }

        position.add(velocity);

        updateGameVariables();
    }

    @Override
    public void hit(Living e) {
        e.health--;
    }

    void updateGameVariables(){

        GameVariables.PLAYER_POSITION.x = getX();
        GameVariables.PLAYER_POSITION.y = getY();

        GameVariables.PLAYER_HEALTH = health;
    }
}
