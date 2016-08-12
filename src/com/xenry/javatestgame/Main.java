package com.xenry.javatestgame;

import com.xenry.javatestgame.game3d.Game3D;

import java.io.File;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class Main {

    public static void main(String[] args){
        // NOTE: this must be updated when running on different systems to use the correct natives
        System.setProperty("org.lwjgl.librarypath", new File("native/macosx/").getAbsolutePath());
        Game3D.main();
    }

}
