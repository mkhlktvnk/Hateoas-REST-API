package com.example.blogapi.web.controller;

import com.example.blogapi.domain.entity.Review;
import com.example.blogapi.service.ReviewService;
import com.example.blogapi.web.mapper.ReviewMapper;
import com.example.blogapi.web.model.PublisherModel;
import com.example.blogapi.web.model.ReviewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Review API", description = "Operations for working with Review API")
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper = ReviewMapper.INSTANCE;
    private final RepresentationModelAssembler<Review, ReviewModel> reviewModelAssembler;
    private final PagedResourcesAssembler<Review> pagedResourcesAssembler;

    @Operation(summary = "Get reviews of article in paging view")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PublisherModel.class))
                    ),
                    description = "List of reviews was returned"
            ),
            @ApiResponse(responseCode = "204", content = @Content, description = "Response is empty"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v0/articles/{articleId}/reviews")
    public ResponseEntity<PagedModel<ReviewModel>> getReviewsByArticleId(
            @ParameterObject @PageableDefault final Pageable pageable, @PathVariable @Min(1) final Long articleId) {
        final PagedModel<ReviewModel> reviews = pagedResourcesAssembler
                .toModel(reviewService.getReviewsByArticleId(pageable, articleId), reviewModelAssembler);
        return reviews.getContent().isEmpty() ?
                new ResponseEntity<>(reviews, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Get reviews of publishers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PublisherModel.class))
                    ),
                    description = "List of reviews was returned"
            ),
            @ApiResponse(responseCode = "204", content = @Content, description = "Response is empty"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v0/publishers/{publisherId}/reviews")
    public ResponseEntity<PagedModel<ReviewModel>> getReviewsByUserId(
            @ParameterObject @PageableDefault final Pageable pageable, @PathVariable @Min(1) final Long publisherId) {
        final PagedModel<ReviewModel> reviews = pagedResourcesAssembler
                .toModel(reviewService.getReviewsByUserId(pageable, publisherId), reviewModelAssembler);
        return reviews.getContent().isEmpty() ?
                new ResponseEntity<>(reviews, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Operation(summary = "Get review by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PublisherModel.class)
                    ),
                    description = "Review was found and returned"
            ),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Review was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v0/reviews/{id}")
    public ReviewModel getReviewById(@PathVariable @Min(1) final Long id) {
        return reviewMapper.mapToModel(reviewService.getReviewById(id));
    }

    @Operation(summary = "Add new review")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PublisherModel.class)
                    ),
                    description = "Review was created"
            ),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v0/reviews")
    public ReviewModel addReview(@RequestBody @Valid final ReviewModel reviewModel,
                                 @RequestParam @Min(1) final Long articleId, @RequestParam @Min(1) final Long userId) {
        final Review saved = reviewService.addReview(reviewMapper.mapToEntity(reviewModel), articleId, userId);
        return reviewMapper.mapToModel(saved);
    }

    @Operation(summary = "Update existing review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content, description = "Review was updated"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Review was not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/v0/reviews/{id}")
    public void updateReviewById(@PathVariable @Min(1) final Long id,
                                 @RequestBody @Valid final ReviewModel reviewModel) {
        reviewService.updateReviewById(reviewMapper.mapToEntity(reviewModel), id);
    }

    @Operation(summary = "Delete existing review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content, description = "Review was deleted"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Review was not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v0/reviews/{id}")
    public void deleteReviewById(@PathVariable final Long id) {
        reviewService.deleteReviewById(id);
    }
}
