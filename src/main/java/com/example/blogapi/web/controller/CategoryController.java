package com.example.blogapi.web.controller;

import com.example.blogapi.domain.entity.Category;
import com.example.blogapi.service.CategoryService;
import com.example.blogapi.web.mapper.CategoryMapper;
import com.example.blogapi.web.mapper.ModelMapper;
import com.example.blogapi.web.model.ArticleModel;
import com.example.blogapi.web.model.CategoryModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Category API", description = "Operations for categories API")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper<Category, CategoryModel> mapper = CategoryMapper.INSTANCE;
    private final RepresentationModelAssembler<Category, CategoryModel> categoryModelAssembler;
    private final PagedResourcesAssembler<Category> pagedResourcesAssembler;

    @Operation(summary = "Get categories in paging view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of categories was returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryModel.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "No categories were returned",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @GetMapping("/api/v0/categories")
    public ResponseEntity<PagedModel<CategoryModel>> getCategories(@PageableDefault final Pageable pageable) {
        final PagedModel<CategoryModel> categories = pagedResourcesAssembler
                .toModel(categoryService.getCategoriesByPaging(pageable), categoryModelAssembler);
        return categories.getContent().isEmpty() ?
                new ResponseEntity<>(categories, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was find and returned",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryModel.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Request data is invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category was not found", content = @Content)
    })
    @GetMapping("/api/v0/categories/{id}")
    public CategoryModel getCategory(@PathVariable @Min(1) final Long id) {
        return mapper.mapToModel(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Add new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category was saved",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryModel.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Request data is invalid", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v0/categories")
    public CategoryModel addCategory(@RequestBody @Valid final CategoryModel categoryModel) {
        return mapper.mapToModel(categoryService.addCategory(mapper.mapToEntity(categoryModel)));
    }

    @Operation(summary = "Update existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category was updated", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request data is invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category was not found", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/api/v0/categories/{id}")
    public void updateCategoryById(
            @PathVariable @Min(1) final Long id, @RequestBody @Valid final CategoryModel categoryModel) {
        categoryService.updateCategoryById(mapper.mapToEntity(categoryModel), id);
    }

    @Operation(summary = "Delete existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category was deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Request data is invalid", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category was not found", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v0/categories/{id}")
    public void deleteCategoryById(@PathVariable final Long id) {
        categoryService.deleteCategoryById(id);
    }
}
