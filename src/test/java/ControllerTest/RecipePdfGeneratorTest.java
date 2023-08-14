package ControllerTest;

import ain.se.recipe.Repository.RecipeRepository;
import ain.se.recipe.models.RecipePdfGenerator;
import ain.se.recipe.models.Recipes;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RecipePdfGeneratorTest {
    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipePdfGenerator recipePdfGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateRecipePdf() {
        try {
            ObjectId recipeId = new ObjectId();
            Recipes recipe = new Recipes();
            recipe.setId(recipeId);
            recipe.setRecipe_name("Awesome Sausage, Apple and Cranberry Stuffing");
            recipe.setCategory("meat");
            recipe.setPrep_time("30");
            recipe.setCook_time("60");
            recipe.setIngredients(Collections.singletonList("3 ¾ cups cubed white bread, 1 ½ cups cubed whole wheat bread, 1 pound ground turkey sausage, 1 cup chopped onion, ¾ cup chopped celery, 2 ½ teaspoons dried sage, 1 ½ teaspoons dried rosemary, ½ teaspoon dried thyme, 1  Golden Delicious apple, cored and chopped, ¾ cup dried cranberries, ⅓ cup minced fresh parsley, 1  cooked turkey liver, finely chopped, ¾ cup turkey stock, 4 tablespoons unsalted butter, melted"));
            recipe.setDirections("Preheat the oven to 350 degrees F (175 degrees C).\nSpread white and whole wheat bread cubes in a single layer on a large baking sheet.\nBake in the preheated oven until evenly toasted, 5 to 7 minutes. Transfer toasted bread cubes to a large bowl.\nCook sausage and onions in a large skillet over medium heat, stirring and breaking up lumps, until evenly browned, 6 to 8 minutes. Add celery, sage, rosemary, and thyme; cook and stir until flavors blend, about 2 minutes.\nPour sausage mixture over bread in bowl. Mix in chopped apple, dried cranberries, parsley, and liver. Drizzle with turkey stock and melted butter; mix lightly.\nAllow stuffing to cool completely before loosely stuffing a turkey.\n\n\n\n\n\n\n\n\n\n\n\nDotdash Meredith Food Studios\n"
);
            recipe.setImg_src("https://www.allrecipes.com/thmb/A16n6yykk6y6ofEi8akwV-6V9Ck=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/1x1-PASSANO_ALR0922_Faves_Stuffing_5279-7495521ab45c4b8880373657bedc0db5.jpg"
);

            Optional<Recipes> optionalRecipe = Optional.of(recipe);

            when(recipeRepository.findById(recipeId)).thenReturn(optionalRecipe);

            recipePdfGenerator.generateRecipePdf(recipeId);

            byte[] actualPdfBytes = recipePdfGenerator.getPdfBytes();

            // Perform assertions on the generated PDF if needed

            verify(recipeRepository, times(1)).findById(recipeId);
        } catch (com.itextpdf.io.IOException ex) {
            // Handle the exception
            ex.printStackTrace(); // or log the exception message
        } catch (Exception ex) {
            // Handle other exceptions if needed
            ex.printStackTrace(); // or log the exception message
        }
    }

    // Other test methods for different scenarios
}
