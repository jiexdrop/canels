package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.routine.MoveTo;
import com.jiedro.canels.model.entity.routine.Routine;
import com.jiedro.canels.model.world.World;
import com.jiedro.canels.view.Textures;

/**
 *
 * Created by jiexdrop on 04/07/17.
 */

public class Enemy extends Entity {

    public Enemy(float x, float y) {
        health = 3;
        color = new Color(GameVariables.SLIME_COLOR);

        position.set(x,y);
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
    public void hit(Entity e) {

    }
}
