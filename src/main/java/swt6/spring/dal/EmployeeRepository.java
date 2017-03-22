package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Employee;


/*
    List<T> findAll();
    List<T> findAll(Sort var1);
    List<T> findAll(Iterable<ID> var1);
    <S extends T> List<S> save(Iterable<S> var1);
    void flush();
    <S extends T> S saveAndFlush(S var1);
    void deleteInBatch(Iterable<T> var1);
    void deleteAllInBatch();
    T getOne(ID var1);
    <S extends T> List<S> findAll(Example<S> var1);
    <S extends T> List<S> findAll(Example<S> var1, Sort var2);
  */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}