package com.jiedro.canels.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * Created by jiexdrop on 15/06/17.
 */

public class Tile extends Sprite {
    private int id = 0;

    private boolean walkable = true;

    public Tile(TextureRegion textureRegion, int id){
        super(textureRegion.getTexture());
        this.id = id;
    }

    public Tile(TextureRegion textureRegion, int id, boolean walkable){
        super(textureRegion.getTexture());
        this.id = id;
        this.walkable = walkable;
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
}
