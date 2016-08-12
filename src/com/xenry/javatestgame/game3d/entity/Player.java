package com.xenry.javatestgame.game3d.entity;

import com.xenry.javatestgame.game3d.models.TexturedModel;
import com.xenry.javatestgame.game3d.render.DisplayManager;

import static org.lwjgl.input.Keyboard.*;

/**
 * JavaTestGame created by Henry Jake on July 23, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Player extends Entity {

    private final float speed = 100, speedRot = 10000, speedJump = 0.4f;
    private final float friction = 7f, frictionRot = 15f, gravity = -1;
    private float velFor = 0, velRot = 0, velUp = 0;

    public Player(TexturedModel model, float scale, Location location){
        super(model, scale, location);
    }

    public void move(){
        checkInputs();
        if((0 < velFor && velFor < 0.1 * DisplayManager.getFrameTimeSeconds()) || (velFor < 0 && -0.1 * DisplayManager.getFrameTimeSeconds() < velFor)){
            velFor = 0;
        }
        if((0 < velRot && velRot < 0.1 * DisplayManager.getFrameTimeSeconds()) || (velRot < 0 && -0.1 * DisplayManager.getFrameTimeSeconds() < velRot)){
            velRot = 0;
        }

        velRot *= frictionRot * DisplayManager.getFrameTimeSeconds();
        location.addRot(0, velRot, 0);

        float dx = (float)(velFor * Math.sin(Math.toRadians(location.getRotY())));
        float dz = (float)(velFor * Math.cos(Math.toRadians(location.getRotY())));
        velFor *= friction * DisplayManager.getFrameTimeSeconds();
        velUp += gravity * DisplayManager.getFrameTimeSeconds();
        location.add(dx, velUp, dz);

        float terrainHeight = location.getWorld().getTerrain(location).getHeightOfTerrain(location.getX(), location.getZ());
        if(location.getY() < terrainHeight){
            velUp = 0;
            location.setY(terrainHeight);
        }
    }

    private void checkInputs(){
        if(isKeyDown(KEY_W) || isKeyDown(KEY_UP)){
            if(velFor < speed)
                velFor += friction * DisplayManager.getFrameTimeSeconds();
        }else if(isKeyDown(KEY_S) || isKeyDown(KEY_DOWN)){
            if(velFor > -speed)
                velFor -= friction * DisplayManager.getFrameTimeSeconds();
        }

        if(isKeyDown(KEY_A) || isKeyDown(KEY_LEFT)){
            if(velRot < speedRot)
                velRot += frictionRot * DisplayManager.getFrameTimeSeconds();
        }else if(isKeyDown(KEY_D) || isKeyDown(KEY_RIGHT)){
            if(velRot > -speedRot)
                velRot -= frictionRot * DisplayManager.getFrameTimeSeconds();
        }

        if(isKeyDown(KEY_SPACE)){
            jump();
        }
    }

    private void jump(){
        if(isGrounded()){
            velUp += friction * DisplayManager.getFrameTimeSeconds();
            if(velUp > speedJump)
                velUp = speedJump;
        }
    }

    public boolean isGrounded(){
        return location.getY() == location.getWorld().getTerrain(location).getHeightOfTerrain(location.getX(), location.getZ());
    }

    /*public void _move(){
        _checkInputs();

        location.addRot(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);

        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float)(distance * Math.sin(Math.toRadians(location.getRotY())));
        float dz = (float)(distance * Math.cos(Math.toRadians(location.getRotY())));
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        location.add(dx, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), dz);

        float terrainHeight = location.getWorld().getTerrain(location).getHeightOfTerrain(location.getX(), location.getZ());
        if(location.getY() < terrainHeight){
            upwardsSpeed = 0;
            isInAir = false;
            location.setY(terrainHeight);
        }
    }*/

    /*private void _checkInputs(){
        if(isKeyDown(KEY_W)){
            this.currentSpeed = RUN_SPEED;
        }else if(isKeyDown(KEY_S)){
            this.currentSpeed = -RUN_SPEED;
        }else{
            this.currentSpeed = 0;
        }

        if(isKeyDown(KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;
        }else if(isKeyDown(KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else{
            this.currentTurnSpeed = 0;
        }

        if(isKeyDown(KEY_SPACE)){
            jump();
        }
    }*/

}
