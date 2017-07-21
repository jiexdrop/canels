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

    String name;

    Vector2 velocity;
    Vector2 position;

    int size;
    int speed;

    Color color;

    Routine routine;

    float elapsedTime;


    public Entity(){
        name = "";
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0,0);

        this.size = GameVariables.NORMAL_SIZE;
        this.speed = GameVariables.ENTITIES_SPEED;
    }

    public abstract boolean isAlive();
    public abstract boolean toClean();
    public abstract boolean canMove();

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

    public abstract void update(World world);

    public Color getColor() {
        return color.add(0, 0, 0, elapsedTime / 256);
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }
}
