package com.xenry.javatestgame.game3d.render;

import com.xenry.javatestgame.game3d.entity.Camera;
import com.xenry.javatestgame.game3d.entity.Entity;
import com.xenry.javatestgame.game3d.entity.Light;
import com.xenry.javatestgame.game3d.models.TexturedModel;
import com.xenry.javatestgame.game3d.shader.StaticShader;
import com.xenry.javatestgame.game3d.shader.TerrainShader;
import com.xenry.javatestgame.game3d.terrain.Terrain;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * JavaTestGame created by Henry Jake on July 23, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class MasterRenderer implements Cleanable {

    private static final float FOV = 70,
            NEAR_PLANE = 0.1f,
            FAR_PLANE = 1000;

    private static final float SKY_RED = 135/255f,
                               SKY_GREEN = 206/255f,
                               SKY_BLUE = 250/255f;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private HashMap<TexturedModel,List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    private Matrix4f projectionMatrix;

    public MasterRenderer(){
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    public static void enableCulling(){
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public void render(Light sun, Camera camera){
        prepare();
        shader.start();
        shader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();

        terrainShader.start();
        terrainShader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();

        entities.clear();
        terrains.clear();
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void processEntity(Entity entity){
        TexturedModel model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if(batch != null){
            batch.add(entity);
        }else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(model, newBatch);
        }
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, 1);
    }

    private void createProjectionMatrix(){
        float aspectRatio = (float)Display.getWidth() / (float)Display.getHeight();
        float y_scale = (float)(1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanup(){
        terrainShader.cleanup();
        shader.cleanup();
    }

}
