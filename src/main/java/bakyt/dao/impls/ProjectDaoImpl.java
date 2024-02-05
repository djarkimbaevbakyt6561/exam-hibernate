package bakyt.dao.impls;

import bakyt.config.DataBaseConnection;
import bakyt.dao.ProjectDao;
import bakyt.entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProjectDaoImpl implements ProjectDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();
    @Override
    public void createProject(Project project) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(project);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Project project = null;
        try {
            entityManager.getTransaction().begin();
            project = entityManager.createQuery("select a from Project a where a.id = :parId", Project.class)
                    .setParameter("parId", id)
                    .getSingleResult();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(project);
    }

    @Override
    public void updateProject(Long id, Project newProject) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Project existingProject = entityManager.find(Project.class, id);
            if (existingProject != null) {
                existingProject.setEndDate(newProject.getEndDate());
                existingProject.setName(newProject.getName());
                existingProject.setStartDate(newProject.getStartDate());
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteProject(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Project project = getProjectById(id).orElseThrow(RuntimeException::new);
            if (project != null) {
                entityManager.remove(project);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public LinkedList<Project> getAllProjects() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LinkedList<Project> linkedList = new LinkedList<>();
        try {
            List<Project> resultList = entityManager.createQuery("FROM Project", Project.class).getResultList();
            linkedList.addAll(resultList);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
        return linkedList;
    }

    @Override
    public List<Project> getProjectsByDateRange(LocalDate startDate, LocalDate endDate) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Project> resultList = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();
            String hql = "FROM Project WHERE startDate >= :start AND endDate <= :end";
            resultList = entityManager.createQuery(hql, Project.class)
                    .setParameter("start", startDate)
                    .setParameter("end", endDate)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return resultList;
    }

    private void handleException(EntityManager entityManager, Exception e) {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        System.err.println(e.getMessage());
    }
}
