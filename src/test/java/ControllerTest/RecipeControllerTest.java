/* Joint Coding Krishna Oli and Hemil Patel */

package ControllerTest;

import ain.se.recipe.Controller.RecipeController;
import ain.se.recipe.Repository.RecipeRepository;
import ain.se.recipe.models.RecipePdfGenerator;
import ain.se.recipe.models.Recipes;
import ain.se.recipe.Service.RecipeService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.io.ByteArrayOutputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@AutoConfigureMockMvc
class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    private RecipeController recipeController;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipePdfGenerator recipePdfGenerator;

    @Autowired
    private MockMvc mockMvc;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        recipeController = new RecipeController();
        recipeController.setRecipeService(recipeService);
        recipeController.setRecipePdfGenerator(recipePdfGenerator);
    }

    @AfterEach
    public void teardown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetRecipes() {
        // Create some sample recipes
        Recipes recipe1 = new Recipes();
        recipe1.setId(new ObjectId("64652ad7f106d208d38485f3"));
        Recipes recipe2 = new Recipes();
        recipe2.setId(new ObjectId("64652ad7f106d208d38485f5"));
        List<Recipes> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        // Mock the behavior of the recipeService
        when(recipeService.findRecipes()).thenReturn(recipes);

        // Call the getRecipes() method
        ResponseEntity<List<Recipes>> response = recipeController.getRecipes();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(recipes, response.getBody());

        // Verify that the recipeService method was called
        verify(recipeService, times(1)).findRecipes();
    }

    @Test
    void testGetRecipeById_ExistingId() {
        // Arrange
        ObjectId recipeId = new ObjectId();
        Recipes recipe = new Recipes();
        when(recipeService.findRecipeById(recipeId)).thenReturn(Optional.of(recipe));

        // Act
        ResponseEntity<Recipes> response = recipeController.getRecipeById(recipeId);

        // Assert
        verify(recipeService).findRecipeById(recipeId);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == recipe;
    }

    @Test
    void testGetRecipeById_NonExistingId() {
        // Arrange
        ObjectId recipeId = new ObjectId();
        when(recipeService.findRecipeById(recipeId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Recipes> response = recipeController.getRecipeById(recipeId);

        // Assert
        verify(recipeService).findRecipeById(recipeId);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testGetRecipeByName_ExistingName() {
        // Arrange
        String recipeName = "Pasta";
        List<Recipes> recipes = Arrays.asList(new Recipes(), new Recipes());
        when(recipeService.findByRecipeName(recipeName)).thenReturn(recipes);

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipeByName(recipeName);

        // Assert
        verify(recipeService).findByRecipeName(recipeName);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == recipes;
    }

    @Test
    void testGetRecipeByName_NonExistingName() {
        // Arrange
        String recipeName = "Non-existing recipe";
        when(recipeService.findByRecipeName(recipeName)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipeByName(recipeName);

        // Assert
        verify(recipeService).findByRecipeName(recipeName);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testGetRecipeByPartial_ExistingPartialName() {
        // Arrange
        String partialName = "Cake-pie";
        List<Recipes> recipes = Arrays.asList(new Recipes(), new Recipes());
        when(recipeService.findByRecipeNameContaining(partialName)).thenReturn(recipes);

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipeByPartial(partialName);

        // Assert
        verify(recipeService).findByRecipeNameContaining(partialName);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() == recipes;
    }

    @Test
    void testGetRecipeByPartial_NonExistingPartialName() {
        // Arrange
        String partialName = "Non-existing partial name";
        when(recipeService.findByRecipeNameContaining(partialName)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipeByPartial(partialName);

        // Assert
        verify(recipeService).findByRecipeNameContaining(partialName);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testGetRecipesByCategory_ExistingCategory() {
        // Arrange
        String category = "Desserts";
        List<Recipes> recipes = Arrays.asList(new Recipes(), new Recipes());
        when(recipeService.findByCategory(category)).thenReturn(recipes);

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipesByCategory(category);

        // Assert
        verify(recipeService).findByCategory(category);
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() <= 10;
    }

    @Test
    void testGetRecipesByCategory_NonExistingCategory() {
        // Arrange
        String category = "Non-existing category";
        when(recipeService.findByCategory(category)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipesByCategory(category);

        // Assert
        verify(recipeService).findByCategory(category);
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testGetRecipesByRating_ExistingRating() {
        // Arrange
        float rating = 4.5f;
        List<Recipes> recipes = Arrays.asList(new Recipes(), new Recipes());
        when(recipeService.findRecipes()).thenReturn(recipes);

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipesByRating(rating);

        // Assert
        verify(recipeService).findRecipes();
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null;
        assert response.getBody().size() <= 10;
    }

    @Test
    void testGetRecipesByRating_NonExistingRating() {
        // Arrange
        float rating = 5.0f;
        when(recipeService.findRecipes()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Recipes>> response = recipeController.getRecipesByRating(rating);

        // Assert
        verify(recipeService).findRecipes();
        assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    }

    @Test
    void testCreateRecipe_ValidRecipe() {
        // Arrange
        Recipes recipe = new Recipes();
        when(recipeService.save(recipe)).thenReturn(recipe);

        // Act
        ResponseEntity<Recipes> response = recipeController.createRecipe(recipe);

        // Assert
        verify(recipeService).save(recipe);
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() == recipe;
    }

}


