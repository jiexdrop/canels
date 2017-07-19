package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.routine.Routine;
import com.jiedro.canels.model.world.World;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {

    Vector2 velocity;
    Vector2 position;

    Color color;

    Orientation orientation;

    //RPG
    int health = 0;

    Routine routine;

    float elapsedTime;


    public Entity(){
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0,0);
        this.orientation = Orientation.STILL;
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation){
        this.orientation = orientation;

    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }

    public Routine getRoutine() {
        return routine;
    }

    public void move(float velocityX, float velocityY){
        this.velocity.x = velocityX;
        this.velocity.y = velocityY;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public boolean isAlive(){
        return health>0;
    }

    public abstract void update(World world);

    public abstract void hit(Entity e);

    public Color getColor() {
        return color.add(0, 0, 0, elapsedTime / 256);
    }

    public float getElapsedTime() {
        return elapsedTime;
    }
}
