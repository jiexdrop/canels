package com.jiedro.canels.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiexdrop on 03/07/17.
 */

public class WorldInput implements InputProcessor {
    private World world;

    private OrthographicCamera tilemapCamera;

    private OrthographicCamera entitiesCamera;

    public WorldInput(World world, OrthographicCamera tilemapCamera, OrthographicCamera entitiesCamera){
        this.world = world;
        this.tilemapCamera = tilemapCamera;
        this.entitiesCamera = entitiesCamera;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode== Input.Keys.PLUS){
            tilemapCamera.zoom -= GameVariables.ZOOM_INPUT;
            entitiesCamera.zoom -= GameVariables.ZOOM_INPUT;
        }
        if(keycode== Input.Keys.MINUS){
            tilemapCamera.zoom += GameVariables.ZOOM_INPUT;
            entitiesCamera.zoom += GameVariables.ZOOM_INPUT;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 result = tilemapCamera.unproject(new Vector3(screenX, screenY, 0.f));
        world.placeTile(result.x, result.y, Textures.getGroundTile());

        HashMap<Vector2, Vector2> breadthFirstSearch = world.breadthFirstSearch(GameVariables.PLAYER_POSITION.x,
                GameVariables.PLAYER_POSITION.y,
                result.x,
                result.y);


        Vector2 whereGo = breadthFirstSearch.get(new Vector2(Math.round(result.x/GameVariables.TILES_SIZE),
                Math.round(result.y/GameVariables.TILES_SIZE)));


        Vector2 whereNow = whereGo;

        Vector2 whereFrom = GameVariables.PLAYER_POSITION;

        if(whereGo!=null){
            while (whereNow!=whereFrom && whereGo!=null){
                whereNow = whereGo;
                whereGo = breadthFirstSearch.get(whereNow);
                if(whereGo!=null)
                    world.placeTile(whereGo.x*GameVariables.TILES_SIZE, whereGo.y*GameVariables.TILES_SIZE, Textures.getDoorTile());
                world.placeTile(whereNow.x*GameVariables.TILES_SIZE, whereNow.y*GameVariables.TILES_SIZE, Textures.getDoorTile());
            }
            System.out.println("found" + whereNow);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 result =  tilemapCamera.unproject(new Vector3(screenX, screenY, 0.f));
        world.placeTile(result.x, result.y, Textures.getGroundTile());


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
