package StateChart.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Trigger;
import StateChart.entity.UMLStateChart;
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;
import StateChart.utils.StringBuilderUtil;

public class NuSMVFactory {
    
    private UMLStateChart usc;
    private Map<String, String> statesNuSMV; //<key,value> - <name, si>
    
    public NuSMVFactory(UMLStateChart usc) {
        if(null == usc) {
            throw new StateChartException(StateChartExceptionCode.NULL_POINTER, "状态图对象不能为空");
        }
        if(!usc.isUMLStateChartValid()) {
            throw new StateChartException(StateChartExceptionCode.UMLSTATECHART_INVALID);
        }
        convertStateNameUMLtoNuSMV(usc.getInitState());
        this.usc = usc;
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
    
    private void convertStateNameUMLtoNuSMV(State initState) {
        statesNuSMV = new HashMap<>();
        Queue<State> q = new LinkedList<>(); 
        int i=1;
        statesNuSMV.put(initState.getName(), "s0");
        for(State s : initState.getNextStates()) {
            q.offer(s);
        }
        while(!q.isEmpty()) {
            State state = q.poll();
            if(!statesNuSMV.containsKey(state.getName())) {
                statesNuSMV.put(state.getName(), "s"+i++);
                for(State s : state.getNextStates()) {
                    q.offer(s);
                }
            }
        }
    }
    
    private String constructHEAD() {
        return "MODULE main\n";
    }
    
    private String constructVAR(Collection<State> states, Collection<Trigger> triggers) {
        StringBuilder sb = new StringBuilder();
        sb.append("VAR\n");
        
        //state
        sb.append("state: {");
        for (String sNuSMV : statesNuSMV.values()) {
            sb.append(sNuSMV).append(",");
        }
        StringBuilderUtil.deleteLastChar(sb);
        sb.append("};\n");
        
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
        sb.append("init(state):=").append(statesNuSMV.get(initState.getName())).append(";\n");
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
                sb.append("    state=").append(statesNuSMV.get(state.getName())).append(": {");
                for (State s : nextStates) {
                    sb.append(statesNuSMV.get(s.getName())).append(",");
                }
                StringBuilderUtil.deleteLastChar(sb);
                sb.append("};\n");
            }
        }
        sb.append("    TRUE: state;\nesac;\n\n");
        
        for(State state : states) {
            sb.append("next(").append(state.getName()).append("):=case\n    ");
            sb.append("next(state)=").append(statesNuSMV.get(state.getName())).append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
        }
        
        for (Trigger trigger : triggers) {
            Set<State> sources = trigger.getSources();
            if(sources.size() > 0) {
                sb.append("next(").append(trigger.getName()).append("):=case\n    ");
                for (State s : sources) {
                    sb.append("next(state)=").append(statesNuSMV.get(s.getName())).append("|");
                }
                StringBuilderUtil.deleteLastChar(sb);
                sb.append(": TRUE;\n    TRUE: FALSE;\nesac;\n\n");
            }
        }
        
        return sb.toString();
    }
}
