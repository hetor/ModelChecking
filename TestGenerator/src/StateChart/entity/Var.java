package StateChart.entity;

@Deprecated
public class Var<V> {
    
    private String name;
    private V value;
    
    public Var() {}
    
    public Var(String name, V value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
