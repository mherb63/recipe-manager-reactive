package com.mherb.recipemanagerreactive.controller;

import com.mherb.recipemanagerreactive.domain.Recipe;
import com.mherb.recipemanagerreactive.repository.RecipeRepository;
import com.mherb.recipemanagerreactive.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RecipeController.class)
@Import(RecipeService.class)
public class RecipeControllerIT {
    @MockBean
    RecipeRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCreateRecipe() {
        Recipe recipe = Recipe.builder()
                .contributorName("Michael Herb")
                .title("Boiled Water")
                .instructions("Heat pot of water to 212 degrees farenheit")
                .notes("serve hot and enjoy")
                .build();

        Mockito.when(repository.save(recipe)).thenReturn(Mono.just(recipe));

        webClient.post()
                .uri("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(recipe))
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(repository, times(1)).save(recipe);
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = Recipe.builder()
                .id("1001")
                .contributorName("Michael Herb")
                .title("Boiled Water")
                .instructions("Heat pot of water to 212 degrees farenheit")
                .notes("serve hot and enjoy")
                .build();

        Mockito.when(repository.findById("1001")).thenReturn(Mono.just(recipe));

        webClient.get().uri("/recipes/{id}", "1001")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1001")
                .jsonPath("$.contributorName").isEqualTo("Michael Herb")
                .jsonPath("$.title").isEqualTo("Boiled Water");

        Mockito.verify(repository, times(1)).findById("1001");
    }
}