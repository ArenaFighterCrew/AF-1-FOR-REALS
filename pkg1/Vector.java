package af.pkg1;

public class Vector {
    private int magnitude;
    private double angle;   //in radians
    public static double FORCE_IMPACT_RATIO = 1;
    
    public Vector(int iMagnitude, double iAngle){
        magnitude = iMagnitude;
        angle = iAngle;
    }
    public int getMagnitude(){
        return magnitude;
    }
    public void setMagnitude(int newMagnitude){
        magnitude = newMagnitude;
    }
    public double getAngle(){
        return angle;
    }
    public void setAngle(double newAngle){
        angle = newAngle;
    }
    public int getComponentX(){
        return (int)(Math.cos(angle) * magnitude);
    }
    public int getComponentY(){
        return (int)(Math.sin(angle) * magnitude);
    }
    public Vector getInverse(){
        if(this instanceof Force)
            return new Force(this.magnitude, this.angle + Math.PI);
        else if(this instanceof Impact)
            return new Impact(this.magnitude, this.angle + Math.PI);
        else
            return new Vector(this.magnitude, this.angle + Math.PI);
    }
}