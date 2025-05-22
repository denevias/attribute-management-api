package com.axiomatics.demo.controller;

import com.axiomatics.demo.controller.request.AttributeCreationRequest;
import com.axiomatics.demo.model.domain.AttributeCategoryDto;
import com.axiomatics.demo.model.domain.AttributeDefinitionDto;
import com.axiomatics.demo.service.AttributeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttributeController.class)
public class AttributeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttributeService attributeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAttributes() throws Exception {
        List<AttributeDefinitionDto> mockAttributes = List.of(
                new AttributeDefinitionDto(1L, "ATTR-TEST-001", "GENERIC", "GENERIC-CATEGORY"),
                new AttributeDefinitionDto(2L, "ATTR-TEST-002", "TECHNICAL", "TECHNICAL-CATEGORY")
        );

        Mockito.when(attributeService.getAttributes()).thenReturn(mockAttributes);

        mockMvc.perform(get("/api/v1/attributes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("ATTR-TEST-001")))
                .andExpect(jsonPath("$[1].name", is("ATTR-TEST-002")));
    }

    @Test
    void testGetAttributesByCategoryId() throws Exception {
        long categoryId = 1L;
        List<AttributeDefinitionDto> mockCategoryAttributes = List.of(
                new AttributeDefinitionDto(1L, "Attribute-test-generic", "GENERIC", "GENERIC-CATEGORY")
        );

        Mockito.when(attributeService.getDefinitionsByCategory(eq(categoryId))).thenReturn(mockCategoryAttributes);

        mockMvc.perform(get("/api/v1/attributes/category/{id}", categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Attribute-test-generic")));
    }

    @Test
    void testSaveAttribute() throws Exception {
        AttributeCreationRequest request = new AttributeCreationRequest();
        request.setName("test-attribute");
        request.setDescription("test-attribute-description");
        request.setCategory("test-category");


        mockMvc.perform(post("/api/v1/attributes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Mockito.verify(attributeService).saveAttributeDefinition(any(AttributeCreationRequest.class));

    }

    @Test
    void testGetCategories() throws Exception {
        List<AttributeCategoryDto> mockCategories = List.of(
                AttributeCategoryDto.builder()
                        .id(1L)
                        .name("Generic")
                        .description("Generic attributes")
                        .definitions(List.of())
                        .build(),
                AttributeCategoryDto.builder()
                        .id(2L)
                        .name("Specific")
                        .description("Specific attributes")
                        .definitions(List.of())
                        .build()
        );

        Mockito.when(attributeService.getAllCategories()).thenReturn(mockCategories);

        mockMvc.perform(get("/api/v1/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Generic")))
                .andExpect(jsonPath("$[0].description", is("Generic attributes")))
                .andExpect(jsonPath("$[0].definitions", hasSize(0)));
    }
}
