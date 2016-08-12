package com.xenry.javatestgame.game3d.render.loader;

import com.xenry.javatestgame.game3d.models.RawModel;
import com.xenry.javatestgame.game3d.render.Cleanable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Loader implements Cleanable {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    /**
     * Load model data to a VAO.
     *
     * @param positions the array of positions
     * @param textureCoords the array of texture coordinates
     * @param normals the array of normals
     * @param indicies the array of indicies
     * @return the RawModel with a loaded VAO
     */
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indicies){
        int vaoID = createVAO();
        bindIndicesBuffer(indicies);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indicies.length);
    }

    /**
     * Load basic model data to a VAO.
     *
     * @param positions the array of positions
     * @return the RawModel with a loaded VAO
     */
    public RawModel loadToVAO(float[] positions){
        int vaoID = createVAO();
        this.storeDataInAttributeList(0, 2, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length/2);
    }

    /**
     * Load model data to a VAO from a ModelData object.
     *
     * @param data the ModelData object with model data
     * @return the RawModel with a loaded VAO
     */
    public RawModel loadToVAO(ModelData data){
        return loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
    }

    /**
     * Load a texture file from the resources.
     *
     * @param fileName the name of the file, without path or extension
     * @return the GL texture id
     */
    public int loadTexture(String fileName){
        Texture texture = null;
        try{
            texture = TextureLoader.getTexture("PNG", new FileInputStream("resources/" + fileName + ".png"));
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.2f);
        }catch(FileNotFoundException ex){
        }catch(IOException ex){
            ex.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    /**
     * Cleanup data to prevent memory leaks at shutdown.
     */
    public void cleanup(){
        for(int vao : vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture : textures){
            GL11.glDeleteTextures(texture);
        }
    }

    /**
     * Create an empty VAO.
     *
     * @return the VAO id
     */
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Put data in an attribute list.
     *
     * @param attributeNumber id of the attribute
     * @param coordSize size of the coords
     * @param data the data to be stored
     */
    private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Unbind the currently bound VAO.
     */
    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
