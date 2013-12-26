package StateChart.test;

import org.junit.Test;

import StateChart.factory.NuSMVFactory;
import StateChart.xmlparser.StateChartParser;

public class NuSMVFactoryTester {

    @Test
    public void testCreateNuSMV() {
        NuSMVFactory factory = new NuSMVFactory();
        System.out.println(factory.createNuSMV(StateChartParser.parser()));
    }

    @Test
    public void testToFile() {
        //TODO
    }

}
