package bakyt.dao.impls;

import bakyt.config.DataBaseConnection;
import bakyt.dao.BlogPostDao;
import bakyt.entities.BlogPost;
import bakyt.entities.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BlogPostDaoImpl implements BlogPostDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void createBlogPost(BlogPost blogPost) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(blogPost);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<BlogPost> getBlogPostById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        BlogPost blogPost = null;
        try {
            entityManager.getTransaction().begin();
            blogPost = entityManager.createQuery("select a from BlogPost a where a.id = :parId", BlogPost.class)
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
        return Optional.ofNullable(blogPost);
    }

    @Override
    public void updateBlogPostContent(Long id, String newContent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            BlogPost existingBlogPost = entityManager.find(BlogPost.class, id);
            if (existingBlogPost != null) {
                existingBlogPost.setContent(newContent);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteBlogPost(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            BlogPost blogPost = getBlogPostById(id).orElseThrow(RuntimeException::new);
            if (blogPost != null) {
                entityManager.remove(blogPost);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public LinkedList<BlogPost> getAllBlogPosts() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        LinkedList<BlogPost> linkedList = new LinkedList<>();
        try {
            List<BlogPost> resultList = entityManager.createQuery("FROM BlogPost", BlogPost.class).getResultList();
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
    public List<BlogPost> searchBlogPostByKeyword(String keyword) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<BlogPost> resultList = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();
            String hql = "FROM BlogPost WHERE title LIKE :keyword OR content LIKE :keyword";
            resultList = entityManager.createQuery(hql, BlogPost.class)
                    .setParameter("keyword", "%" + keyword + "%")
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
