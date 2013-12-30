package StateChart.interfaces;

import java.util.Collection;
import java.util.List;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;

public interface IUMLStateChartPublic {

    String getNuSMVCode();
    List<String> getStateCoverageCTL();
    List<String> getTranCoverageCTL();
    List<String> getTranPairCoverageCTL();
    
    Collection<Transition> getTransitions();
    Collection<State> getStates();
    UMLStateChart getUMLStateChart();
}
