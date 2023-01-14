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
@Schema(description = "Category model")
@Relation(itemRelation = "category", collectionRelation = "categories")
public class CategoryModel extends RepresentationModel<CategoryModel> {
    @Schema(description = "Category unique id")
    @Min(0)
    @NotNull
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Category unique name")
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("name")
    private String name;

    @Schema(description = "Category description")
    @NotNull
    @Size(min = 1, max = 1000)
    @JsonProperty("description")
    private String description;
}
