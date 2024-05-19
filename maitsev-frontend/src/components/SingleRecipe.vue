<template>
  <div class="recipe">
    <div id="form">
      <h3>A Recipe</h3>
      <label for="title">Title: </label>
      <input name="title" type="text" id="title" required v-model="recipe.title"/>

      <label for="cuisineType">Cuisine Type: </label>
      <input name="cuisineType" type="text" id="cuisineType" required v-model="recipe.cuisineType"/>

      <label for="stepsTaken">Steps Taken: </label>
      <input name="stepsTaken" type="text" id="stepsTaken" required v-model="recipe.steps"/>
      
      <label for="servingSize">Serving Size: </label>
      <input name="servingSize" type="text" id="servingSize" required v-model="recipe.servingSize"/>

      <label for="postedBy">Posted By: </label>
      <span>{{ postedBy }}</span> <br />
           
      <label for="ingredients">Ingredients: </label>
      <ul>
        <li v-for="(ingredient, index) in recipe.ingredients" :key="index">{{ ingredient }}</li>
      </ul> <br />
 
    </div>
    <div>
      <button v-if="isOwnProfile" @click="updateRecipe" class="updateRecipe">Update Recipe</button>
      <button v-if="isOwnProfile" @click="deleteRecipe" class="deleteRecipe">Delete Recipe</button>
    </div>
  </div>
</template>

<script>
import { jwtDecode } from 'jwt-decode';

export default {
  name: "Recipe",
  data() {
    return {
      recipe: {
        id: "",
        title:"",
        ingredients: [],
        servingSize: 0,
        cookingTime: "",
        cuisineType: "",
        steps: "",
        postedById:""
      },
      postedBy: "",
    };
  },
  computed: {
    isOwnProfile() {
      const token = localStorage.getItem('jwtToken');
      if (token) {
        const decoded = jwtDecode(token);
        return this.recipe.postedById === decoded.userId;
      }
      return false;
    }
  },
  methods: {
    fetchRecipe(id) {
      fetch(`http://localhost:8003/api/recipes/${id}`)
        .then((response) => response.json())
        .then((data) => {
          this.recipe = data;
          this.fetchUserName(data.postedById); // Fetch username after fetching recipe
        })
        .catch((err) => console.log(err.message));
    },
    fetchUserName() {
      fetch(`http://localhost:8000/api/profiles/${this.recipe.postedById}`)
        .then((response) => response.json())
        .then((data) => {
          this.postedBy = data.username; // Store the username in postedBy variable
        })
        .catch((err) => console.log(err.message));
    },
    updateRecipe() {
      const token = localStorage.getItem('jwtToken');
      fetch(`http://localhost:8003/api/recipes/${this.recipe.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(this.recipe),
      })
        .then((response) => {
          console.log(response.data);
          this.$router.push("/recipes");
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteRecipe() {
      const token = localStorage.getItem('jwtToken');
      fetch(`http://localhost:8003/api/recipes/${this.recipe.id}`, {
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${token}`
        }
      })
        .then((response) => {
          if (response.ok) {
            console.log("Recipe deleted successfully");
          } else {
            console.log("Failed to delete recipe");
          }
          this.$router.push("/recipes");
        })
        .catch((error) => {
          console.error("Error deleting recipe:", error);
        });
    },
  },
  mounted() {
    this.fetchRecipe(this.$route.params.id);
  },
};
</script>

<style scoped>
#form {
  max-width: 420px;
  margin: 10px auto;
  background-color: #f0f0f0;
  text-align: left;
  padding: 20px;
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
  margin-top: 10px;
  color: white;
  border-radius: 20px;
}
.post {
  margin: 10px;
}
</style>
