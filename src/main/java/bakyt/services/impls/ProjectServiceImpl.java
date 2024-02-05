package bakyt.services.impls;

import bakyt.dao.ProjectDao;
import bakyt.dao.impls.ProjectDaoImpl;
import bakyt.entities.Project;
import bakyt.services.ProjectService;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    ProjectDao projectDao = new ProjectDaoImpl();
    @Override
    public void createProject(Project project) {
        projectDao.createProject(project);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectDao.getProjectById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateProject(Long id, Project newProject) {
        projectDao.updateProject(id, newProject);
    }

    @Override
    public void deleteProject(Long id) {
        projectDao.deleteProject(id);
    }

    @Override
    public LinkedList<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    public List<Project> getProjectsByDateRange(LocalDate startDate, LocalDate endDate) {
        return projectDao.getProjectsByDateRange(startDate, endDate);
    }
}
