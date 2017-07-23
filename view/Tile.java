package com.jiedro.canels.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 15/06/17.
 */

public class Tile extends Sprite {

    private String name = GameVariables.EMPTY;

    private boolean walkable;

    private int resistance;

    public Tile(TextureRegion textureRegion, String name, boolean walkable, Color color, int resistance){
        this.setRegion(textureRegion);

        this.walkable = walkable;
        this.setColor(color);
        this.name = name;
        this.resistance = resistance;

    }


    public Tile addTextureRegion(TextureRegion textureRegion){
        if (!this.getTexture().getTextureData().isPrepared()) {
            this.getTexture().getTextureData().prepare();
        }

        if (!textureRegion.getTexture().getTextureData().isPrepared()) {
            textureRegion.getTexture().getTextureData().prepare();
        }

        Pixmap pixmap = this.getTexture().getTextureData().consumePixmap();

        pixmap.drawPixmap(textureRegion.getTexture().getTextureData().consumePixmap(),0,0);

        this.setTexture(new Texture(pixmap));
        return this;
    }

    public boolean isWalkable(){
        return this.walkable;
    }

    public int getResistance() {
        return resistance;
    }

    public String getName() {
        return name;
    }
}
