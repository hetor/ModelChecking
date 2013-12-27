package StateChart.entity;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class UMLStateChart {

    /**UML工程中的唯一id**/
    private String id;
    
    /**UML工程中的name**/
    private String name;
    
    /**UML工程的初始状态**/
    private State initState;
    
    /**包含的所有状态<stateId, State>**/
    private Map<String, State> states;
    
    /**包含的所有迁移<transitionId, Transition>**/
    private Map<String, Transition> transitions;
    
    /**包含的所有触发<triggerId, Trigger>**/
    private Map<String, Trigger> triggers;
    
    
    /**added method**/
    public State getStateById(String id) {
        if(null==states || states.size()==0) {
            return null;
        }
        return states.get(id);
    } 
    
    public Transition getTransitionById(String id) {
        if(null==transitions || transitions.size()==0) {
            return null;
        }
        return transitions.get(id);
    }
    
    public Trigger getTrigerById(String id) {
        if(null==triggers || triggers.size()==0) {
            return null;
        }
        return triggers.get(id);
    }
    
    public boolean isUMLStateChartValid() {
        return StringUtils.isNotBlank(id)
                && StringUtils.isNotBlank(name) 
                && null != initState 
                && null != states && states.size() > 0 
                && null != transitions && transitions.size() > 0
                && null != triggers && triggers.size() > 0;
    }
    
    /**getter, setter**/
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

    public State getInitState() {
        return initState;
    }

    public void setInitState(State initState) {
        this.initState = initState;
    }

    public Map<String, State> getStates() {
        return states;
    }

    public void setStates(Map<String, State> states) {
        this.states = states;
    }

    public Map<String, Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Map<String, Transition> transitions) {
        this.transitions = transitions;
    }

    public Map<String, Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(Map<String, Trigger> triggers) {
        this.triggers = triggers;
    }

    /**override method**/
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
        UMLStateChart other = (UMLStateChart) obj;
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
        
        result.append("STATECHART: \nname:").append(name).append(", initState:").append(initState.getName()).append("\n");
        
        //states
        result.append("\nSTATE: \n");
        if(null != states) {
            Collection<State> statesValues = states.values();
            for (State state : statesValues) {
                result.append(state).append("\n");
            }
        }
        
        //triggers
        result.append("\nTRIGGER: \n");
        if(null != triggers) {
            Collection<Trigger> triggersValues = triggers.values();
            for (Trigger trigger : triggersValues) {
                result.append(trigger).append("\n");
            }
        }
        
        //transitions
        result.append("\nTRANSITION： \n");
        if(null != transitions) {
            Collection<Transition> transValues = transitions.values();
            for (Transition t : transValues) {
                result.append(t).append("\n");
            }
        }
        
        return result.toString();
    }
}
