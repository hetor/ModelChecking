package StateChart.test;

import org.junit.Test;

import StateChart.factory.NuSMVFactory;
import StateChart.xmlparser.StateChartParser;

public class NuSMVFactoryTester {

    @Test
    public void testCreateNuSMV() {
        NuSMVFactory factory = new NuSMVFactory(StateChartParser.parser());
        System.out.println(factory.createNuSMV());
    }

    @Test
    public void testToFile() {
        //TODO
    }

}
