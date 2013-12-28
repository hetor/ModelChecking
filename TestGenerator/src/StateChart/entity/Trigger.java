package StateChart.entity;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * UML״̬ͼ�Ĵ���
 * @author hetao
 */
public class Trigger {
    
    /**��״̬ͼ�е�id**/
    private String id;
    
    /**trigger����**/
    private String name;
    
    /**����trigger��Ǩ�Ƽ�**/
    private Set<Transition> trans;
    
//    /**trigger��source��**/
//    private Set<State> sources;
//    
//    /**trigger��target��**/
//    private Set<State> targets;
    
    
    /********added method**/
    /**
     * add t to trans
     */
    public void addTransition(Transition t) {
        if(null == t) return;
        if(null == trans) {
            trans = new HashSet<>();
        }
        trans.add(t);
    }
    
    /**
     * ���û���
     * @return if no return empty set
     */
    public Set<State> getSources() {
        Set<State> states = new HashSet<>();
        if(null != trans) {
            for (Transition t : trans) {
                states.add(t.getSource());
            }
        }
        return states;
    }
    
    /**
     * ���û���
     * @return if no return empty set
     */
    public Set<State> getTargets() {
        Set<State> states = new HashSet<>();
        if(null != trans) {
            for (Transition t : trans) {
                states.add(t.getTarget());
            }
        }
        return states;
    }
    
    public boolean isTriggerValid() {
        return StringUtils.isNotBlank(id)
                && StringUtils.isNotBlank(name)
                && null != trans
                && trans.size() > 0;
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
