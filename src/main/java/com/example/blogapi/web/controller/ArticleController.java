package com.example.blogapi.web.controller;

import com.example.blogapi.domain.entity.Article;
import com.example.blogapi.service.ArticleService;
import com.example.blogapi.web.mapper.ArticleMapper;
import com.example.blogapi.web.model.ArticleModel;
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
@Tag(name = "Articles API", description = "Operations for articles API")
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper mapper = ArticleMapper.INSTANCE;
    private final RepresentationModelAssembler<Article, ArticleModel> articleModelAssembler;
    private final PagedResourcesAssembler<Article> pagedResourcesAssembler;

    @Operation(summary = "Get articles in paging view")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArticleModel.class))
                    ),
                    description = "List of articles was returned"
            ),
            @ApiResponse(responseCode = "204", description = "No articles were returned", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping("/api/v0/articles")
    public ResponseEntity<PagedModel<ArticleModel>> getArticles(
            @ParameterObject @PageableDefault final Pageable pageable) {
        final PagedModel<ArticleModel> articles =  pagedResourcesAssembler
                .toModel(articleService.getArticlesByPaging(pageable), articleModelAssembler);
        return articles.getContent().isEmpty() ?
                new ResponseEntity<>(articles, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Operation(summary = "Get articles of user in paging view by user id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArticleModel.class))
                    ),
                    description = "list of articles was returned"
            ),
            @ApiResponse(responseCode = "204", description = "No articles were returned", content = @Content)
    })
    @GetMapping("/api/v0/users/{publisherId}/articles")
    public ResponseEntity<PagedModel<ArticleModel>> getArticlesByUserId(
            @ParameterObject @PageableDefault final Pageable pageable, @PathVariable @Min(1) final Long publisherId) {
        final PagedModel<ArticleModel> articles = pagedResourcesAssembler.toModel(articleService
                .getArticlesByPublisherId(pageable, publisherId), articleModelAssembler);
        return articles.getContent().isEmpty() ?
                new ResponseEntity<>(articles, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Operation(summary = "Get articles of category in paging view by category id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArticleModel.class))
                    ),
                    description = "list of articles was returned"
            ),
            @ApiResponse(responseCode = "204", description = "No articles were returned", content = @Content)
    })
    @GetMapping("/api/v0/categories/{categoryId}/articles")
    public ResponseEntity<PagedModel<ArticleModel>> getArticlesByCategoryId(
            @ParameterObject @PageableDefault final Pageable pageable, @PathVariable @Min(1) final Long categoryId) {
        final PagedModel<ArticleModel> articles = pagedResourcesAssembler.toModel(articleService
                .getArticlesByCategoryId(pageable, categoryId), articleModelAssembler);
        return articles.getContent().isEmpty() ?
                new ResponseEntity<>(articles, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Operation(summary = "Get article by id")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "list of articles was returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticleModel.class))
                    ),
            @ApiResponse(responseCode = "400", description = "Article id is not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article was not found", content = @Content)
    })
    @GetMapping("/api/v0/articles/{id}")
    public ArticleModel getArticle(@PathVariable final Long id) {
        return mapper.mapToModel(articleService.getArticleById(id));
    }

    @Operation(summary = "Add new article")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "New article was created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArticleModel.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Request data is not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "User or category was not found", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v0/articles")
    public ArticleModel addArticle(@RequestBody @Valid final ArticleModel articleModel,
                                     @RequestParam("userId") @Min(1) final Long userId,
                                     @RequestParam("categoryId") @Min(1) final Long categoryId) {
        final Article article = mapper.mapToEntity(articleModel);
        return mapper.mapToModel(articleService.addArticle(article, userId, categoryId));
    }

    @Operation(summary = "Update existing article")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Article was updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request data is not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article was not found", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/v0/articles/{id}")
    public ArticleModel updateArticleById(
            @PathVariable @Min(1) final Long id, @RequestBody @Valid final Article newArticle) {
        return mapper.mapToModel(articleService.updateArticleById(id, newArticle));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Article was updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request data is not valid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Article was not found", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v0/articles/{id}")
    public void deleteArticleById(@PathVariable @Min(1) final Long id) {
        articleService.deleteArticleById(id);
    }
}
