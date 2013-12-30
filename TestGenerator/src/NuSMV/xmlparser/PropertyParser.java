package NuSMV.xmlparser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import NuSMV.entity.Property;
import NuSMV.entity.PropertyType;


/**
 * 解析NuSMV中的性质
 * @author hetao
 */
public class PropertyParser {
    
    private static Document doc = null;
    
    @SuppressWarnings("unchecked")
    public static List<Property> parser(String pPath) {
        init(pPath);
        Element root = doc.getRootElement(); 
        List<Property> properties = new ArrayList<>();
        List<Element> propertyEles = root.elements("property");
        
        for (Element propertyEle : propertyEles) {
            Property property = new Property();
            property.setName(propertyEle.elementTextTrim("name"));
            property.setIndex(Integer.valueOf(propertyEle.elementTextTrim("index")));
            property.setFormula(propertyEle.element("formula").getData().toString().trim());
            property.setType(PropertyType.valueOf(propertyEle.elementTextTrim("type")));
            property.setStatus(Boolean.valueOf(propertyEle.elementTextTrim("status")));
            property.setBound(Integer.valueOf(propertyEle.elementTextTrim("bound")));
            property.setTraceId(Integer.valueOf(propertyEle.elementTextTrim("trace")));
            properties.add(property);
        }
        
        return properties; 
    }
    
    private static void init(String pPath) {
        SAXReader reader = new SAXReader();  
        InputStream in = PropertyParser.class.getClassLoader().
                getResourceAsStream(pPath);
        try {  
            doc = reader.read(in);
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } 
    }
}
