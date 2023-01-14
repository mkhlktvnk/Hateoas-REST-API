package com.example.blogapi.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Article model")
@Relation(itemRelation = "article", collectionRelation = "articles")
public class ArticleModel extends RepresentationModel<ArticleModel> {
    @Schema(description = "Article unique id")
    @Min(0)
    @NotNull
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Topic of article")
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("topic")
    private String topic;

    @Schema(description = "Description of article")
    @NotNull
    @Size(min = 1, max = 1000)
    @JsonProperty("description")
    private String description;

    @Schema(description = "Content of article")
    @NotNull
    @Size(min = 1, max = 3000)
    @JsonProperty("content")
    private String content;
}
