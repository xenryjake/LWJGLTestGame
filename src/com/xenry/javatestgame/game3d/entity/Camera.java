package com.xenry.javatestgame.game3d.entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;
    private static final float Y_OFF = 2.8f;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 0;
    private float yaw = 0;
    private float roll = 0;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizD = calculateHorizontalDistance();
        float vertiD = calculateVerticalDistance();
        calculateCameraPosition(horizD, vertiD);
        this.yaw = 180 - (player.getLocation().getRotY() + angleAroundPlayer);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }

    public float getYaw() {
        return yaw;
    }

    private void calculateCameraPosition(float horizD, float vertiD){
        float theta = player.getLocation().getRotY() + angleAroundPlayer;
        float offX = (float)(horizD * Math.sin(Math.toRadians(theta)));
        float offZ = (float)(horizD * Math.cos(Math.toRadians(theta)));
        position.x = player.getLocation().getX() - offX;
        position.z = player.getLocation().getZ() - offZ;
        position.y = player.getLocation().getY() + Y_OFF + vertiD;
    }

    private void calculateZoom(){
        distanceFromPlayer -= Mouse.getDWheel() * 0.1f;
        if(distanceFromPlayer < 4)
            distanceFromPlayer = 4;
        if(distanceFromPlayer > 100)
            distanceFromPlayer = 100;
    }

    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            pitch -= Mouse.getDY() * 0.1f;
        }
        if(pitch > 90)
            pitch = 90;
        if(pitch < -90)
            pitch = -90;
    }

    private void calculateAngleAroundPlayer(){
        if(Mouse.isButtonDown(0)){
            angleAroundPlayer -= Mouse.getDX() * 0.3f;
        }
    }

    private float calculateHorizontalDistance(){
        return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

}
