package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    protected static Texture entityTexture;

    protected float elapsedTime;

    protected float velocityX;
    protected float velocityY;

    private float distance;
    private Vector2 direction;

    protected Vector2 position;

    protected Color color;

    protected ArrayDeque<Vector2> movementPoints = new ArrayDeque<Vector2>();

    protected TextureRegion[] entityFrames;

    protected TextureRegion currentFrame;

    protected Animation walkAnimation;

    public Entity(String texture, int frames){
        entityTexture = new Texture(texture);
        TextureRegion[][] tmp = TextureRegion.split(entityTexture, entityTexture.getWidth()/4,entityTexture.getHeight());
        entityFrames = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            entityFrames[i] = tmp[0][i];
        }

        walkAnimation = new Animation(0.2f, entityFrames);
        this.position = new Vector2(0,0);

        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(elapsedTime,true);
    }

    public Entity(TextureRegion[][] texture, int frames){

        entityFrames = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            entityFrames[i] = texture[0][i];
        }

        walkAnimation = new Animation(0.2f, entityFrames);
        this.position = new Vector2(0,0);

        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(elapsedTime,true);
    }


    public void setOrientation(Orientation orientation){
        switch (orientation){
            case LEFT:
                if(!currentFrame.isFlipX())
                    currentFrame.flip(true, false);
                break;
            case RIGHT:
                if(currentFrame.isFlipX())
                    currentFrame.flip(true, false);
                break;
        }
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
        elapsedTime += Gdx.graphics.getDeltaTime();

        currentFrame = (TextureRegion) walkAnimation.getKeyFrame(elapsedTime,true);

        if(this.isMoving()){
            this.position.x += direction.x * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();
            this.position.y += direction.y * GameVariables.ENTITIES_SPEED * Gdx.graphics.getDeltaTime();

            if(Vector2.dst(this.movementPoints.peekFirst().x, this.movementPoints.peekFirst().y, this.getX(), this.getY()) >= distance)
            {
                this.movementPoints.pop();
                moveTowards(this.movementPoints);
            }


            if(direction.x<0){
                setOrientation(Orientation.LEFT);
            } else if(direction.x>0) {
                setOrientation(Orientation.RIGHT);
            }

        }



    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public  Color getColor() {
        return color;
    }
}
