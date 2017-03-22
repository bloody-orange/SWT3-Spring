package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.ProjectManager;
import swt6.spring.dal.ProjectRepository;
import swt6.spring.domain.Project;

@Component
public class ProjectManagerImpl extends AbstractBaseManager<Project, Long, ProjectRepository> implements ProjectManager{
    @Autowired
    public ProjectManagerImpl(ProjectRepository repo) {
        super(Project.class, repo);
    }
}
