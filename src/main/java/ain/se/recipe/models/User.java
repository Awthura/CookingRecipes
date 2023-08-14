package ain.se.recipe.models;

import java.rmi.server.ObjID;

// import java.util.Set;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import org.bson.types.ObjectId;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.index.IndexDirection;
// import org.springframework.data.mongodb.core.index.Indexed;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.mongodb.core.mapping.Field;
// import org.springframework.data.mongodb.core.mapping.DBRef;


// @Document(collection = "user-auth")
// @Data
// @NoArgsConstructor
// public class User {
//     @Id
//     private ObjectId id;
//     @Field("name")
//     private String name;
//     private String email;
//     private String password;
//     private String username;
//     private boolean enabled;
//     @DBRef
//     private Set<Roles> roles;
//     /*public User() {}*/

//     public User(ObjectId id, String name, String email, String password, String username) {
//         this.id = id;
//         this.name = name;
//         this.password = password;
//         this.email = email;
//         this.username = username;
//     }

      
//     public ObjectId getId() {
//         return id;
//     }

//     public String getEmail() {
//         return email;
//     }
//     public String getName() {
//         return name;
//     }
//     public String getUsername() {
//         return username;
//     }

//     public String getPassword() {
//         return password;
//     }
    
//     public Set<Roles> getRoles() {
//         return roles;
//     }

//     public boolean isEnabled() {
//         return enabled;
//     }

//     public void setId(ObjectId id) {
//         this.id = id;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     /*  this was added recenlty added bro, Arthur */
//     public void setUserName(String username){
//         this.username = username; 
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public void setRoles(Set<Roles> roles) {
//         this.roles = roles;
//     }
    
//     public void setEnabled(boolean enabled) {
//         this.enabled = enabled;
//     }

//     public boolean authenticate(String username, String password) {
//         return this.username.equals(username) && this.password.equals(password);
//     }


// }
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.lang.NonNull;

@Document(collection = "user")
public class User {

    @Id
    private ObjectId id;
    @Field("name")
    private String name;
    private String email;
    private String password;
    private String fullname;
    private boolean enabled;
    @DBRef
    private Set<Roles> roles;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
    
    
    
}
/*
# to use method of the authentication, we create a user class and call the method provided username and password.

User user = new User(id, name, email, password, username);
boolean authenticated = user.authenticate(inputUsername, inputPassword);

if (authenticated) {
    // Login successful
} else {
    // Invalid username or password
}
 */