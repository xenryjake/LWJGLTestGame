package com.xenry.javatestgame.game3d.terrain;

import com.xenry.javatestgame.game3d.entity.Entity;
import com.xenry.javatestgame.game3d.entity.Location;
import com.xenry.javatestgame.game3d.render.Cleanable;
import com.xenry.javatestgame.game3d.render.MasterRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaTestGame created by Henry Jake on July 24, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class World implements Cleanable {

    private String name;
    private long seed;
    private List<Terrain> terrains;
    private List<Entity> entities;
    private Random random;

    public World(String name, long seed){
        this.name = name;
        this.seed = seed;
        this.terrains = new ArrayList<>();
        this.entities = new ArrayList<>();
        random = new Random(seed);
    }

    public String getName() {
        return name;
    }

    public long getSeed() {
        return seed;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Random getRandom(){
        return random;
    }

    public void addEntity(Entity entity){
        entities.add(entity);
    }

    public void addTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public Terrain getTerrain(int terrainX, int terrainZ){
        for(Terrain terrain : terrains){
            if(terrain.getX()/Terrain.SIZE == terrainX && terrain.getZ()/Terrain.SIZE == terrainZ){
                return terrain;
            }
        }
        return null;
    }

    public Terrain getTerrain(Location location){
        return getTerrain(location.getX(), location.getZ());
    }

    public Terrain getTerrain(float x, float z){
        for(Terrain terrain : terrains){
            if(x >= terrain.getX() && x < (terrain.getX() + Terrain.SIZE) && z >= terrain.getZ() && z < (terrain.getZ() + Terrain.SIZE)){
                return terrain;
            }
        }
        return null;
    }

    public void process(MasterRenderer renderer){
        for(Terrain terrain : terrains){
            renderer.processTerrain(terrain);
        }
        for(Entity entity : entities){
            renderer.processEntity(entity);
        }
    }

    public void cleanup(){
        entities.clear();
        terrains.clear();
    }

}
