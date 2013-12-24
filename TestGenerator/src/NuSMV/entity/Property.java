package NuSMV.entity;

import NuSMV.enums.PropertyType;

import com.google.gson.Gson;

public class Property {

    /**name**/
    private String name;
    /**性质索引**/
    private int index;
    /**性质公式**/
    private String formula;
    /**性质类型**/
    private PropertyType type;
    /**性质验证状态**/
    private boolean status;
    /**bound**/
    private int bound;
    /**性质对应的迹索引**/
    private int trace;
    
    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getIndex() {
        return index;
    }


    public void setIndex(int index) {
        this.index = index;
    }


    public String getFormula() {
        return formula;
    }


    public void setFormula(String formula) {
        this.formula = formula;
    }


    public PropertyType getType() {
        return type;
    }


    public void setType(PropertyType type) {
        this.type = type;
    }


    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public int getBound() {
        return bound;
    }


    public void setBound(int bound) {
        this.bound = bound;
    }


    public int getTrace() {
        return trace;
    }


    public void setTrace(int trace) {
        this.trace = trace;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
