<template>
  <div class="form">
    <h3>Add a Recipe</h3>
    
    <label for="title">Title: </label>
    <input name="title" type="text" id="title" required v-model="recipe.title"/>

    <label for="title">Cuisine Type: </label>
    <input name="cuisineType" type="cuisineType" id="cuisineType" required v-model="recipe.cuisineType"/>

    <label for="Serving Time">Serving Size: </label>
    <input name="serving time" type="text" id="servingTime" required v-model="recipe.servingSize"/>

    <label for="Cooking Time">Cooking Time: </label>
    <input name="Cookingtime" type="text" id="cookingTime" required v-model="recipe.cookingTime"/>

    <label for="Size">Steps: </label>
    <input name="Steps" type="text" id="steps" required v-model="recipe.steps"/>
    
    <label for="ingredients">Ingredients: </label>
    <input name="ingredients" type="text" id="ingredients" v-model="ingredientInput" @keyup.enter="addIngredients" placeholder="Enter ingredient separated by commas"/>
  
    <button @click="addRecipe" class="addRecipe">Add Recipe</button>
  </div>
</template>

<script>
import { jwtDecode } from 'jwt-decode';

export default {
  name: "AddRecipe",
  data() {
    return {
      recipe: {
        title: "",
        ingredients: [],
        servingSize: 0,
        cookingTime: "",
        cuisineType: "",
        steps: "",
        postedById: ""
      },
      ingredientInput: ""
    };
  },
  methods: {
    addIngredients() {
      const ingredients = this.ingredientInput.split(",").map((ingredient) => ingredient.trim());
      this.recipe.ingredients.push(...ingredients);
      this.ingredientInput = "";
    },
    addRecipe() {
      this.addIngredients();
      const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode(token);
        this.recipe.postedById = decoded.userId;
      }
      fetch("http://localhost:8003/api/recipes", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(this.recipe),
      })
        .then((response) => {
          if (response.ok) {
            console.log("Recipe added successfully");
            this.$router.push("/recipes"); // Redirect to the recipes page after successful addition
          } else {
            console.log("Failed to add recipe", response.error);
          }
        })
        .catch((error) => {
          console.error("Error adding recipe:", error);
        });
    }
  },
  mounted() {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const decoded = jwtDecode(token);
      this.recipe.postedById = decoded.userId;
    }
  }
};
</script>

<style scoped>
.form {
  max-width: 420px;
  margin: 30px auto;
  background: rgb(167, 154, 154);
  text-align: left;
  padding: 40px;
  border-radius: 10px;
}
h3 {
  text-align: center;
  color: rgb(8, 110, 110);
}
label {
  color: rgb(8, 110, 110);
  display: inline-block;
  margin: 25px 0 15px;
  font-size: 0.8em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
}
input {
  display: block;
  padding: 10px 6px;
  width: 100%;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid white;
  color: blue;
}
button {
  background: rgb(8, 110, 110);
  border: 0;
  padding: 10px 20px;
  margin-top: 20px;
  color: white;
  border-radius: 20px;
  align-items: center;
  text-align: center;
}
</style>
