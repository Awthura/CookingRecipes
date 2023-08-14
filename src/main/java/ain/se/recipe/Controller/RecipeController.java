package ain.se.recipe.Controller;
import ain.se.recipe.Repository.RecipeRepository;
import ain.se.recipe.models.RecipePdfGenerator;
import ain.se.recipe.models.Recipes;
import ain.se.recipe.Service.RecipeService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.jayway.jsonpath.internal.function.text.Length;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/recipes")
public class RecipeController {   
    @Autowired
    private RecipeService recipeService;

    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Autowired
    public void setRecipePdfGenerator(RecipePdfGenerator recipePdfGenerator) {
        this.recipePdfGenerator = recipePdfGenerator;
    }

    @Autowired
    private RecipePdfGenerator recipePdfGenerator;

    @GetMapping
    public ResponseEntity<List<Recipes>> getRecipes(){
        return new ResponseEntity<List<Recipes>>(recipeService.findRecipes(), HttpStatus.OK);
    }

    @GetMapping("/id/{recipeId}")
    public ResponseEntity<Recipes> getRecipeById(@PathVariable("recipeId") ObjectId recipeId) {
        Optional<Recipes> recipes = recipeService.findRecipeById(recipeId);
        return recipes.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // @GetMapping("/delete/{recipeId}")
    // public ResponseEntity<Recipes> deleteRecipeById(@PathVariable("recipeId") ObjectId recipeId) {
    //     Optional<Recipes> recipes = recipeService.deleteRecipeById(recipeId);
    //     return recipes.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    @GetMapping("/byname/{recipeName}")  // Updated mapping
    public ResponseEntity<List<Recipes>> getRecipeByName(@PathVariable("recipeName") String recipeName) {
        List<Recipes> recipes = recipeService.findByRecipeName(recipeName);
        if (!recipes.isEmpty()) {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/bypartial/{partialName}")
    public ResponseEntity<List<Recipes>> getRecipeByPartial(@PathVariable("partialName") String partialName) {        
        if(partialName.equals("rusty1234")){partialName="";}
        List<Recipes> recipes;
        if (partialName.isEmpty()) {
            recipes = recipeService.findRecipes();        
        } else  {
            recipes = recipeService.findByRecipeNameContaining(partialName);
        } 
        
        if (!recipes.isEmpty()) {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bynamecategory/{partialName}/{category}")
    public ResponseEntity<List<Recipes>> getRecipeByNameCategory(@PathVariable("partialName") String partialName,@PathVariable("category") String category) {        
        if(partialName.equals("rusty1234")){partialName="";}
        if(category.equals("rusty1234")){category="";}
        
        List<Recipes> recipes;
        if (partialName.isEmpty() && category.isEmpty()) {
            recipes = recipeService.findRecipes();        
        } else if (category.isEmpty()) {
            recipes = recipeService.findByRecipeNameContaining(partialName);
        } else {
            recipes = recipeService.findByRecipeNameAndCategory(partialName, category);
        }
        
        if (!recipes.isEmpty()) {
            return new ResponseEntity<>(recipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Recipes>> getRecipesByCategory(@PathVariable("category") String category) {
        List<Recipes> recipes = recipeService.findByCategory(category);
        if (!recipes.isEmpty()) {
            // Shuffle the recipes list
            Collections.shuffle(recipes);

            // Get the first 10 recipes or less if the list size is smaller
            int limit = Math.min(recipes.size(), 10);
            List<Recipes> randomRecipes = recipes.subList(0, limit);

            return new ResponseEntity<>(randomRecipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rating/{rating}") 
    public ResponseEntity<List<Recipes>> getRecipesByRating(@PathVariable("rating") float rating) {
        List<Recipes> recipes = recipeService.findRecipes(); // Assuming a method to get all recipes
        if (!recipes.isEmpty()) {
            // Sort the recipes based on rating in descending order
            recipes.sort(Comparator.comparingDouble(Recipes::getRating).reversed());

            // Get the top 10 recipes or less if the list size is smaller
            int limit = Math.min(recipes.size(), 10);
            List<Recipes> topRatedRecipes = recipes.subList(0, limit);

            return new ResponseEntity<>(topRatedRecipes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





    @PostMapping
    public ResponseEntity<Recipes> createRecipe(@RequestBody Recipes recipe) {
        Recipes savedRecipe = recipeService.save(recipe);
        return new ResponseEntity<>(savedRecipe, HttpStatus.CREATED);
    }



    @PostMapping("/pdf/{recipeId}")
    public ResponseEntity<String> saveRecipeAsPdf(@PathVariable("recipeId") ObjectId recipeId) {
        try {
            recipePdfGenerator.generateRecipePdf(recipeId);

            // Get the byte array representation of the generated PDF
            byte[] pdfBytes = recipePdfGenerator.getPdfBytes();

            // Save the PDF file to the database
            // Replace "savePdfToDatabase" with the appropriate method to save the PDF in your database
            savePdfToDatabase(pdfBytes);

            return ResponseEntity.ok("PDF saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving PDF: " + e.getMessage());
        }
    }

    // Method to save the PDF file to the database
    public void savePdfToDatabase(byte[] pdfBytes) {
        // Implement the logic to save the PDF file in your database
    }

    @GetMapping("/pdf/{recipeId}")
    public ResponseEntity<byte[]> getRecipePdf(@PathVariable("recipeId") ObjectId recipeId) {
        try {
            recipePdfGenerator.generateRecipePdf(recipeId);
            byte[] pdfBytes = recipePdfGenerator.getPdfBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("inline").filename("recipe.pdf").build());
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error generating or retrieving PDF: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // code by Hemil Patel
    // @GetMapping("/total_time/{timeRange}")
    // public ResponseEntity<List<Recipes>> getRecipesByTotalTime(@PathVariable("timeRange") String timeRange) {
    //     List<Recipes> recipes = recipeService.getRecipesByTotalTime(timeRange);
    //     List<Recipes> matchingRecipes = new ArrayList<>();

    //     String[] rangeParts = timeRange.split("-");
    //     if (rangeParts.length == 2) {
    //         int minTime = 0;
    //         int maxTime = 0;
    //         try {
    //             minTime = Integer.parseInt(rangeParts[0].trim());
    //             maxTime = Integer.parseInt(rangeParts[1].trim());
    //         } catch (NumberFormatException e) {
    //             e.printStackTrace();
    //             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //         }

    //         for (Recipes recipe : recipes) {
    //             int recipeTotalTime = convertTimeToMinutes(recipe.getTotal_time());
    //             System.out.println("Recipe: " + recipe.getTotal_time() + " | Total Time (minutes): " + recipeTotalTime);
    //             if (recipeTotalTime >= minTime && recipeTotalTime <= maxTime) {
    //                 matchingRecipes.add(recipe);
    //             }
    //         }
    //     }

    //     if (!matchingRecipes.isEmpty()) {
    //         matchingRecipes.sort(Comparator.comparingInt(recipe -> convertTimeToMinutes(recipe.getTotal_time())));
    //         int limit = Math.min(matchingRecipes.size(), 10);
    //         List<Recipes> topRecipes = matchingRecipes.subList(0, limit);
    //         return new ResponseEntity<>(topRecipes, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }


    // private int convertTimeToMinutes(String time) {
    //     int minutes = 0;
    //     String[] parts = time.split("-");

    //     for (int i = 0; i < parts.length; i++) {
    //         String part = parts[i];

    //         if (part.contains("hrs") && i < parts.length - 1) {
    //             int hours = Integer.parseInt(parts[i + 1].trim());
    //             minutes += hours * 60;
    //         } else if (part.contains("mins")) {
    //             minutes += Integer.parseInt(part.replaceAll("\\D+", ""));
    //         }
    //     }

    //     return minutes;
    // }





}


