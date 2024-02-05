package bakyt;

import bakyt.entities.Project;
import bakyt.services.ProjectService;
import bakyt.services.impls.ProjectServiceImpl;

import java.time.LocalDate;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectServiceImpl();
//        projectService.createProject(new Project("Java", LocalDate.of(2024, 1, 4), LocalDate.of(2024, 1, 5)));
//        projectService.createProject(new Project("SQL", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 6)));
//        projectService.createProject(new Project("Javascript", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 7)));
//        System.out.println(projectService.getProjectsByDateRange(LocalDate.of(2024, 1, 5), LocalDate.of(2024, 1, 6)));

    }
}
