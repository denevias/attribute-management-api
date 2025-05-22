package com.axiomatics.demo.controller;

import com.axiomatics.demo.controller.request.AttributeCreationRequest;
import com.axiomatics.demo.controller.request.AttributeUpdateRequest;
import com.axiomatics.demo.controller.request.ApiResponse;
import com.axiomatics.demo.model.domain.AttributeCategoryDto;
import com.axiomatics.demo.model.domain.AttributeDefinitionDto;
import com.axiomatics.demo.service.AttributeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
@Slf4j
public class AttributeController {

    private AttributeService attributeService;

    @GetMapping(path = "/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AttributeDefinitionDto>> getAttributes() {
        log.debug("Fetching all attributes");
        return ResponseEntity.ok(attributeService.getAttributes());
    }

    @GetMapping("/attributes/category/{id}")
    public ResponseEntity<List<AttributeDefinitionDto>> getAttributesByCategoryId(@PathVariable Long id) {
        log.debug("Fetching attributes for category id: {}", id);
        List<AttributeDefinitionDto> attributesByCategory = attributeService.getDefinitionsByCategory(id);
        return ResponseEntity.ok(attributesByCategory);
    }

    @PostMapping(path = "/attributes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AttributeDefinitionDto> saveAttribute(@RequestBody final AttributeCreationRequest request) {
        log.debug("Creating new attribute with name: {}", request.getName());
        AttributeDefinitionDto createdAttribute = attributeService.saveAttributeDefinition(request);
        return new ResponseEntity<>(createdAttribute, HttpStatus.CREATED);
    }

    @PutMapping(path = "/attributes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AttributeDefinitionDto> updateAttribute(
            @PathVariable("id") Long definitionId,
            @RequestBody AttributeUpdateRequest request) {

        log.debug("Updating attribute with id: {}", definitionId);
        AttributeDefinitionDto updatedDefinition = attributeService.updateDefinition(
                definitionId,
                request.getName(),
                request.getDescription()
        );

        return ResponseEntity.ok(updatedDefinition);
    }

    @DeleteMapping(path = "/attributes/{id}")
    public ResponseEntity<ApiResponse> deleteAttribute(@PathVariable("id") Long definitionId) {
        log.debug("Deleting attribute with id: {}", definitionId);
        try {
            attributeService.deleteDefinition(definitionId);
            log.debug("Successfully deleted attribute with id: {}", definitionId);
            return ResponseEntity.ok(new ApiResponse("DELETION_SUCCESS", "Attribute deleted successfully", null));
        } catch (Exception e) {
            log.error("Failed to delete attribute with id: {}", definitionId, e);
            throw e;
        }
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AttributeCategoryDto>> getCategories() {
        log.debug("Fetching all categories");
        List<AttributeCategoryDto> categories = attributeService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}