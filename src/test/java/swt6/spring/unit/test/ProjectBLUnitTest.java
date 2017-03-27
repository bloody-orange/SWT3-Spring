package swt6.spring.unit.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import swt6.spring.bl.EmployeeManager;
import swt6.spring.bl.impl.EmployeeManagerImpl;
import swt6.spring.dal.EmployeeRepository;
import swt6.spring.dal.ProjectRepository;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Project;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProjectBLUnitTest {

    @Mock
    private EmployeeRepository emplRepo;

    @Mock
    private ProjectRepository projRepo;

    @Mock
    Project mockProj;

    @Mock
    Employee mockEmpl;

    @Rule
    public final ExpectedException exception =
            ExpectedException.none();

    @Test
    public void testInit() {
        assertNotNull(emplRepo);
        assertNotNull(projRepo);
        assertNotNull(mockEmpl);
        assertNotNull(mockProj);
    }

    @Test
    public void findAll() {
        List<Employee> mockEmpls = new ArrayList<>();
        mockEmpls.add(mockEmpl);
        mockEmpls.add(mockEmpl);
        mockEmpls.add(mockEmpl);
        Mockito.when(emplRepo.findAll()).thenReturn(mockEmpls);

        EmployeeManager emplMgr = new EmployeeManagerImpl(emplRepo, projRepo);
        assertEquals(3, emplMgr.findAll().size());
    }

    @Test
    public void findAllByProject() {
        Mockito.when(emplRepo.findOne(1L)).thenReturn(mockEmpl);
        Mockito.when(emplRepo.findOne(2L)).thenReturn(mockEmpl);
        Mockito.doNothing().when(mockProj).removeMember(mockEmpl);

        EmployeeManager emplMgr = new EmployeeManagerImpl(emplRepo, projRepo);
        emplMgr.removeById(1L);
        emplMgr.removeById(2L);
    }
}
