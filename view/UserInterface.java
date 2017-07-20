package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

/**
 * Display user controls
 * Created by jiexdrop on 30/06/17.
 */

public class UserInterface extends Stage {
    private World world;

    private Touchpad touchpad;

    private BitmapFont font;

    private Table mainTable;

    private Table leftTable;

    private Table rightTable;

    private Label debugLabel;

    private Table sideBar;


    public UserInterface(com.jiedro.canels.model.world.World world, Batch batch){
        super(new ScreenViewport(), batch);
        this.world = world;

        mainTable = new Table();
        leftTable = new Table();
        rightTable = new Table();

        mainTable.setFillParent(true);
        mainTable.defaults().pad(GameVariables.UI_OFFSET);

        mainTable.add(leftTable).left().fill();
        mainTable.add(rightTable).width(GameVariables.SIDEBAR_WIDTH).right().expand();

        //if(GameVariables.DEBUG) mainTable.setDebug(true);
        //if(GameVariables.DEBUG) leftTable.setDebug(true);
        //if(GameVariables.DEBUG) rightTable.setDebug(true);

        if(GameVariables.DEBUG) setupDebugLabel();
        setupTouchPad();

        setupSideBar();

        this.addActor(mainTable);
    }

    @Override
    public void draw(){
        super.draw();

        if(GameVariables.DEBUG)
        debugLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond()
                + "\nRENDER_CALLS: " + GameVariables.RENDER_CALLS
                + "\nPLAYER_POS: " + GameVariables.PLAYER_POSITION
                + "\nENTITIES: " + GameVariables.ENTITIES
                + "\nPLAYER_HEALTH: " + GameVariables.PLAYER_HEALTH);

        world.movePlayer(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());

    }

    private void setupDebugLabel(){
        font = new BitmapFont();
        debugLabel = new Label(GameVariables.EMPTY, new Label.LabelStyle(font, Color.WHITE));

        leftTable.add(debugLabel).width(GameVariables.TOUCH_PAD_SIZE).top().expand().row();
    }

    private void setupTouchPad(){
        touchpad = new Touchpad(10,getTouchpadStyle());

        leftTable.add(touchpad).height(GameVariables.TOUCH_PAD_SIZE).width(GameVariables.TOUCH_PAD_SIZE).expand().bottom();
    }

    private void setupSideBar(){
        sideBar = new Table();
        //sideBar.setBackground(new TiledDrawable(GameTextures.getSideBarTexture()));
        rightTable.add(sideBar).width(GameVariables.SIDEBAR_WIDTH).top().right().expandY();

        for(int i =0; i < GameVariables.VISIBLE_PLAYER_ITEM_SLOTS; i++){
            sideBar.add(new Image(GameTextures.getSlotTexture())).width(GameVariables.SIDEBAR_WIDTH).height(GameVariables.SIDEBAR_WIDTH).row();
        }
    }


    private static Touchpad.TouchpadStyle getTouchpadStyle() {
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", GameTextures.getTouchPadBackgroundTexture());

        touchpadSkin.add("touchKnob", GameTextures.getTouchPadKnobTexture());

        touchpadStyle.background  = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob  = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.knob.setMinHeight(GameVariables.TOUCH_PAD_SIZE/2);
        touchpadStyle.knob.setMinWidth(GameVariables.TOUCH_PAD_SIZE/2);

        return touchpadStyle;
    }

    @Override
    public void dispose() {
        super.dispose();
        if(GameVariables.DEBUG) font.dispose();
    }
}
