package swt6.spring.client;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.test.DataOperations;
import swt6.spring.domain.Project;
import swt6.spring.ui.UIProcessFacade;
import swt6.util.DbSetupUtil;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class ConsoleApplication {
    public static void main(String[] args) {

        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/client/applicationContext*.xml")) {
            EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
            emFactory.createEntityManager();

            try {
                DbSetupUtil.getDbSetup(DataOperations.DELETE_INSERT_ALL).launch();
            }
            catch (IOException e) {
                e.printStackTrace();
                System.err.println("Can't insert test data, exiting...");
                return;
            }

            UIProcessFacade ui = factory.getBean("uiComponent", UIProcessFacade.class);
            Project proj = new Project("Hefgllo", null);
            ui.createProject(proj);
            ui.displayAllProjects();
            ui.displayWorktimesByProject(101L);
            ui.displayWorktimesByProject(102L);
        }
    }
}
