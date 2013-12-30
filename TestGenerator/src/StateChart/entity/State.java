package StateChart.entity;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * UML状态图的状态
 * @author hetao
 */
public class State {
    
    /**在状态图中的id**/
    private String id;
    
    /**状态名称**/
    private String name;
    
    /**别名**/
    private String alias;
    
    /**incoming迁移集**/
    private Set<Transition> incomings;
    
    /**outgoing迁移集**/
    private Set<Transition> outgoings;
    
//     需要缓存时使用  
//    /**状态的prev状态集**/
//    private Set<State> prevStates;
//    
//    /**状态next状态集**/
//    private Set<State> nextStates;
//    
//    /**该状态包含的triggers集**/
//    private Set<Trigger> triggers;
    
    
    /***************added method**/
    /**
     * add t to incommings
     */
    public void addIncomings(Transition t) {
        if(null == t) return;
        if(null == incomings) {
            incomings = new HashSet<>();
        }
        incomings.add(t);
    }
    
    /**
     * add t to outgoings
     */
    public void addOutgoing(Transition t) {
        if(null == t) return;
        if(null == outgoings) {
            outgoings = new HashSet<>();
        }
        outgoings.add(t);
    }
    
    /**
     * 不用缓存
     * @return if no return empty set
     */
    public Set<State> getPrevStates() {
        Set<State> states = new HashSet<>();
        if(null != incomings) {
            for (Transition t : incomings) {
                states.add(t.getSource());
            }
        }
        return states;
    }
    
    /**
     * 不用缓存
     * @return if no return empty set
     */
    public Set<State> getNextStates() {
        Set<State> states = new HashSet<>();
        if(null != outgoings) {
            for (Transition t : outgoings) {
                states.add(t.getTarget());
            }
        }
        return states;
    }
    
    /**
     * 不用缓存
     * @return if no return empty set
     */
    public Set<Trigger> getTriggers() {
        Set<Trigger> ts = new HashSet<>();
        if(null != outgoings) {
            for(Transition t : outgoings) {
                ts.add(t.getTrigger());
            }
        }
        return ts;
    }
    
    /**
     * make sure the state is valid to use
     */
    public boolean isStateValid() {
        return StringUtils.isNotBlank(id)
                && StringUtils.isNotBlank(name)
                && null != incomings
                && null != outgoings;
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
    
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("(name:").append(name).append(", ").append("incomings:{");
        
        if(null != incomings) {
            for(Transition t : incomings) {
                sb.append(t).append(",");
            }
        }
        
        sb.append("}, outgoings:{");
        if(null != outgoings) {
            for(Transition t : outgoings) {
                sb.append(t).append(",");
            }
        }
        
        sb.append("}, prevStates:{");
        Set<State> prevStates = getPrevStates();
        if(null != prevStates) {
            for (State s : prevStates) {
                sb.append(s.getName()).append(",");
            }
        }
        
        sb.append("}, nextStates:{");
        Set<State> nextStates = getNextStates();
        if(null != nextStates) {
            for(State s : nextStates) {
                sb.append(s.getName()).append(",");
            }
        }
        
        sb.append("}, triggers:{");
        Set<Trigger> triggers = getTriggers();
        if(null != triggers) {
            for (Trigger trigger : triggers) {
                sb.append(trigger.getName()).append(",");
            }    
        }
        sb.append("})");
        return sb.toString();
    }
}
