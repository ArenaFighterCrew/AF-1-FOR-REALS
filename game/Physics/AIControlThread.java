package com.arenafighter.game.Physics;

import com.arenafighter.game.AITasks.AITask;
import com.arenafighter.game.Sprites.AIFighter;
import com.arenafighter.game.AITasks.*;
import com.arenafighter.game.Sprites.Fighter;
import com.arenafighter.game.States.PlayState;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.TargetLostException;

/**
 * Created by Winged_Shade on 5/11/2017.
 */

public class AIControlThread extends Thread {
    private AIFighter avatar;
    private AITask currentTask = new GoToPositionTask(PlayState.map);

    private long timer;
    int TIMESTEP = EnergyAndForceManagerThread.TIMESTEP;

    boolean keepRunning = true;
    boolean paused = false;

    public AIControlThread(AIFighter fighter){
        avatar = fighter;
    }

    public void run(){
        timer = System.currentTimeMillis() + TIMESTEP;
        assignNewTask();
        while(keepRunning){
            if(!paused && System.currentTimeMillis() > timer){
                if(currentTask instanceof ApproachFighterTask){
                    //handle ApproachFighterTask
                    handleApproachFighterTask();
                }
                else if(currentTask instanceof GoToPositionTask){
                    //handle GoToPositionTask
                    handleGoToPositionTask();
                }
                else if(currentTask instanceof DashTask){
                    //handle DashTask
                    handleDashTask();
                }
                //NEXT TIMER TRIGGER
                timer += TIMESTEP;
            }
        }
    }

    public void assignNewTask(){
        long t = System.currentTimeMillis();
        while(System.currentTimeMillis() < t + 500 * AIFighter.DIFFICULTY){

        }

        if(currentTask instanceof ApproachFighterTask){
            Fighter target = ((ApproachFighterTask)currentTask).getTarget();
            currentTask = new DashTask(target);
            System.out.println("AI " + avatar.getIndex() + ": ApproachTask -> DashTask");
        }
        else if(currentTask instanceof GoToPositionTask){
            int random = (int)(Math.random() * 10);
            if(random <= 4) {
                currentTask = new ApproachFighterTask(avatar.getIndex());
                System.out.println("AI " + avatar.getIndex() + ": GoToPositionTask -> ApproachFighterTask");
            }
            else {
                currentTask = new GoToPositionTask(PlayState.map);
                System.out.println("AI " + avatar.getIndex() + ": GoToPositionTask -> GoToPositionTask");
            }
        }
        else if(currentTask instanceof DashTask){
            currentTask = new GoToPositionTask(PlayState.map);
            System.out.println("AI " + avatar.getIndex() + ": DashTask -> GoToPositionTask");
        }
    }
    public void handleGoToPositionTask(){
        GoToPositionTask gtt = (GoToPositionTask)currentTask;
        int fighterX = (int)avatar.getPosition().x;
        int fighterY = (int)avatar.getPosition().y;

        int targetX = gtt.getX();
        int targetY = gtt.getY();

        if(fighterX > targetX + AITask.POSITION_LEEWAY){
            avatar.walk(2, true);
            avatar.walk(0, false);
            //System.out.println("Player " + avatar.getIndex() + " walk left");
            gtt.setXReached(false);
        }
        else if(fighterX < targetX - AITask.POSITION_LEEWAY){
            avatar.walk(0, true);
            avatar.walk(2, false);
            //System.out.println("Player " + avatar.getIndex() + " walk right");
            gtt.setXReached(false);
        }
        else {
            avatar.walk(0, false);
            avatar.walk(2, false);
            //System.out.println("Player " + avatar.getIndex() + " reached target X");
            gtt.setXReached(true);
        }

        if(fighterY > targetY + AITask.POSITION_LEEWAY){
            avatar.walk(3, true);
            avatar.walk(1, false);
            //System.out.println("Player " + avatar.getIndex() + " walk left");
            gtt.setYReached(false);
        }
        else if(fighterX < targetX - AITask.POSITION_LEEWAY){
            avatar.walk(1, true);
            avatar.walk(3, false);
            //System.out.println("Player " + avatar.getIndex() + " walk right");
            gtt.setYReached(false);
        }
        else {
            avatar.walk(1, false);
            avatar.walk(3, false);
            //System.out.println("Player " + avatar.getIndex() + " reached target Y");
            gtt.setYReached(true);
        }

        if(gtt.getXReached() && gtt.getYReached())
            assignNewTask();

    }
    public void handleApproachFighterTask(){
        ApproachFighterTask aft = (ApproachFighterTask)currentTask;
        if(aft.getTarget().getDead()){
            assignNewTask();
            return;
        }
        int fighterX = (int)avatar.getPosition().x;
        int fighterY = (int)avatar.getPosition().y;

        int targetX = aft.getX();
        int targetY = aft.getY();

        if(fighterX > targetX + AITask.APPROACH_RANGE){
            avatar.walk(2, true);
            avatar.walk(0, false);
            //System.out.println("Player " + avatar.getIndex() + " walk left");
            aft.setXReached(false);
        }
        else if(fighterX < targetX - AITask.APPROACH_RANGE){
            avatar.walk(0, true);
            avatar.walk(2, false);
            //System.out.println("Player " + avatar.getIndex() + " walk right");
            aft.setXReached(false);
        }
        else {
            avatar.walk(0, false);
            avatar.walk(2, false);
            //System.out.println("Player " + avatar.getIndex() + " reached target X");
            aft.setXReached(true);
        }

        if(fighterY > targetY + AITask.APPROACH_RANGE){
            avatar.walk(3, true);
            avatar.walk(1, false);
            //System.out.println("Player " + avatar.getIndex() + " walk left");
            aft.setYReached(false);
        }
        else if(fighterX < targetX - AITask.APPROACH_RANGE){
            avatar.walk(1, true);
            avatar.walk(3, false);
            //System.out.println("Player " + avatar.getIndex() + " walk right");
            aft.setYReached(false);
        }
        else {
            avatar.walk(1, false);
            avatar.walk(3, false);
            //System.out.println("Player " + avatar.getIndex() + " reached target Y");
            aft.setYReached(true);
        }

        if(aft.getXReached() && aft.getYReached())
            assignNewTask();

    }
    public void handleDashTask(){
        DashTask dt = (DashTask)currentTask;

        if(!dt.getTarget().getDead())
            avatar.dash(dt.getTarget().getPosition().x, dt.getTarget().getPosition().y);
        assignNewTask();
    }
    public void pause(){
        if(!paused) {
            paused = true;
            avatar.removeForce(0);
            avatar.removeForce(1);
            avatar.removeForce(2);
            avatar.removeForce(3);
            avatar.setVelocityX(0);
            avatar.setVelocityY(0);
            avatar.setAccelerationX(0);
            avatar.setAccelerationY(0);

            avatar.isLeft = false;
            avatar.isRight = false;
            avatar.isUp = false;
            avatar.isDown = false;
            System.out.println("AI thread paused");
        }
    }
    public void unpause(){
        if(paused) {
            timer = System.currentTimeMillis() + TIMESTEP;
            paused = false;

            avatar.isLeft = false;
            avatar.isRight = false;
            avatar.isUp = false;
            avatar.isDown = false;
            this.assignNewTask();

            System.out.println("AI thread unpaused");
        }
    }
}
