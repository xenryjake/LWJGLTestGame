package com.xenry.javatestgame.basictest;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * JavaTestGame created by Henry Jake on July 22, 2016.
 * Copyright 2016 Henry Jake.
 * All content in this file may not be used without written consent of Henry Jake.
 */
public class InputTest {

    public void start(){
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();

            while(!Display.isCloseRequested()) {
                pollInput();
                Display.update();
            }

            Display.destroy();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
    }

    public void pollInput(){
        if(Mouse.isButtonDown(0)){
            int x = Mouse.getX();
            int y = Mouse.getY();
            p("MOUSE DOWN @ X: " + x + " Y: " + y);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            p("SPACE KEY IS DOWN");
        }

        while(Keyboard.next()){
            if(Keyboard.getEventKeyState()){
                if(Keyboard.getEventKey() == Keyboard.KEY_A){
                    p("A Key Pressed!");
                }
                if(Keyboard.getEventKey() == Keyboard.KEY_S){
                    p("S Key Pressed!");
                }
                if(Keyboard.getEventKey() == Keyboard.KEY_D){
                    p("D Key Pressed!");
                }
            }else{
                if(Keyboard.getEventKey() == Keyboard.KEY_A){
                    p("A Key Released!");
                }
                if(Keyboard.getEventKey() == Keyboard.KEY_S){
                    p("S Key Released!");
                }
                if(Keyboard.getEventKey() == Keyboard.KEY_D){
                    p("D Key Released!");
                }
            }
        }
    }

    public static void main(String[] args){
        InputTest game = new InputTest();
        game.start();
    }

    public static void p(Object o){
        System.out.println(o);
    }

}
