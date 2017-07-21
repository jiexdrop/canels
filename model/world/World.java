package com.jiedro.canels.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Enemy;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.entity.Item;
import com.jiedro.canels.model.entity.Living;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.entity.routine.MoveTo;
import com.jiedro.canels.view.GameTextures;
import com.jiedro.canels.view.GameTiles;
import com.jiedro.canels.view.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Has all entities
 * Draws them
 * Created by jiexdrop on 03/07/17.
 */

public class World {
    private Player player;

    private Terrain terrain;

    private ArrayList<Entity> entities;

    private ArrayList<Entity> entitiesToClean;

    public float deltaTime;

    public World() {
        player = new Player();
        terrain = new Terrain();
        entities = new ArrayList<Entity>();
        entitiesToClean = new ArrayList<Entity>();

    }

    public void placeEnemies(){
        Random random = new Random();

        while (entities.size() < GameVariables.ENEMIES_PAR_LEVEL){
            int x = random.nextInt(GameVariables.ENEMIES_SPACING) - random.nextInt(GameVariables.ENEMIES_SPACING);
            int y = random.nextInt(GameVariables.ENEMIES_SPACING) - random.nextInt(GameVariables.ENEMIES_SPACING);
            if(terrain.isScreenWalkable(x + player.getX(),y + player.getY())) {
                entities.add(new Enemy(x + player.getX(), y+player.getY()));
            }
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
                if (!came_from.containsKey(v)) {
                    frontier.push(v);
                    //came_from.remove(v);
                    came_from.put(v, current);
                }
            }
        }

        return came_from;
    }


    public void movePlayer(float knobPercentX, float knobPercentY) {
        float dt = Gdx.graphics.getDeltaTime();

        if (terrain.isScreenWalkable(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED),
                    knobPercentY * (dt * GameVariables.PLAYER_SPEED));
        } else if (terrain.isScreenWalkable(player.getX(),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(0f, knobPercentY * (dt * GameVariables.PLAYER_SPEED));
        } else if  (terrain.isScreenWalkable(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY())) {
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED), 0f);
        }
        else {
            player.move(0,0);
        }
        terrain.updateWorld(player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);

        if(entities.size()<GameVariables.ENEMIES_PAR_LEVEL){
            placeEnemies();
        }
    }


    public void placeTile(Vector2 pos, Tile groundTile) {
        terrain.placeTile(pos.x,pos.y,groundTile);
    }

    public void interpretClick(float x, float y){
        ArrayList<Entity> selectedEntities = getSelectedEntities(x, y);
        HashMap<Vector2,Tile> selectedTiles = terrain.getSelectedTiles(x, y);

        if(selectedTiles.size()>0){
            for (Map.Entry<Vector2,Tile> st:selectedTiles.entrySet()) {
                entities.add(new Item(st.getValue().getName(), Helpers.itemToScreen(st.getKey(), 1), st.getValue().getColor()));
                terrain.removeTile(st.getKey());
            }
        } else if(selectedEntities.size()>0) {
            for (Entity e : getSelectedEntities(x, y)) {
                if(e.isAlive())
                    player.hit((Living)e);
            }
        }
    }

    public void interpretDrag(float x, float y){
        terrain.placeTile(x, y, GameTiles.getGrassTile()); //TODO player selected tile from inventory ?
    }

    public ArrayList<Entity> getSelectedEntities(float x, float y){
        ArrayList<Entity> result = new ArrayList<Entity>();
        for(Entity e:entities){
            if(Helpers.checkIfNear(x, y, GameVariables.CURSOR_SIZE, e.getX(), e.getY(), GameVariables.ENEMY_SIZE)){
                result.add(e);
            }
        }

        return result;
    }


    /**
     * Can update the model from here
     */
    public void update() {
        deltaTime = Gdx.graphics.getDeltaTime();

        player.update(this);

        cleanEntities();

        for (Entity e:entitiesToClean) {
            entities.remove(e);
        }

        for (Entity e:entities) {
            setEntitiesRoutines(e);

            e.update(this);
        }

    }

    private void cleanEntities() {
        // If entities outside of range then clear them or died
        for (Entity e:entities) {
            if (!Helpers.checkIfNear(player.getX(), player.getY(), GameVariables.ENEMIES_SPACING,
                    e.getX(), e.getY(), GameVariables.ENEMIES_SPACING)) {
                entitiesToClean.add(e);
            }

            if(!e.isAlive() && e.toClean())
                entitiesToClean.add(e);

            if(!e.toClean()) {
                //clean items at ground
                if (Helpers.checkIfNear(player.getX(), player.getY(), GameVariables.ITEM_SIZE,e.getX(), e.getY(), 1 )){
                    entitiesToClean.add(e);
                    player.addItem((Item)e);
                }
            }
        }
    }

    private void setEntitiesRoutines(Entity e){
        if(Helpers.checkIfNear(player.getX(), player.getY(), GameVariables.ENEMY_SIZE,
                e.getX(), e.getY(), GameVariables.ENEMY_SIZE)) {
            // If player is near then move to player
            if (e.getRoutine() == null) {
                e.setRoutine(new MoveTo(e.getPosition(), player.getPosition()));
            }


        } else {
            //Wander

            //& reset things
            if (e.getRoutine() != null && e.getRoutine().hasSucceeded()) {
                e.setRoutine(null);
            }
        }

    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}


