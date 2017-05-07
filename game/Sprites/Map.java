package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Frank on 2017-04-25.
 */

public class Map {

    private Rectangle bounds;
    public Texture map;
    public int mapX;
    public int mapY;

    public Map(int x, int y, Texture texture){

        mapX = x;
        mapY = y;
        map = texture;
        bounds = new Rectangle(x - ArenaFighter.radius, y - ArenaFighter.radius ,map.getWidth()+(2* ArenaFighter.radius), map.getHeight()+(2* ArenaFighter.radius));

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

    public Rectangle getBounds(){

        return bounds;
    }

}
