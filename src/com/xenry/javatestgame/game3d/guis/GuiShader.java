package com.xenry.javatestgame.game3d.guis;

import com.xenry.javatestgame.game3d.shader.ShaderProgram;
import org.lwjgl.util.vector.Matrix4f;

/**
 * JavaTestGame created by Henry Jake on August 02, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/com/xenry/javatestgame/game3d/guis/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/com/xenry/javatestgame/game3d/guis/guiFragmentShader.glsl";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
