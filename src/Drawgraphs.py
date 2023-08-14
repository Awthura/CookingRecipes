import graphviz

# Create a new graph
graph = graphviz.Digraph()

# Add User class
graph.node("User", shape="rectangle")

# Add User class attributes
attributes = [
    "id: ObjectId",
    "name: String",
    "email: String",
    "password: String",
    "fullname: String",
    "enabled: boolean",
    "roles: Set<Roles>"
]
for attribute in attributes:
    graph.node(attribute, shape="rectangle")
    graph.edge("User", attribute)

# Add User class methods
methods = [
    "getId(): ObjectId",
    "setId(id: ObjectId): void",
    "getEmail(): String",
    "setEmail(email: String): void",
    "getPassword(): String",
    "setPassword(password: String): void",
    "getFullname(): String",
    "setFullname(fullname: String): void",
    "isEnabled(): boolean",
    "setEnabled(enabled: boolean): void",
    "getRoles(): Set<Roles>",
    "setRoles(roles: Set<Roles>): void"
]
for method in methods:
    graph.node(method, shape="rectangle")
    graph.edge("User", method)

# Add Recipes class
graph.node("Recipes", shape="rectangle")

# Add Recipes class attributes
attributes = [
    "id: ObjectId",
    "recipe_name: String",
    "Category: String",
    "url: String",
    "prep_time: String",
    "cuisine_path: String",
    "cook_time: String",
    "total_time: String",
    "servings: String",
    "yield: String",
    "ingredients: List<String>",
    "directions: String",
    "rating: float",
    "nutrition: List<String>",
    "timing: String",
    "img_src: String"
]
for attribute in attributes:
    graph.node(attribute, shape="rectangle")
    graph.edge("Recipes", attribute)

# Add Recipes class methods
methods = [
    "getId(): ObjectId",
    "setId(id: ObjectId): void",
    "getRecipe_name(): String",
    "setRecipe_name(recipe_name: String): void",
    "getCategory(): String",
    "setCategory(Category: String): void",
    "getUrl(): String",
    "setUrl(url: String): void",
    "getPrep_time(): String",
    "setPrep_time(prep_time: String): void",
    "getCuisine_path(): String",
    "setCuisine_path(cuisine_path: String): void",
    "getCook_time(): String",
    "setCook_time(cook_time: String): void",
    "getTotal_time(): String",
    "setTotal_time(total_time: String): void",
    "getServings(): String",
    "setServings(servings: String): void",
    "getYield(): String",
    "setYield(yield: String): void",
    "getIngredients(): List<String>",
    "setIngredients(ingredients: List<String>): void",
    "getDirections(): String",
    "setDirections(directions: String): void",
    "getRating(): float",
    "setRating(rating: float): void",
    "getNutrition(): List<String>",
    "setNutrition(nutrition: List<String>): void",
    "getTiming(): String",
    "setTiming(timing: String): void",
    "getImg_src(): String",
    "setImg_src(img_src: String): void"
]
for method in methods:
    graph.node(method, shape="rectangle")
    graph.edge("Recipes", method)

# Save the graph as a PDF file
graph.format = "pdf"
graph.render("class_diagram", view=True)

