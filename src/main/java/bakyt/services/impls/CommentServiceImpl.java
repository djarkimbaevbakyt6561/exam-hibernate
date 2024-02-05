package bakyt.services.impls;

import bakyt.dao.CommentDao;
import bakyt.dao.impls.CommentDaoImpl;
import bakyt.entities.Comment;
import bakyt.services.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    CommentDao commentDao = new CommentDaoImpl();

    @Override
    public void addComment(Long postId, Comment comment) {
        commentDao.addComment(postId, comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentDao.getCommentById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void updateCommentText(Long id, String newText) {
        commentDao.updateCommentText(id, newText);
    }

    @Override
    public void deleteComment(Long id) {
        commentDao.deleteComment(id);
    }

    @Override
    public List<Comment> groupCommentsByPost() {
        return commentDao.groupCommentsByPost();
    }

    @Override
    public List<Comment> sortByPublishDate() {
        return commentDao.sortByPublishDate();
    }
}
