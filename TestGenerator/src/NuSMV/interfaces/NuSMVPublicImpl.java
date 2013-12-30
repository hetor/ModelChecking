package NuSMV.interfaces;

import java.util.List;

import NuSMV.entity.Property;
import NuSMV.entity.TestCase;
import NuSMV.entity.Trace;
import NuSMV.factory.TestCaseFactory;
import NuSMV.xmlparser.PropertyParser;
import NuSMV.xmlparser.TracesParser;
import StateChart.entity.UMLStateChart;

/**
 * ��NuSMV������������testcase
 * @author hetao
 */
public class NuSMVPublicImpl implements INuSMVPublic{
    
    private String pPath;
    private String tPath;
    private UMLStateChart usc;
    
    private List<Property> properties;
    private List<Trace> traces;
    
    public NuSMVPublicImpl(String pPath, String tPath, UMLStateChart usc) {
        this.pPath = pPath;
        this.tPath = tPath;
        this.usc = usc;
    }
    
    /**
     * ��ȡ��ϸ�Ĳ�������
     */
    @Override
    public List<TestCase> getTestCases() {
        if(null == properties) {
            properties = PropertyParser.parser(pPath);
        }
        if(null == traces) {
            traces = TracesParser.parser(tPath);
        }
        return TestCaseFactory.createTestCase(properties, traces, usc);
    }
    
    /**
     * ��ֻ֧����CTL
     * @return ������֤���ʵĽ��
     */
    @Override
    public List<Property> getProperties() {
        if(null == properties) {
            properties = PropertyParser.parser(pPath);
        }
        return properties;
    }
    
    /**
     * @return ���з������Ľ��
     */
    @Override
    public List<Trace> getTraces() {
        if(null == traces) {
            traces = TracesParser.parser(tPath);
        }
        return traces;
    }
}
