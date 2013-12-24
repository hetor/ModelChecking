package StateChart.entity;

import java.util.Set;

/**
 * UML״̬ͼ�Ĵ���
 * @author hetao
 */
public class Trigger {
    
    /**��״̬ͼ�е�id**/
    private String id;
    
    /**trigger����**/
    private String name;
    
    /**trigger��source��**/
    private Set<State> sources;
    
    /**trigger��target��**/
    private Set<State> targets;
    
    /**����trigger��Ǩ�Ƽ�**/
    private Set<Transition> transitions;
    
    
    //constructor
    public Trigger(String name) {
        this.name = name;
    }
    

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
    public Set<State> getSources() {
        return sources;
    }
    public void setSources(Set<State> sources) {
        this.sources = sources;
    }
    public Set<State> getTargets() {
        return targets;
    }
    public void setTargets(Set<State> targets) {
        this.targets = targets;
    }
    public Set<Transition> getTransitions() {
        return transitions;
    }
    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trigger)){
            return false;
        }
        Trigger trigger = (Trigger) obj;
        return getName().equals(trigger.getName());
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
