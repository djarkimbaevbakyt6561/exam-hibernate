package bakyt.services.impls;

import bakyt.dao.BlogPostDao;
import bakyt.dao.impls.BlogPostDaoImpl;
import bakyt.entities.BlogPost;
import bakyt.services.BlogPostService;

import java.util.LinkedList;
import java.util.List;

public class BlogPostServiceImpl implements BlogPostService {
    BlogPostDao blogPostDao = new BlogPostDaoImpl();
    @Override
    public void createBlogPost(BlogPost blogPost) {
        blogPostDao.createBlogPost(blogPost);
    }

    @Override
    public BlogPost getBlogPostById(Long id) {
        return blogPostDao.getBlogPostById(id).orElseThrow(id);
    }

    @Override
    public void updateBlogPostContent(Long id, String newContent) {
        blogPostDao.updateBlogPostContent(id, newContent);
    }

    @Override
    public void deleteBlogPost(Long id) {
        blogPostDao.deleteBlogPost(id);
    }

    @Override
    public LinkedList<BlogPost> getAllBlogPosts() {
        return blogPostDao.getAllBlogPosts();
    }

    @Override
    public List<BlogPost> searchBlogPostByKeyword(String keyword) {
        return blogPostDao.searchBlogPostByKeyword(keyword);
    }
}
