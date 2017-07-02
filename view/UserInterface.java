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

/**
 * Display user controls
 * Created by jiexdrop on 30/06/17.
 */

public class UserInterface implements InputProcessor{
    private Touchpad touchpad;
    private WorldRender worldRender;
    private Stage stage;
    private InputMultiplexer inputMultiplexer;
    private Viewport viewport = new ScreenViewport();
    private BitmapFont font;


    public UserInterface(WorldRender worldRender, Batch batch){
        this.worldRender = worldRender;
        this.stage = new Stage(viewport, batch);
        touchpad = new Touchpad(10,getTouchpadStyle());
        touchpad.setBounds(0, 0, GameVariables.TOUCH_PAD_SIZE, GameVariables.TOUCH_PAD_SIZE);

        touchpad.setPosition(GameVariables.TOUCH_PAD_OFFSET_X,GameVariables.TOUCH_PAD_OFFSET_Y);
        stage.addActor(touchpad);

        //TODO
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);

        font = new BitmapFont();

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void draw(){
        float dt = Gdx.graphics.getDeltaTime();
        if(worldRender.getTerrain().canMove(worldRender.getTilemapCamera().position.x + touchpad.getKnobPercentX(),
                worldRender.getTilemapCamera().position.y +touchpad.getKnobPercentY())) {
            worldRender.getTilemapCamera().translate(touchpad.getKnobPercentX() * (dt *GameVariables.PLAYER_SPEED),
                    touchpad.getKnobPercentY() * (dt *GameVariables.PLAYER_SPEED));
            worldRender.getTerrain().updateWorld(worldRender.getTilemapCamera().position.x - GameVariables.TILEMAP_CENTER,
                    worldRender.getTilemapCamera().position.y - GameVariables.TILEMAP_CENTER);
        }

        stage.draw();

        stage.getBatch().begin();
        font.draw(stage.getBatch(), "FPS: " + Gdx.graphics.getFramesPerSecond()
                + " RC: " + worldRender.totalRenderCalls, 10, Gdx.graphics.getHeight()-10);
        stage.getBatch().end();
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
        Vector3 result = worldRender.getTilemapCamera().unproject(new Vector3(screenX, screenY, 0.f));
        worldRender.getTerrain().placeTile(result.x, result.y, Tiles.getGroundTile());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 result =  worldRender.getTilemapCamera().unproject(new Vector3(screenX, screenY, 0.f));
        worldRender.getTerrain().placeTile(result.x, result.y, Tiles.getGroundTile());
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
