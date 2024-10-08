// from package com.maitsev.postservice.post.dto;
package com.maitsev.contentpersonalizationservice.contentpersonalization.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDto {
  @Id
  private String id;
  private String description;
  private String imgUrl;
  private Integer likes;
  private LocalDate createdAt;
  private String postedById;
  private List<String> tags;
  private List<String> comments;
}
