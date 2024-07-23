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
    PImage runSprite;
    PImage jump;
    PImage fall;
    PImage keep;
    boolean dFlag;
    Character virtualGuy;
    float counter;
    float runCounter;
    
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
        dFlag = true;
    }

    public void setupImage() {
        backgroundImg = loadImage("src/main/resources/Tanks/basic.png");
        appleImg = loadImage("src/main/resources/Tanks/tempCharacter.png");
        //I use the "virtual guy" for our character
        idleSprite = loadImage("src/main/resources/Tanks/Free/Main Characters/Virtual Guy/Idle (32x32).png");
        runSprite = loadImage("src/main/resources/Tanks/Free/Main Characters/Virtual Guy/Run (32x32).png");
        keep = idleSprite;
        jump = loadImage("src/main/resources/Tanks/Free/Main Characters/Virtual Guy/Jump (32x32).png");
        fall = loadImage("src/main/resources/Tanks/Free/Main Characters/Virtual Guy/Fall (32x32).png");
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        if (event.getKeyCode() == 65) {
            virtualGuy.moveLeft = true;
            dFlag = false;
        }
        if (event.getKeyCode() == 68) {
            virtualGuy.moveRight = true;
            dFlag = true;
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
        int runIndex = (int)Math.floor(runCounter);
        idleSprite.resize(cellSize*11,cellSize);
        runSprite.resize(cellSize*12,cellSize);
        jump.resize(cellSize,cellSize);
        fall.resize(cellSize,cellSize);
        
        PImage idleFlipped = flipHorizontal(idleSprite);
        PImage runFlipped = flipHorizontal(runSprite);
        PImage jumpFlipped = flipHorizontal(jump);
        PImage fallFlipped = flipHorizontal(fall);

        if (virtualGuy.onAir == true) {
            if (dFlag == true) {
                if (virtualGuy.yVol <= 0) {
                    image(jump, virtualGuy.x, virtualGuy.y);
                }
                else {
                    image(fall, virtualGuy.x, virtualGuy.y);
                }
            }
            else {
                if (virtualGuy.yVol <= 0) {
                    image(jumpFlipped, virtualGuy.x, virtualGuy.y);
                }
                else {
                    image(fallFlipped, virtualGuy.x, virtualGuy.y);
                }
            }
        }
        else {
            if (virtualGuy.moveRight == true) {
                keep = idleSprite;
                image(runSprite, virtualGuy.x, virtualGuy.y, cellSize, cellSize, runIndex * cellSize, 0, (runIndex + 1) * cellSize, cellSize);
            }
            else if (virtualGuy.moveLeft == true) {
                keep = idleFlipped;
                image(runFlipped, virtualGuy.x, virtualGuy.y, cellSize, cellSize, runIndex * cellSize, 0, (runIndex + 1) * cellSize, cellSize);
            }
            else {
                image(keep, virtualGuy.x, virtualGuy.y, cellSize, cellSize, index * cellSize, 0, (index + 1) * cellSize, cellSize);
            }
        }
        
        
        
        runCounter += 0.5;
        counter += 0.5;
        //System.out.println(index);

        if (runCounter >= 12) {
            runCounter = 0;
        }

        if (counter >= 11) {
            counter = 0;
        }
    }

    public PImage flipHorizontal(PImage img) {
        int w = img.width;
        int h = img.height;
        
        PImage flippedImg = createImage(w, h, ARGB);
        
        img.loadPixels();
        flippedImg.loadPixels();

        int[] temp = new int[w];

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                temp[w - 1 - x] = img.pixels[y * w + x];
            }
            for (int x = 0; x < w; x++) {
                flippedImg.pixels[y * w + x] = temp[x];
            }
        }

        img.updatePixels();
        flippedImg.updatePixels();
        return flippedImg;
    }


    public static void main(String[] args) {
        PApplet.main("Tanks.App");
    }

}