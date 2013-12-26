package StateChart.test;

import org.junit.Test;

import StateChart.entity.UMLStateChart;
import StateChart.xmlparser.StateChartParser;

public class StateCharParserTester {

    @Test
    public void testParser() {
       UMLStateChart usc = StateChartParser.parser();
       System.out.println(usc);
    }

}
