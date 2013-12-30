package NuSMV.test;

import java.util.List;

import org.junit.Test;

import NuSMV.entity.Property;
import NuSMV.xmlparser.PropertyParser;

public class PropertyParserTest {

    @Test
    public void testParser() {
        List<Property> properties = PropertyParser.parser("mc-property.xml");
        for(Property property: properties) {
            System.out.println(property.toString());
        }
    }

}
