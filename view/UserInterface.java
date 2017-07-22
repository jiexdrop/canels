package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.ItemSlot;
import com.jiedro.canels.model.world.World;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Display user controls
 * Created by jiexdrop on 30/06/17.
 */

public class UserInterface extends Stage implements Observer {
    private World world;

    private Touchpad touchpad;

    private BitmapFont font;

    private Table mainTable;

    private Table leftTable;

    private Table rightTable;

    private Label debugLabel;

    private Table sideBar;

    private ArrayList<ImageTextButton> itemSlots;


    public UserInterface(World world, Batch batch){
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

        setupDebugLabel();
        setupTouchPad();

        setupSideBar();

        world.addObserver(this);
        this.addActor(mainTable);
    }

    @Override
    public void draw(){
        super.draw();

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

    private void updateItemSlots(){
        for (int i = 0; i < world.getPlayer().getItemSlots().size(); i++) {
            ItemSlot item = world.getPlayer().getItemSlots().get(i);
            itemSlots.get(i).setText(item.toString());
            itemSlots.get(i).getStyle().imageUp =
                    new TextureRegionDrawable(GameTextures.getTextureRegionByName(item.getItemName()))
                            .tint(item.getItemColor());

        }
    }

    private void setupSideBar(){
        sideBar = new Table();
        rightTable.add(sideBar).width(GameVariables.SIDEBAR_WIDTH).top().right().expandY();
        itemSlots = new ArrayList<ImageTextButton>();

        for(int i =0; i < GameVariables.VISIBLE_PLAYER_ITEM_SLOTS; i++){

            ImageTextButton imageTextButton = new ImageTextButton(null, getImageTextButtonStyle());
            imageTextButton.clearChildren();

            imageTextButton.add(imageTextButton.getImage()).height(GameVariables.SIDEBAR_BUTTON_HEIGHT/2)
                    .width(GameVariables.SIDEBAR_BUTTON_HEIGHT/2).fill().row();
            imageTextButton.add(imageTextButton.getLabel());


            itemSlots.add(imageTextButton);
            sideBar.add(imageTextButton).width(GameVariables.SIDEBAR_WIDTH).height(GameVariables.SIDEBAR_BUTTON_HEIGHT).row();
        }

        for(int i =GameVariables.VISIBLE_PLAYER_ITEM_SLOTS; i <  world.getPlayer().getItemSlots().size(); i++) {
            ImageTextButton imageTextButton = new ImageTextButton(null, getImageTextButtonStyle());
            itemSlots.add(imageTextButton);
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

    private static ImageTextButton.ImageTextButtonStyle getImageTextButtonStyle() {
        TextureRegionDrawable buttonBackgroundUp = new TextureRegionDrawable(GameTextures.getSlotTexture());
        TextureRegionDrawable buttonBackgroundDown = new TextureRegionDrawable(GameTextures.getSlotOverlayTexture());
        TextureRegionDrawable buttonBackgroundChecked = new TextureRegionDrawable(GameTextures.getSlotOverlayTexture());
        ImageTextButton.ImageTextButtonStyle imageTextButtonStyle = new ImageTextButton.ImageTextButtonStyle(buttonBackgroundUp,
                buttonBackgroundDown,buttonBackgroundChecked, new BitmapFont());

        imageTextButtonStyle.imageUp = new TextureRegionDrawable(GameTextures.getTouchPadKnobTexture());

        return imageTextButtonStyle;
    }

    @Override
    public void dispose() {
        super.dispose();
        if(GameVariables.DEBUG) font.dispose();
    }

    @Override
    public void update(Observable observable, Object o) {
        updateItemSlots();

        if(GameVariables.DEBUG) {
            debugLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond()
                    + "\nRENDER_CALLS: " + GameVariables.RENDER_CALLS
                    + "\nPLAYER_POS: " + GameVariables.PLAYER_POSITION
                    + "\nENTITIES: " + GameVariables.ENTITIES
                    + "\nPLAYER_HEALTH: " + GameVariables.PLAYER_HEALTH);
        }
        else {
            debugLabel.setText(GameVariables.EMPTY);
        }

    }
}
