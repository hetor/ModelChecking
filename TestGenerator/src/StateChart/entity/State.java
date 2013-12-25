package StateChart.entity;

import java.util.Set;

/**
 * UML״̬ͼ��״̬
 * @author hetao
 */
public class State {
    
    /**��״̬ͼ�е�id**/
    private String id;
    
    /**״̬����**/
    private String name;
    
//    /**״̬��prev״̬��**/
//    @Deprecated
//    private Set<State> prevStates;
//    
//    /**״̬next״̬��**/
//    @Deprecated
//    private Set<State> nextStates;
//    
//    /**��״̬������triggers��**/
//    @Deprecated
//    private Set<Trigger> triggers;
    
    /**incomingǨ�Ƽ�**/
    private Set<Transition> incomings;
    
    /**outgoingǨ�Ƽ�**/
    private Set<Transition> outgoings;
    
    
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
//    public Set<State> getPrevStates() {
//        return prevStates;
//    }
//    public void setPrevStates(Set<State> prevStates) {
//        this.prevStates = prevStates;
//    }
//    public Set<State> getNextStates() {
//        return nextStates;
//    }
//    public void setNextStates(Set<State> nextStates) {
//        this.nextStates = nextStates;
//    }
//    public Set<Trigger> getTriggers() {
//        return triggers;
//    }
//    public void setTriggers(Set<Trigger> triggers) {
//        this.triggers = triggers;
//    }
    public Set<Transition> getIncomings() {
        return incomings;
    }
    
    public void setIncomings(Set<Transition> incomings) {
        this.incomings = incomings;
    }
    
    public void addIncomings(Transition t) {
        incomings.add(t);
    }
    
    public Set<Transition> getOutgoings() {
        return outgoings;
    }
    
    public void setOutgoings(Set<Transition> outgoings) {
        this.outgoings = outgoings;
    }
    
    public void addOutgoing(Transition t) {
        outgoings.add(t);
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
        StringBuilder result = new StringBuilder();
        result.append("(name: ").append(name).append(", ").append("incomings: {");
        for(Transition t : incomings) {
            result.append(t).append(",");
        }
        result.append("}, outgoings: {");
        for(Transition t : outgoings) {
            result.append(t).append(",");
        }
        result.append("})");
        return result.toString();
    }
}
