// package com.maitsev.postservice.post.model;
package com.maitsev.postservice.comment.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maitsev.postservice.post.model.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "commentstable")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String text;
    private LocalDateTime createdAt;
    private String postedById;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;
}

