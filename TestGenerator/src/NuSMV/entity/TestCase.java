package NuSMV.entity;

public class TestCase {

    /**唯一编号**/
    private int id;
    
    /**性质名**/
    private String propertyFormula;
    
    /**执行路径**/
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPropertyFormula() {
        return propertyFormula;
    }

    public void setPropertyFormula(String propertyFormula) {
        this.propertyFormula = propertyFormula;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" Property Formula: ").append(propertyFormula).append(" Path: ").append(path).append(";");
        return sb.toString();
    }
}

