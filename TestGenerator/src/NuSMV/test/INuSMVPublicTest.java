package NuSMV.test;

import java.util.List;

import org.junit.Test;

import NuSMV.entity.TestCase;
import NuSMV.interfaces.NuSMVPublicImpl;
import StateChart.interfaces.IUMLStateChartPublic;
import StateChart.interfaces.UMLStateChartPublicImpl;

public class INuSMVPublicTest {

    @Test
    public void testGetTestCases() {
        IUMLStateChartPublic uscp = new UMLStateChartPublicImpl("mc-statechart.xmi");
        List<TestCase> cases = new NuSMVPublicImpl("mc-property.xml", "mc-trace.xml", uscp.getUMLStateChart()).getTestCases();
        for (TestCase tc : cases) {
            System.out.println(tc);
        }
    }

    @Test
    public void testGetProperties() {
        
    }

    @Test
    public void testGetTraces() {
        
    }
}
