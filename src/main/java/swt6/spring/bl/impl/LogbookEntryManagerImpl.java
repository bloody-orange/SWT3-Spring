package swt6.spring.bl.impl;

import org.apache.log4j.spi.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.LogbookEntryManager;
import swt6.spring.dal.LogbookEntryRepository;
import swt6.spring.domain.LogbookEntry;

@Component
public class LogbookEntryManagerImpl extends AbstractBaseManager<LogbookEntry, Long, LogbookEntryRepository> implements LogbookEntryManager {
    @Autowired
    public LogbookEntryManagerImpl(LogbookEntryRepository repo) {
        super(LogbookEntry.class, repo);
    }

    @Override
    public LogbookEntry save(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Entry is null");
        }
        if (entry.getProject() == null &&
                (entry.getIssue() == null || entry.getIssue().getProject() == null)) {
            throw new IllegalArgumentException("Entry is not assigned to project (or issue with project)");
        }
        return super.save(entry);
    }
}
