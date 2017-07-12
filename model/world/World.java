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

    public void placeEnemies(){
        Random random = new Random();
        for (int i = 0; i < GameVariables.ENEMIES_PAR_LEVEL; i++) {
            entities.add( new Enemy(random.nextInt(GameVariables.ENEMIES_SPACING) -random.nextInt(GameVariables.ENEMIES_SPACING),
                    random.nextInt(GameVariables.ENEMIES_SPACING) -random.nextInt(GameVariables.ENEMIES_SPACING) ));

        }
    }


    public HashMap<Vector2, Vector2> breadthFirstSearch(Vector2 start, Vector2 destination){
        Deque<Vector2> frontier = new ArrayDeque<Vector2>();


        frontier.push(start);
        HashMap<Vector2, Vector2> came_from = new HashMap<Vector2, Vector2>();
        came_from.put(start, null);

        while (!frontier.isEmpty()){
            Vector2 current = frontier.pollLast();

            if (current.x == destination.x
                    && current.y == destination.y)
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

    public void movePlayer(float knobPercentX, float knobPercentY) {
        float dt = Gdx.graphics.getDeltaTime();

        if (terrain.canMove(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED),
                    knobPercentY * (dt * GameVariables.PLAYER_SPEED));
        } else if (terrain.canMove(player.getX(),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(0f, knobPercentY * (dt * GameVariables.PLAYER_SPEED));
        } else if  (terrain.canMove(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY())) {
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED), 0f);
        }
        else {
            player.move(0,0);
        }
        terrain.updateWorld(player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);

    }

    public void drawTilemapBackground(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(player.getX(),
                player.getY(), 0.f);

        terrain.drawBackground(tilemapBatch,
                player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);


        GameVariables.ENTITIES = entities.size();
    }

    public void drawTilemapForeground(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(player.getX(),
                player.getY(), 0.f);

        terrain.drawForeground(tilemapBatch,
                player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);

        GameVariables.ENTITIES = entities.size();
    }

    public void drawEntities(SpriteBatch batch, OrthographicCamera entitiesCamera) {
        player.getSprite().draw(batch);

        for (Entity e:entities) {
            e.getSprite().draw(batch);
        }
    }

    public void placeTile(float x, float y, Tile groundTile) {
        terrain.placeTile(x,y,groundTile);
    }

    public void placeTile(Vector2 pos, Tile groundTile) {
        terrain.placeTile(pos.x,pos.y,groundTile);
    }

    /**
     * Can update the model from here
     */
    public void update() {
        player.update();

        for (Entity e:entities) {

            //TODO REFACTOR
            if(!e.isMoving()) {
                if(checkcirclecollide(player.getX(), player.getY(),
                        GameVariables.CHUNK_SIZE*4, e.getX(), e.getY(), GameVariables.CHUNK_SIZE*4)) {
                    e.moveTowards(Helpers.getMovementPoints(this, e.getPosition(), player.getPosition()));
                }else {
                    e.moveTowards(new ArrayDeque<Vector2>());
                }
            } else {
                if(!checkcirclecollide(player.getX(), player.getY(),
                        GameVariables.CHUNK_SIZE*4, e.getX(), e.getY(), GameVariables.CHUNK_SIZE*4)) {
                    e.moveTowards(new ArrayDeque<Vector2>());
                } else {
                    e.moveTowards(Helpers.getMovementPoints(this, e.getPosition(), player.getPosition()));
                }
            }

            e.update();

        }
    }

    boolean checkcirclecollide(double x1, double y1, float r1, double x2, double y2, float r2){
        return Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < (r1 + r2) * (r1 + r2);
    }
}


