package swt6.spring.bl.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.BaseEntity;
import swt6.spring.bl.BaseManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractBaseManager<
        T extends BaseEntity<IdT>,
        IdT extends Serializable,
        RepoT extends JpaRepository<T, IdT>>
        implements BaseManager<T, IdT> {

    private final Class<T> entityType;
    protected final JpaRepository<T, IdT> repo;

    protected AbstractBaseManager(Class<T> entityType, RepoT repo) {
        this.entityType = entityType;
        this.repo = repo;
    }

    @Override
    public T save(T entity) {
        return repo.saveAndFlush(entity);
    }

    @Override
    public T findById(IdT id) {
        return repo.findOne(id);
    }

    @Override
    public List<T> findAll() {
        return repo.findAll();
    }

    @Override
    public void remove(T obj) {
        obj.removeDependencies();
        repo.delete(obj);
    }

    @Override
    public void removeById(IdT id) {
        T entity = repo.findOne(id);
        remove(entity);
    }
}
