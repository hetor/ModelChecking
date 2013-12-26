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
     * 1.�ҵ��������Ŀ�ʼ״̬��������״̬ͼ��Pseudostate���������״̬�ڵ�Pseudostate��
     * 2.�ҵ����õ�����Ǩ�ƣ�ֻ��һ����
     * 3.ȡ����Ǩ�Ƶ�target����ó�ʼ״̬�������״̬�����״̬ȡ�����״̬�еĵ�һ��״̬������Ǽ�״̬����������
     */
    @SuppressWarnings("unchecked")
    private static String parseInitState(Element usm) {
        
        List<Node> nodes = usm.selectNodes("UML:StateMachine.top/UML:CompositeState/UML:CompositeState.subvertex/UML:Pseudostate[@xmi.id]");
        
        if(null == nodes || nodes.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.NO_INIT_STATE, "״̬ͼû�г�ʼ��״̬");
        }
        
        if(nodes.size() > 1) {
            throw new StateChartException(StateChartExceptionCode.MULTI_INIT_STATE, "״̬ͼ�����ʼ״̬");
        }
        
        Element el = (Element) nodes.get(0);
        List<Node> pseudoRefNodes = usm.selectNodes(".//UML:Pseudostate[@xmi.idref='"+el.attributeValue("xmi.id")+"']");
        if(null == pseudoRefNodes || pseudoRefNodes.size() == 0) {
            throw new StateChartException(StateChartExceptionCode.NO_TRANSITION_INIT_STATE, "״̬ͼ��ʼ״̬û��Ǩ��");
        }
        if(pseudoRefNodes.size() > 1) {
            throw new StateChartException(StateChartExceptionCode.MULTI_TRANSITION_INIT_STATE, "״̬ͼ��ʼ״̬�������Ǩ��");
        }
        
        List<Element> targetStates = pseudoRefNodes.get(0).selectNodes("../../UML:Transition.target/*");
        if(null == targetStates ||targetStates.size() != 1) {
            throw  new StateChartException(StateChartExceptionCode.XMI_FORMAT_ERROR, "xmi��ʽ���󣬿�ʼ״̬targetû�л����ж��");
        }
        
        if("UML:CompositeState".equals(targetStates.get(0).getName())) {
            return getFirstStateIdInCompositeState(targetStates.get(0));
        }else {
            return targetStates.get(0).attributeValue("xmi.idref");
        }
    }
    
    /**
     * �������״̬�еĳ�ʼ��״̬id
     * @param �������״̬�Ľڵ�  eg.<UML:CompositeState xmi.idref = '111--70-113-77--5c58af58:1431fb405ae:-8000:0000000000000965'/>
     */
    private static String getFirstStateIdInCompositeState(Node node){
        
        if(null == node) {
            throw new StateChartException(StateChartExceptionCode.METHOD_PARAM_ERROR, "����������Ϊnull");
        }
        
        Element e = (Element) node;
        
        if("SimpleState".equals(e.getName())) {
            return e.attributeValue("xmi.idref");
        }else if("CompositeState".equals(e.getName())) {
            Element compositeStateEl = (Element) doc.selectSingleNode("//UML:CompositeState[@xmi.id='" + e.attributeValue("xmi.idref") + "']");
            Element cStateOutTransRefEl = (Element) compositeStateEl.selectSingleNode("UML:CompositeState.subvertex/UML:Pseudostate/UML:StateVertex.outgoing/UML:Transition");
            if(null == cStateOutTransRefEl) {
                throw  new StateChartException(StateChartExceptionCode.XMI_FORMAT_ERROR, "xml��ʽ�����⣬���״̬��û��Pseudostate״̬");
                
            }
            Element firstStateEl = (Element) doc.selectSingleNode("//UML:Transition[@xmi.id='"+cStateOutTransRefEl.attributeValue("xmi.idref")+"']/UML:Transition.target/UML:SimpleState");
            return firstStateEl.attributeValue("xmi.idref");
        }else {
            throw  new StateChartException(StateChartExceptionCode.METHOD_PARAM_ERROR, "����node����SimpleState����CompositeState");
        }
    } 
    
    /**
     * �ж�Ǩ���Ƿ�����Ч��Ǩ��
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
