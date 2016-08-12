package com.xenry.javatestgame.game3d.entity;

import com.xenry.javatestgame.game3d.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Entity {

    protected TexturedModel model;
    protected float scale;
    protected Location location;

    private int textureIndex = 0;

    public Entity(TexturedModel model, float scale, Location location){
        this.model = model;
        this.scale = scale;
        this.location = location;
    }

    public Entity(TexturedModel model, int textureIndex, float scale, Location location){
        this(model, scale, location);
        this.textureIndex = textureIndex;
    }

    /**
     * Get the X offset of the texture from a texture atlas.
     *
     * @return the x offset of the texture
     */
    public float getTextureXOffset(){
        return (float)(textureIndex%model.getTexture().getNumberOfRows())/(float)model.getTexture().getNumberOfRows();
    }

    /**
     * Get the Y offset of the texture from a texture atlas.
     *
     * @return the y offset of the texture
     */
    public float getTextureYOffset(){
        return (float)(textureIndex/model.getTexture().getNumberOfRows())/(float)model.getTexture().getNumberOfRows();
    }

    public void increaseScale(float scale){
        this.scale += scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Location getLocation() {
        return location;
    }

    public float getScale() {
        return scale;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}
