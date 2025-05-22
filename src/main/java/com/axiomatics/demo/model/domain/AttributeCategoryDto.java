package com.axiomatics.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeCategoryDto {
    private Long id;
    private String name;
    private String description;
    private List<AttributeDefinitionDto> definitions;
}
