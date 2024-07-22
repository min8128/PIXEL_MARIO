package Tanks;

import processing.core.PApplet;
import processing.core.PImage;


public class Character {

    public float x;
    public float y;
    public float xVol;
    public float yVol;
    public float gravity;
    public boolean moveLeft;
    public boolean moveRight;
    public boolean jump;
    public boolean onAir;
    public boolean jumpable;

    public Character(float x, float y){
        this.x = x;
        this.y = y;
        this.xVol = 0;
        this.yVol = 0;
        this.gravity = 2;
        this.moveLeft = false;
        this.moveRight = false;
        this.onAir = false;

    }

    public void checkMovement() {
        this.checkLeftRight();
        this.checkUpDown();
    }

    public void checkLeftRight() {
        if (this.moveLeft == true && this.x >= 0) {
            this.x -= 10;
        }

        if (this.moveRight == true && this.x <= 864) {
            this.x += 10;
        }
    }


    public void checkUpDown() {

        //on air
        if (this.y < 400) {
            this.onAir = true;
            yVol += gravity;
            y += yVol;
        }
        //on ground
        else {
            if (this.yVol == 30) {
                this.yVol = 0;
            }
            this.onAir = false;
            if (yVol <= 0) {
                y += yVol;
            }
        }
       
    }
}