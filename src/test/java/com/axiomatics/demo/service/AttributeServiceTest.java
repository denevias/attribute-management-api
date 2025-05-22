package com.axiomatics.demo.service;

import com.axiomatics.demo.controller.exceptions.ResourceNotFoundException;
import com.axiomatics.demo.controller.request.AttributeCreationRequest;
import com.axiomatics.demo.model.entity.AttributeCategories;
import com.axiomatics.demo.model.entity.AttributeDefinition;
import com.axiomatics.demo.repository.AttributeCategoriesRepository;
import com.axiomatics.demo.repository.AttributeDefinitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttributeServiceTest {

    @Mock
    private AttributeDefinitionRepository definitionRepository;

    @Mock
    private AttributeCategoriesRepository categoriesRepository;

    @InjectMocks
    private AttributeService attributeService;

    private AttributeCategories testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new AttributeCategories();
        testCategory.setId(1L);
        testCategory.setName("cat-test-generic");
    }

    @Test
    void testGetDefinitionsByCategory() {
        Long categoryId = 1L;
        when(definitionRepository.findByCategoryId(categoryId)).thenReturn(Collections.emptyList());

        var result = attributeService.getDefinitionsByCategory(categoryId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(definitionRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void testSaveAttributeDefinition() {
        AttributeCreationRequest request = new AttributeCreationRequest();
        request.setCategory("cat-test-generic");
        request.setName("attr-generic");
        request.setDescription("generic");

        when(categoriesRepository.findByName("cat-test-generic")).thenReturn(Optional.of(testCategory));
        when(definitionRepository.save(any(AttributeDefinition.class))).thenReturn(new AttributeDefinition());

        var result = attributeService.saveAttributeDefinition(request);

        assertNotNull(result);
        verify(categoriesRepository, times(1)).findByName("cat-test-generic");
        verify(definitionRepository, times(1)).save(any(AttributeDefinition.class));
    }

    @Test
    void testSaveAttributeDefinitionThrowsExceptionWhenCategoryNotFound() {
        AttributeCreationRequest request = new AttributeCreationRequest();
        request.setCategory("InvalidCategory");
        request.setName("TestName");
        request.setDescription("TestDescription");

        when(categoriesRepository.findByName("InvalidCategory")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attributeService.saveAttributeDefinition(request));
        verify(categoriesRepository, times(1)).findByName("InvalidCategory");
    }

    @Test
    void testUpdateDefinition() {
        Long definitionId = 1L;
        String newName = "UpdatedName";
        String newDescription = "UpdatedDescription";
        AttributeDefinition definition = new AttributeDefinition();

        when(definitionRepository.findById(definitionId)).thenReturn(Optional.of(definition));
        when(definitionRepository.save(definition)).thenReturn(definition);

        var result = attributeService.updateDefinition(definitionId, newName, newDescription);

        assertNotNull(result);
        assertEquals(newName, definition.getName());
        assertEquals(newDescription, definition.getDescription());
        verify(definitionRepository, times(1)).findById(definitionId);
        verify(definitionRepository, times(1)).save(definition);
    }

    @Test
    void testUpdateDefinitionThrowsExceptionWhenNotFound() {
        Long definitionId = 1L;

        when(definitionRepository.findById(definitionId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> attributeService.updateDefinition(definitionId, "Name", "Description"));
        verify(definitionRepository, times(1)).findById(definitionId);
    }

    @Test
    void testDeleteDefinition() {
        Long definitionId = 1L;

        when(definitionRepository.existsById(definitionId)).thenReturn(true);
        doNothing().when(definitionRepository).deleteById(definitionId);

        assertDoesNotThrow(() -> attributeService.deleteDefinition(definitionId));
        verify(definitionRepository, times(1)).existsById(definitionId);
        verify(definitionRepository, times(1)).deleteById(definitionId);
    }

    @Test
    void testDeleteDefinitionThrowsExceptionWhenNotFound() {
        Long definitionId = 1L;

        when(definitionRepository.existsById(definitionId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> attributeService.deleteDefinition(definitionId));
        verify(definitionRepository, times(1)).existsById(definitionId);
    }
}
