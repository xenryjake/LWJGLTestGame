package com.xenry.javatestgame.game3d.entity;

import org.lwjgl.util.vector.Vector3f;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Light {

    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color){
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

}
