package NuSMV.entity;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Trace {

    private List<Node> nodes;
    private String loops;
    
    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }

    public class Node {
        private String stateId;
        private Map<String, String> stateVars;
        
        public String getStateId() {
            return stateId;
        }
        public void setStateId(String stateId) {
            this.stateId = stateId;
        }
        public Map<String, String> getStateVars() {
            return stateVars;
        }
        public void setStateVars(Map<String, String> stateVars) {
            this.stateVars = stateVars;
        }
    }
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
