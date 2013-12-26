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
import StateChart.exception.StateChartException;
import StateChart.exception.StateChartExceptionCode;

public class StateChartParser {
    
    private static Document doc = null;
    
    static {
        SAXReader reader = new SAXReader();  
        InputStream in = StateChartParser.class.getClassLoader().
                getResourceAsStream("statechart2.xmi");
        //FIXME
        try {  
            doc = reader.read(in);
        } catch (DocumentException e) {  
            e.printStackTrace();  
        } 
    }

    public static UMLStateChart parser() throws Exception{
        Element usm = (Element) doc.selectSingleNode("//UML:StateMachine");
        if(null == usm) {
            return null;
        }
        UMLStateChart usc = new UMLStateChart();
        usc.setId(usm.attributeValue("xmi.id"));
        usc.setName(usm.attributeValue("name"));
        usc.setTriggers(parseTriggers());
        usc.setStates(parseStates());
        usc.setTransitions(parseTransitions(usc));
        usc.setInitState(usc.getStateById(parseInitState(usm)));
        
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
            
            trigger.addTransition(t);
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
            trigger.setTransitions(new HashSet<Transition>()); //empty
            triggers.put(id, trigger);
        }
        return triggers;
    }
    
    /**
     * 1.找到在最外层的开始状态（即整个状态图的Pseudostate，而非组合状态内的Pseudostate）
     * 2.找到引用到它的迁移（只有一个）
     * 3.取出该迁移的target，获得初始状态，如果该状态是组合状态取出组合状态中的第一个状态，如果是简单状态不须做处理
     */
    @SuppressWarnings("unchecked")
    private static String parseInitState(Element usm) {
        
        List<Node> nodes = usm.selectNodes("UML:StateMachine.top/UML:CompositeState/UML:CompositeState.subvertex/UML:Pseudostate[@xmi.id]");
        
        if(null == nodes || nodes.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.NO_INIT_STATE, "状态图没有初始化状态");
        }
        
        if(nodes.size() > 1) {
            throw new StateChartException(StateChartExceptionCode.MULTI_INIT_STATE, "状态图多个初始状态");
        }
        
        Element el = (Element) nodes.get(0);
        List<Node> pseudoRefNodes = usm.selectNodes(".//UML:Pseudostate[@xmi.idref='"+el.attributeValue("xmi.id")+"']");
        if(null == pseudoRefNodes || pseudoRefNodes.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.NO_TRANSITION_INIT_STATE, "状态图初始状态没有迁移");
        }
        if(pseudoRefNodes.size() > 1) {
            throw new StateChartException(StateChartExceptionCode.MULTI_TRANSITION_INIT_STATE, "状态图初始状态包含多个迁移");
        }
        
        List<Element> targetStates = pseudoRefNodes.get(0).selectNodes("../../UML:Transition.target/*");
        if(null == targetStates ||targetStates.size() != 1) {
            throw  new StateChartException(StateChartExceptionCode.XMI_FORMAT_ERROR, "xmi格式错误，开始状态target没有或者有多个");
        }
        
        if("UML:CompositeState".equals(targetStates.get(0).getName())) {
            return getFirstStateIdInCompositeState(targetStates.get(0));
        }else {
            return targetStates.get(0).attributeValue("xmi.idref");
        }
    }
    
    /**
     * 返回组合状态中的初始子状态id
     * @param 引用组合状态的节点  eg.<UML:CompositeState xmi.idref = '111--70-113-77--5c58af58:1431fb405ae:-8000:0000000000000965'/>
     */
    private static String getFirstStateIdInCompositeState(Node node){
        
        if(null == node) {
            throw new StateChartException(StateChartExceptionCode.METHOD_PARAM_ERROR, "参数不允许为null");
        }
        
        Element e = (Element) node;
        
        if("SimpleState".equals(e.getName())) {
            return e.attributeValue("xmi.idref");
        }else if("CompositeState".equals(e.getName())) {
            Element compositeStateEl = (Element) doc.selectSingleNode("//UML:CompositeState[@xmi.id='" + e.attributeValue("xmi.idref") + "']");
            Element cStateOutTransRefEl = (Element) compositeStateEl.selectSingleNode("UML:CompositeState.subvertex/UML:Pseudostate/UML:StateVertex.outgoing/UML:Transition");
            if(null == cStateOutTransRefEl) {
                throw  new StateChartException(StateChartExceptionCode.XMI_FORMAT_ERROR, "xml格式有问题，组合状态中没有Pseudostate状态");
                
            }
            Element firstStateEl = (Element) doc.selectSingleNode("//UML:Transition[@xmi.id='"+cStateOutTransRefEl.attributeValue("xmi.idref")+"']/UML:Transition.target/UML:SimpleState");
            return firstStateEl.attributeValue("xmi.idref");
        }else {
            throw  new StateChartException(StateChartExceptionCode.METHOD_PARAM_ERROR, "参数node不是SimpleState或者CompositeState");
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
