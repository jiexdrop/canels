package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jiedro.canels.GameVariables;

import java.util.Random;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {
    protected Sprite sprite;

    protected float x = 0f;
    protected float y = 0f;

    protected float velocityX;
    protected float velocityY;

    private float distance;
    private Vector2 direction;

    protected Entity moveTo;

    protected boolean isMoving;

    Random random = new Random();

    public Entity(Sprite sprite){
        this.sprite = sprite;
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

    public void moveTo(Entity e){
        this.moveTo = e;
        if(e!=null) {
            distance = Vector2.dst(this.getX(), this.getY(), e.getX(), e.getY());
            Vector2 result = new Vector2();
            result.x = e.getX() - this.getX();
            result.y = e.getY() - this.getY();
            direction = result.nor();
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isMoving() {
        return moveTo != null;
    }

    public void update() {
        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        }

        if(this.moveTo !=null){
            this.x += direction.x * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();
            this.y += direction.y * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();
            if(Vector2.dst(moveTo.getX(), moveTo.getY(), this.getX(), this.getY()) >= distance)
            {
                this.moveTo = null;
            }

        }

        this.x += velocityX;
        this.y += velocityY;
        sprite.setPosition(this.x, this.y);
    }

}
