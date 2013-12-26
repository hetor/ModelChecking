package StateChart.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * UML×´Ì¬Í¼µÄ×´Ì¬
 * @author hetao
 */
public class State {
    
    /**ÔÚ×´Ì¬Í¼ÖÐµÄid**/
    private String id;
    
    /**×´Ì¬Ãû³Æ**/
    private String name;
    
    /**incomingÇ¨ÒÆ¼¯**/
    private Set<Transition> incomings;
    
    /**outgoingÇ¨ÒÆ¼¯**/
    private Set<Transition> outgoings;
//    
//    /**×´Ì¬µÄprev×´Ì¬¼¯**/
//    private Set<State> prevStates;
//    
//    /**×´Ì¬next×´Ì¬¼¯**/
//    private Set<State> nextStates;
//    
//    /**¸Ã×´Ì¬°üº¬µÄtriggers¼¯**/
//    private Set<Trigger> triggers;
    
    
    
    /***************added method**/
    public void addIncomings(Transition t) {
        incomings.add(t);
    }
    
    public void addOutgoing(Transition t) {
        outgoings.add(t);
    }
    
    public Set<State> getPrevStates() { //²»ÓÃ»º´æ
        if(null == incomings) {
            return null;
        }
        Set<State> states = new HashSet<>();
        for (Transition t : incomings) {
            states.add(t.getSource());
        }
        return states;
    }
    
    public Set<State> getNextStates() { //²»ÓÃ»º´æ
        if(null == outgoings) {
            return null;
        }
        Set<State> states = new HashSet<>();
        for (Transition t : outgoings) {
            states.add(t.getTarget());
        }
        return states;
    }
    
    public Set<Trigger> getTriggers() { //²»ÓÃ»º´æ
        if(null == outgoings) {
            return null;
        }
        Set<Trigger> ts = new HashSet<>();
        for(Transition t : outgoings) {
            ts.add(t.getTrigger());
        }
        return ts;
    }
    
    /*****************getter, setter**/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Set<Transition> getIncomings() {
        return incomings;
    }
    
    public void setIncomings(Set<Transition> incomings) {
        this.incomings = incomings;
    }
    
    public Set<Transition> getOutgoings() {
        return outgoings;
    }
    
    public void setOutgoings(Set<Transition> outgoings) {
        this.outgoings = outgoings;
    }
    
    
    /***************override methods**/
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
        State other = (State) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        
        StringBuilder state = new StringBuilder();
        
        state.append("(name:").append(name).append(", ").append("incomings:{");
        
        if(null != incomings) {
            for(Transition t : incomings) {
                state.append(t).append(",");
            }
        }
        
        state.append("}, outgoings:{");
        if(null != outgoings) {
            for(Transition t : outgoings) {
                state.append(t).append(",");
            }
        }
        
        state.append("}, prevStates:{");
        Set<State> prevStates = getPrevStates();
        if(null != prevStates) {
            for (State s : prevStates) {
                state.append(s.getName()).append(",");
            }
        }
        
        state.append("}, nextStates:{");
        Set<State> nextStates = getNextStates();
        if(null != nextStates) {
            for(State s : nextStates) {
                state.append(s.getName()).append(",");
            }
        }
        
        state.append("}, triggers:{");
        Set<Trigger> triggers = getTriggers();
        if(null != triggers) {
            for (Trigger trigger : triggers) {
                state.append(trigger.getName()).append(",");
            }    
        }
        state.append("})");
        return state.toString();
    }
}
