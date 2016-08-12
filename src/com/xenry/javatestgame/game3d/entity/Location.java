package com.xenry.javatestgame.game3d.entity;

import com.xenry.javatestgame.game3d.terrain.World;
import org.lwjgl.util.vector.Vector3f;

/**
 * JavaTestGame created by Henry Jake on July 25, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Location {

    private World world;
    private float x;
    private float y;
    private float z;
    private float rotX = 0;
    private float rotY = 0;
    private float rotZ = 0;

    public Location(World world, float x, float y, float z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(World world, float x, float y, float z, float rotY){
        this(world, x, y, z);
        this.rotY = rotY;
    }

    public World getWorld() {
        return world;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getRotX() {
        return rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public Vector3f getVectorLocation(){
        return new Vector3f(x, y, z);
    }

    public void add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void addRot(float rotX, float rotY, float rotZ){
        this.rotX += rotX;
        this.rotY += rotY;
        this.rotZ += rotZ;
    }

}
