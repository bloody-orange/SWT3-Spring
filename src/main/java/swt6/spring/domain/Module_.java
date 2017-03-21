package swt6.spring.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Module.class)
public class Module_ {
    public static volatile SingularAttribute<Module, Project> project;
}
