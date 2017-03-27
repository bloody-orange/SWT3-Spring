package swt6.spring.integration.test;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EmployeeBLTest.class,
        IssueBLTest.class,
        AddressBLTest.class
})
public class TestSuite {
}