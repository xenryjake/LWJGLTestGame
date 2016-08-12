package com.xenry.javatestgame.game3d.guis;

import com.xenry.javatestgame.game3d.models.RawModel;
import com.xenry.javatestgame.game3d.render.Cleanable;
import com.xenry.javatestgame.game3d.render.loader.Loader;
import com.xenry.javatestgame.game3d.util.MathUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

/**
 * JavaTestGame created by Henry Jake on August 02, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class GuiRenderer implements Cleanable {

    // NOTE: GUI is a WIP

    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer(Loader loader){
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVAO(positions);
        shader = new GuiShader();
    }

    public void render(List<GuiTexture> guis){
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for(GuiTexture gui : guis){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureID());
            Matrix4f matrix = MathUtil.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

}
