package com.arenafighter.game.Physics;

/**
 * Created by Frank on 2017-04-15.
 */

public class Force extends Vector{

    public Force(int iMagnitude, double iAngle){
        super(iMagnitude, iAngle);
    }

    public Impact toImpact(){
        return new Impact((int)(this.getMagnitude() * FORCE_IMPACT_RATIO), this.getAngle());
    }
    /*public Force multiply(double scalar){
        return new Force((int)(getMagnitude() * scalar), getAngle());
    }*/
}
