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
import swt6.spring.bl.AddressManager;
import swt6.spring.bl.EmployeeManager;
import swt6.spring.domain.Address;
import swt6.spring.domain.AddressId;
import swt6.test.DataOperations;
import swt6.util.DbSetupUtil;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:swt6/spring/integration/test/testApplicationContext.xml",
        "classpath:swt6/spring/client/applicationContext*.xml"})
public class AddressBLTest {
    private static DbSetupTracker tracker = new DbSetupTracker();
    private static final AddressId id1 = new AddressId("4232", "Hagenberg", "Softwarepark 14");
    private static final AddressId id2 = new AddressId("4232", "Hagenberg", "Softwarepark 105");

    @Autowired
    private AddressManager addrMgr;

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
        assertEquals(2, addrMgr.findAll().size());
        Address a = new Address("4232", "Hagenberg", "Softwarepark 12345");
        a = addrMgr.save(a);
        assertNotNull(a.getId());
        assertEquals(3, addrMgr.findAll().size());
    }

    @Test
    public void testGetById() {
        assertNotNull(addrMgr.findById(id1));
    }

    @Test
    public void testUpdate() {
        Address addr = addrMgr.findById(id1);
        assertNotNull(addr);
        assertNotEquals(addr.getCity(), "NotHagenberg");
        addr.setCity("NotHagenberg");
        addr = addrMgr.save(addr);
        assertEquals(addr.getCity(), "NotHagenberg");
    }

    @Test
    @Transactional
    public void testDelete() {
        Address a = addrMgr.findById(id1);
        assertNotNull(a);
        addrMgr.remove(a);
        Address nullA = addrMgr.findById(id1);
        assertNull(nullA);

        a = addrMgr.findById(id2);
        assertNotNull(a);
        addrMgr.removeById(id2);
        nullA = addrMgr.findById(id2);
        assertNull(nullA);
    }
}
