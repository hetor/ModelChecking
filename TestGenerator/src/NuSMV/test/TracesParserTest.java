package NuSMV.test;

import java.util.List;

import org.junit.Test;

import NuSMV.entity.Trace;
import NuSMV.xmlparser.TracesParser;

public class TracesParserTest {

    @Test
    public void testParser() {
        List<Trace> traces = TracesParser.parser("mc-trace.xml");
        for (Trace trace : traces) {
            System.out.println(trace);
        }
    }

}
