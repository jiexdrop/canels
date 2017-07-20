package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.*;

import java.util.HashMap;

/**
 * UI for
 * Created by jiexdrop on 03/07/17.
 */

public class WorldInput implements InputProcessor {
    private World world;

    private OrthographicCamera tilemapCamera;

    private OrthographicCamera entitiesCamera;

    public WorldInput(com.jiedro.canels.model.world.World world, OrthographicCamera tilemapCamera, OrthographicCamera entitiesCamera){
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
        world.interpretClick(result.x, result.y);

        if(GameVariables.DEBUG) debug(result);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 result =  tilemapCamera.unproject(new Vector3(screenX, screenY, 0.f));
        world.interpretDrag(result.x, result.y);

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

    private void debug(Vector3 result){
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            HashMap<Vector2, Vector2> breadthFirstSearch = world.breadthFirstSearch(Helpers.screenToMap(GameVariables.PLAYER_POSITION),
                    Helpers.screenToMap(result.x, result.y));

            Vector2 whereGo = breadthFirstSearch.get(Helpers.screenToMap(result.x, result.y));

            Vector2 whereNow = whereGo;

            Vector2 whereFrom = GameVariables.PLAYER_POSITION;

            if (whereGo != null) {
                while (whereNow != whereFrom && whereGo != null) {
                    whereNow = whereGo;
                    whereGo = breadthFirstSearch.get(whereNow);
                    if (whereGo != null) {
                        world.placeTile(Helpers.mapToScreen(whereNow), GameTiles.getDoorTile());
                    }
                }
            }
        }
    }
}
