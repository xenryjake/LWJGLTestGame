package com.xenry.javatestgame.game3d.render;

import com.xenry.javatestgame.game3d.entity.Entity;
import com.xenry.javatestgame.game3d.models.TexturedModel;
import com.xenry.javatestgame.game3d.shader.StaticShader;
import com.xenry.javatestgame.game3d.texture.ModelTexture;
import com.xenry.javatestgame.game3d.util.MathUtil;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class EntityRenderer {

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f matrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(matrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities){
        for(TexturedModel model : entities.keySet()){
            prepareTextureModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTextureModel(TexturedModel model){
        GL30.glBindVertexArray(model.getRawModel().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        shader.loadNumberOfRows(texture.getNumberOfRows());
        if(texture.hasTransparency()){
            MasterRenderer.disableCulling();
        }
        shader.loadFakeLighting(texture.usesFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel(){
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getLocation().getVectorLocation(), entity.getLocation().getRotX(), entity.getLocation().getRotY(), entity.getLocation().getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
    }

}
