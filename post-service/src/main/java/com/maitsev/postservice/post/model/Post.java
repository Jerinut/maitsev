package com.maitsev.postservice.post.model;

import java.time.LocalDate;
import java.util.List;

import com.maitsev.postservice.comment.model.Comment;

// import org.springframework.context.annotation.Profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posttable")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
  @Id
  private String id;
  private String description;
  private String imgUrl;
  private Integer likes;
  private LocalDate createdAt;
  private String postedById;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Comment> comments;
}
