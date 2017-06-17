package com.jiedro.canels.model.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Direction;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.world.Terrain;
import com.jiedro.canels.view.Tile;
import com.jiedro.canels.view.Tiles;

/**

 * Created by jorge on 25/05/17.
 */

public class MainInputProcessor implements InputProcessor {

    private Player player;
    private Terrain terrain;

    public MainInputProcessor(Player player, Terrain terrain){
        this.player = player;
        this.terrain = terrain;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.UP:
                player.move(Direction.UP);
                break;
            case Input.Keys.DOWN:
                player.move(Direction.DOWN);
                break;
            case Input.Keys.LEFT:
                player.move(Direction.LEFT);
                break;
            case Input.Keys.RIGHT:
                player.move(Direction.RIGHT);
                break;
        }

        terrain.update(player);

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
        System.out.println(terrain.screenToMap(screenX,screenY));
        terrain.placeTile((int)terrain.screenToMap(screenX,screenY).x, (int)terrain.screenToMap(screenX,screenY).y, Tiles.getGrassTile());

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
