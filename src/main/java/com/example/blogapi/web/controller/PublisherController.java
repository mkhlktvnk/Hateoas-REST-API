package com.example.blogapi.web.controller;

import com.example.blogapi.domain.entity.Publisher;
import com.example.blogapi.service.PublisherService;
import com.example.blogapi.web.mapper.PublisherMapper;
import com.example.blogapi.web.model.PublisherModel;
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
@Tag(name = "Publisher API", description = "Operations for working with Publisher API")
public class PublisherController {
    private final PublisherService publisherService;
    private final PublisherMapper mapper = PublisherMapper.INSTANCE;
    private final RepresentationModelAssembler<Publisher, PublisherModel> publisherModelAssembler;
    private final PagedResourcesAssembler<Publisher> pagedResourcesAssembler;

    @Operation(summary = "Get publishers in paging view")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = PublisherModel.class))
                    ),
                    description = "The list of publishers was returned"
            ),
            @ApiResponse(responseCode = "204", content = @Content, description = "Response is empty"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v0/publishers")
    public ResponseEntity<PagedModel<PublisherModel>> getPublishers(@PageableDefault final Pageable pageable) {
        PagedModel<PublisherModel> publishers = pagedResourcesAssembler
                .toModel(publisherService.getAllPublishers(pageable), publisherModelAssembler);
        return publishers.getContent().isEmpty() ?
                new ResponseEntity<>(publishers, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(publishers, HttpStatus.OK);
    }

    @Operation(summary = "Get publisher by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = PublisherModel.class)
                    ),
                    description = "Publisher was found and returned"
            ),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Publisher was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/v0/publishers/{id}")
    public PublisherModel getPublisher(@PathVariable @Min(1) final Long id) {
        return mapper.mapToModel(publisherService.getPublisherById(id));
    }

    @Operation(summary = "Add new publisher")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PublisherModel.class)
                    ),
                    description = "Publisher was saved"
            ),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v0/publishers")
    public PublisherModel addPublisher(@RequestBody @Valid final PublisherModel user) {
        return mapper.mapToModel(publisherService.addPublisher(mapper.mapToEntity(user)));
    }

    @Operation(summary = "Update existing publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content, description = "Publisher was updated"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Publisher was not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/api/v0/publishers/{id}")
    public void updatePublisherById(@PathVariable final Long id, @RequestBody @Valid final PublisherModel publisher) {
        publisherService.updatePublisher(mapper.mapToEntity(publisher), id);
    }

    @Operation(summary = "Delete existing publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", content = @Content, description = "Publisher was deleted"),
            @ApiResponse(responseCode = "400", content = @Content, description = "Request data is invalid"),
            @ApiResponse(responseCode = "404", content = @Content, description = "Publisher was not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v0/publishers/{id}")
    public void deletePublisherById(@PathVariable final Long id) {
        publisherService.deletePublisherById(id);
    }
}
