package Tanks;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 0;
    public static int WIDTH = 864; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE;
    public static final int BOARD_HEIGHT = 20;

    public static final int FPS = 30;

    public String configPath;

    public static Random random = new Random();

    PImage backgroundImg;
    PImage appleImg;
    PImage idleSprite;
    Character virtualGuy;
    boolean jumped;
    float counter;
    
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
		//See PApplet javadoc:
		//loadJSONObject(configPath)
		//loadImage(this.getClass().getResource(filename).getPath().toLowerCase(Locale.ROOT).replace("%20", " "));
        setupImage();
        appleImg.resize(100, 100);
        virtualGuy = new Character(200,400);
    }

    public void setupImage() {
        backgroundImg = loadImage("src/main/resources/Tanks/basic.png");
        appleImg = loadImage("src/main/resources/Tanks/tempCharacter.png");
        //I use the "virtual guy" for our character
        idleSprite = loadImage("src/main/resources/Tanks/Free/Main Characters/Virtual Guy/Idle (32x32).png");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        if (event.getKeyCode() == 65) {
            virtualGuy.moveLeft = true;
        }
        if (event.getKeyCode() == 68) {
            virtualGuy.moveRight = true;
        }

        if (event.getKeyCode() == 87) {
            if (virtualGuy.onAir == false) {
                virtualGuy.yVol -= 30;
            }
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(KeyEvent event){
        if (event.getKeyCode() == 65) {
            virtualGuy.moveLeft = false;
        }
        if (event.getKeyCode() == 68) {
            virtualGuy.moveRight = false;
        }
        if (event.getKeyCode() == 87) {
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO - powerups, like repair and extra fuel and teleport
        


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        //----------------------------------
        //display HUD:
        //----------------------------------
        //TODO
        background(backgroundImg);
  
        processCharacterSprite();
        
        //image(spriteSheet, x, y, spriteWidth, spriteHeight, x1, y1, x2, y2);
        
        virtualGuy.checkMovement();
        //----------------------------------
        //display scoreboard:
        //----------------------------------
        //TODO
        
		//----------------------------------
        //----------------------------------

        //TODO: Check user action
    }

    public void processCharacterSprite() {
        int cellSize = 100;
        int index = (int)Math.floor(counter);
        idleSprite.resize(cellSize*11,cellSize);
        image(idleSprite, virtualGuy.x, virtualGuy.y, cellSize, cellSize, index * cellSize, 0, (index + 1) * cellSize, cellSize);
        counter += 0.5;
        //System.out.println(index);

        if (counter >= 11) {
            counter = 0;
        }
    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}