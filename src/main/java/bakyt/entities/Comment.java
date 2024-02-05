package bakyt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "comment_seq", allocationSize = 1)
public class Comment extends BaseEntity{
    @Column(name = "comment_text")
    private String commentText;
    @Column(name = "publish_date")
    private LocalDate publishDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private BlogPost blogPost;
}
