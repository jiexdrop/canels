package com.jiedro.canels.view;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jiedro.canels.GameVariables;

import java.util.ArrayList;

/**
 *
 * Created by jiexdrop on 15/06/17.
 */

public class Tile extends StaticTiledMapTile {
    public Tile(TextureRegion textureRegion, int id){
        super(textureRegion);
        super.setId(id);
    }


    public Tile addTextureRegion(TextureRegion textureRegion){
        if (!this.getTextureRegion().getTexture().getTextureData().isPrepared()) {
            this.getTextureRegion().getTexture().getTextureData().prepare();
        }

        if (!textureRegion.getTexture().getTextureData().isPrepared()) {
            textureRegion.getTexture().getTextureData().prepare();
        }

        Pixmap pixmap = this.getTextureRegion().getTexture().getTextureData().consumePixmap();

        pixmap.drawPixmap(textureRegion.getTexture().getTextureData().consumePixmap(),0,0);

        this.getTextureRegion().setTexture(new Texture(pixmap));
        return this;
    }
}
