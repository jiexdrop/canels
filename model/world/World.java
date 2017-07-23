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
import com.jiedro.canels.view.TileType;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

/**
 * Has all entities
 * Draws them
 * Created by jiexdrop on 03/07/17.
 */

public class World extends Observable{
    private Player player;

    private Terrain terrain;

    private ArrayList<Entity> entities;

    private ArrayList<Entity> entitiesToClean;

    private Vector2 workingTile;

    public float deltaTime;

    private boolean holding;

    private int holdTime;

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

        //TODO better
        setChanged();
        notifyObservers();
    }


    public void placeTile(Vector2 pos, TileType tileType) {
        terrain.placeTile(pos.x,pos.y, tileType);
    }

    public void interpretClick(float x, float y){
        holding = true;
        ArrayList<Entity> selectedEntities = getSelectedEntities(x, y);
        Vector2 selectedPos = Helpers.screenToMap(x,y);
        TileType selectedTile = terrain.getVegetationTileFromPos(selectedPos);

        if(selectedTile != null){
            workingTile = selectedPos;
        } else if(selectedEntities.size()>0) {
            for (Entity e : getSelectedEntities(x, y)) {
                if(e.isAlive())
                    player.hit((Living)e);
            }
        }

    }

    public void interpretDrag(float x, float y){
        //TODO player selected tile from inventory ?
        holding = true;

            Vector2 selectedPos = Helpers.screenToMap(x,y);
            TileType selectedTile = terrain.getVegetationTileFromPos(selectedPos);

            if(selectedTile != null)
                workingTile = selectedPos;

    }

    public void endInterpret(){
        holding = false;
        holdTime = 0;

        setChanged();
        notifyObservers();
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
        if (holding) holdTime++;
        GameVariables.HOLD_TIME = holdTime;

        player.update(this);

        updateTiles();
        cleanEntities();

        for (Entity e:entitiesToClean) {
            entities.remove(e);
        }

        for (Entity e:entities) {
            setEntitiesRoutines(e);

            e.update(this);
        }

    }

    public void updateTiles(){
        TileType tileType = terrain.getVegetationTileFromPos(workingTile);
        if(workingTile!=null && tileType!=null){
            if(tileType.resistance < holdTime) {
                entities.add(new Item(tileType.name(), Helpers.itemToScreen(workingTile, 1), tileType.color));
                terrain.removeTile(workingTile);

                holding = false;
                workingTile = null;
                holdTime = 0;
            }
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

    public int getHoldTime() {
        return holdTime;
    }

    public Vector2 getWorkingTile() {
        return workingTile;
    }
}


