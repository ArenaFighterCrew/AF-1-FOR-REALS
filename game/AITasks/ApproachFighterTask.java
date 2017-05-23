package com.arenafighter.game.AITasks;

import com.arenafighter.game.Physics.EnergyAndForceManagerThread;
import com.arenafighter.game.Sprites.Fighter;
import com.arenafighter.game.Sprites.Map;
import com.arenafighter.game.States.PlayState;

/**
 * Created by Winged_Shade on 5/11/2017.
 */

public class ApproachFighterTask extends GoToPositionTask {
    private Fighter target;

    public ApproachFighterTask(int excludedTargetIndex){
        super(PlayState.map);
        int targetIndex = (int)(Math.random() * 3) + 1;
        if(targetIndex >= excludedTargetIndex)
            targetIndex++;
        target = EnergyAndForceManagerThread.getFighters().get(targetIndex - 1);
    }
    @Override
    public int getX(){
        return (int)target.getPosition().x;
    }
    @Override
    public int getY(){
        return (int)target.getPosition().y;
    }
    public Fighter getTarget(){
        return target;
    }
}
