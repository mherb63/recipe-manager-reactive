package com.mherb.recipemanagerreactive.bootstrap;

import com.mherb.recipemanagerreactive.domain.Recipe;
import com.mherb.recipemanagerreactive.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RecipeRepositoryBoot {

    private final RecipeRepository recipeRepository;

    public RecipeRepositoryBoot(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        log.info("Received Spring ContextRefreshedEvent: {}", event);
        if (recipeRepository.count().block() == 0) {
            createMockRecipes();
        }
    }

    private void createMockRecipes() {
        recipeRepository.save(Recipe.builder()
                .contributorName("Abe Lincoln")
                .title("Abe's Chile")
                .instructions("bla bla bla")
                .notes("Here are some notes")
                .build()
        ).block();

        recipeRepository.save(Recipe.builder()
                .contributorName("Teddy Roosevelt")
                .title("Teddy's Famous Ribs")
                .instructions("bla bla bla")
                .notes("Here are some notes")
                .build()
        ).block();

        recipeRepository.save(Recipe.builder()
                .contributorName("Robert E Lee")
                .title("Bobby's Biscuits with Country Gravy")
                .instructions("bla bla bla")
                .notes("Here are some notes")
                .build()
        ).block();

        recipeRepository.save(Recipe.builder()
                .contributorName("John F Kennedy")
                .title("Jack's Famous Clam Chowder")
                .instructions("bla bla bla")
                .notes("Here are some notes")
                .build()
        ).block();
    }
}
