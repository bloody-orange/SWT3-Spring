package swt6.spring.bl;

import swt6.spring.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseManager<T extends BaseEntity<IdT>, IdT extends Serializable> {
    T save(T entity);
    T findById(IdT id);
    List<T> findAll();
    void remove(T obj);
    void removeById(IdT id);
}
