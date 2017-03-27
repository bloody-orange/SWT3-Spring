package swt6.spring.bl;

import sun.rmi.runtime.Log;
import swt6.spring.domain.Employee;
import swt6.spring.domain.Issue;
import swt6.spring.domain.LogbookEntry;

import java.util.List;

public interface LogbookEntryManager extends BaseManager<LogbookEntry, Long>  {
    List<LogbookEntry> findAllByEmployeeAndIssue(Employee employee, Issue issue);
}
