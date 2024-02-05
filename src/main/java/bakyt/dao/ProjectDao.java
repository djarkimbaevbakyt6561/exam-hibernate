package bakyt.dao;

import bakyt.entities.Project;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface ProjectDao {
    void createProject(Project project);
    Optional<Project> getProjectById(Long id);
    void updateProject(Long id, Project newProject);
    void deleteProject(Long id);
    LinkedList<Project> getAllProjects();
    List<Project> getProjectsByDateRange(LocalDate startDate, LocalDate endDate);

}
