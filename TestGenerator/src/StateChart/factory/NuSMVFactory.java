package StateChart.factory;

import java.util.Collection;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Trigger;
import StateChart.entity.UMLStateChart;
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;
import StateChart.utils.StringBuilderUtil;

public class NuSMVFactory {
    
    private UMLStateChart usc;
    
    public NuSMVFactory(UMLStateChart usc) {
        if(null == usc) {
            throw new StateChartException(StateChartExceptionCode.NULL_POINTER, "状态图对象不能为空");
        }
        this.usc = usc;
    }
    
    public String createNuSMV() {
        Collection<State> states = usc.getStates().values();
        Collection<Trigger> triggers = usc.getTriggers().values();
        return constructHEAD() + constructVAR(states, triggers) + constructASSIGN(states, triggers, usc.getInitState());
    }
    
    public void toFile(String path) {
        //TODO
    }
    
    private String constructHEAD() {
        return "MODULE main\n";
    }
    
    private String constructVAR(Collection<State> states, Collection<Trigger> triggers) {
        StringBuilder sb = new StringBuilder();
        sb.append("VAR\n");
        
        //state
        sb.append("state: {");
        for (State s : states) {
            sb.append(s.getAlias()).append(",");
        }
        sb.deleteCharAt(sb.length()-1).append("};\n");
        
        //other vars(triggers)
        for(State s : states) {
            sb.append(s.getName()).append(":boolean;\n");
        }
        for (Trigger trigger : triggers) {
            sb.append(trigger.getName()).append(":boolean;\n");
        }
        
        sb.append("\n");
        return sb.toString();
    }
    
    private String constructASSIGN(Collection<State> states,  Collection<Trigger> triggers, State initState) {
        Set<Trigger> initTriggers = initState.getTriggers();
        StringBuilder sb = new StringBuilder();
        sb.append("ASSIGN\n");
        
        //init
        sb.append("init(state):=").append(initState.getAlias()).append(";\n");
        sb.append("init(").append(initState.getName()).append("):=TRUE;\n");
        for(State s : states) {
            if(initState.equals(s)) continue;
            sb.append("init(").append(s.getName()).append("):=FALSE;\n");
        }
        for(Trigger trigger : triggers) {
            sb.append("init(").append(trigger.getName()).append("):=");
            if(initTriggers.contains(trigger)) {
                sb.append("TRUE;\n");
            }else {
                sb.append("FALSE;\n");
            }
        }
        sb.append("\n");
        
        //next
        sb.append("next(state):=case\n");
        for (State state : states) {
            Set<State> nextStates = state.getNextStates();
            if(!nextStates.isEmpty()) {
                sb.append("    state=").append(state.getAlias()).append(": {");
                for (State s : nextStates) {
                    sb.append(s.getAlias()).append(",");
                }
                StringBuilderUtil.deleteLastChar(sb);
                sb.append("};\n");
            }
        }
        sb.append("    TRUE: state;\nesac;\n\n");
        
        for(State state : states) {
            sb.append("next(").append(state.getName()).append("):=case\n    ");
            sb.append("next(state)=").append(state.getAlias()).append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
        }
        
        for (Trigger trigger : triggers) {
            Set<State> sources = trigger.getSources();
            if(sources.size() > 0) {
                sb.append("next(").append(trigger.getName()).append("):=case\n    ");
                for (State s : sources) {
                    sb.append("next(state)=").append(s.getAlias()).append("|");
                }
                StringBuilderUtil.deleteLastChar(sb);
                sb.append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
            }
        }
        
        return sb.toString();
    }
}
