package NuSMV.entity;

import com.google.gson.Gson;

public class Property {

    /**name**/
    private String name;
    /**��������**/
    private int index;
    /**���ʹ�ʽ**/
    private String formula;
    /**��������**/
    private PropertyType type;
    /**������֤״̬**/
    private boolean status;
    /**bound**/
    private int bound;
    /**���ʶ�Ӧ�ļ�����**/
    private int traceId;
    
    
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


    public int getTraceId() {
        return traceId;
    }


    public void setTraceId(int traceId) {
        this.traceId = traceId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Property other = (Property) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
