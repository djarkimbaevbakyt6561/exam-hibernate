package bakyt.dao.impls;

import bakyt.config.DataBaseConnection;
import bakyt.dao.CommentDao;
import bakyt.entities.BlogPost;
import bakyt.entities.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDaoImpl implements CommentDao {
    EntityManagerFactory entityManagerFactory = DataBaseConnection.getEntityManagerFactory();

    @Override
    public void addComment(Long postId, Comment comment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            BlogPost blogPost = entityManager.find(BlogPost.class, postId);

            if (blogPost != null) {
                comment.setBlogPost(blogPost);
                entityManager.persist(comment);
            } else {
                System.err.println("BlogPost with id " + postId + " not found!");
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public Optional<Comment> getCommentById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Comment comment = null;
        try {
            entityManager.getTransaction().begin();
            comment = entityManager.createQuery("select a from Comment a where a.id = :parId", Comment.class)
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
        return Optional.ofNullable(comment);
    }

    @Override
    public void updateCommentText(Long id, String newText) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comment existingComment = entityManager.find(Comment.class, id);
            if (existingComment != null) {
                existingComment.setCommentText(newText);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteComment(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Comment comment = entityManager.find(Comment.class, id);
            if (comment != null) {
                entityManager.remove(comment);
            } else {
                System.err.println("Comment with id " + id + " not found!");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public List<Comment> groupCommentsByPost() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Comment> resultList = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String hql = "SELECT c FROM Comment c GROUP BY c.blogPost ORDER BY c.blogPost.id";
            resultList = entityManager.createQuery(hql, Comment.class).getResultList();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            handleException(entityManager, e);
        } finally {
            entityManager.close();
        }

        return resultList;
    }
    @Override
    public List<Comment> sortByPublishDate() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Comment> resultList = new ArrayList<>();

        try {
            entityManager.getTransaction().begin();

            String hql = "FROM Comment c ORDER BY c.publishDate DESC";
            resultList = entityManager.createQuery(hql, Comment.class).getResultList();

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
