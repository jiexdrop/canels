package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jiedro.canels.GameVariables;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {
    protected Sprite sprite;

    protected float velocityX;
    protected float velocityY;

    private float distance;
    private Vector2 direction;

    protected Vector2 position;

    protected Entity moveTo;

    protected ArrayDeque<Vector2> movementPoints = new ArrayDeque<Vector2>();

    Random random = new Random();

    public Entity(Sprite sprite){
        this.sprite = sprite;
        this.position = new Vector2(0,0);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setOrientation(Orientation orientation){
        switch (orientation){
            case LEFT:
                sprite.setFlip(true,false);
                break;
            case RIGHT:
                sprite.setFlip(false,false);
                break;
        }
    }

    public void setTint(Color color) {
        this.sprite.setColor(color);
    }

    public void move(float velocityX, float velocityY){
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void moveTowards(ArrayDeque<Vector2> movementPoints){
        this.movementPoints = movementPoints;
        if(this.movementPoints !=null && this.movementPoints.size() > 0) {
            Vector2 first = movementPoints.pollFirst();

            distance = Vector2.dst(this.getX(), this.getY(), first.x, first.y);
            Vector2 result = new Vector2();
            result.x = first.x - this.getX();
            result.y = first.y - this.getY();
            direction = result.nor();
        }
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public boolean isMoving() {
        return movementPoints.size()>1;
    }

    public void update() {
        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        }

        if(this.isMoving()){
            this.position.x += direction.x * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();
            this.position.y += direction.y * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();

            if(Vector2.dst(this.movementPoints.peekFirst().x, this.movementPoints.peekFirst().y, this.getX(), this.getY()) >= distance)
            {
                this.movementPoints.pop();
                moveTowards(this.movementPoints);
            }

        }

        //this.x += velocityX;
        //this.y += velocityY;
        sprite.setPosition(position.x, position.y);
    }

}
