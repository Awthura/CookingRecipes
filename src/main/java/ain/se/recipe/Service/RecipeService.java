package ain.se.recipe.Service;
import ain.se.recipe.models.Recipes;
import ain.se.recipe.Repository.RecipeRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipes> findRecipes(){
        return recipeRepository.findAll();

    }

    public Optional<Recipes> findRecipeById(ObjectId recipeId) {
        return recipeRepository.findById(recipeId);
    }

    public List<Recipes> findByRecipeName(String recipeName) {
        return recipeRepository.findByRecipe_name(recipeName);  // Updated method call
    }
    public List<Recipes> findByRecipeNameContaining(String recipeName) {
        return recipeRepository.findByRecipeNameContaining(recipeName).stream()
                //.limit(5) // Limit the result to maximum 5 items
                .collect(Collectors.toList());
    }
    public List<Recipes> findByRecipeNameAndCategory(String recipeName, String category) {
        return recipeRepository.findByRecipeNameAndCategory(recipeName, category).stream()
                //.limit(5) // Limit the result to maximum 5 items
                .collect(Collectors.toList());
    }
    public List<Recipes> findByCategory(String category) {
        return recipeRepository.findByCategory(category);
    }

    public List<Recipes> findByRating(float rating) {
        return recipeRepository.findByRating(rating);
    }


    public Recipes save(Recipes recipe){
        return recipeRepository.save(recipe);
    }

    


}
