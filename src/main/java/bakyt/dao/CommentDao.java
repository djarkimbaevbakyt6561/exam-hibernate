package bakyt.dao;

import bakyt.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    void addComment(Long postId, Comment comment);
    Optional<Comment> getCommentById(Long id);
    void updateCommentText(Long id, String newText);
    void deleteComment(Long id);
    List<Comment> groupCommentsByPost();
    List<Comment> sortByPublishDate();
}
