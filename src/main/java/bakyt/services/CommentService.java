package bakyt.services;

import bakyt.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void addComment(Long postId, Comment comment);
    Comment getCommentById(Long id);
    void updateCommentText(Long id, String newText);
    void deleteComment(Long id);
    List<Comment> groupCommentsByPost();
    List<Comment> sortByPublishDate();
}
