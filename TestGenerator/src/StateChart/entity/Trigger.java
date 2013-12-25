package StateChart.entity;

/**
 * UML״̬ͼ�Ĵ���
 * @author hetao
 */
public class Trigger {
    
    /**��״̬ͼ�е�id**/
    private String id;
    
    /**trigger����**/
    private String name;
//    
//    /**trigger��source��**/
//    @Deprecated
//    private Set<State> sources;
//    
//    /**trigger��target��**/
//    @Deprecated
//    private Set<State> targets;
//    
//    /**����trigger��Ǩ�Ƽ�**/
//    @Deprecated
//    private Set<Transition> transitions;
    
    
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
//    public Set<State> getSources() {
//        return sources;
//    }
//    public void setSources(Set<State> sources) {
//        this.sources = sources;
//    }
//    public Set<State> getTargets() {
//        return targets;
//    }
//    public void setTargets(Set<State> targets) {
//        this.targets = targets;
//    }
//    public Set<Transition> getTransitions() {
//        return transitions;
//    }
//    public void setTransitions(Set<Transition> transitions) {
//        this.transitions = transitions;
//    }


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
        StringBuilder result = new StringBuilder();
        result.append("(name:").append(name).append(")");
        return result.toString();
    }
 }
