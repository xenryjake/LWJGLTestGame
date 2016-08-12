package com.xenry.javatestgame.game3d;

import com.xenry.javatestgame.game3d.entity.*;
import com.xenry.javatestgame.game3d.guis.GuiRenderer;
import com.xenry.javatestgame.game3d.guis.GuiTexture;
import com.xenry.javatestgame.game3d.models.TexturedModel;
import com.xenry.javatestgame.game3d.render.DisplayManager;
import com.xenry.javatestgame.game3d.render.loader.Loader;
import com.xenry.javatestgame.game3d.render.MasterRenderer;
import com.xenry.javatestgame.game3d.render.loader.OBJFileLoader;
import com.xenry.javatestgame.game3d.terrain.Terrain;
import com.xenry.javatestgame.game3d.terrain.World;
import com.xenry.javatestgame.game3d.texture.ModelTexture;
import com.xenry.javatestgame.game3d.texture.TerrainTexture;
import com.xenry.javatestgame.game3d.texture.TerrainTexturePack;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Game3D {

    private Loader loader;
    private MasterRenderer renderer;
    private GuiRenderer guiRenderer;
    private World world;

    public Game3D() {}

    public static void main(){
        Game3D game = new Game3D();
        game.start();
    }

    public void start(){
        init();
        loop();
        end();
    }

    /**
     * Initiate the starting of the game, setup
     */
    private void init(){
        DisplayManager.create();
        loader = new Loader();
        renderer = new MasterRenderer();
        guiRenderer = new GuiRenderer(loader);
        world = new World("world", new Random().nextLong());
    }

    /**
     * Begin the loop
     */
    private void loop(){
        // TERRAIN TEXTURE PACK
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        // ENTITY TEXTURES
        TexturedModel tree = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("tree")), new ModelTexture(loader.loadTexture("tree"), 5, 0.2f));
        TexturedModel lowPolyTree = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lowPolyTree")), new ModelTexture(loader.loadTexture("lowPolyTree"), 5, 0.2f));
        TexturedModel grass = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")), new ModelTexture(loader.loadTexture("grassTexture"), true, true, 5, 0.2f).setNumberOfRows(4));
        //TexturedModel flower = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")), new ModelTexture(loader.loadTexture("flower"), true, true, 5, 0.2f));
        TexturedModel fern = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("fern")), new ModelTexture(loader.loadTexture("fern"), true, true, 5, 0.2f).setNumberOfRows(2));

        // TERRAIN GENERATION
        int lowTer = -1;
        int upTer = 1;
        for(int x = lowTer; x <= upTer; x++){
            for(int z = lowTer; z <= upTer; z++){
                world.addTerrain(new Terrain(x, z, loader, texturePack, blendMap, "heightMap"));
            }
        }

        // ENTITY GENERATION
        Random random = world.getRandom();
        for(int i = 0; i < 1000 * ((upTer - lowTer) * (upTer - lowTer)); i++){
            if(i % 2 == 0){
                Terrain terrain = world.getTerrain(random.nextInt(upTer - lowTer + 1) + lowTer, random.nextInt(upTer - lowTer + 1) + lowTer);
                float x = random.nextFloat() * Terrain.SIZE + terrain.getX();
                float z = random.nextFloat() * Terrain.SIZE + terrain.getZ();
                float y = terrain.getHeightOfTerrain(x, z);
                world.addEntity(new Entity(i % 4 == 0 ? tree : lowPolyTree, i % 4 == 0 ? 3 : 0.3f, new Location(world, x, y, z, random.nextFloat() * 360)));
            }
            {
                Terrain terrain = world.getTerrain(random.nextInt(upTer - lowTer + 1) + lowTer, random.nextInt(upTer - lowTer + 1) + lowTer);
                float x = random.nextFloat() * Terrain.SIZE + terrain.getX();
                float z = random.nextFloat() * Terrain.SIZE + terrain.getZ();
                float y = terrain.getHeightOfTerrain(x, z);
                world.addEntity(new Entity(grass, random.nextInt(9), 1, new Location(world, x, y, z, random.nextFloat() * 360)));
            }
            if(i % 2 == 0){
                Terrain terrain = world.getTerrain(random.nextInt(upTer - lowTer + 1) + lowTer, random.nextInt(upTer - lowTer + 1) + lowTer);
                float x = random.nextFloat() * Terrain.SIZE + terrain.getX();
                float z = random.nextFloat() * Terrain.SIZE + terrain.getZ();
                float y = terrain.getHeightOfTerrain(x, z);
                world.addEntity(new Entity(fern, random.nextInt(4), 0.6f, new Location(world, x, y, z, random.nextFloat() * 360)));
            }
        }
        Light sun = new Light(new Vector3f(20000, 20000, 20000), new Vector3f(1, 1, 1));

        TexturedModel playerModel = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("person")), new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(playerModel, 0.3f, new Location(world, 0, 0, 0));
        Camera camera = new Camera(player);

        // GUI: wip
        //List<GuiTexture> guis = new ArrayList<>();
        //GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
        //guis.add(gui);

        // ACTUAL LOOP
        while(!Display.isCloseRequested()){
            camera.move();
            player.move();
            renderer.processEntity(player);
            world.process(renderer);
            renderer.render(sun, camera);
            //guiRenderer.render(guis);
            DisplayManager.update();
        }
    }

    /**
     * End the game & close the window
     */
    private void end(){
        guiRenderer.cleanup();
        world.cleanup();
        renderer.cleanup();
        loader.cleanup();
        DisplayManager.close();
    }

    private float rFT(Random random, float up, float low) {
        //return random.nextFloat() * (((up) * Terrain.SIZE) - (low * Terrain.SIZE)) + (low * Terrain.SIZE);
        return rand(random, up * Terrain.SIZE, low * Terrain.SIZE);
    }

    private float rand(Random random, float max, float min){
        return random.nextFloat() * (max - min) + min;
    }

}
