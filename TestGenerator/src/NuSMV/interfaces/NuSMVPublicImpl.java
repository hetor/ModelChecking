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
 * 从NuSMV测试用例生成testcase
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
     * 获取详细的测试用例
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
     * 暂只支持了CTL
     * @return 所有验证性质的结果
     */
    @Override
    public List<Property> getProperties() {
        if(null == properties) {
            properties = PropertyParser.parser(pPath);
        }
        return properties;
    }
    
    /**
     * @return 所有反例迹的结果
     */
    @Override
    public List<Trace> getTraces() {
        if(null == traces) {
            traces = TracesParser.parser(tPath);
        }
        return traces;
    }
}
