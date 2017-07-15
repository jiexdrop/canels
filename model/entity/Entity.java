package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;

import java.util.ArrayDeque;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {
    float elapsedTime;

    float velocityX;
    float velocityY;

    private float distance;
    private Vector2 direction;
    Vector2 position;

    Animation<TextureRegion> walkAnimation;

    TextureRegion currentFrame;
    Color color;
    private ArrayDeque<Vector2> movementPoints = new ArrayDeque<Vector2>();

    //RPG
    int health = 0;


    public Entity(TextureRegion[][] texture, int frames, int health){

        TextureRegion[] entityFrames = new TextureRegion[frames];

        System.arraycopy(texture[0], 0, entityFrames, 0, frames);

        walkAnimation = new Animation<TextureRegion>(0.2f, entityFrames);
        this.position = new Vector2(0,0);

        currentFrame = walkAnimation.getKeyFrame(elapsedTime,true);

        this.health = health;
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
            case STILL:
                currentFrame = walkAnimation.getKeyFrame(0);
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

        currentFrame = walkAnimation.getKeyFrame(elapsedTime,true);

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
            } else {
                setOrientation(Orientation.STILL);
            }

        }

    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Color getColor() {
        return color;
    }
}
