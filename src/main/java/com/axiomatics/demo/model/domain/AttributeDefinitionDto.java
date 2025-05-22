package com.axiomatics.demo.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinitionDto {
    private Long id;
    private String name;
    private String description;

    private String categoryName;
}
