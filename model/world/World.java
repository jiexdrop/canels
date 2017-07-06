package com.jiedro.canels.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Enemy;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.view.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 * Has all entities
 * Created by jiexdrop on 03/07/17.
 */

public class World {
    private Player player;

    private Terrain terrain;

    private ArrayList<Entity> entities;


    public World() {
        player = new Player();
        terrain = new Terrain();
        entities = new ArrayList<Entity>();
        placeEnemies();
    }

    public void moveEnemies(){

    }

    public void placeEnemies(){
        Random random = new Random();
        for (int i = 0; i < GameVariables.ENEMIES_PAR_LEVEL; i++) {
            entities.add( new Enemy(random.nextInt(GameVariables.ENEMIES_SPACING) -random.nextInt(GameVariables.ENEMIES_SPACING),
                    random.nextInt(GameVariables.ENEMIES_SPACING) -random.nextInt(GameVariables.ENEMIES_SPACING) ));

        }
    }


    public HashMap<Vector2, Vector2> breadthFirstSearch(float startX, float startY, float destinationX, float destinationY){
        Deque<Vector2> frontier = new ArrayDeque<Vector2>();
        Vector2 fromCoords = new Vector2(startX, startY);
        frontier.push(fromCoords);
        HashMap<Vector2, Vector2> came_from = new HashMap<Vector2, Vector2>();
        came_from.put(fromCoords, null);

        while (!frontier.isEmpty()){
            Vector2 current = frontier.pop();

            if (Math.round(current.x) == Math.round(destinationX) && Math.round(current.y) == Math.round(destinationY))
                return came_from;

            for (Vector2 v :terrain.isWalkableNeighbor(current.x, current.y)) {
                if(!came_from.containsKey(v)){
                    frontier.push(v);
                    //came_from.remove(v);
                    came_from.put(v,current);
                }
            }
        }

        return came_from;
    }

    public Vector2 getPlayerPosition(){
        return new Vector2(player.getX(), player.getY());
    }

    public void movePlayer(float knobPercentX, float knobPercentY) {
        float dt = Gdx.graphics.getDeltaTime();

        if (terrain.canMove(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED),
                    knobPercentY * (dt * GameVariables.PLAYER_SPEED));

            terrain.updateWorld(player.getX() - GameVariables.TILEMAP_CENTER,
                    player.getY() - GameVariables.TILEMAP_CENTER);
        } else {
            player.move(0,0);
        }

    }

    public void drawTilemap(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(player.getX(),
                player.getY(), 0.f);
        terrain.draw(tilemapBatch,
                player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);

        for (Entity e:entities) {
            e.getSprite().draw(tilemapBatch);
        }
    }

    public void drawEntities(SpriteBatch entitiesBatch, OrthographicCamera entitiesCamera) {
        player.getSprite().draw(entitiesBatch);


    }

    public void placeTile(float x, float y, Tile groundTile) {
        terrain.placeTile(x,y,groundTile);
    }

    /**
     * Can update the model from here
     */
    public void update() {
        player.update();

        for (Entity e:entities) {
            e.moveTo(player);
            e.update();
        }
    }

}


