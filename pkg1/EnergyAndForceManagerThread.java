
package af.pkg1;

import java.util.ArrayList;

public class EnergyAndForceManagerThread extends Thread{
    ArrayList<Fighter> fighters = new ArrayList<Fighter>();
    static int TIMESTEP = 1000/40;
    static double FRICTION_MULTIPLIER = 0.9;
    
    @Override
    public void run(){
        long timer = System.currentTimeMillis() + 50;
        
        while(true){                                    //loop forever
            if(System.currentTimeMillis() > timer){     //once timestep is reached
                for(Fighter f: fighters){               //for each fighter...
                    updateFighter(f);
                }
                //sets the next timer trigger
                timer += TIMESTEP;
            }
        }
    }
    public void addFighter(Fighter f){
        
        //NOTE: will not accept new fighters once the thread is run
        
        if(!this.isAlive()){
            fighters.add(f);
            System.out.println("Fighter added to Thread.");
        }
        else
            System.out.println("Fighter not added: Thread already running.");
    }
    public void updateFighter(Fighter f){
        double positionX = f.getTranslateX();
        double netForceX = f.getNetForceX();
        double velocityX = f.getVelocityX();
        double accelerationX = f.getAccelerationX();
        
        double positionY = f.getTranslateY();
        double netForceY = f.getNetForceY();
        double velocityY = f.getVelocityY();
        double accelerationY = f.getAccelerationY();
        
        double frictionForce = f.getMaxFrictionForce();
        int mass = f.getMass();
        
                    
        //adjusts accelerationX if forceX is greater than the max friction, otherwise sets to 0
        if(netForceX > 0 && netForceX > frictionForce){
            accelerationX = (netForceX - frictionForce) / mass;
        }
        else if(netForceX < 0 && Math.abs(netForceX) > frictionForce){
            accelerationX = (netForceX + frictionForce) / mass;
        }
        else if(Math.abs(velocityX) < 0.25){
            velocityX = 0;
            accelerationX = 0;
        }
        else{
            accelerationX = 0;
        }
                    
        //adjusts the position based on velocity and acceleration
        positionX += TIMESTEP / 10 * (velocityX + TIMESTEP * accelerationX / 2);
        
        //adjusts the velocity based on acceleration
        velocityX += TIMESTEP * accelerationX / 2;
        velocityX *= FRICTION_MULTIPLIER;
                    
        //adjusts accelerationY if forceY is greater than the max friction, otherwise sets to 0
        if(netForceY > 0 && netForceY > frictionForce){
            accelerationY = (netForceY - frictionForce) / mass;
        }
        else if(netForceY < 0 && Math.abs(netForceY) > frictionForce){
            accelerationY = (netForceY + frictionForce) / mass;
        }
        else if(Math.abs(velocityY) < 0.25){
            velocityY = 0;
            accelerationY = 0;
        }
        else{
            accelerationY = 0;
        }
                  
        //adjusts positionY based on velocityY and accelerationY
        positionY += TIMESTEP / 10 * (velocityY + TIMESTEP * accelerationY / 2);
              
        //adjusts velocityY based on accelerationY
        velocityY += TIMESTEP * accelerationY / 2;
        velocityY *= FRICTION_MULTIPLIER;
                   
        f.setAccelerationX(accelerationX);
        f.setTranslateX(positionX);
        f.setVelocityX(velocityX);
                   
        f.setAccelerationY(accelerationY);
        f.setTranslateY(positionY);
        f.setVelocityY(velocityY);
    }
}
