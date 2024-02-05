package bakyt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "blos_posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "base_gen", sequenceName = "blog_post_seq", allocationSize = 1)
public class BlogPost extends BaseEntity{
    private String title;
    private String content;
    @Column(name = "publish_date")
    private LocalDate publishDate;
    @OneToMany(mappedBy = "blogPost", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<Comment> comments;

}
