package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.EmployeeManager;
import swt6.spring.dal.ProjectRepository;
import swt6.spring.domain.Employee;
import swt6.spring.dal.AddressRepository;
import swt6.spring.dal.EmployeeRepository;
import swt6.spring.domain.Address;
import swt6.spring.domain.Project;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeManagerImpl extends AbstractBaseManager<Employee, Long, EmployeeRepository> implements EmployeeManager {
    private ProjectRepository projRepo;

    @Autowired
    public EmployeeManagerImpl(EmployeeRepository repo, ProjectRepository projRepo) {
        super(Employee.class, repo);
        this.projRepo = projRepo;
    }

    @Override
    public List<Employee> findAllByProject(Long projId) {
            Project proj = projRepo.findOne(projId);
            return new ArrayList<>(proj.getMembers());
    }
}
