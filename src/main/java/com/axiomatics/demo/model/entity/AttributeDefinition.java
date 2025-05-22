package com.axiomatics.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "attribute_definitions")
@NoArgsConstructor
@AllArgsConstructor
public class AttributeDefinition extends AuditDefinitions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "attribute_definitions_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private AttributeCategories category;


}
