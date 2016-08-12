package com.xenry.javatestgame.game3d.guis;

import org.lwjgl.util.vector.Vector2f;

/**
 * JavaTestGame created by Henry Jake on August 02, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class GuiTexture {

    private int textureID;
    private Vector2f position;
    private Vector2f scale;

    public GuiTexture(int textureID, Vector2f position, Vector2f scale){
        this.textureID = textureID;
        this.position = position;
        this.scale = scale;
    }

    public int getTextureID() {
        return textureID;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }

}
