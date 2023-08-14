package ain.se.recipe.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ain.se.recipe.models.Roles;

public interface RoleRepository extends MongoRepository<Roles, String> {
    
    Roles findByRole(String role);
    
}