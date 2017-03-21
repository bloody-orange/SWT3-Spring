package swt6.spring.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Issue.class)
public class Issue_ {
    public static volatile SingularAttribute<Issue, IssueState> state;
    public static volatile SingularAttribute<Issue, Employee> assignee;
    public static volatile SingularAttribute<Issue, Project> project;
    public static volatile SetAttribute<Issue, LogbookEntry> entries;
}
