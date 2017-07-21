package com.jiedro.canels.model.entity.routine;

import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.entity.Living;
import com.jiedro.canels.model.world.Helpers;
import com.jiedro.canels.model.world.World;

/**
 * Move to a destination
 * Created by jiexdrop on 18/07/17.
 */

public class MoveTo extends Routine {

    Vector2 destination = new Vector2(0,0);
    Vector2 direction = new Vector2(0,0);

    float distance;

    public MoveTo(Vector2 start, Vector2 end){
        super();
        start();
        destination = end.cpy();
        distance = Vector2.dst(start.x, start.y, end.x, end.y);
        direction = destination.sub(start.x, start.y).nor();
    }

    @Override
    public void act(Entity entity, World world) {
        if(isRunning()){
            if (!entity.canMove()) {
                entity.move(0, 0);
                fail();
                return;
            }

            if(!hasReachedDestination(entity.getX(), entity.getY(), entity.getSize(),
                    world.getPlayer().getX(), world.getPlayer().getY(), world.getPlayer().getSize())){
                move(entity,world);
            } else {
                entity.move(0,0);
                succeed();
            }
        }
    }

    /**
     * Moving routine avoiding world entities and obstacles
     * @param entity entity to move
     * @param world where the entity moves
     */
    private void move(Entity entity, World world){
        distance = Vector2.dst(entity.getX(), entity.getY(), world.getPlayer().getX(), world.getPlayer().getY());
        destination = world.getPlayer().getPosition().cpy();
        direction = destination.sub(entity.getX(), entity.getY()).nor();

        entity.move(direction.x * entity.getSpeed() * world.deltaTime,
                direction.y * entity.getSpeed() * world.deltaTime);

    }

    private boolean hasReachedDestination(float startX, float startY, int sizeA, float playerX, float playerY, int sizeB) {
        return Helpers.checkIfNear(startX, startY, sizeA/4, playerX, playerY, sizeB/4);
    }

}
