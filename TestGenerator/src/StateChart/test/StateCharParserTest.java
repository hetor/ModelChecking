package StateChart.test;

import org.junit.Test;

import StateChart.entity.UMLStateChart;
import StateChart.xmlparser.UMLStateChartParser;

public class StateCharParserTest {

    @Test
    public void testParser() {
       UMLStateChart usc = UMLStateChartParser.parser("mc-statechart.xmi");
       System.out.println(usc);
    }

}
