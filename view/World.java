package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public World(){
        player = new Player();
        terrain = new Terrain();

        terrain.update(player);

        Gdx.input.setInputProcessor(new MainInputProcessor(player, terrain));
    }

    public void renderTerrain(OrthographicCamera camera){
        terrain.getRenderer().setView(camera);
        terrain.getRenderer().render();
    }

    public void dispose(){
        terrain.dispose();
    }
}
