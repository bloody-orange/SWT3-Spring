package swt6.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.orm.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

public interface BaseDao<T extends BaseEntity<IdT>, IdT extends Serializable> extends JpaRepository<T, IdT> {
    void remove(T obj);
    void removeById(IdT id);
    List<T> findByPredicate(Predicate<T> pred);
}
