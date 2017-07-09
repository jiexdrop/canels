package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

/**
 * Display user controls
 * Created by jiexdrop on 30/06/17.
 */

public class UserInterface {
    private World world;

    private Touchpad touchpad;
    private Stage stage;
    private Viewport viewport = new ScreenViewport();
    private BitmapFont font;


    public UserInterface(World world, Batch batch){
        this.world = world;

        this.stage = new Stage(viewport, batch);

        touchpad = new Touchpad(10,getTouchpadStyle());
        touchpad.setBounds(0, 0, GameVariables.TOUCH_PAD_SIZE, GameVariables.TOUCH_PAD_SIZE);

        touchpad.setPosition(GameVariables.TOUCH_PAD_OFFSET_X,GameVariables.TOUCH_PAD_OFFSET_Y);
        stage.addActor(touchpad);

        font = new BitmapFont();
    }

    public void draw(int totalRenderCalls){

        world.movePlayer(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());

        stage.draw();

        stage.getBatch().begin();
        font.draw(stage.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond()
                + "\nRENDER_CALLS: " + totalRenderCalls
                + "\nPLAYER_POS: " + GameVariables.PLAYER_POSITION
                + "\nENTITIES: " + GameVariables.ENTITIES, 10, Gdx.graphics.getHeight()-10);
        stage.getBatch().end();
    }

    public Stage getStage() {
        return stage;
    }

    private static Touchpad.TouchpadStyle getTouchpadStyle() {
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(GameVariables.TOUCH_PAD_BACKGROUND));

        touchpadSkin.add("touchKnob", new Texture(GameVariables.TOUCH_PAD_KNOB));

        touchpadStyle.background  = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob  = touchpadSkin.getDrawable("touchKnob");

        return touchpadStyle;
    }

}
