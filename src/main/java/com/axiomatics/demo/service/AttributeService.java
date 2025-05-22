package com.axiomatics.demo.service;

import com.axiomatics.demo.controller.exceptions.ResourceNotFoundException;
import com.axiomatics.demo.controller.request.AttributeCreationRequest;
import com.axiomatics.demo.converters.AttributeMapper;
import com.axiomatics.demo.model.domain.AttributeCategoryDto;
import com.axiomatics.demo.model.domain.AttributeDefinitionDto;
import com.axiomatics.demo.model.entity.AttributeCategories;
import com.axiomatics.demo.model.entity.AttributeDefinition;
import com.axiomatics.demo.repository.AttributeCategoriesRepository;
import com.axiomatics.demo.repository.AttributeDefinitionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class AttributeService {

    private final AttributeDefinitionRepository definitionRepository;
    private final AttributeCategoriesRepository categoriesRepository;

    // Fetches all attribute definitions for a given category ID
    public List<AttributeDefinitionDto> getDefinitionsByCategory(Long categoryId) {
        log.info("Fetching definitions for category ID: {}", categoryId);
        return definitionRepository.findByCategoryId(categoryId)
                .stream()
                .map(AttributeMapper::toDefinitionDTO)
                .collect(Collectors.toList());
    }

    // Fetches all attribute definitions for a given category name
    public List<AttributeDefinitionDto> getDefinitionsByCategoryName(String categoryName) {
        log.info("Fetching definitions for category name: {}", categoryName);
        return definitionRepository.findDefinitionsByCategoryName(categoryName)
                .stream()
                .map(AttributeMapper::toDefinitionDTO)
                .collect(Collectors.toList());
    }

    // Saves a new attribute definition based on the provided request
    public AttributeDefinitionDto saveAttributeDefinition(AttributeCreationRequest request) {
        log.info("Saving attribute definition: {}", request);
        AttributeCategories category = categoriesRepository.findByName(request.getCategory())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with Name: " + request.getCategory()));

        AttributeDefinition definition = AttributeDefinition.builder()
                .category(category)
                .name(request.getName() != null ? request.getName() : "NULL")
                .description(request.getDescription())
                .build();

        return AttributeMapper.toDefinitionDTO(definitionRepository.save(definition));
    }

    // Updates an existing attribute definition with new name and description
    public AttributeDefinitionDto updateDefinition(Long definitionId, String name, String description) {
        log.info("Updating attribute definition with ID: {}", definitionId);
        AttributeDefinition definition = definitionRepository.findById(definitionId)
                .orElseThrow(() -> new EntityNotFoundException("Definition not found with ID: " + definitionId));

        definition.setName(name);
        definition.setDescription(description);

        return AttributeMapper.toDefinitionDTO(definitionRepository.save(definition));
    }

    // Moves an attribute definition to a new category
    public AttributeDefinitionDto moveDefinitionToCategory(Long definitionId, Long newCategoryId) {
        log.info("Moving attribute definition with ID: {} to new category ID: {}", definitionId, newCategoryId);
        AttributeDefinition definition = definitionRepository.findById(definitionId)
                .orElseThrow(() -> new RuntimeException("Definition not found with ID: " + definitionId));

        AttributeCategories category = categoriesRepository.findById(newCategoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + newCategoryId));

        definition.setCategory(category);

        return AttributeMapper.toDefinitionDTO(definitionRepository.save(definition));
    }

    // Deletes an attribute definition by its ID
    public void deleteDefinition(Long definitionId) {
        if (!definitionRepository.existsById(definitionId)) {
            throw new ResourceNotFoundException("Attribute", definitionId);
        }

        definitionRepository.deleteById(definitionId);
    }

    // Counts the number of attribute definitions in a given category
    public Long getDefinitionCountByCategory(Long categoryId) {
        return definitionRepository.countDefinitionsByCategory(categoryId);
    }

    // Fetches a single attribute definition by its ID
    public Optional<AttributeDefinitionDto> getDefinitionById(Long id) {
        return definitionRepository.findById(id).map(AttributeMapper::toDefinitionDTO);
    }

    // Fetches all attribute definitions
    public List<AttributeDefinitionDto> getAttributes() {
        log.info("Fetching all attribute definitions");
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(definitionRepository.findAll().iterator(), Spliterator.ORDERED), false)
                .map(AttributeMapper::toDefinitionDTO)
                .collect(Collectors.toList());
    }

    // Fetches all attribute categories
    public List<AttributeCategoryDto> getAllCategories() {
        log.info("Fetching all attribute categories");
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(categoriesRepository.findAll().iterator(), Spliterator.ORDERED), false)
                .map(AttributeMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}