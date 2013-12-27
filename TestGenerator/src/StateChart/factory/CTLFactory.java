package StateChart.factory;

import java.util.Collection;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;
import StateChart.utils.StringBuilderUtil;

public class CTLFactory {

    public String createStateCoverageCTL(UMLStateChart usc) {
        if(null == usc) {
            return "";
        }
        
        Collection<State> states = usc.getStates().values();
        if(null == states || states.size() <= 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("--可达性验证\n");
        for (State s : states) {
            sb.append("SPEC AG !").append(s.getName()).append(";\n");
        }
        
        return sb.toString();
    }
    
    public String createTranCoverageCTL(UMLStateChart usc) {
        if(null == usc) {
            return "";
        }
        
        Collection<Transition> trans = usc.getTransitions().values();
        if(null == trans || trans.size() <= 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("--活性验证\n");
        for(Transition t : trans) {
            sb.append("SPEC AG(").append(t.getSource().getName()).append(" -> !(")
            .append(t.getTrigger().getName()).append(" & EX ").append(t.getTarget().getName())
            .append("));\n");
        }
        
        return sb.toString();
    }
    
    public String createTranPairCoverageCTL(UMLStateChart usc) {
        if(null == usc) {
            return "";
        }
        
        Collection<State> states = usc.getStates().values();
        if(null == states || states.size() <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("--安全性验证\n");
        for (State s : states) {
            Set<Transition> outgoings = s.getOutgoings();
            if(null != outgoings && outgoings.size() > 0) {
                sb.append("SPEC AG(").append(s.getName()).append(" -> !EX!(");
                for(Transition t : outgoings) {
                    sb.append(t.getTarget().getName()).append("|");
                }
                StringBuilderUtil.deleteLastChar(sb);
                sb.append("));\n");
            }
        }
        
        return sb.toString();
    }
}
