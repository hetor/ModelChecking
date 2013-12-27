package StateChart.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * UML×´Ì¬Í¼µÄÇ¨ÒÆ
 * @author hetao
 */
public class Transition {
    
    /**ÔÚ×´Ì¬Í¼ÖÐµÄid**/
    private String id;
    
    /**Ô´×´Ì¬**/
    private State source;
    
    /**Ä¿±ê×´Ì¬**/
    private State target;
    
    /**´¥·¢**/
    private Trigger trigger;
    
    public boolean isTransitionValid() {
        return StringUtils.isNotBlank(id)
                && null != source
                && null != target
                && null != trigger;
    }
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transition other = (Transition) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        
        StringBuilder trans = new StringBuilder();
        
        trans.append("<");
        
        if(null != source) {
            trans.append(source.getName()).append(", ");
        }
        
        if(null != trigger) {
            trans.append(trigger.getName()).append(", ");
        }
        
        if(null != target) {
            trans.append(target.getName());
        }
        
        trans.append(">");
        
        return trans.toString();
    }
}
