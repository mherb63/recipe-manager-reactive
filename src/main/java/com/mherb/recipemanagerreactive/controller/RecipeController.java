package com.mherb.recipemanagerreactive.controller;

import com.mherb.recipemanagerreactive.domain.Recipe;
import com.mherb.recipemanagerreactive.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Recipe> findAll() {
        return recipeService.findAll();
    }

    @GetMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> findById(@PathVariable String id) {
        return recipeService.findById(id);
    }

    @GetMapping("/findByContributor")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Recipe> findByContributor(@RequestParam String contributor) {
        return recipeService.findByContributorName(contributor);
    }

    @GetMapping("/findByTitle")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Recipe> findByTitle(@RequestParam String title) {
        return recipeService.findByTitle(title);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteRecipe(@PathVariable String id) {
         return recipeService.deleteRecipe(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Recipe> createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Recipe> replaceRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        return recipeService.replaceRecipe(recipe);
    }
}
