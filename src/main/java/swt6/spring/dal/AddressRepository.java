package swt6.spring.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.spring.domain.Address;
import swt6.spring.domain.AddressId;

public interface AddressRepository extends JpaRepository<Address, AddressId> {
}
