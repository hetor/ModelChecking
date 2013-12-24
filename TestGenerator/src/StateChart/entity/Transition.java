package StateChart.entity;

/**
 * UML×´Ì¬Í¼µÄÇ¨ÒÆ
 * @author hetao
 */
public class Transition {
    
    /**Ô´×´Ì¬**/
    private State source;
    
    /**Ä¿±ê×´Ì¬**/
    private State target;
    
    /**´¥·¢**/
    private Trigger trigger;
    
    
    public State getSource() {
        return source;
    }
    public void setSource(State source) {
        this.source = source;
    }
    public State getTarget() {
        return target;
    }
    public void setTarget(State target) {
        this.target = target;
    }
    public Trigger getTrigger() {
        return trigger;
    }
    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Transition)){
            return false;
        }
        Transition t = (Transition) obj;
       if(source.getName().equals(t.source.getName())
              && target.getName().equals(t.getTarget().getName())
              && trigger.getName().equals(t.getTrigger().getName())) {
           return true;
       }
       
       return false;
    }
    
    @Override
    public int hashCode() {
        return trigger.getName().hashCode();
    }
}
