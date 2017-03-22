package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.EmployeeManager;
import swt6.spring.domain.Employee;
import swt6.spring.dal.AddressRepository;
import swt6.spring.dal.EmployeeRepository;
import swt6.spring.domain.Address;

@Component
public class EmployeeManagerImpl extends AbstractBaseManager<Employee, Long, EmployeeRepository> implements EmployeeManager {
    @Autowired
    public EmployeeManagerImpl(EmployeeRepository repo) {
        super(Employee.class, repo);
    }
}
