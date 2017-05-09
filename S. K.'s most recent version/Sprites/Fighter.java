package com.arenafighter.game.Sprites;

import com.arenafighter.game.ArenaFighter;
import com.arenafighter.game.Physics.Force;
import com.arenafighter.game.Physics.Impact;
import com.arenafighter.game.Physics.Vector;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.awt.geom.AffineTransform;
import java.util.Stack;

/**
 * Created by Frank on 2017-04-15.
 */


/*import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;*/


public /*abstract*/ class Fighter /*extends StackPane*/{
    private int fighterIndex;
    private int mass;
    private double netForceX;
    private double netForceY;
    private double velocityX;
    private double velocityY;
    private double accelerationX;
    private double accelerationY;
    //private Shape icon;
    private int maxEnergy;
    private int currentEnergy;
    private boolean alive;
    private boolean bracing;

    //My variables
    private Texture player;
    private Rectangle bounds;
    public Vector3 position;



    public boolean isRight;
    public boolean isLeft;
    public boolean isUp;
    public boolean isDown;

    Force rightWalk = new Force(Vector.BASE_FORCE_MAGNITUDE, 0);
    Force leftWalk = (Force)rightWalk.getInverse();

    Impact rightDodge = rightWalk.toImpact();
    Impact leftDodge = leftWalk.toImpact();

    Force upWalk = new Force(Vector.BASE_FORCE_MAGNITUDE, Math.PI / 2);
    Force downWalk = (Force)upWalk.getInverse();

    Impact upDodge = upWalk.toImpact();
    Impact downDodge = downWalk.toImpact();

    private Force[] forces;
    private Stack<Impact> impacts;

    //private EnergyAndForceManagerThread manager;
    public static double FRICTION_CONSTANT = 0.5;
    public static double DIAGONAL_MULTIPLIER = 0.707;

    public Fighter(int x, int y){
        mass = 500;
        netForceX = 0;
        netForceY = 0;
        velocityX = 0;
        velocityY = 0;
        accelerationX = 0;
        accelerationY = 0;

        /*icon = new Circle(18);
        icon.setFill(Color.LIGHTGRAY);
        icon.setStroke(Color.LIGHTGRAY.darker().darker());
        icon.setStrokeWidth(4);*/

        position = new Vector3(x, y, 0);
        player = new Texture("White Circle.png");
        bounds = new Rectangle(x, y, player.getWidth(), player.getHeight());

        maxEnergy = 200;
        currentEnergy = 200;
        alive = true;
        bracing = false;
        forces = new Force[4];
        impacts = new Stack<Impact>();

        boolean isRight = false;
        boolean isLeft = false;
        boolean isUp = false;
        boolean isDown = false;

        //getChildren().add(icon);
    }
    public void collideWith(Fighter f){
        double x1 = this.getPosition().x;
        double y1 = this.getPosition().y;

        double x2 = f.getPosition().x;
        double y2 = f.getPosition().y;

        double angleBetween = Math.atan2(y2-y1, x2-x1);
        double vAngle1 = Math.atan2(this.getVelocityY(), this.getVelocityX());
        double vAngle2 = Math.atan2(f.getVelocityY(), f.getVelocityX());

        double deltaAngle1 = vAngle1 - angleBetween;
        double deltaAngle2 = vAngle2 - (angleBetween + Math.PI);

        double magF1toF2 = Math.cos(deltaAngle1) * this.getVelocityMagnitude();
        double magF2toF1 = Math.cos(deltaAngle2) * f.getVelocityMagnitude();

        double netMagnitude = (magF1toF2 + magF2toF1) * Vector.BOUNCINESS_FACTOR;

        System.out.println("Magnitude: " + netMagnitude);
        System.out.println("Angle: " + angleBetween);

        Impact imp = new Impact((int)netMagnitude, angleBetween);
        this.addImpact((Impact)imp.getInverse());
        f.addImpact(imp);
        /*
        double v1 = Math.sqrt(getVelocityX() * getVelocityX() + getVelocityY() * getVelocityY());
        double v2 = Math.sqrt(f.getVelocityX() * f.getVelocityX() + f.getVelocityY() * f.getVelocityY());
        double m1 = getMass();
        double m2 = f.getMass();

        double theta1, theta2;
        if(getVelocityX() != 0)
            theta1 = Math.atan(getVelocityY() / getVelocityX());
        else
            theta1 = 0;
        if(f.getVelocityX() != 0)
            theta2 = Math.atan(f.getVelocityY() / f.getVelocityX());
        else
            theta2 = 0;

        double dX = getTranslateX() - f.getTranslateX();
        double dY = getTranslateY() - f.getTranslateY();
        double phi = Math.atan(dY / dX);

        System.out.println("v1: " + v1);
        System.out.println("v2: " + v2);
        System.out.println("m1: " + m1);
        System.out.println("m2: " + m2);
        System.out.println("theta1: " + theta1);
        System.out.println("theta2: " + theta2);
        System.out.println("dX: " + dX);
        System.out.println("dY: " + dY);
        System.out.println("phi: " + phi);

        double vfx = v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi);
        System.out.println(vfx);
        vfx *= Math.cos(phi) / (m1 + m2);
        System.out.println(vfx);
        vfx += v1 * Math.sin(theta1 - phi) * Math.cos(phi + Math.PI / 2);
        System.out.println(vfx);

        double vfy = v1 * Math.cos(theta1 - phi) * (m1 - m2) + 2 * m2 * v2 * Math.cos(theta2 - phi);
        System.out.println(vfy);
        vfy *= Math.sin(phi) / (m1 + m2);
        System.out.println(vfy);
        vfy += v1 * Math.sin(theta1 - phi) * Math.sin(phi + Math.PI / 2);
        System.out.println(vfy);

        System.out.println("VelocityX: " + vfx);
        System.out.println("VelocityY: " + vfy);

        setVelocityX(vfx * 3);
        setVelocityY(vfy * 3);
        */
    }

    //My methods
    public Texture getTexture() {
        return player;
    }
    public Vector3 getPosition() {
        return position;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        player.dispose();
    }

    public void update(float dt){

        if(position.y < 0)
            position.y = 0;

        if(position.y > ArenaFighter.HEIGHT - player.getHeight())
            position.y = ArenaFighter.HEIGHT - player.getHeight();

        if(position.x < 0)
            position.x = 0;

        if(position.x > ArenaFighter.WIDTH - player.getWidth())
            position.x = ArenaFighter.WIDTH - player.getWidth();

        bounds.setPosition(position.x, position.y);

    }


    public void walk(int direction, boolean tf){
        if(tf){
            switch(direction){
                case 0:
                    if(!isRight){
                        isRight = true;
                        addForce(direction, rightWalk);
                    }
                    break;
                case 1:
                    if(!isUp){
                        isUp = true;
                        addForce(direction, upWalk);
                    }
                    break;
                case 2:
                    if(!isLeft){
                        isLeft = true;
                        addForce(direction, leftWalk);
                    }
                    break;
                case 3:
                    if(!isDown){
                        isDown = true;
                        addForce(direction, downWalk);
                    }
                    break;
            }
        }
        else{
            switch(direction){
                case 0:
                    if(isRight){
                        isRight = false;
                        removeForce(direction);
                    }
                    break;
                case 1:
                    if(isUp){
                        isUp = false;
                        removeForce(direction);
                    }
                    break;
                case 2:
                    if(isLeft){
                        isLeft = false;
                        removeForce(direction);
                    }
                    break;
                case 3:
                    if(isDown){
                        isDown = false;
                        removeForce(direction);
                    }
                    break;
            }
        }
    }

    public void dodge(){
        if(isRight)
            addImpact(rightDodge);
        if(isUp)
            addImpact(upDodge);
        if(isLeft)
            addImpact(leftDodge);
        if(isDown)
            addImpact(downDodge);
        System.out.println("Dodged");
    }

    public void dash(double mouseX, double mouseY){
        double angle = Math.atan2(mouseY - this.getPosition().y, mouseX - this.getPosition().x);
        System.out.println("Mouse: (" + mouseX + "," + mouseY + ")");
        System.out.println("Fighter: (" + this.getPosition().x + "," + this.getPosition().y + ")");
        System.out.println("Angle: " + angle);

        this.addImpact(new Impact((int)(leftDodge.getMagnitude() * Vector.DODGE_DASH_RATIO), angle));
    }

    public void addImpact(Impact i){
        impacts.push(i);
    }

    public Stack<Impact> getImpacts(){
        return impacts;
    }
    public boolean isDiagonal(){
        int directions = 0;
        if(isUp)
            directions++;
        if(isDown)
            directions++;
        if(isLeft)
            directions++;
        if(isRight)
            directions++;
        if(directions == 2){
            return !((isUp && isDown) || (isRight && isLeft));
        }
        return false;
    }

    public double getNetForceX() {
        return netForceX;
    }

    public void setNetForceX(double netForceX) {
        this.netForceX = netForceX;
    }

    public double getNetForceY() {
        return netForceY;
    }

    public void setNetForceY(double netForceY) {
        this.netForceY = netForceY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getVelocityMagnitude(){
        return Math.sqrt(velocityX * velocityX + velocityY * velocityY);
    }

    public int getIndex(){
        return fighterIndex;
    }

    public int getMass(){
        return mass;
    }

    public double getAccelerationX(){
        return accelerationX;
    }

    public double getAccelerationY(){
        return accelerationY;
    }

    /*public Shape getIcon(){
        return icon;
    }*/

    public int getMaxEnergy(){
        return maxEnergy;
    }

    public int getCurrentEnergy(){
        return currentEnergy;
    }

    public boolean isAlive(){
        return alive;
    }

    public boolean isBracing(){
        return bracing;
    }

    public double getFrictionConstant(){
        return FRICTION_CONSTANT;
    }

    public void setIndex(int newIndex){
        fighterIndex = newIndex;
    }

    public void setMass(int newMass){
        mass = newMass;
    }

    public void setAccelerationX(double newAcceleration){
        accelerationX = newAcceleration;
    }

    public void setAccelerationY(double newAcceleration){
        accelerationY = newAcceleration;
    }

    /*public void setIcon(Shape newIcon){
        icon = newIcon;
    }*/

    public void setMaxEnergy(int newMaxEnergy){
        maxEnergy = newMaxEnergy;
    }

    public void setCurrentEnergy(int newCurrentEnergy){
        currentEnergy = newCurrentEnergy;
    }

    /*public void setColor(Color c){
        icon.setFill(c);
        icon.setStroke(c.darker().darker());
    }*/

    public void setAlive(boolean newState){
        alive = newState;
    }

    public void setBracing(boolean newState){
        bracing = newState;
    }
    public void addForce(int index, Force newForce){
        //0 = right, 1 = up, 2 = left, 3 = down
        forces[index] = newForce;

        int sumX = 0;
        int sumY = 0;

        for(Force f: forces){
            if(f != null){
                sumX += f.getComponentX();
                sumY += f.getComponentY();
            }
        }

        this.setNetForceX(sumX);
        this.setNetForceY(sumY);
    }
    public void removeForce(int index) {
        forces[index] = null;

        double sumX = 0;
        double sumY = 0;

        for (Force f : forces) {
            if (f != null) {
                sumX += f.getComponentX();
                sumY += f.getComponentY();
            }
        }

        this.setNetForceX(sumX);
        this.setNetForceY(sumY);
    }
}
