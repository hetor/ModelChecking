package NuSMV.xmlparser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import NuSMV.entity.Trace;
import NuSMV.entity.Trace.Node;


/**
 * 解析NuSMV产生的反例迹
 * @author hetao
 */
public class TracesParser {
    
    private static Document doc = null;
    
    @SuppressWarnings("unchecked")
    public static List<Trace> parser(String tPath) {
        init(tPath);
        Element root = doc.getRootElement(); 
        List<Trace> traces = new ArrayList<>();
        List<Element> counterEls = root.elements("counter-example");
        
        for (Element counterEl : counterEls) {
            Trace trace = new Trace();
            List<Node> nodes = new ArrayList<>();
            
            List<Element> nodeEls = counterEl.elements("node");
            for(Element nodeEl : nodeEls) {
                Node node = trace.new Node();
                Map<String, String> stateVars = new HashMap<>();
                Element stateEl = nodeEl.element("state");
                node.setStateId(stateEl.attributeValue("id"));
                List<Element> valueEls = stateEl.elements("value");
                for (Element valueEl : valueEls) {
                    stateVars.put(valueEl.attributeValue("variable"), valueEl.getTextTrim());
                }
                node.setStateVars(stateVars);
                nodes.add(node);
            }
            
            trace.setNodes(nodes);
            trace.setLoops(counterEl.elementTextTrim("loops"));
            traces.add(trace);
        }
        
        return traces;
    }
    
    private static void init(String tPath) {
        SAXReader reader = new SAXReader();  
        InputStream in = TracesParser.class.getClassLoader().
                getResourceAsStream(tPath);
        try {  
            doc = reader.read(in);
        } catch (DocumentException e) {  
            e.printStackTrace();
        } 
    }
}
