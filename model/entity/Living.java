package com.jiedro.canels.model.entity;

/**
 * Created by jiexdrop on 20/07/17.
 */

public abstract class Living extends Entity {
    Orientation orientation;

    //RPG
    int health = 0;

    public Living(){
        super();
        this.orientation = Orientation.STILL;
    }


    @Override
    public boolean isAlive(){
        return health>0;
    }

    @Override
    public boolean toClean() {
        return isAlive();
    }

    @Override
    public boolean canMove() {
        return isAlive();
    }

    public abstract void hit(Living e);

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation){
        this.orientation = orientation;

    }
}
