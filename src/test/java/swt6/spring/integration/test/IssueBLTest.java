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
import swt6.spring.domain.*;
import swt6.spring.ui.UIProcessFacade;
import swt6.test.DataOperations;
import swt6.util.DateUtil;
import swt6.util.DbSetupUtil;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:swt6/spring/integration/test/testApplicationContext.xml",
        "classpath:swt6/spring/client/applicationContext*.xml"})
public class IssueBLTest {
    private static DbSetupTracker tracker = new DbSetupTracker();
    private static final Long id1 = 101L;
    private static final Long id2 = 102L;
    private static final Long id3 = 103L;

    @Autowired
    private IssueManager issueMgr;
    @Autowired
    private ProjectManager projMgr;
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
        Operation operation = DataOperations.DELETE_INSERT_ALL;
        tracker.launchIfNecessary(DbSetupUtil.getTestDbSetup(operation));
    }

    @Test
    public void testAdd() {
        assertEquals(5, issueMgr.findAll().size());
        Project proj = projMgr.findById(101L);
        Employee empl = emplMgr.findById(101L);
        Issue issue = new Issue("Testissue", proj, IssueState.OPEN,
                IssuePriority.HIGH, 0, 0);
        issueMgr.save(issue);
        assertEquals(6, issueMgr.findAll().size());
    }

    @Test
    public void testGetById() {
        assertNotNull(issueMgr.findById(id1));
    }

    @Test
    public void testUpdate() {
        final String otherName = "OtherName";
        Issue issue = issueMgr.findById(id1);
        assertNotNull(issue);
        assertNotEquals(issue.getDescription(), otherName);
        issue.setDescription(otherName);
            issue = issueMgr.save(issue);
        Issue otherProj = issueMgr.findById(issue.getId());
        assertEquals(otherProj.getDescription(), otherName);
    }

    @Test
    @Transactional
    public void testDelete() {
        Issue issue = issueMgr.findById(id1);
        assertNotNull(issue);
        issueMgr.remove(issue);
        Issue nullEntity = issueMgr.findById(id1);
        assertNull(nullEntity);

        Issue otherEntity = issueMgr.findById(id2);
        assertNotNull(otherEntity);
        issueMgr.removeById(otherEntity.getId());
        nullEntity = issueMgr.findById(id2);
        assertNull(nullEntity);
    }

    @Test
    public void findByState() {
        List<Issue> issues = issueMgr.findByState(IssueState.NEW);
        assertNotNull(issues);
        assertEquals(1, issues.size());
    }

    @Test
    public void findByEmployee() {
        Project proj = projMgr.findById(101L);
        assertNotNull(proj);
        List<Issue> issues = issueMgr.findByProject(proj);
        assertNotNull(issues);
        assertEquals(3, issues.size());
    }

    @Test
    public void findByEmployeeAndState() {
        Project proj = projMgr.findById(101L);
        assertNotNull(proj);
        List<Issue> issues = issueMgr.findByProjectAndState(proj, IssueState.OPEN);
        assertNotNull(issues);
        assertEquals(1, issues.size());
    }
}
