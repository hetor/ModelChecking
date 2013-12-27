package StateChart.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Trigger;
import StateChart.entity.UMLStateChart;
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;
import StateChart.utils.StringBuilderUtil;

public class NuSMVFactory {
    
    private UMLStateChart usc;
    private Map<String, String> statesNuSMV;
    
    public NuSMVFactory(UMLStateChart usc) {
        if(null == usc) {
            throw new StateChartException(StateChartExceptionCode.NULL_POINTER, "状态图对象不能为空");
        }
        if(!usc.isUMLStateChartValid()) {
            throw new StateChartException(StateChartExceptionCode.UMLSTATECHART_INVALID);
        }
        this.usc = usc;
        Collection<State> states = usc.getStates().values();
        statesNuSMV = new HashMap<>();
        int i=0;
        for (State s : states) {
            statesNuSMV.put(s.getName(), "s"+i++);
        }
    }
    
    public String createNuSMV() {
        
        Collection<State> states = usc.getStates().values();
        Collection<Trigger> triggers = usc.getTriggers().values();
        State initState = usc.getInitState();
        
        if(!initState.isStateValid()) {
            throw new StateChartException(StateChartExceptionCode.STATE_INVALID);
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
        StringBuilderUtil.deleteLastChar(sb);
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
                StringBuilderUtil.deleteLastChar(sb);
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
                StringBuilderUtil.deleteLastChar(sb);
                sb.append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
            }
        }
        
        return sb.toString();
    }
}
