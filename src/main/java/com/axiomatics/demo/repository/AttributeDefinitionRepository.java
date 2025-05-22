package com.axiomatics.demo.repository;

import com.axiomatics.demo.model.entity.AttributeCategories;
import com.axiomatics.demo.model.entity.AttributeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeDefinitionRepository extends JpaRepository<AttributeDefinition, Long> {

    List<AttributeDefinition> findByCategoryId(Long categoryId);

    @Query("SELECT ad FROM AttributeDefinition ad WHERE ad.category.name = :categoryName")
    List<AttributeDefinition> findDefinitionsByCategoryName(@Param("categoryName") String categoryName);

    List<AttributeDefinition> findByCategory(AttributeCategories category);


    @Query("SELECT COUNT(ad) FROM AttributeDefinition ad WHERE ad.category.id = :categoryId")
    Long countDefinitionsByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT ad FROM AttributeDefinition ad WHERE ad.category.name = :categoryName ORDER BY ad.name ASC")
    List<AttributeDefinition> findDefinitionsByCategoryNameSorted(@Param("categoryName") String categoryName);
}
