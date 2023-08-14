<script>
import WelcomeItem from './WelcomeItem.vue'
import SearchResults from './SearchResults.vue'
import RecipeModal from './RecipeModal.vue'
export default { 
  components: {
    WelcomeItem,
    SearchResults,
    RecipeModal
  },
  props: {
    userLoggedIn: {
            type: Boolean,
            required: true
        }
    },
  data() {
    return {
      showResults: false,
      searchResults: [],
      selectedRecipe: null,
      searchText: "",
      category: '',
    };
  },
  methods: {
    handleClick() {
      let url='' //initializes the url variable to an empty string.
      if (this.searchText === ""){
        this.searchText="rusty1234" //is empty, this line sets the value of searchText to "rusty1234".
       url='api/recipes/bynamecategory/' + this.searchText // ets the value of url by concatenating the string 
      this.searchText=""
    }else{
       url='api/recipes/bynamecategory/' + this.searchText
    }

    if (this.category === ""){
       // if category is empty, assign a default value
        this.category="rusty1234"
       url=url + '/' + this.category
      this.category=""
    }else{
        // append the category value to the URL
       url=url + '/' + this.category
    }
      fetch(url)
      // perform a fetch request to the constructed URL
        .then(response => response.json())
        .then(data => {
              // handle the retrieved data
          this.searchResults = data;
          this.showResults = true;
        });
    },
    handleRecipeSelected(recipe) {
      //the selection of a recipe
      this.selectedRecipe = recipe;
    },
    closeModal() {
      //dismiss the selected recipe
      this.selectedRecipe = null;
    }
  }
};
</script>

<template>
  <WelcomeItem>
    <template #icon>
      <DocumentationIcon />
    </template>
    <template #heading>Search Recipe</template>
    <input v-model="searchText" placeholder="search by keyword" @keydown.enter="handleClick">
    <button type="submit" v-on:click="handleClick">Search</button>
    <div>{{ message }}</div>
    <div>Category</div>
    <select v-model="category">
      <!-- category select dropdown -->
      <option value="">Select one category</option>
      <option>Vegan</option>
      <option>Vegetarian</option>
      <option>Meat</option>
    </select>
    <SearchResults v-if="showResults" :recipes="searchResults" @recipe-selected="handleRecipeSelected" />
      <!-- displaying search results -->
    <RecipeModal v-if="selectedRecipe" :recipe="selectedRecipe" :userLoggedIn="userLoggedIn" @close-modal="closeModal" />
      <!-- displaying recipe modal -->
  </WelcomeItem>
</template>
