package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.input.MainInputProcessor;
import com.jiedro.canels.model.world.Terrain;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class World {
    private Player player;

    private Terrain terrain;

    private OrthographicCamera camera;


    private MainInputProcessor mainInputProcessor;

    public World(){
        player = new Player();
        terrain = new Terrain();

        terrain.update(player);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        camera.update();

        mainInputProcessor = new MainInputProcessor(player, terrain, camera);


        Gdx.input.setInputProcessor(mainInputProcessor);

    }


    public void renderTerrain(){
        camera.update();
        terrain.getRenderer().setView(camera);
        terrain.getRenderer().render();
    }

    public void dispose(){
        terrain.dispose();
    }
}
