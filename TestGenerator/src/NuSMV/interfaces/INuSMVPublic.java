package NuSMV.interfaces;

import java.util.List;

import NuSMV.entity.Property;
import NuSMV.entity.TestCase;
import NuSMV.entity.Trace;

public interface INuSMVPublic {

    List<TestCase> getTestCases();

    List<Property> getProperties();

    List<Trace> getTraces();

}
