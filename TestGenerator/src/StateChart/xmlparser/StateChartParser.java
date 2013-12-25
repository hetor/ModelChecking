package StateChart.xmlparser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import StateChart.entity.State;
import StateChart.entity.Transition;
import StateChart.entity.Trigger;
import StateChart.entity.UMLStateChart;

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
    public static UMLStateChart parser() throws Exception{
        List<Element> usmNodes = doc.selectNodes("//UML:StateMachine");
        if(null == usmNodes || usmNodes.size() > 1) {
            return null;
        }
        
        Element usmEl = (Element)usmNodes.get(0);
        UMLStateChart usc = new UMLStateChart();
        usc.setId(usmEl.attributeValue("xmi.id"));
        usc.setName(usmEl.attributeValue("name"));
        
        usc.setTriggers(parseTriggers());
        usc.setStates(parseStates());
        usc.setTransitions(parseTransitions(usc));
        
        return usc;
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, Transition> parseTransitions(UMLStateChart usc) throws Exception{
        List<Node> transitionNodes = doc.selectNodes("//UML:Transition[@xmi.id]");
        Map<String, Transition> trans = new HashMap<>();
        for(Node node : transitionNodes) {
            Element el = (Element) node;
            if(!isTransitionValid(el)) continue;
            
            String id = el.attributeValue("xmi.id");
            Trigger trigger = usc.getTrigerById(((Element)el.selectSingleNode("UML:Transition.trigger/UML:SignalEvent")).attributeValue("xmi.idref"));
            State source = usc.getStateById(getFirstStateIdInCompositeState((Node)el.element("Transition.source").elements().get(0)));
            State target = usc.getStateById(getFirstStateIdInCompositeState((Node)el.element("Transition.target").elements().get(0)));
            
            Transition t = new Transition();
            t.setId(id);
            t.setTrigger(trigger);
            t.setSource(source);
            t.setTarget(target);
            
            source.addOutgoing(t);
            target.addIncomings(t);
            trans.put(id, t);
        }
        return trans;
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, State> parseStates() {
        List<Node> singleStateNodes = doc.selectNodes("//UML:SimpleState[@xmi.id]");
        Map<String, State> states = new HashMap<>();
        for(Node node : singleStateNodes) {
            State state = new State();
            Element el = (Element)node;
            String id = el.attributeValue("xmi.id");
            state.setId(id);
            state.setName(el.attributeValue("name"));
            state.setIncomings(new HashSet<Transition>()); //empty
            state.setOutgoings(new HashSet<Transition>()); //empty
            states.put(id, state);
        }
        return states;
    }
    
    @SuppressWarnings("unchecked")
    private static Map<String, Trigger> parseTriggers() {
        List<Node> singalEventNodes = doc.selectNodes("//UML:SignalEvent[@xmi.id]");
        Map<String, Trigger> triggers = new HashMap<>();
        for (Node node : singalEventNodes) {
            Element el = (Element)node;
            String id = el.attributeValue("xmi.id");
            Trigger trigger = new Trigger();
            trigger.setId(id);
            trigger.setName(el.attributeValue("name"));
            triggers.put(id, trigger);
        }
        return triggers;
    }
    
    /**
     * 返回组合状态中的初始子状态id
     * @param 引用组合状态的节点  eg.<UML:CompositeState xmi.idref = '111--70-113-77--5c58af58:1431fb405ae:-8000:0000000000000965'/>
     */
    private static String getFirstStateIdInCompositeState(Node node) throws Exception{
        
        if(null == node) {
            throw new Exception("参数不允许为null");
        }
        
        Element e = (Element) node;
        
        if("SimpleState".equals(e.getName())) {
            return e.attributeValue("xmi.idref");
        }else if("CompositeState".equals(e.getName())) {
            Element compositeStateEl = (Element) doc.selectSingleNode("//UML:CompositeState[@xmi.id='" + e.attributeValue("xmi.idref") + "']");
            Element cStateOutTransRefEl = (Element) compositeStateEl.selectSingleNode("UML:CompositeState.subvertex/UML:Pseudostate/UML:StateVertex.outgoing/UML:Transition");
            if(null == cStateOutTransRefEl) {
                throw new Exception("xml格式有问题，组合状态中没有Pseudostate状态");
            }
            Element firstStateEl = (Element) doc.selectSingleNode("//UML:Transition[@xmi.id='"+cStateOutTransRefEl.attributeValue("xmi.idref")+"']/UML:Transition.target/UML:SimpleState");
            return firstStateEl.attributeValue("xmi.idref");
        }else {
            throw new Exception("xml格式有问题");
        }
    } 
    
    /**
     * 判断迁移是否是有效的迁移
     */
    private static boolean isTransitionValid(Element el) {
        Node triggerRefNode = el.selectSingleNode("UML:Transition.trigger/UML:SignalEvent");
        return null != triggerRefNode;
    }
    
    public static void main(String[] args) {
        try {
            UMLStateChart umlStateChart = parser();
            System.out.println(umlStateChart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
