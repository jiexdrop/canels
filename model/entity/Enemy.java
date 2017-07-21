package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

/**
 *
 * Created by jiexdrop on 04/07/17.
 */

public class Enemy extends Living {

    public Enemy(float x, float y) {
        health = 3;
        color = new Color(GameVariables.SLIME_COLOR);

        position.set(x,y);

        this.size = GameVariables.ENEMY_SIZE;
    }

    @Override
    public void update(World world) {
        elapsedTime += world.deltaTime;

        if(routine !=null) {
            routine.act(this, world);
        }

        if(velocity.x>0){
            setOrientation(Orientation.RIGHT);
        } else if(velocity.x<0) {
            setOrientation(Orientation.LEFT);
        } else {
            setOrientation(Orientation.STILL);
        }

        position.add(velocity);
    }

    @Override
    public void hit(Living l) {

    }
}
