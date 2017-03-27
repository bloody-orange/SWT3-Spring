package swt6.spring.integration.test;

import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.bl.*;
import swt6.spring.domain.Employee;
import swt6.spring.ui.UIProcessFacade;
import swt6.test.DataOperations;
import swt6.util.DateUtil;
import swt6.util.DbSetupUtil;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:swt6/spring/integration/test/testApplicationContext.xml",
        "classpath:swt6/spring/client/applicationContext*.xml"})
public class EmployeeBLTest {
    private static DbSetupTracker tracker = new DbSetupTracker();
    private static final Long id1 = 101L;
    private static final Long id2 = 102L;
    private static final Long id3 = 103L;

    @Autowired
    private EmployeeManager emplMgr;

    @BeforeClass
    public static void init() {
    }

    @AfterClass
    public static void close() {
    }

    @Before
    public void prepare() throws Exception {
        Operation operation = Operations.sequenceOf(
                DataOperations.DELETE_ALL,
                DataOperations.INSERT_ADDRESSES,
                DataOperations.INSERT_EMPLOYEES,
                DataOperations.INSERT_PROJECTS,
                DataOperations.INSERT_PROJECTEMPLOYEE);
        tracker.launchIfNecessary(DbSetupUtil.getTestDbSetup(operation));
    }


    @Test
    public void testAdd() {
        Employee empl = new Employee("test", "test", DateUtil.getDate(2000, 1, 1));
        assertNotNull(empl);
        emplMgr.save(empl);
        assertNotNull(empl);
        assertNotNull(empl.getId());
    }

    @Test
    public void testGetById() {
        assertNotNull(emplMgr.findById(id1));
    }

    @Test
    public void testUpdate() {
        final String otherName = "OtherName";
        Employee empl = emplMgr.findById(id1);
        assertNotNull(empl);
        assertNotEquals(empl.getFirstName(), otherName);
        empl.setFirstName(otherName);
        empl = emplMgr.save(empl);
        Employee otherEmpl = emplMgr.findById(empl.getId());
        assertEquals(otherEmpl.getId(), empl.getId());
        assertEquals(otherEmpl.getFirstName(), otherName);
    }

    @Test
    @Transactional
    public void testDelete() {
        Employee empl = emplMgr.findById(id2);
        assertNotNull(empl);
        emplMgr.remove(empl);
        Employee nullEmpl = emplMgr.findById(id2);
        assertNull(nullEmpl);

        empl = emplMgr.findById(id3);
        assertNotNull(empl);
        emplMgr.removeById(id3);
        nullEmpl = emplMgr.findById(id3);
        assertNull(nullEmpl);
    }

    @Test
    public void testFindByProject() {
        assertTrue(emplMgr.findAllByProject(id1).size() == 2);
        assertTrue(emplMgr.findAllByProject(id2).size() == 3);
    }
}
