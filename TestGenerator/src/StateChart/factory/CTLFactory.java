package StateChart.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;
/**
 * 性质生成工厂
 * @author hetao
 */
public class CTLFactory {
    
    /**
     * 生成模型usc的可达性性质
     */
    public static List<String> createStateCoverageCTL(UMLStateChart usc) {
        List<String> ctls = new ArrayList<>();
        if(null != usc) {
            for (State s : usc.getStates().values()) {
                ctls.add(String.format("SPEC AG !%s;", s.getName()));
            }
        }
        return ctls;
    }
    
    /**
     * 生成模型usc的活性性质
     */
    public static List<String> createTranCoverageCTL(UMLStateChart usc) {
        List<String> ctls = new ArrayList<>();
        if(null != usc) {
            for(Transition t : usc.getTransitions().values()) {
                ctls.add(String.format("SPEC AG(%s -> !(%s & EX %s));", t.getSource().getName(), t.getTrigger().getName(), t.getTarget().getName()));
            }
        }
        return ctls;
    }
    
    /**
     * 生成模型usc的安全性性质
     */
    public static List<String> createTranPairCoverageCTL(UMLStateChart usc) {
        List<String> ctls = new ArrayList<>();
        if(null != usc) {
            for (State s : usc.getStates().values()) {
                Set<Transition> outgoings = s.getOutgoings();
                if(!outgoings.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("SPEC AG(").append(s.getName()).append(" -> !EX!(");
                    for(Transition t : outgoings) {
                        sb.append(t.getTarget().getName()).append("|");
                    }
                    sb.deleteCharAt(sb.length()-1).append("));");
                    ctls.add(sb.toString());
                }
            }
        }
        return ctls;
    }
}
