package com.arenafighter.game.Sprites;

import com.arenafighter.game.Physics.AIControlThread;

/**
 * Created by Winged_Shade on 5/10/2017.
 */

public class AIFighter extends Fighter {
    private AIControlThread AI;
    public static double DIFFICULTY = 1; //1 = easy, 0.5 = medium, 0 = hard

    public AIFighter(int x, int y, int color, int index) {
        super(x, y, color, index);
        AI = new AIControlThread(this);
    }
    public AIControlThread getControlThread(){
        return AI;
    }
}
