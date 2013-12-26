package StateChart.factory;

import java.util.Collection;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Trigger;
import StateChart.entity.UMLStateChart;
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;

public class NuSMVFactory {
    
    public String createNuSMV(UMLStateChart usc) {
        if(null == usc) {
            return null;
        }
        Collection<State> states = usc.getStates().values();
        if(null == states || states.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.XMI_PARSE_ERROR, "没有状态");
        }
        Collection<Trigger> triggers = usc.getTriggers().values();
        if(null == triggers || triggers.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.XMI_PARSE_ERROR, "没有事件");
        }
        State initState = usc.getInitState();
        if(null == initState ) {
            throw new StateChartException(StateChartExceptionCode.XMI_PARSE_ERROR, "无初始状态"); 
        }
        
        return constructHEAD() + constructVAR(states, triggers) + constructASSIGN(states, triggers, initState);
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
            sb.append(s.getName()).append(",");
        }
        deleteLastChar(sb);
        sb.append("};\n");
        
        //other vars(triggers)
        for (Trigger trigger : triggers) {
            sb.append(trigger.getName()).append(":boolean;\n");
        }
        
        sb.append("\n");
        return sb.toString();
    }
    
    private String constructASSIGN(Collection<State> states,  Collection<Trigger> triggers, State initState) {
        Set<Trigger> initTriggers = initState.getTriggers();
        if(null == initTriggers || initTriggers.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.XMI_PARSE_ERROR, "初始状态无包含的事件");
        }
            
        StringBuilder sb = new StringBuilder();
        sb.append("ASSIGN\n");
        
        //init
        sb.append("init(state):=").append(initState.getName()).append(";\n");
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
            if(null != nextStates && nextStates.size() > 0) {
                sb.append("    state=").append(state.getName()).append(": {");
                for (State s : nextStates) {
                    sb.append(s.getName()).append(",");
                }
                deleteLastChar(sb);
                sb.append("};\n");
            }
        }
        sb.append("    TRUE: state;\nesac;\n\n");
        
        for (Trigger trigger : triggers) {
            Set<State> sources = trigger.getSources();
            if(null != sources && sources.size() > 0) {
                sb.append("next(").append(trigger.getName()).append("):=case\n    ");
                for (State s : sources) {
                    sb.append("next(state)=").append(s.getName()).append("|");
                }
                deleteLastChar(sb);
                sb.append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
            }
        }
        
        return sb.toString();
    }
    
    private void deleteLastChar(StringBuilder sb) {
        sb.deleteCharAt(sb.length()-1);
    } 
}
