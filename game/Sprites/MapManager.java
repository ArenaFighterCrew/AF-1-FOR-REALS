package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.States.PlayState;
import com.badlogic.gdx.graphics.Texture;
import com.sun.media.sound.MidiUtils;

/**
 * Created by Frank on 2017-04-25.
 */

public class MapManager {


    private static Texture map1 = new Texture("Map1.png");
    private static int map1X = (ArenaFighter.WIDTH - map1.getWidth()) / 2;
    private static int map1Y = (ArenaFighter.HEIGHT - ArenaFighter.border.getHeight() - map1.getHeight()) / 2;

    public static Map map = new Map(map1X, map1Y, map1);



}
