<template>
    <div class="create-recipe scroll-container">
      
      <form ref="editForm" @submit.prevent="submitForm">
        <label for="recipeName">Recipe Name: </label>
        <input type="text" id="recipeName" v-model="recipeName">
  
        <div></div>
        <label for="recipeCategory">Category: </label>
        <select id="recipeCategory" v-model="recipeCategory" >
          <option>Vegan</option>
          <option>Vegetarian</option>
          <option>Meat</option>
        </select>
        
        <div></div>
        <label for="servings">Servings: </label>
        <input type="number" id="servings" v-model="servings" >
        
        <div></div>
        <label for="ingredients">Ingredients: </label>
        <textarea id="ingredients" v-model="ingredients" ></textarea>
        
        <div></div>
        <label for="directions">Directions: </label>
        <textarea id="directions" v-model="directions" ></textarea>
        
        
        
        <div></div>
        <label for="prepTime">Preparation Time: </label>
        <input type="text" id="prepTime" v-model="prepTime" >
  
        <button type="submit">Submit Changes</button>
        <button @click="cancel">Cancel</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    // export an object
    props: {
        recipe: {
            type: Object,
            required: true
        }
    },
    data() {
      return {
        recipeName: '',
       
        recipeCategory: '',
        
        servings: '',
        ingredients: '',
        directions: '',
        recipeImage: null,
        prepTime: ''
      };
    },
    created() {
    this.recipeName = this.recipe.recipe_name;
    this.recipeCategory = this.recipe.Category;
    this.servings = this.recipe.servings;
    this.ingredients = this.recipe.ingredients;
    this.directions = this.recipe.directions;
    this.recipeImage = this.recipe.img_src;
    this.prepTime = this.recipe.prep_time;
  },
    methods: {
      submitForm() {
        // submission + sending the data to a server
        // You can access the form data via the component's data properties
        const newRecipe = {
          "recipe_name": this.recipeName,          
           "Category": this.recipeCategory,
          
          "servings": this.servings,
          //"ingredients": this.ingredients,
          "directions": this.directions,
          //"img_src": this.recipeImage,
          "prep_time": this.prepTime,
        };
        let url = 'api/recipes';
        axios.post(url, newRecipe)  
    .then(response => {
      // after successfully completes the HTTP request returns the data (response ) from the server
      console.log(response.data); //inspect and debug the response data received from the server.
    })
    .catch(error => { //handle errors that occur during the execution
      console.error('Error:', error);
    });

    this.$refs.editForm.reset();
    this.$emit("edited");
    this.cancel();

      },
      handleImageUpload(event) {
        // the logic related to image upload , storing the uploaded image
        this.recipeImage = event.target.files[0];
      },
      cancel(){
        this.$emit("cancel");
      },
    }
  };
  </script>
  
  <style>
  

  .scroll-container {
  overflow-y: auto; /*  vertical scrollbar */
  max-height: 300px; 
}
  </style>
  