package swt6.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.orm.domain.Address;
import swt6.orm.domain.AddressId;

public interface AddressDao extends BaseDao<Address, AddressId>{
}
