package com.jiedro.canels.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.model.entity.Orientation;

/**
 * An animated entity with
 * Created by jiexdrop on 17/07/17.
 */

public class AnimatedEntity {
    Animation<TextureRegion> animation;

    TextureRegion stillFrame;

    public AnimatedEntity(TextureRegion[][] textureRegions, int frames){

        TextureRegion[] framesTextures = new TextureRegion[frames];
        int index = 0;
        for (int i = 0; i < frames; i++) {
            framesTextures[index++] = textureRegions[0][i];
        }

        animation = new Animation<TextureRegion>(0.25f, framesTextures);

        stillFrame = animation.getKeyFrame(0);
    }

    public TextureRegion getCurrentFrame(float elapsedTime, Orientation orientation){
        switch (orientation) {
            case LEFT:
                for (int i = 0; i < animation.getKeyFrames().length; i++) {
                    if(!animation.getKeyFrames()[i].isFlipX()) {
                        animation.getKeyFrames()[i].flip(true, false);
                    }
                }
                break;
            case RIGHT:
                for (int i = 0; i < animation.getKeyFrames().length; i++) {
                    if(animation.getKeyFrames()[i].isFlipX()) {
                        animation.getKeyFrames()[i].flip(true, false);
                    }
                }
                break;
            case STILL:
                return stillFrame;
        }

        return animation.getKeyFrame(elapsedTime, true);
    }



}
