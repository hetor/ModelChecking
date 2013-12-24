package StateChart.entity;

import java.util.Set;

/**
 * UML×´Ì¬Í¼µÄ×´Ì¬
 * @author hetao
 */
public class State {
    /**×´Ì¬Ãû³Æ**/
    private String name;
    
    /**×´Ì¬µÄprev×´Ì¬¼¯**/
    private Set<State> prevStates;
    
    /**×´Ì¬next×´Ì¬¼¯**/
    private Set<State> nextStates;
    
    /**¸Ã×´Ì¬°üº¬µÄtriggers¼¯**/
    private Set<Trigger> triggers;
    
    /**incomingÇ¨ÒÆ¼¯**/
    private Set<Transition> incomings;
    
    /**outgoingÇ¨ÒÆ¼¯**/
    private Set<Transition> outgoing;
    
    
    //constructor
    public State(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<State> getPrevStates() {
        return prevStates;
    }
    public void setPrevStates(Set<State> prevStates) {
        this.prevStates = prevStates;
    }
    public Set<State> getNextStates() {
        return nextStates;
    }
    public void setNextStates(Set<State> nextStates) {
        this.nextStates = nextStates;
    }
    public Set<Trigger> getTriggers() {
        return triggers;
    }
    public void setTriggers(Set<Trigger> triggers) {
        this.triggers = triggers;
    }
    public Set<Transition> getIncomings() {
        return incomings;
    }
    public void setIncomings(Set<Transition> incomings) {
        this.incomings = incomings;
    }
    public Set<Transition> getOutgoing() {
        return outgoing;
    }
    public void setOutgoing(Set<Transition> outgoing) {
        this.outgoing = outgoing;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)){
            return false;
        }
        State state = (State) obj;
        return getName().equals(state.getName());
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
