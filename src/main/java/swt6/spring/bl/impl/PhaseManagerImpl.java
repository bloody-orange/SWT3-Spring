package swt6.spring.bl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swt6.spring.bl.PhaseManager;
import swt6.spring.dal.PhaseRepository;
import swt6.spring.domain.Phase;

@Component
public class PhaseManagerImpl extends AbstractBaseManager<Phase, Long, PhaseRepository> implements PhaseManager{
    @Autowired
    public PhaseManagerImpl(PhaseRepository repo) {
        super(Phase.class, repo);
    }
}
