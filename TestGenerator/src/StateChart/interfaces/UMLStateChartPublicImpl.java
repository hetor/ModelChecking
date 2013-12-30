package StateChart.interfaces;

import java.util.Collection;
import java.util.List;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;
import StateChart.factory.CTLFactory;
import StateChart.factory.NuSMVFactory;
import StateChart.xmlparser.UMLStateChartParser;

public class UMLStateChartPublicImpl implements IUMLStateChartPublic{
    
    private UMLStateChart usc;
    
    public UMLStateChartPublicImpl(String uscPath) {
        this.usc = UMLStateChartParser.parser(uscPath);
    }

    @Override
    public String getNuSMVCode() {
        return new NuSMVFactory(usc).createNuSMV();
    }

    @Override
    public List<String> getStateCoverageCTL() {
        return CTLFactory.createStateCoverageCTL(usc);
    }
    
    @Override
    public List<String> getTranCoverageCTL() {
        return CTLFactory.createTranCoverageCTL(usc);
    }

    @Override
    public List<String> getTranPairCoverageCTL() {
        return CTLFactory.createTranPairCoverageCTL(usc);
    }

    @Override
    public Collection<Transition> getTransitions() {
        return usc.getTransitions().values();
    }

    @Override
    public Collection<State> getStates() {
        return usc.getStates().values();
    }

    @Override
    public UMLStateChart getUMLStateChart() {
        return usc;
    }
}
