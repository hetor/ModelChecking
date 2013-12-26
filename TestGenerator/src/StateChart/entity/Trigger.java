package StateChart.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * UML状态图的触发
 * @author hetao
 */
public class Trigger {
    
    /**在状态图中的id**/
    private String id;
    
    /**trigger名称**/
    private String name;
    
    /**包含trigger的迁移集**/
    private Set<Transition> trans;
    
//    /**trigger的source集**/
//    private Set<State> sources;
//    
//    /**trigger的target集**/
//    private Set<State> targets;
    
    
    /********added method**/
    public void addTransition(Transition t) {
        trans.add(t);
    }
    
    public Set<State> getSources() { //不用缓存
        if(null == trans) {
            return null;
        }
        Set<State> states = new HashSet<>();
        for (Transition t : trans) {
            states.add(t.getSource());
        }
        return states;
    }
    
    public Set<State> getTargets() { //不用缓存
        if(null == trans) {
            return null;
        }
        Set<State> states = new HashSet<>();
        for (Transition t : trans) {
            states.add(t.getTarget());
        }
        return states;
    }
    
    /**********getter, setter**/
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
    
    public Set<Transition> getTransitions() {
        return trans;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.trans = transitions;
    }

    /*************override method**/
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
        Trigger other = (Trigger) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder trigger = new StringBuilder();
        trigger.append("(name:").append(name).append(", sources:{");
        
        Set<State> sources = getSources();
        if(null != sources) {
            for (State state : sources) {
                trigger.append(state.getName()).append(",");
            }
        }

        trigger.append("}, targets:{");
        Set<State> targets = getTargets();
        if(null != targets) {
            for (State state : targets) {
                trigger.append(state.getName()).append(",");
            }
        }
        
        trigger.append("}, transitions:{");
        if(null != trans) {
            for (Transition t : trans) {
                trigger.append(t).append(",");
            }
        }
        
        trigger.append("})");
        return trigger.toString();
    }
 }
