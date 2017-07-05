package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    protected float x;
    protected float y;

    protected float velocityX;
    protected float velocityY;

    protected Entity moveTo;

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
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update() {
        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        }

        if(this.moveTo !=null){
            if(checkcirclecollide(moveTo.getX(), moveTo.getY(),
                    GameVariables.CHUNK_SIZE*4, this.x, this.y, GameVariables.CHUNK_SIZE*4)) {
                move((moveTo.getX() - this.x) / (GameVariables.ENTITIES_SPEED + random.nextInt(16 * 16)),
                        (moveTo.getY() - this.y) / (GameVariables.ENTITIES_SPEED + random.nextInt(16 * 16)));
            } else {
                moveTo(null);
                velocityX = 0;
                velocityY = 0;
            }
        }

        this.x += velocityX;
        this.y += velocityY;
        sprite.setPosition(this.x, this.y);
    }

    boolean checkcirclecollide(double x1, double y1, float r1, double x2, double y2, float r2){
        return Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < (r1 + r2) * (r1 + r2);
    }
}
