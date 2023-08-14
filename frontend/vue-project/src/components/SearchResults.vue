<template>
    <div class="search-results">
      <h2>Search Results</h2>
      <div class="results-container">
        <ul class="results-list">
            <!-- loop  each recipe in the recipes array -->
          <li v-for="recipe in recipes" :key="recipe.id">
            <a href="#" @click="handleClick(recipe)">{{ recipe.recipe_name }}</a>
             <!-- clickable link to select a recipe -->
          </li>
       </ul>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      recipes: {
        type: Array,
        required: true
      }
    },
    methods: {
      handleClick(recipe) {
        this.$emit('recipe-selected', recipe);
        // emit the 'recipe-selected' event with the clicked recipe as payload
      }
    }
  };
  </script>

<style scoped>
.search-results {
  max-height: 400px;
  overflow-y: auto;
}

.results-container {
  padding: 10px;
}

.results-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.results-list li {
  margin-bottom: 10px;
}

.results-list li a {
  display: block;
  padding: 5px;
  background-color: rgb(0, 189, 126, 0.7);
  text-decoration: none;
  color: #e0e0e0;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.results-list li a:hover {
  background-color: #ffffff;
  color: #333333;
}
</style>