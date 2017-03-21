package swt6.spring.dao.data;

import swt6.orm.dao.BaseDao;
import swt6.orm.domain.BaseEntity;

public abstract class AbstractDataBaseDao<T extends BaseEntity<IdT>, IdT> implements BaseDao<T, IdT> {
}
