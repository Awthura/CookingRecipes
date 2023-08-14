package ain.se.recipe.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "recipes")
@Data
/*@AllArgsConstructor*/
@NoArgsConstructor
public class Recipes {
    @Id
    private ObjectId id;
    private String recipe_name;
    //@Field("Category")
    private String Category;
    private String url;
    private String prep_time;
    private String cuisine_path;
    private String cook_time;
    private String total_time;
    private String servings;
    private String yield;
    private List<String> ingredients;
    private String directions;
    private float rating;
    /* for the time being the Nutrition values are stored as an url links*/
    private List<String> nutrition;
    private String timing;

    /* for the time being the images are stored as an url links*/
    private String img_src;

    public Recipes(ObjectId id, String recipe_name, String Category, String url,
                   String prep_time, String cuisine_path, String cook_time, String total_time,
                   String servings, String yield, List<String> ingredients, String directions,
                   float rating,List<String> nutrition,  String timing, String img_src){
        this.id = id;
        this.recipe_name = recipe_name;
        this.Category = Category;
        this.url = url;
        this.prep_time = prep_time;
        this.cuisine_path = cuisine_path;
        this.cook_time = cook_time;
        this.total_time = total_time;
        this.servings = servings;
        this.yield = yield;
        this.ingredients = ingredients;
        this.directions = directions;
        this.rating = rating;
        this.nutrition = nutrition;
        this.timing = timing;
        this.img_src = img_src;

    }

}
