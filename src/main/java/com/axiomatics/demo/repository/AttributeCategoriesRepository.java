package com.axiomatics.demo.repository;

import com.axiomatics.demo.model.entity.AttributeCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AttributeCategoriesRepository extends JpaRepository<AttributeCategories, Long> {

    Optional<AttributeCategories> findByName(String name);

    boolean existsByName(String name);


    @Query("SELECT DISTINCT ac FROM AttributeCategories ac JOIN ac.definitions")
    List<AttributeCategories> findCategoriesWithDefinitions();
}
