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

    /**
     * Perform camera move (every frame).
     */
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

    /**
     * Calculate the position of the camera.
     *
     * @param horizD the horizontal distance of the camera from the player
     * @param vertiD the vertical distance of the camera from the player
     */
    private void calculateCameraPosition(float horizD, float vertiD){
        double rad = Math.toRadians(player.getLocation().getRotY() + angleAroundPlayer);
        float offX = (float)(horizD * Math.sin(rad));
        float offZ = (float)(horizD * Math.cos(rad));
        position.x = player.getLocation().getX() - offX;
        position.z = player.getLocation().getZ() - offZ;
        position.y = player.getLocation().getY() + Y_OFF + vertiD;
    }

    /**
     * Calculate the zoom (distance from player) of
     * the camera. 1/10 of the distance the mouse
     * wheel is moved.
     *
     * Range: 4 (close) to 100 (wide)
     */
    private void calculateZoom(){
        distanceFromPlayer -= Mouse.getDWheel() * 0.1f;
        if(distanceFromPlayer < 4)
            distanceFromPlayer = 4;
        if(distanceFromPlayer > 100)
            distanceFromPlayer = 100;
    }

    /**
     * Calculate the pitch of the camera view.
     * 1/10 of the y distance of mouse motion when
     * right mouse button is down.
     *
     * Range: 90° to -90°
     */
    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            pitch -= Mouse.getDY() * 0.1f;
        }
        if(pitch > 90)
            pitch = 90;
        if(pitch < -90)
            pitch = -90;
    }

    /**
     * Calculate the angle of the camera around the player.
     * 3/10 the x distance of mouse motion when left mouse
     * button is down.
     */
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
