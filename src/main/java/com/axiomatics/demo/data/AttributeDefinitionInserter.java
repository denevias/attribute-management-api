package com.axiomatics.demo.data;

import com.axiomatics.demo.model.entity.AttributeCategories;
import com.axiomatics.demo.model.entity.AttributeDefinition;
import com.axiomatics.demo.repository.AttributeCategoriesRepository;
import com.axiomatics.demo.repository.AttributeDefinitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttributeDefinitionInserter implements CommandLineRunner {

    private final AttributeDefinitionRepository definitionRepository;
    private final AttributeCategoriesRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (definitionRepository.count() == 0) {

            // Define reusable categories
            AttributeCategories subjectCategory = getOrCreateCategory("custom:category:subject", "Subject-related attributes");
            AttributeCategories resourceCategory = getOrCreateCategory("custom:category:resource", "Resource-related attributes");
            AttributeCategories actionCategory = getOrCreateCategory("custom:category:action", "Action-related attributes");

            // Create multiple definitions per category
            List<AttributeDefinition> definitions = List.of(
                    // Subject category
                    AttributeDefinition.builder().name("user-id").description("Unique user ID").category(subjectCategory).build(),
                    AttributeDefinition.builder().name("role").description("User role in the system").category(subjectCategory).build(),
                    AttributeDefinition.builder().name("department").description("Department of the user").category(subjectCategory).build(),

                    // Resource category
                    AttributeDefinition.builder().name("resource-id").description("Unique resource ID").category(resourceCategory).build(),
                    AttributeDefinition.builder().name("resource-type").description("Type of the resource").category(resourceCategory).build(),

                    // Action category
                    AttributeDefinition.builder().name("read").description("Read action").category(actionCategory).build(),
                    AttributeDefinition.builder().name("write").description("Write action").category(actionCategory).build(),
                    AttributeDefinition.builder().name("delete").description("Delete action").category(actionCategory).build()
            );

            definitionRepository.saveAll(definitions);

        }
    }

    private AttributeCategories getOrCreateCategory(String name, String description) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(
                        AttributeCategories.builder()
                                .name(name)
                                .description(description)
                                .build()
                ));
    }
}
