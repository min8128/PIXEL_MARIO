package Tanks;

import processing.core.PApplet;
import processing.core.PImage;


public class Character {
    public PImage characterImg;
    public float x;
    public float y;
    public Character(){
        this.x = 0;
        this.y = 0;
    }
    public void draw (PApplet app) {
        app.loadImage("src/main/resources/Tanks/tempCharacter.png");
        app.image(characterImg,x,y);
    }
}
