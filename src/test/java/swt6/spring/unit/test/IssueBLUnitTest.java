package swt6.spring.unit.test;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import sun.plugin.dom.exception.InvalidStateException;
import swt6.spring.bl.IssueManager;
import swt6.spring.bl.impl.IssueManagerImpl;
import swt6.spring.dal.IssueRepository;
import swt6.spring.dal.LogbookEntryRepository;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.LogbookEntry;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueBLUnitTest {

    @Mock
    private LogbookEntryRepository entryRepo;

    @Mock
    private LogbookEntry mockEntry;

    @Mock
    private IssueRepository issueRepo;

    @Mock
    private Issue mockIssue1;

    @Mock
    private Issue mockIssue2;

    @Mock
    private Employee mockEmpl1;

    @Mock
    private Employee mockEmpl2;

    @Rule
    public final ExpectedException exception =
            ExpectedException.none();

    @Test
    public void testInit() {
        assertNotNull(issueRepo);
        assertNotNull(mockIssue1);
        assertNotNull(mockIssue2);
        assertNotNull(entryRepo);
        assertNotNull(mockEntry);
        assertNotNull(mockEmpl1);
        assertNotNull(mockEmpl2);
    }

    @Test
    public void AssigneeCantChange() {
        Mockito.when(mockIssue1.getId()).thenReturn(1L);
        Mockito.when(mockIssue1.getAssignee()).thenReturn(mockEmpl1);
        Mockito.when(mockIssue2.getId()).thenReturn(1L);
        Mockito.when(mockIssue2.getAssignee()).thenReturn(mockEmpl2);
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(mockEmpl2.getId()).thenReturn(2L);
        Mockito.when(issueRepo.findOne(1L)).thenReturn(mockIssue2);
        Mockito.when(entryRepo.findAllByEmployeeAndIssue(mockEmpl2, mockIssue1))
                .thenReturn(Arrays.asList(mockEntry));
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(mockEmpl2.getId()).thenReturn(2L);

        IssueManager mgr = new IssueManagerImpl(issueRepo, entryRepo);
        exception.expect(IllegalStateException.class);
        mgr.save(mockIssue1);
    }

    @Test
    public void ValidAssigneeChange() {
        Mockito.when(mockIssue1.getId()).thenReturn(1L);
        Mockito.when(mockIssue1.getAssignee()).thenReturn(mockEmpl1);
        Mockito.when(mockIssue2.getId()).thenReturn(1L);
        Mockito.when(mockIssue2.getAssignee()).thenReturn(mockEmpl2);
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(mockEmpl2.getId()).thenReturn(2L);
        Mockito.when(issueRepo.findOne(1L)).thenReturn(mockIssue2);
        Mockito.when(entryRepo.findAllByEmployeeAndIssue(mockEmpl2, mockIssue1))
                .thenReturn(new ArrayList<>());
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(mockEmpl2.getId()).thenReturn(2L);

        IssueManager mgr = new IssueManagerImpl(issueRepo, entryRepo);
        mgr.save(mockIssue1);
    }


    @Test
    public void MissingTimeEstimate() {
        Mockito.when(mockIssue1.getId()).thenReturn(1L);
        Mockito.when(mockIssue1.getAssignee()).thenReturn(mockEmpl1);
        Mockito.when(mockIssue2.getId()).thenReturn(1L);
        Mockito.when(mockIssue2.getAssignee()).thenReturn(null);
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(issueRepo.findOne(1L)).thenReturn(mockIssue2);
        Mockito.when(mockEmpl1.getId()).thenReturn(1L);
        Mockito.when(mockIssue1.getEstimatedMinutes()).thenReturn(null);

        IssueManager mgr = new IssueManagerImpl(issueRepo, entryRepo);
        exception.expect(IllegalStateException.class);
        mgr.save(mockIssue1);
    }
}
