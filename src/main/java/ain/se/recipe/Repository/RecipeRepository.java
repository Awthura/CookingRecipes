package ain.se.recipe.Repository;

import ain.se.recipe.models.Recipes;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface RecipeRepository extends MongoRepository<Recipes, ObjectId> {
   @Query("{'recipe_name': ?0}")
   List<Recipes>findByRecipe_name(String recipeName);  // Updated method name
   
   @Query("{'recipe_name': {$regex : ?0, $options: 'i'}}")
   List<Recipes> findByRecipeNameContaining(String recipeName);
   @Query("{'Category': ?0}")
   List<Recipes> findByCategory(String category);
   @Query("{'rating': ?0}")
   List<Recipes> findByRating(float rating);
   @Query("{'recipe_name': {$regex : ?0, $options: 'i'}, 'Category': {$regex : ?1, $options: 'i'}}")
   List<Recipes> findByRecipeNameAndCategory(String recipeName, String category);

}

