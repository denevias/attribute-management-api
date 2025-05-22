package com.axiomatics.demo.converters;

import com.axiomatics.demo.model.domain.AttributeCategoryDto;
import com.axiomatics.demo.model.domain.AttributeDefinitionDto;
import com.axiomatics.demo.model.entity.AttributeCategories;
import com.axiomatics.demo.model.entity.AttributeDefinition;

import java.util.stream.Collectors;

public class AttributeMapper {

    public static AttributeDefinitionDto toDefinitionDTO(AttributeDefinition def) {
        return AttributeDefinitionDto.builder()
                .id(def.getId())
                .name(def.getName())
                .description(def.getDescription())
                .categoryName(def.getCategory() == null ? null : def.getCategory().getName())
                .build();
    }

    public static AttributeCategoryDto toCategoryDTO(AttributeCategories category) {
        return AttributeCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .definitions(category.getDefinitions() == null ? null :
                        category.getDefinitions().stream()
                                .map(AttributeMapper::toDefinitionDTO)
                                .collect(Collectors.toList()))
                .build();
    }
}
