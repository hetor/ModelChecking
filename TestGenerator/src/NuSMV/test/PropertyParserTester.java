package NuSMV.test;

import java.util.List;

import org.junit.Test;

import NuSMV.entity.Property;
import NuSMV.xmlparser.PropertyParser;

public class PropertyParserTester {

    @Test
    public void testParser() {
        List<Property> properties = PropertyParser.parser();
        for(Property property: properties) {
            System.out.println(property.toString());
        }
    }

}
