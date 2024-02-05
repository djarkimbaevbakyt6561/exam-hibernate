package bakyt.dao;

import bakyt.entities.BlogPost;
import bakyt.entities.Project;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface BlogPostDao {
    void createBlogPost(BlogPost blogPost);
    Optional<BlogPost> getBlogPostById(Long id);
    void updateBlogPostContent(Long id, String newContent);
    void deleteBlogPost(Long id);
    LinkedList<BlogPost> getAllBlogPosts();
    List<BlogPost> searchBlogPostByKeyword(String keyword);
}
