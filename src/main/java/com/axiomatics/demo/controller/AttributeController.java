package com.axiomatics.demo.controller;

import com.axiomatics.demo.controller.request.AttributeCreationRequest;
import com.axiomatics.demo.model.domain.AttributeCategoryDto;
import com.axiomatics.demo.model.domain.AttributeDefinitionDto;
import com.axiomatics.demo.service.AttributeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class AttributeController {

    private AttributeService attributeService;

    @GetMapping(path = "/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AttributeDefinitionDto>> getAttributes() {

        return ResponseEntity.ok(attributeService.getAttributes());
    }

    @GetMapping("/attributes/category/{id}")
    public ResponseEntity<List<AttributeDefinitionDto>> getAttributesByCategoryId(@PathVariable Long id) {
        List<AttributeDefinitionDto> attributesByCategory = attributeService.getDefinitionsByCategory(id);
        return ResponseEntity.ok(attributesByCategory);
    }

    @PutMapping(path = "/attributes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveAttribute (@RequestBody final AttributeCreationRequest request) {
        attributeService.saveAttributeDefinition(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AttributeCategoryDto>> getCategories() {

        List<AttributeCategoryDto> categories = attributeService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

}
