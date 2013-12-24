package StateChart.xmlparser;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class StateChartParser {
    
    private static Document doc = null;
    
    static {
        SAXReader reader = new SAXReader();  
        InputStream in = StateChartParser.class.getClassLoader().
                getResourceAsStream("statechart.xmi");
        //FIXME
        try {  
            doc = reader.read(in);
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } 
    }

    @SuppressWarnings("unchecked")
    public static void parser() {
//        System.out.println(doc.getRootElement().element("XMI.content").getName());
//        Element el = doc.getRootElement().element("XMI.content").element("Model").element("Namespace.ownedElement");
//        Element stateMachineEl = el.element("StateMachine");
        List<Node> sinalEventNodes = doc.selectNodes("//UML:SignalEvent[@xmi.id]");
        
        List<Node> singleStateNodes = doc.selectNodes("//UML:SimpleState[@xmi.id]");
        
        List<Node> transitionNodes = doc.selectNodes("//UML:Transition[@xmi.id]");
        
        System.out.println(sinalEventNodes.size() + " " + singleStateNodes.size() + " " + transitionNodes.size());
    }
    
    public static void main(String[] args) {
        parser();
    }
}
