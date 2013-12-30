/**
 * 
 */
package StateChart.test;


import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import StateChart.factory.CTLFactory;
import StateChart.xmlparser.UMLStateChartParser;

/**
 * 
 * @author hetao
 */
public class CTLFactoryTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
       //System.out.println("BeforeClass");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        //System.out.println("AfterClass");
    }

    @Before
    public void setUp() throws Exception {
        //System.out.println("Before test method");
    }

    @After
    public void tearDown() throws Exception {
        //System.out.println("After test method");
    }

    @Test
    public final void testCreateStateCoverageCTL() {
//        System.out.println("testCreateStateCoverageCTL");
        List<String> ctls = CTLFactory.createStateCoverageCTL(UMLStateChartParser.parser("mc-statechart.xmi"));
        for (String ctl : ctls) {
            System.out.println(ctl);
        }
        System.out.println();
    }

    @Test
    public final void testCreateTranCoverageCTL() {
//        System.out.println("testCreateTranCoverageCTL");
        List<String> ctls = CTLFactory.createTranCoverageCTL(UMLStateChartParser.parser("mc-statechart.xmi"));
        for (String ctl : ctls) {
            System.out.println(ctl);
        }
        System.out.println();
    }

    @Test
    public final void testCreateTranPairCoverageCTL() {
//        System.out.println("testCreateTranPairCoverageCTL");
        List<String> ctls = CTLFactory.createTranPairCoverageCTL(UMLStateChartParser.parser("mc-statechart.xmi"));
        for (String ctl : ctls) {
            System.out.println(ctl);
        }
        System.out.println();
    }

}
