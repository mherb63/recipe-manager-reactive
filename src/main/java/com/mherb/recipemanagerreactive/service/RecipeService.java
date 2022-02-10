package com.mherb.recipemanagerreactive.service;

import com.mherb.recipemanagerreactive.domain.Recipe;
import com.mherb.recipemanagerreactive.exception.RecipeNotFoundException;
import com.mherb.recipemanagerreactive.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;


    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Flux<Recipe> findAll() {
        log.info("Received request to get all recipes");
        return recipeRepository.findAll();
    }

    public Mono<Recipe> findById(String id) {
        log.info("Received request to find Recipe with id: {}", id);
        return recipeRepository.findById(id).switchIfEmpty(Mono.error(new RecipeNotFoundException("Could not find Recipe with id: " + id)));
    }

    public Flux<Recipe> findByContributorName(String contributor) {
        log.info("Received request to find Recipe with contributor : {}", contributor);
        return recipeRepository.findByContributorName(contributor);
    }

    public Flux<Recipe> findByTitle(String title) {
        log.info("Received request to find Recipe with title : {}", title);
        return recipeRepository.findByTitle(title);
    }

    public Mono<Recipe> createRecipe(Recipe recipe) {
        log.info("Received request to create a new Recipe: {}", recipe);

        return recipeRepository.save(Recipe.builder()
                .contributorName(recipe.getContributorName())
                .title(recipe.getTitle())
                .instructions(recipe.getInstructions())
                .notes(recipe.getNotes())
                .build()
        );
    }

    public Mono<Recipe> replaceRecipe(Recipe recipe) {
        log.info("Received request to replace a Recipe: {}", recipe);

        return recipeRepository.findById(recipe.getId()).switchIfEmpty(Mono.error(new RecipeNotFoundException("Could not find Recipe with id: " + recipe.getId())))
                .map(r -> recipe)
                .flatMap(recipeRepository::save);
    }

    public Mono<Void> deleteRecipe(String id) {
        log.info("Received request to delete Recipe with id: {}", id);

        return recipeRepository.deleteById(id);

//        recipeRepository.findById(id).switchIfEmpty(Mono.error(new RecipeNotFoundException("Could not find Recipe with id: " + id)))
//                .flatMap(recipeRepository::delete);
    }
}
