package swt6.spring.unit.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import swt6.spring.integration.test.AddressBLTest;
import swt6.spring.integration.test.EmployeeBLTest;
import swt6.spring.integration.test.IssueBLTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IssueBLUnitTest.class,
        ProjectBLUnitTest.class
})
public class TestSuite {
}
