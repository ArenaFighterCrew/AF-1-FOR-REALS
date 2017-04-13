package af.pkg1;

public class Force extends Vector{
    
    public Force(int iMagnitude, double iAngle){
        super(iMagnitude, iAngle);
    }
    
    public Impact toImpact(){
        return new Impact((int)(this.getMagnitude() / FORCE_IMPACT_RATIO), this.getAngle());
    }
}
