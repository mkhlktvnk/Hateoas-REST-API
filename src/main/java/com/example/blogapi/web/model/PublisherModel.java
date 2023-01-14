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
@Schema(description = "Publisher model")
@Relation(itemRelation = "publisher", collectionRelation = "publishers")
public class PublisherModel extends RepresentationModel<PublisherModel> {
    @Schema(description = "Publisher unique id")
    @Min(0)
    @NotNull
    @JsonProperty("id")
    private Long id;

    @Schema(description = "Publisher unique username")
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("username")
    private String username;

    @Schema(description = "Publisher unique email")
    @NotNull
    @Size(min = 1, max = 255)
    @JsonProperty("email")
    private String email;
}
