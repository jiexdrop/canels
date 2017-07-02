package com.jiedro.canels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.jiedro.canels.view.WorldRender;

public class Main extends Game {

    private WorldRender worldRender;

    @Override
    public void create() {
        worldRender = new WorldRender();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRender.renderTerrain();
    }

    @Override
    public void dispose() {
        worldRender.dispose();
    }


}
