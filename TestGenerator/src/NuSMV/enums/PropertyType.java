package NuSMV.enums;

public enum PropertyType {

    CTL("CTL"),
    LTL("LTL"),
    INVAR("INVAR"),
    PSL("PSL"),
    COMPUTE("COMPUTE");
    
    private String name;
    
    private PropertyType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
