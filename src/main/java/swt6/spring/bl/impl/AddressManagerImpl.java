package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.AddressManager;
import swt6.spring.dal.AddressRepository;
import swt6.spring.domain.Address;
import swt6.spring.domain.AddressId;

import java.util.List;

@Component
public class AddressManagerImpl extends AbstractBaseManager<Address, AddressId, AddressRepository> implements AddressManager {
    @Autowired
    public AddressManagerImpl(AddressRepository repo) {
        super(Address.class, repo);
    }
}
