package com.example.blogapi.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
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
@Schema(description = "Review model")
@Relation(itemRelation = "review", collectionRelation = "reviews")
public class ReviewModel extends RepresentationModel<ReviewModel> {
    @Schema(description = "Review unique id")
    @Min(0)
    @NotNull
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Mark for article")
    @Min(0)
    @Max(5)
    @NotNull
    @JsonProperty("mark")
    private Integer mark;

    @Schema(description = "Content of review")
    @NotNull
    @Size(min = 1, max = 10000)
    @JsonProperty("content")
    private String content;
}
