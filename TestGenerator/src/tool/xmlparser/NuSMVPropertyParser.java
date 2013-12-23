package tool.xmlparser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import tool.entity.Property;
import tool.enums.PropertyType;


/**
 * 解析NuSMV中的性质
 * @author hetao
 */
public class NuSMVPropertyParser {
	
	private static Document doc = null;
	
	static {
		SAXReader reader = new SAXReader();  
		InputStream in = NuSMVPropertyParser.class.getClassLoader().
				getResourceAsStream("property.xml");
		//FIXME
		try {  
	        doc = reader.read(in);
	    } catch (DocumentException e) {  
	        e.printStackTrace();  
	    } 
	}

	@SuppressWarnings("unchecked")
	public static List<Property> parser() {
		
		List<Property> properties = new ArrayList<>();
		
		Element root = doc.getRootElement(); 
		List<Element> propertyEles = root.elements("property");
		
		for (Element propertyEle : propertyEles) {
			Property property = new Property();
			property.setName(propertyEle.elementTextTrim("name"));
			property.setIndex(Integer.valueOf(propertyEle.elementTextTrim("index")));
			property.setFormula(propertyEle.element("formula").getData().toString().trim());
			property.setType(PropertyType.valueOf(propertyEle.elementTextTrim("type")));
			property.setStatus(Boolean.valueOf(propertyEle.elementTextTrim("status")));
			property.setBound(Integer.valueOf(propertyEle.elementTextTrim("bound")));
			property.setTrace(Integer.valueOf(propertyEle.elementTextTrim("trace")));
			properties.add(property);
		}
		
		return properties; 
	}
	
	public static void main(String[] args) {
		List<Property> properties = parser();
		for(Property property: properties) {
			System.out.println(property.toString());
		}
	}
}
