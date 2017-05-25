package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.jiedro.canels.GameVariables;

/**
 * Created by jorge on 23/05/17.
 */

public class Player extends Entity {
    protected Behaviour attackBehaviour;
    protected Behaviour moveBehaviour;

    public Player(){
        super(new Texture(GameVariables.TERRAIN_TILES));
    }

    public Behaviour getAttackBehaviour() {
        return attackBehaviour;
    }

    public void setAttackBehaviour(Behaviour attackBehaviour) {
        this.attackBehaviour = attackBehaviour;
    }

    public Behaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    public void setMoveBehaviour(Behaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    public void attack(){
        attackBehaviour.behave();
    }

    public void move(){
        moveBehaviour.behave();
    }


}
