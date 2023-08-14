package ain.se.recipe.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingredients {
    @Id
    private ObjectId id;
    private String body;

}
