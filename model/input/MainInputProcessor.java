package com.jiedro.canels.model.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.world.Terrain;
import com.jiedro.canels.view.Tiles;

/**

 * Created by jorge on 25/05/17.
 */

public class MainInputProcessor implements InputProcessor {

    private Player player;
    private Terrain terrain;
    private OrthographicCamera camera;

    public MainInputProcessor(Player player, Terrain terrain, OrthographicCamera camera){
        this.player = player;
        this.terrain = terrain;
        this.camera = camera;
    }


    @Override
    public boolean keyDown(int keycode) {
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
        Vector3 convert = camera.unproject(new Vector3(screenX,screenY,0));
        Vector2 result = new Vector2(convert.x/GameVariables.TILES_SIZE, convert.y/GameVariables.TILES_SIZE);

        terrain.placeTile((int)result.x, (int)result.y, (int)player.getMapX(), (int)player.getMapY(), Tiles.getGrassTile());

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 convert = camera.unproject(new Vector3(screenX,screenY,0));
        Vector2 result = new Vector2(convert.x/GameVariables.TILES_SIZE, convert.y/GameVariables.TILES_SIZE);

        terrain.placeTile((int)result.x, (int)result.y, (int)player.getMapX(), (int)player.getMapY(), Tiles.getGrassTile());

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
