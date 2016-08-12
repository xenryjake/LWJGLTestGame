package com.xenry.javatestgame.game3d.texture;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class ModelTexture {

    private int textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    private int numberOfRows = 1;

    public ModelTexture(int textureID){
        this.textureID = textureID;
    }

    public ModelTexture(int textureID, boolean hasTransparency, boolean useFakeLighting){
        this(textureID);
        this.hasTransparency = hasTransparency;
        this.useFakeLighting = useFakeLighting;
    }

    public ModelTexture(int textureID, float shineDamper, float reflectivity){
        this(textureID);
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
    }

    public ModelTexture(int textureID, boolean hasTransparency, boolean useFakeLighting, float shineDamper, float reflectivity){
        this(textureID, hasTransparency, useFakeLighting);
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public boolean hasTransparency() {
        return hasTransparency;
    }

    public boolean usesFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public ModelTexture setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        return this;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

}
