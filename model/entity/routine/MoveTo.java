package com.jiedro.canels.model.entity.routine;

import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.world.Helpers;
import com.jiedro.canels.model.world.World;

/**
 * Move to a destination
 * Created by jiexdrop on 18/07/17.
 */

public class MoveTo extends Routine {

    Vector2 destination = new Vector2(0,0);
    Vector2 start = new Vector2(0,0);
    Vector2 direction = new Vector2(0,0);

    float distance;

    public MoveTo(float startX, float startY, float endX, float endY){
        super();
        start();
        destination.x = endX;
        destination.y = endY;
        start.x = startX;
        start.y = startY;
        distance = Vector2.dst(startX, startY, endX, endY);
        direction = destination.sub(startX, startY).nor();
    }

    @Override
    public void act(Entity entity, World world) {
        if(isRunning()){
            if(!entity.isAlive()){
                entity.move(0,0);
                fail();
                return;
            }
            if(!hasReachedDestination(entity.getX(), entity.getY())){
                move(entity,world);
            }
        }
    }

    /**
     * Moving routine avoiding world entities and obstacles
     * @param entity entity to move
     * @param world where the entity moves
     */
    private void move(Entity entity, World world){
        entity.move(direction.x * GameVariables.ENTITIES_SPEED * world.deltaTime,
                direction.y * GameVariables.ENTITIES_SPEED * world.deltaTime);
        if(hasReachedDestination(entity.getX() + (direction.x * GameVariables.ENTITIES_SPEED * world.deltaTime),
                entity.getY() + (direction.y * GameVariables.ENTITIES_SPEED * world.deltaTime))){
            entity.move(0,0);
            succeed();
        }

    }

    private boolean hasReachedDestination(float x, float y) {
        return Vector2.dst(start.x, start.y, x, y) >= distance;
    }

}
