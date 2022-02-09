package com.mherb.recipemanagerreactive.repository;

import com.mherb.recipemanagerreactive.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String> {
    Flux<Recipe> findByContributorName(String contributorName);
    Flux<Recipe> findByTitle(String title);
}