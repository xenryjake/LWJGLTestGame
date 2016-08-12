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

    /**
     * Move the player (every frame). Takes frame rate
     * into account to have consistent movement speed
     * based on actual time.
     */
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

    /**
     * Check which keys are pressed and adjust
     * velocities accordingly.
     */
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

    /**
     * Adjust the upwards velocity of the player
     * when they jump.
     */
    private void jump(){
        if(isGrounded()){
            velUp += friction * DisplayManager.getFrameTimeSeconds();
            if(velUp > speedJump)
                velUp = speedJump;
        }
    }

    /**
     * Check if the player is on the ground.
     *
     * @return if the player is on the ground
     */
    public boolean isGrounded(){
        return location.getY() == location.getWorld().getTerrain(location).getHeightOfTerrain(location.getX(), location.getZ());
    }

}
