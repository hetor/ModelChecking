package StateChart.factory;

import java.util.Set;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;
import StateChart.utils.StringBuilderUtil;

public class CTLFactory {
    
    private UMLStateChart usc;
    
    public CTLFactory(UMLStateChart usc) {
        if(null == usc) {
            throw new StateChartException(StateChartExceptionCode.NULL_POINTER, "状态图对象不能为空");
        }
        if(!usc.isUMLStateChartValid()) {
            throw new StateChartException(StateChartExceptionCode.UMLSTATECHART_INVALID);
        }
        this.usc = usc;
    }

    public String createStateCoverageCTL() {
        StringBuilder sb = new StringBuilder();
        sb.append("--可达性验证\n");
        for (State s : usc.getStates().values()) {
            if(!s.isStateValid()) {
                throw new StateChartException(StateChartExceptionCode.STATE_INVALID);
            }
            sb.append("SPEC AG !").append(s.getName()).append(";\n");
        }
        return sb.toString();
    }
    
    public String createTranCoverageCTL() {
        StringBuilder sb = new StringBuilder();
        sb.append("--活性验证\n");
        for(Transition t : usc.getTransitions().values()) {
            if(!t.isTransitionValid()) {
                throw new StateChartException(StateChartExceptionCode.TRANSITION_INVALID);
            }
            sb.append("SPEC AG(").append(t.getSource().getName()).append(" -> !(")
            .append(t.getTrigger().getName()).append(" & EX ").append(t.getTarget().getName())
            .append("));\n");
        }
        return sb.toString();
    }
    
    public String createTranPairCoverageCTL() {
        StringBuilder sb = new StringBuilder();
        sb.append("--安全性验证\n");
        for (State s : usc.getStates().values()) {
            if(!s.isStateValid()) {
                throw new StateChartException(StateChartExceptionCode.STATE_INVALID);
            }
            Set<Transition> outgoings = s.getOutgoings();
            if(!outgoings.isEmpty()) {
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
