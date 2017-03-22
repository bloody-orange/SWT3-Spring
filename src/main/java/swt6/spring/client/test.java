package swt6.spring.client;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.dal.AddressDao;

import javax.persistence.EntityManagerFactory;

public class test {
    public static void main(String[] args) {

        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/client/applicationContext.xml")) {
            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            emFactory.createEntityManager();
        }
    }
}
