package bakyt.services;

import bakyt.entities.BlogPost;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface BlogPostService {
    void createBlogPost(BlogPost blogPost);
    BlogPost getBlogPostById(Long id);
    void updateBlogPostContent(Long id, String newContent);
    void deleteBlogPost(Long id);
    LinkedList<BlogPost> getAllBlogPosts();
    List<BlogPost> searchBlogPostByKeyword(String keyword);
}
