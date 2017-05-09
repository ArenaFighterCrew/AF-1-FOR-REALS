package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Created by Frank on 2017-04-25.
 */

public class Map {

    private Rectangle bounds1;
    private Circle bounds2;
    //private Shape2D bounds;
    public Texture map;
    public int mapX;
    public int mapY;

    public Map(int x, int y, Texture texture, int type){

        mapX = x;
        mapY = y;
        map = texture;
        bounds1 = new Rectangle(x - ArenaFighter.radius, y - ArenaFighter.radius ,map.getWidth()+(2* ArenaFighter.radius), map.getHeight()+(2* ArenaFighter.radius));
        bounds2 = new Circle(x - ArenaFighter.radius, y - ArenaFighter.radius, (map.getWidth()+(2* ArenaFighter.radius))/2);

        /*switch (type) {
            //Rectangular
            case 1:
                //bounds1 = new Rectangle(x - ArenaFighter.radius, y - ArenaFighter.radius ,map.getWidth()+(2* ArenaFighter.radius), map.getHeight()+(2* ArenaFighter.radius));
                bounds = bounds1;
                break;
            //Circle
            case 2:
                bounds = bounds2;
                break;
        }*/


    }

    public int getMapX(){
        return mapX;
    }

    public int getMapY(){
        return mapY;
    }

    public Texture getTexture(){
        return map;
    }

    public Rectangle getBounds1(){
        return bounds1;
    }

    public Circle getBounds2(){
        return bounds2;
    }

    public void getBounds(int type){
        switch (type) {
            case 1:
                getBounds1();
                break;
            case 2:
                getBounds2();
                break;
        }

    }

}
