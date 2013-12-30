package NuSMV.factory;

import java.util.ArrayList;
import java.util.List;

import NuSMV.entity.Property;
import NuSMV.entity.TestCase;
import NuSMV.entity.Trace;
import NuSMV.entity.Trace.Node;
import StateChart.entity.Transition;
import StateChart.entity.UMLStateChart;

public class TestCaseFactory {
    
    public static final String STATE_NAME_KEY = "state";

    public static List<TestCase> createTestCase(List<Property> properties, List<Trace> traces, UMLStateChart usc) {
        List<TestCase> testCases = new ArrayList<>();
        int index=0;
        for (Property p : properties) {
            if(p.isStatus()) continue;
            
            TestCase tc = new TestCase();
            tc.setId(++index);
            tc.setPropertyFormula(p.getFormula());
            
            Trace trace = traces.get(p.getTraceId()-1);
            List<String> sAlias = new ArrayList<>();
            for (Node n : trace.getNodes()) {
                sAlias.add(n.getStateVars().get(STATE_NAME_KEY));
            }
            
            StringBuilder path = new StringBuilder();
            path.append("<");
            for (int i=0; i<sAlias.size(); i++ ) {
                path.append(usc.getStateByAlias(sAlias.get(i)).getName()).append(",");
                if(i != sAlias.size()-1) {
                    Transition t = usc.getTransitionBySourceAndTarget(usc.getStateByAlias(sAlias.get(i)), usc.getStateByAlias(sAlias.get(i+1)));
                    path.append(t.getTrigger().getName()).append(",");
                }
            }
            path.deleteCharAt(path.length()-1).append(">");
            tc.setPath(path.toString());
            
            testCases.add(tc);
        }
        return testCases;
    }
}
