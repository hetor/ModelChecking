/**
 * 
 */
package StateChart.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import StateChart.factory.CTLFactory;
import StateChart.xmlparser.StateChartParser;

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
        CTLFactory factory = new CTLFactory(StateChartParser.parser());
        String ctl = factory.createStateCoverageCTL();
        System.out.println(ctl);
    }

    @Test
    public final void testCreateTranCoverageCTL() {
//        System.out.println("testCreateTranCoverageCTL");
        CTLFactory factory = new CTLFactory(StateChartParser.parser());
        String ctl = factory.createTranCoverageCTL();
        System.out.println(ctl);
    }

    @Test
    public final void testCreateTranPairCoverageCTL() {
//        System.out.println("testCreateTranPairCoverageCTL");
        CTLFactory factory = new CTLFactory(StateChartParser.parser());
        String ctl = factory.createTranPairCoverageCTL();
        System.out.println(ctl);
    }

}
