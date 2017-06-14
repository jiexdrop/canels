package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.jiedro.canels.GameVariables;

/**
 * Created by jorge on 23/05/17.
 */

public class Player extends Entity {
    protected Behaviour attackBehaviour;

    public Player(){
        super(new Texture(GameVariables.PLAYER_BASE));
    }

    public Behaviour getAttackBehaviour() {
        return attackBehaviour;
    }

    public void setAttackBehaviour(Behaviour attackBehaviour) {
        this.attackBehaviour = attackBehaviour;
    }

    public void attack(){
        attackBehaviour.behave();
    }


    public void move(Direction direction){
        switch (direction){
            case UP:
                setPosition(getX(), getY()+ GameVariables.TILES_SIZE );
                break;
            case DOWN:
                setPosition(getX(), getY()- GameVariables.TILES_SIZE );
                break;
            case LEFT:
                setPosition(getX() - GameVariables.TILES_SIZE, getY());
                break;
            case RIGHT:
                setPosition(getX() + GameVariables.TILES_SIZE, getY() );
                break;
        }
    }


}
