package com.mherb.recipemanagerreactive.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Recipes")
public class Recipe {
    @Id
    private String id;
    private String contributorName;
    private String title;
    private String instructions;
    private String notes;
}
