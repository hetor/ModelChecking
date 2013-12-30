package StateChart.test;

import org.junit.Test;

import StateChart.interfaces.UMLStateChartPublicImpl;

public class IUMLStateChartPublicTest {

    @Test
    public void testGetNuSMVCode() {
        System.out.println(new UMLStateChartPublicImpl("mc-statechart.xmi").getNuSMVCode());
    }

    @Test
    public void testGetStateCoverageCTL() {
        System.out.println(new UMLStateChartPublicImpl("mc-statechart.xmi").getStateCoverageCTL());
    }

    @Test
    public void testGetTranCoverageCTL() {
        System.out.println(new UMLStateChartPublicImpl("mc-statechart.xmi").getTranCoverageCTL());
    }

    @Test
    public void testGetTranPairCoverageCTL() {
        System.out.println(new UMLStateChartPublicImpl("mc-statechart.xmi").getTranPairCoverageCTL());
    }

    @Test
    public void testGetTransitions() {
        
    }

    @Test
    public void testGetStates() {
        
    }

    @Test
    public void testGetUMLStateChart() {
        
    }

}
