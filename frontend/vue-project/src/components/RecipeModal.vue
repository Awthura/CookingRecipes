<template>
  <!-- button for changing the unit systems and dropdown lists -->
  <div class="recipe-modal">
    <button @click="editModal" class="edit-button" v-if="userLoggedIn">Edit</button>
    <EditRecipe class="modal-content" v-if="editEnabled" :recipe="recipe" @edited="deleteAndClose" @cancel="editEnabled=!editEnabled;"/>
    <div class="modal-content" v-if="!editEnabled" >
      <button @click="closeModal" class="close-button">X</button>
      <h2>{{ recipe.recipe_name }}</h2>
      <img :src="recipe.img_src" alt="Recipe Image" width="200">
      <p><strong>Category:</strong> {{ recipe.Category }}</p>
      <p><strong>Prep Time:</strong> {{ recipe.prep_time }}</p>
      <p><strong>Cook Time:</strong> {{ recipe.cook_time }}</p>
      <p><strong>Total Time:</strong> {{ recipe.total_time }}</p>
      <p><strong>Serves:</strong> {{ recipe.servings }}</p>
      <div class="recipe-section">
        <p><strong>Ingredients:</strong></p>
        <div class="unit-toggle">
          <button @click="toggleUnitSystem" :class="{ active: isMetric, 'metric-button': isMetric }">Metric</button>
          <button @click="toggleUnitSystem" :class="{ active: !isMetric, 'imperial-button': !isMetric }">Imperial</button>
        </div>
        <ul>
          <li v-for="(ingredient, index) in convertedIngredients" :key="index">{{ ingredient }}</li>
        </ul>
      </div>
      <div class="recipe-section">
        <p><strong>Directions:</strong></p>
        <ol>
          <li v-for="(step, index) in recipe.directions.split('\n')" :key="index">{{ step }}</li>
          <!-- render recipe's directions as a list item -->
        </ol>
      </div>
        <div class="link-buttons">
          <a :href="recipe.url" target="_blank" class="recipe-link">Open Recipe</a>
          <button @click="downloadIngredients" class="download-button">Download Ingredients</button>
          <!-- add a button to download ingredients -->
       </div>
    </div>
  </div>
</template>

<script>
import EditRecipe from './EditRecipe.vue';
export default {
  components: {
    EditRecipe
  },
  props: {
    recipe: {
      type: Object,
      required: true
    },
    userLoggedIn: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      editEnabled: false,
      isMetric: false // flag to toggle between metric and non-metric units
    };
  },
  methods: {
    deleteAndClose() {
      this.closeModal();
    },
    closeModal() {
      this.$emit("close-modal"); // emits 'close-modal' event to parent component
    },
    editModal() {
      this.editEnabled = true;
      //this.$emit("close-modal"); // emits 'close-modal' event to parent component
    },
    toggleUnitSystem() {
      this.isMetric = !this.isMetric;
    },
    parseFraction(fractionString) {
      const fractionMap = {
        // mapping of fraction strings to decimal values
        "½": 0.5,
        "¼": 0.25,
        "¾": 0.75,
        "⅓": 0.33,
        "⅔": 0.66,
        "⅛": 0.125,
        "⅜": 0.375,
        "⅝": 0.625,
        "⅞": 0.875
      };
      const fractionValue = fractionString.replace(/[\s\n]+/g, "").toLowerCase();
      return fractionMap[fractionValue] || parseFloat(fractionValue); // returns decimal value for the given fraction string
    },
    downloadIngredients() {
      const text = this.convertedIngredients.join('\n');
      const element = document.createElement('a');
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
      element.setAttribute('download', 'ingredients.txt');
      element.style.display = 'none';
      document.body.appendChild(element);
      element.click();
      document.body.removeChild(element);
    }
  },
  computed: {
    convertedIngredients() {
      if (this.isMetric) {
        // check if metric units are selected
        return this.recipe.ingredients.map((ingredient) => {
          const matches = ingredient.match(/([\d\s½¼¾⅓⅔⅛⅜⅝⅞]+(?:\.\d+)?)\s?([a-z]+)/i); // extract measurement and unit from ingredient string using regex
          if (matches) {
            let measurement = this.parseFraction(matches[1]); // convert the measurement to its decimal value using parseFraction method
            const unit = matches[2].toLowerCase();
            if (measurement === 1 && matches[1].includes("½")) {
              // special case: if measurement is 1 and includes '½', set it to 1.5
              measurement = 1.5;
            }
            let convertedMeasurement = measurement;
            switch (unit) {
              case "ounce":
              case "ounces":
                convertedMeasurement *= 28.35; // Convert ounces to grams
                return `${convertedMeasurement.toFixed(2)} g ${ingredient.replace(matches[0], "")}`;
              case "lb":
                convertedMeasurement *= 453.59; // Convert pounds to grams
                return `${convertedMeasurement.toFixed(2)} g ${ingredient.replace(matches[0], "")}`;
              case "teaspoon":
              case "teaspoons":
                convertedMeasurement *= 4.93; // Convert teaspoons to milliliters
                return `${convertedMeasurement.toFixed(2)} ml ${ingredient.replace(matches[0], "")}`;
              case "tablespoon":
              case "tablespoons":
                convertedMeasurement *= 14.79; // Convert tablespoons to milliliters
                return `${convertedMeasurement.toFixed(2)} ml ${ingredient.replace(matches[0], "")}`;
              case "cup":
              case "cups":
                convertedMeasurement *= 236.59; // Convert cups to milliliters
                return `${convertedMeasurement.toFixed(2)} ml ${ingredient.replace(matches[0], "")}`;
              case "qt":
              case "quart":
              case "quarts":
                convertedMeasurement *= 946.35; // Convert quarts to milliliters
                return `${convertedMeasurement.toFixed(2)} ml ${ingredient.replace(matches[0], "")}`;
              default:
                return ingredient;
            }
          } else {
            return ingredient;
          }
        });
      } else {
        return this.recipe.ingredients;
      }
    }
  },
};
</script>

<style scoped>
/* style for opening a recipe */
.recipe-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.modal-content {
  color: black;
  background-color: rgb(255, 255, 255, 0.9);
  padding: 20px;
  border-radius: 8px;
  max-width: 600px;
  width: 90%;
  max-height: 90%;
  overflow-y: auto;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.15);
  position: relative;
}
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 8px;
  background-color: #ffffff;
  color: #333333;
  border: none;
  cursor: pointer;
  font-size: 18px;
}
.close-button:hover {
  background-color: #f5f5f5;
}
.recipe-section {
  margin-bottom: 20px;
}

.recipe-section p {
  margin: 0;
}
.recipe-section ul,
.recipe-section ol {
  margin-top: 0.5em;
  margin-bottom: 1em;
  padding-left: 1.2em;
}
.recipe-section ul li,
.recipe-section ol li {
  margin-bottom: 0.5em;
}
.link-buttons {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}
.recipe-link {
  display: inline-block;
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #4caf50;
  color: #ffffff;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}
.recipe-link:hover {
  background-color: #45a049;
}
.metric-button.active {
  background-color: #4caf50;
  color: #ffffff;
}
.imperial-button.active {
  background-color: #2196f3;
  color: #ffffff;
}
.download-button {
  display: inline-block;
  margin-top: 10px;
  padding: 8px 16px;
  background-color: #2196f3;
  color: #ffffff;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s ease;
}
.download-button:hover {
  background-color: #1976d2;
}
</style>
