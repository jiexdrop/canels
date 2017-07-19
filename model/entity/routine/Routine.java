package com.jiedro.canels.model.entity.routine;

import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.world.World;

/**
 * Ai
 * Created by jiexdrop on 18/07/17.
 */

public abstract class Routine {

    protected RoutineState routineState;

    public enum RoutineState{
        SUCCESS, FAILURE, RUNNING
    }

    public Routine(){

    }

    public void start() {
        this.routineState = RoutineState.RUNNING;
    }

    public abstract void act(Entity entity, World world);

    public void succeed() {
        this.routineState = RoutineState.SUCCESS;
    }

    public void fail() {
        this.routineState = RoutineState.FAILURE;
    }

    public boolean isRunning(){
        return routineState.equals(RoutineState.RUNNING);
    }

    public boolean hasSucceeded(){
        return routineState.equals(RoutineState.SUCCESS);
    }

    public boolean hasFailed(){
        return routineState.equals(RoutineState.FAILURE);
    }

    public RoutineState getState() {
        return routineState;
    }
}
