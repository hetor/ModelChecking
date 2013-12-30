package StateChart.test;

import org.junit.Test;

import StateChart.factory.NuSMVFactory;
import StateChart.xmlparser.UMLStateChartParser;

public class NuSMVFactoryTest {

    @Test
    public void testCreateNuSMV() {
        NuSMVFactory factory = new NuSMVFactory(UMLStateChartParser.parser("mc-statechart.xmi"));
        System.out.println(factory.createNuSMV());
    }

    @Test
    public void testToFile() {
        //TODO
    }

}
